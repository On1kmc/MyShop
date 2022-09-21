package com.ivanov.MyShop.services;

import com.ivanov.MyShop.models.Market;
import com.ivanov.MyShop.models.Person;
import com.ivanov.MyShop.repo.MarketRepo;
import com.ivanov.MyShop.repo.PersonRepo;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PeopleServiceForValidation {

    private final PersonRepo personRepo;

    private final MarketRepo marketRepo;

    public PeopleServiceForValidation(PersonRepo personRepo, MarketRepo marketRepo) {
        this.personRepo = personRepo;
        this.marketRepo = marketRepo;
    }


    public boolean isExist(String email) {
        Optional<Person> person = personRepo.findByEmail(email);
        Optional<Market> market = marketRepo.findByEmail(email);
        return person.isPresent() || market.isPresent();
    }
}
