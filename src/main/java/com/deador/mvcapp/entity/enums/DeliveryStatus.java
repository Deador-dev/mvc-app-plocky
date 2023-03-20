package com.deador.mvcapp.entity.enums;

public enum DeliveryStatus {
    PREPARATION(1, "Preparation"),
    IN_PROGRESS(2, "In progress"),
    DELIVERED(3, "Delivered"),
    ERROR(4, "Error");
    private final Integer id;
    private final String name;

    DeliveryStatus(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }
    public String getName() {
        return name;
    }


}
