package com.eblj.catalog.rest.DTO;

import java.io.Serializable;

public class InformationItemOrderDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String productName;
    private Double unitPrice;
    private Integer quantity;
    public InformationItemOrderDTO(){}
    public InformationItemOrderDTO(String productName, Double unitPrice, Integer quantity) {
        this.productName = productName;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }


}
