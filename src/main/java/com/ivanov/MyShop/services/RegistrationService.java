package com.ivanov.MyShop.services;

import com.ivanov.MyShop.models.Cart;
import com.ivanov.MyShop.models.Market;
import com.ivanov.MyShop.models.Person;
import com.ivanov.MyShop.repo.CartRepo;
import com.ivanov.MyShop.repo.MarketRepo;
import com.ivanov.MyShop.repo.PersonRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistrationService {

    private final PersonRepo personRepo;

    private final MarketRepo marketRepo;

    private final CartRepo cartRepo;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationService(PersonRepo personRepo, MarketRepo marketRepo, CartRepo cartRepo, PasswordEncoder passwordEncoder) {
        this.personRepo = personRepo;
        this.marketRepo = marketRepo;
        this.cartRepo = cartRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void register(Person person) {
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        Cart cart = new Cart();
        cart.setPerson(person);
        person.setCart(cart);
        cartRepo.save(cart);
        personRepo.save(person);
    }

    @Transactional
    public void register(Market market) {

        market.setPassword(passwordEncoder.encode(market.getPassword()));
        marketRepo.save(market);
    }
}
