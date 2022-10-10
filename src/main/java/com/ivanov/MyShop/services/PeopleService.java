package com.ivanov.MyShop.services;

import com.ivanov.MyShop.models.Order;
import com.ivanov.MyShop.models.Person;
import com.ivanov.MyShop.repo.OrderRepo;
import com.ivanov.MyShop.repo.PersonRepo;
import org.hibernate.Hibernate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PeopleService {

    private final PersonRepo personRepo;

    private final PasswordEncoder passwordEncoder;

    private final OrderRepo orderRepo;


    public PeopleService(PersonRepo personRepo, PasswordEncoder passwordEncoder, OrderRepo orderRepo) {
        this.personRepo = personRepo;
        this.passwordEncoder = passwordEncoder;
        this.orderRepo = orderRepo;
    }

    @Transactional
    public void update(Person person, Person personForUpdate) {
        personForUpdate.setName(person.getName());
        personForUpdate.setLastName(person.getLastName());
        personForUpdate.setPatronymic(person.getPatronymic());
        personForUpdate.setPassword(passwordEncoder.encode(person.getPassword()));
        personForUpdate.setAddress(person.getAddress());
        personRepo.save(personForUpdate);
    }

}
