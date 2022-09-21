package com.ivanov.MyShop.services;

import com.ivanov.MyShop.models.Product;
import com.ivanov.MyShop.repo.ProductRepo;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductServiceForValidation {

    private final ProductRepo productRepo;


    public ProductServiceForValidation(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    public boolean isExist(int article) {
        return productRepo.findByArticle(article).isPresent();
    }
}
