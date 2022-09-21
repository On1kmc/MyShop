package com.ivanov.MyShop.services;

import com.ivanov.MyShop.models.Authority;
import com.ivanov.MyShop.models.Person;
import com.ivanov.MyShop.repo.PersonRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PeopleService {

    private final PersonRepo personRepo;

    private final PasswordEncoder passwordEncoder;


    public PeopleService(PersonRepo personRepo, PasswordEncoder passwordEncoder) {
        this.personRepo = personRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public void update(Person person, Person personForUpdate) {
        personForUpdate.setName(person.getName());
        personForUpdate.setLastName(person.getLastName());
        personForUpdate.setPatronymic(person.getPatronymic());
        personForUpdate.setPassword(passwordEncoder.encode(person.getPassword()));
        personForUpdate.setAddress(person.getAddress());
        personRepo.save(personForUpdate);
    }

    public boolean equalsPass(String passForCheck, String currentPass) {
        return passwordEncoder.matches(passForCheck, currentPass);
    }
}
