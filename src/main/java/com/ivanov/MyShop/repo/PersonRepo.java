package com.ivanov.MyShop.repo;

import com.ivanov.MyShop.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonRepo extends JpaRepository<Person, Integer> {

    Optional<Person> findByEmail(String email);

}
