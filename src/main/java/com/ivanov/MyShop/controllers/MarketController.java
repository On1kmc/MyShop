package com.ivanov.MyShop.controllers;

import com.ivanov.MyShop.models.Authority;
import com.ivanov.MyShop.models.Market;
import com.ivanov.MyShop.security.PersonDetails;
import com.ivanov.MyShop.services.MarketService;
import com.ivanov.MyShop.services.PasswordService;
import com.ivanov.MyShop.services.PeopleService;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@Controller
@RequestMapping("/market")
public class MarketController {

    private final MarketService marketService;

    private final PeopleService peopleService;

    private final PasswordService passwordService;

    @Autowired
    public MarketController(MarketService marketService, PeopleService peopleService, PasswordService passwordService) {
        this.marketService = marketService;
        this.peopleService = peopleService;
        this.passwordService = passwordService;
    }

    @GetMapping("/{id}")
    public String show(Model model, @PathVariable("id") int id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) auth.getPrincipal();
        model.addAttribute("person", personDetails.getPerson());
        model.addAttribute("products", marketService.findById(id).getProducts());
        model.addAttribute("market", marketService.findById(id));
        return "market/market-page";
    }

    @GetMapping("/{id}/lk")
    public String lk(Model model, @PathVariable("id") int id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) auth.getPrincipal();
        Authority authority = personDetails.getPerson();

        if (authority.getId() != id || !authority.getRole().equals("ROLE_MARKET")) return "404";

        Market market = (Market) authority;
        model.addAttribute("person", market);
        model.addAttribute("marketForUpdate", market);
        return "market/lk";
    }

    @PatchMapping("/{id}/lk")
    public String edit(@ModelAttribute("marketForUpdate") @Valid Market market, BindingResult bindingResult,
                       @PathVariable("id") int id,
                       @RequestParam(value = "newpassword", required = false, defaultValue = "0")  String newPass,
                       @RequestParam(value = "v-password", required = false, defaultValue = "0") String confirmPass,
                       Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) auth.getPrincipal();
        Authority authority = personDetails.getPerson();
        if (authority.getId() != id) return "404";
        if (!newPass.equals(confirmPass)) bindingResult.rejectValue("password", "", "Новый пароль не подтвержден");
        if (!passwordService.equalsPass(market.getPassword(), authority.getPassword())) {
            bindingResult.rejectValue("password", "", "Неправильный пароль");
        }
        if (bindingResult.hasErrors()) {
            return "market/lk";
        }
        if (!newPass.equals("0")) market.setPassword(newPass);
        Market marketForUpdate = (Market) authority;
        marketService.update(market, marketForUpdate);

        return "redirect:/market/" + id + "/lk";
    }

    @PutMapping("/{id}/lk")
    public String changePhoto(@PathVariable("id") int id,
                              @RequestParam(value = "photo", required = false) MultipartFile photo,
                              @RequestParam(value = "banner", required = false) MultipartFile banner) {

        if (!photo.isEmpty()) marketService.savePhoto(photo, id);
        if (!banner.isEmpty()) marketService.saveBanner(banner, id);

        return "redirect:/market/" + id + "/lk";
    }

}
