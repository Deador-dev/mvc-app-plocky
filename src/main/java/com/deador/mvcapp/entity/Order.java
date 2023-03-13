package com.deador.mvcapp.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> items;

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String townCity;
    private String address;
    private String postcode;
    private String email;
    private String additionalInformation;
    private String deliveryStatus;
    private Double totalAmount;
}
