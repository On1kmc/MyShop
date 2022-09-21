package com.ivanov.MyShop.models;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "person")
public class Person  implements Authority {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "email")
    @Email
    @NotEmpty
    private String email;

    @Column(name = "name")
    @NotEmpty
    @Size(max = 30, min = 3)
    private String name;

    @Column(name = "lastname")
    @NotEmpty
    @Size(max = 30, min = 3, message = "Короткая фамилия")
    private String lastName;

    @Column(name = "patronymic")
    private String patronymic;

    @Column(name = "address")
    @Size(min = 10, max = 200)
    private String address;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role;



    @OneToMany(mappedBy = "person")
    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    private List<Order> ordersList;


    @OneToOne(mappedBy = "person")
    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    private Bill bill;

    @OneToOne(mappedBy = "person")
    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    private Cart cart;

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", address='" + address + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public Person() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }
}

