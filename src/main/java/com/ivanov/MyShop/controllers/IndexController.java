package com.ivanov.MyShop.controllers;

import com.ivanov.MyShop.models.Product;
import com.ivanov.MyShop.security.PersonDetails;
import com.ivanov.MyShop.services.MarketService;
import com.ivanov.MyShop.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class IndexController {

    private final ProductService productService;

    private final MarketService marketService;



    @Autowired
    public IndexController(ProductService productService, MarketService marketService) {
        this.productService = productService;
        this.marketService = marketService;
    }


    @GetMapping()
    public String index(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) auth.getPrincipal();
        model.addAttribute("person", personDetails.getPerson());
        model.addAttribute("markets", marketService.getRandomList(2));
        model.addAttribute("products", productService.getRandomList(4));

        return "index";
    }


}
