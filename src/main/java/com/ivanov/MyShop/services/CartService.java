package com.ivanov.MyShop.services;

import com.ivanov.MyShop.models.Cart;
import com.ivanov.MyShop.repo.CartRepo;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private final CartRepo cartRepo;


    public CartService(CartRepo cartRepo) {
        this.cartRepo = cartRepo;
    }

    public Cart findByPersonId(int person_id) {
        return cartRepo.findByPersonId(person_id);
    }
}
