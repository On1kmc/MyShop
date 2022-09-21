package com.ivanov.MyShop.repo;

import com.ivanov.MyShop.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {

    Optional<Product> findByArticle(int article);


    List<Product> findAllByMarket_id(int id);
}
