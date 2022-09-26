package com.ivanov.MyShop.services;

import com.ivanov.MyShop.models.Order;
import com.ivanov.MyShop.models.Person;
import com.ivanov.MyShop.models.Product;
import com.ivanov.MyShop.repo.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepo orderRepo;

    @Autowired
    public OrderService(OrderRepo orderRepo) {
        this.orderRepo = orderRepo;
    }


    @Transactional
    public void saveOrder(Person person) {
        Order order = new Order();
        order.setOrderProducts(person.getCart().getProducts());
        order.setPerson(person);
        order.setDate(new Date());
        orderRepo.save(order);
        person.getOrdersList().add(order);
        person.getCart().setProducts(Collections.emptyList());
    }

    @Transactional
    public void deleteOrder(int id, Person person) {
        Order orderForDelete = orderRepo.findById(id).orElse(null);
        List<Order> orderList = person.getOrdersList();
        orderList.remove(orderForDelete);
        person.setOrdersList(orderList);
        orderRepo.delete(orderForDelete);
    }
}
