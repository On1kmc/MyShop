package com.ivanov.MyShop.controllers;

import com.ivanov.MyShop.models.Order;
import com.ivanov.MyShop.models.Person;
import com.ivanov.MyShop.security.PersonDetails;
import com.ivanov.MyShop.services.CartService;
import com.ivanov.MyShop.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    private final CartService cartService;

    @Autowired
    public OrderController(OrderService orderService, CartService cartService) {
        this.orderService = orderService;
        this.cartService = cartService;
    }


    @PostMapping("/new")
    public String newOrder() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) auth.getPrincipal();
        Person person = (Person) personDetails.getPerson();
        orderService.saveOrder(person);
        return "redirect:/cart";
    }

    @DeleteMapping("/{id}")
    public String deleteOrder(@PathVariable("id") int id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) auth.getPrincipal();
        Person person = (Person) personDetails.getPerson();
        orderService.deleteOrder(id, person);
        return "redirect:/account";
    }


}
