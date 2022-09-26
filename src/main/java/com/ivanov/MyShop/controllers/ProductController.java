package com.ivanov.MyShop.controllers;

import com.ivanov.MyShop.models.Market;
import com.ivanov.MyShop.models.Person;
import com.ivanov.MyShop.models.Product;
import com.ivanov.MyShop.security.PersonDetails;
import com.ivanov.MyShop.services.ProductService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;


    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) auth.getPrincipal();
        model.addAttribute("person", personDetails.getPerson());

        Product product = productService.findById(id);
        model.addAttribute("product2", product);

        File file = new File("upload/product/" + product.getArticle());
        model.addAttribute("files", file.listFiles());

        model.addAttribute("products", productService.getRandomListFromMarket(product.getMarket(), 5));

        return "product/product";
    }

    @GetMapping("/new")
    public String addProduct(@ModelAttribute Product product) {
        return "product/add-new-product";
    }

    @PostMapping("/new")
    public String newProduct(@RequestParam("file") MultipartFile[] files,
                             @ModelAttribute("product") @Valid Product product, BindingResult bindingResult) throws IOException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) auth.getPrincipal();
        product.setMarket((Market) personDetails.getPerson());
        productService.save(product, files);
        return "redirect:/";
    }

    @GetMapping("/{id}/add-to-cart")
    public String inCart(@PathVariable("id") int id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) auth.getPrincipal();
        Person person = (Person) personDetails.getPerson();
        productService.addToCart(person, id);
        return "redirect:/";
    }


}
