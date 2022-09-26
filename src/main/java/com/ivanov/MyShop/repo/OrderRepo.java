package com.ivanov.MyShop.repo;

import com.ivanov.MyShop.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<Order, Integer> {

}
