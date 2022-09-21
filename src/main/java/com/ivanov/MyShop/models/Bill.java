package com.ivanov.MyShop.models;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "bill")
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotNull
    @Min(0)
    private double balance;


    @OneToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person person;

}
