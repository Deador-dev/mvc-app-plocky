package com.deador.mvcapp.entity.dto;

import com.deador.mvcapp.entity.User;
import com.deador.mvcapp.entity.dto.marker.Convertible;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
public class OrderDTO implements Convertible {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private User userId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String address;
    private String postcode;
    private String townCity;
    private String additionalInformation;
    private String deliveryStatus;

}
