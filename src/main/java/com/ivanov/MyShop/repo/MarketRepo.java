package com.ivanov.MyShop.repo;

import com.ivanov.MyShop.models.Market;
import com.ivanov.MyShop.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.yaml.snakeyaml.error.Mark;

import java.util.Optional;

public interface MarketRepo extends JpaRepository<Market, Integer> {

    Optional<Market> findByEmail(String email);

    Optional<Market> findById(int id);
}
