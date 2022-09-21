package com.ivanov.MyShop.services;

import com.ivanov.MyShop.models.Market;
import com.ivanov.MyShop.models.Person;
import com.ivanov.MyShop.repo.MarketRepo;
import com.ivanov.MyShop.repo.PersonRepo;
import com.ivanov.MyShop.security.PersonDetails;
import org.hibernate.Hibernate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PersonDetailsService implements UserDetailsService {

    private final PersonRepo personRepo;

    private final MarketRepo marketRepo;

    public PersonDetailsService(PersonRepo personRepo, MarketRepo marketRepo) {
        this.personRepo = personRepo;
        this.marketRepo = marketRepo;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Person> person = personRepo.findByEmail(email);
        if (person.isEmpty()) {
            Optional<Market> market = marketRepo.findByEmail(email);
            if (market.isEmpty())
                throw new UsernameNotFoundException("Not found!");
            return new PersonDetails(market.get());
        }
        Hibernate.initialize(person.get().getCart().getProducts());
        return new PersonDetails(person.get());
    }
}
