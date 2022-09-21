package com.ivanov.MyShop.repo;

import com.ivanov.MyShop.models.Cart;
import com.ivanov.MyShop.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepo extends JpaRepository<Cart, Integer> {

    Cart findByPersonId(int id);
}
