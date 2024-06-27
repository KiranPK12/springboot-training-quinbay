package com.quinbaytraining.inventory.service.implementation;

import com.quinbaytraining.inventory.DTO.SellerDTO;
import com.quinbaytraining.inventory.model.Seller;
import com.quinbaytraining.inventory.repository.SellerRepository;
import com.quinbaytraining.inventory.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    private SellerRepository sellerRepository;

    @Override
    public Seller createSeller(SellerDTO sellerDTO) {
        Seller seller = Seller.builder()
                .sellerName(sellerDTO.getSellerName())
                .sellerEmail(sellerDTO.getSellerEmail())
                .sellerPhoneNumber(sellerDTO.getSellerPhoneNumber())
                .sellerStreet(sellerDTO.getSellerStreet())
                .sellerState(sellerDTO.getSellerState())
                .sellerCity(sellerDTO.getSellerCity())
                .sellerZipCode(sellerDTO.getSellerZipCode())
                .build();
        return sellerRepository.save(seller);
    }

    @Override
    public List<Seller> getAllSellers() {
        return sellerRepository.findAll();
    }

    @Override
    public Seller getSellerById(Long id) {
        Optional<Seller> optionalSeller = sellerRepository.findById(id);
        return optionalSeller.orElse(null);
    }

    @Override
    public Seller updateSeller(Long id, SellerDTO sellerDTO) {
        Optional<Seller> optionalSeller = sellerRepository.findById(id);
        if (optionalSeller.isPresent()) {
            Seller seller = optionalSeller.get();
            seller.setId(id);
            seller.setSellerName(sellerDTO.getSellerName());
            seller.setSellerEmail(sellerDTO.getSellerEmail());
            seller.setSellerPhoneNumber(sellerDTO.getSellerPhoneNumber());
            seller.setSellerStreet(sellerDTO.getSellerStreet());
            seller.setSellerCity(sellerDTO.getSellerCity());
            seller.setSellerState(sellerDTO.getSellerState());
            seller.setSellerZipCode(sellerDTO.getSellerZipCode());
            return sellerRepository.save(seller);
        }
        return null;
    }

    @Override
    public boolean deleteSeller(Long id) {
        if (sellerRepository.existsById(id)) {
            sellerRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
