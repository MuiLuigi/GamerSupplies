package com.starmodestudios.gamersupplies.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.starmodestudios.gamersupplies.model.Product;
import com.starmodestudios.gamersupplies.repository.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public List<Product> findByCategory(String category) {
        return productRepository.findAll().stream()
                .filter(p -> category.equalsIgnoreCase(p.getCategory()))
                .collect(Collectors.toList());
    }
    
    public List<Product> findByMaxPrice(Double maxPrice) {
        return productRepository.findAll().stream()
                .filter(p -> p.getPrice() != null && p.getPrice() <= maxPrice)
                .collect(Collectors.toList());
    }
    
    public List<Product> findByCategoryAndMaxPrice(String category, Double maxPrice) {
        return productRepository.findAll().stream()
                .filter(p -> category.equalsIgnoreCase(p.getCategory()))
                .filter(p -> p.getPrice() != null && p.getPrice() <= maxPrice)
                .collect(Collectors.toList());
    }
}

