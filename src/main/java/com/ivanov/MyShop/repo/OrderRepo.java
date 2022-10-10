package com.ivanov.MyShop.repo;

import com.ivanov.MyShop.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepo extends JpaRepository<Order, Integer> {

    List<Order> findByPersonId(int id);
}
