package com.ivanov.MyShop.controllers;

import com.ivanov.MyShop.models.Market;
import com.ivanov.MyShop.models.Person;
import com.ivanov.MyShop.services.MarketService;
import com.ivanov.MyShop.services.RegistrationService;
import com.ivanov.MyShop.util.MarketValidator;
import com.ivanov.MyShop.util.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final PersonValidator personValidator;

    private final MarketValidator marketValidator;

    private final RegistrationService registrationService;

    private final MarketService marketService;


    @Autowired
    public AuthController(PersonValidator personValidator, MarketValidator marketValidator, RegistrationService registrationService, MarketService marketService) {
        this.personValidator = personValidator;
        this.marketValidator = marketValidator;
        this.registrationService = registrationService;
        this.marketService = marketService;
    }


    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("person") Person person) {
        return "auth/register";
    }


    @PostMapping("/registration")
    public String confirmRegistration(@ModelAttribute("person") @Valid Person person,
                                      BindingResult bindingResult, @RequestParam("pass") String password) {
        if (!person.getPassword().equals(password)) {
            bindingResult.rejectValue("password", "","Пароли не совпадают!");
            return "/auth/register";
        }
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) return "/auth/register";
        person.setRole("ROLE_USER");
        registrationService.register(person);
        return "redirect:/auth/login";
    }

    @GetMapping("/registration-market")
    public String regMarketPage(@ModelAttribute("market") Market market) {
        return "auth/register-market";
    }


    @PostMapping("/registration-market")
    public String confirmRegMarket(@ModelAttribute("market") @Valid Market market,
                                   BindingResult bindingResult, @RequestParam("pass") String password,
                                   @RequestParam("file") MultipartFile file) {
        if (!market.getPassword().equals(password)) {
            bindingResult.rejectValue("password", "","Пароли не совпадают!");
            return "/auth/register-market";
        }

        if (file.isEmpty()) {
            bindingResult.rejectValue("name", "", "Загрузите фото");
            return "/auth/register-market";
        }

        marketValidator.validate(market, bindingResult);
        if (bindingResult.hasErrors()) return "/auth/register-market";
        market.setRole("ROLE_MARKET");
        registrationService.register(market);
        marketService.savePhoto(file, market.getId());
        return "redirect:/auth/login";
    }

}
