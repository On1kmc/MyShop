package com.ivanov.MyShop.controllers;

import com.ivanov.MyShop.models.Authority;
import com.ivanov.MyShop.models.Person;
import com.ivanov.MyShop.security.PersonDetails;
import com.ivanov.MyShop.services.PeopleService;
import com.ivanov.MyShop.services.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
@RequestMapping("/account")
public class AccountController {

    private final RegistrationService registrationService;

    private final PeopleService peopleService;

    @Autowired
    public AccountController(RegistrationService registrationService, PeopleService peopleService) {
        this.registrationService = registrationService;
        this.peopleService = peopleService;
    }


    @GetMapping("/{id}")
    public String showLK(@PathVariable("id") int id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) auth.getPrincipal();
        Authority authority = personDetails.getPerson();
        if (authority.getId() != id || authority.getRole().equals("ROLE_MARKET")) return "404";

        Person person = (Person) authority;

        model.addAttribute("personForUpdate", person);
        model.addAttribute("person", authority);
        return "account";
    }

    @PatchMapping("/{id}")
    public String edit(@ModelAttribute("personForUpdate") @Valid Person person, BindingResult bindingResult,
                       @PathVariable("id") int id,
                       @RequestParam(value = "newpassword", required = false, defaultValue = "0")  String newPass,
                       @RequestParam(value = "v-password", required = false, defaultValue = "0") String confirmPass,
                       Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) auth.getPrincipal();
        Authority authority = personDetails.getPerson();
        if (authority.getId() != id || authority.getRole().equals("ROLE_MARKET")) return "404";

        if (!newPass.equals(confirmPass)) bindingResult.rejectValue("password", "", "Новый пароль не подтвержден");
        if (!peopleService.equalsPass(person.getPassword(), authority.getPassword())) {
            bindingResult.rejectValue("password", "", "Неправильный пароль");
        }
        if (bindingResult.hasErrors()) {
            Person personForUpdate = (Person) authority;
            model.addAttribute("person", personForUpdate);
            return "account";
        }
        if (!newPass.equals("0")) person.setPassword(newPass);

        Person personForUpdate = (Person) authority;
        peopleService.update(person, personForUpdate);

        return "redirect:/account/" + id;
    }
}
