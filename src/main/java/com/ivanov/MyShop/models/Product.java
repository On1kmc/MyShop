package com.ivanov.MyShop.models;

import javax.persistence.*;
import javax.transaction.TransactionScoped;
import javax.validation.constraints.*;
import java.io.File;
import java.text.DecimalFormat;
import java.util.List;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @NotNull
    private int article;

    @NotEmpty
    @Size(min = 3, max = 30)
    private String name;

    @NotEmpty
    @Size(min = 10, max = 1000)
    private String description;

    @NotNull
    @Min(1)
    private double price;

    @ManyToOne
    @JoinColumn(name = "market_id", referencedColumnName = "id")
    private Market market;


    @ManyToMany(mappedBy = "products")
    private List<Cart> carts;


    @Transient
    private List<File> files;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getArticle() {
        return article;
    }

    public void setArticle(int article) {
        this.article = article;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Market getMarket() {
        return market;
    }

    public void setMarket(Market market) {
        this.market = market;
    }

    public List<Cart> getCarts() {
        return carts;
    }

    public void setCarts(List<Cart> carts) {
        this.carts = carts;
    }
}
