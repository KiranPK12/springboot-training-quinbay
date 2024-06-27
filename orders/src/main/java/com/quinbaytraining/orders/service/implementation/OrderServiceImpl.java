package com.quinbaytraining.orders.service.implementation;

import com.quinbaytraining.orders.DTO.OrderDTO;
import com.quinbaytraining.orders.DTO.OrderProductDTO;
import com.quinbaytraining.orders.DTO.ProductDTO;
import com.quinbaytraining.orders.config.KafkaConfig;
import com.quinbaytraining.orders.model.Order;
import com.quinbaytraining.orders.model.Product;
import com.quinbaytraining.orders.repository.OrderRepository;
import com.quinbaytraining.orders.service.OrderService;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private KafkaConfig kafkaConfig;

    @Override
    public List<Product> getProductDetails() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ParameterizedTypeReference<List<Product>> responseType = new ParameterizedTypeReference<List<Product>>() {
        };
        ResponseEntity<List<Product>> response = restTemplate.exchange("http://localhost:8080/product/getAll", HttpMethod.GET, entity, responseType);
        return response.getBody();
    }

    @Override
    public Order addOrder(OrderDTO orderDTO) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);
        List<Product> products = new ArrayList<>();
        Iterator<OrderProductDTO> iterator = orderDTO.getBuyProductWithQuantities().iterator();
        while (iterator.hasNext()) {
            OrderProductDTO orderProductDTO = iterator.next();
            ResponseEntity<Product> response = restTemplate.exchange(
                    "http://localhost:8080/product/getProduct/" + orderProductDTO.getId(),
                    HttpMethod.GET,
                    entity,
                    Product.class);

            Product product = response.getBody();
            if (product == null || product.getProdQuantity() < orderProductDTO.getBuyQuantity()) {
                iterator.remove();
            } else {
                product.setProdQuantity(product.getProdQuantity() - orderProductDTO.getBuyQuantity());
                ProductDTO productDTO = new ProductDTO();
                productDTO.setProdName(product.getProdName());
                productDTO.setProdPrice(product.getProdPrice());
                productDTO.setProdQuantity(product.getProdQuantity());
                productDTO.setSellerId(product.getSeller().getId());
                productDTO.setCategoryName(product.getCategory().getCategoryName());

                HttpEntity<ProductDTO> requestEntity = new HttpEntity<>(productDTO, headers);
                restTemplate.exchange(
                        "http://localhost:8080/product/updateProduct/" + orderProductDTO.getId(),
                        HttpMethod.PUT,
                        requestEntity,
                        Void.class);
                product.setProdQuantity(orderProductDTO.getBuyQuantity());
                products.add(product);
            }
        }
        if (products.isEmpty()) {
            throw new RuntimeException("Insufficient product quantities for the order.");
        } else {
            Order order = new Order();
            order.setCustomerId(orderDTO.getCustomerId());
            order.setCustomerName(orderDTO.getCustomerName());
            order.setProducts(products);
//            kafkaConfig.kafkaTemplate().send("order.service", order.toString());
            return orderRepository.save(order);
        }
    }

    @Override
    public Order getOrderById(String orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order with ID " + orderId + " not found."));
        kafkaConfig.kafkaTemplate().send("order.service", order.toString());
        return order;
    }

    @Override
    public List<Order> getOrdersByCustomerId(String customerId) {
        return orderRepository.findByCustomerId(customerId);
    }
}
