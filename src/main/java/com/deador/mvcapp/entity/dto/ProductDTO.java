package com.deador.mvcapp.entity.dto;

import com.deador.mvcapp.entity.Category;
import lombok.Data;

import javax.persistence.*;

@Data
public class ProductDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long categoryId;
    private Double price;
    private String brand;
    private String model;
    private String operatingSystem;
    private String screenSize;
    private String displayResolution;
    private String matrixType;
    private String processor;
    private String numberOfCores;
    private String battery;
    private String ram;
    private String storageCapacity;
    private String numberOfMainCameras;
    private String mainCameraResolution;
    private String numberOfFrontCameras;
    private String frontCameraResolution;
    private String numberOfSimCards;
    private String connectivity;
    private String bluetooth;
    private String nfc;
    private Double weight;
    private String action;
    private Double priceBeforeAction;
    private Double priceAfterAction;
    private String description;
    private String imageName;
    private Long countOfViews;
}
