package com.eblj.catalog.rest.DTO;

import com.eblj.catalog.entities.Order;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;

import java.io.Serializable;
import java.time.Instant;

public class PaymentDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;

    @PastOrPresent(message = "Data inválida.")
    @NotBlank(message = "Campo obrigatório.")
    private Instant momnet;
    @NotBlank(message = "Campo obrigatório.")
    private Order order;

    @NotBlank(message = "Campo obrigatório.")
    private Double totalValue;

    public PaymentDTO(){}

    public PaymentDTO(Long id, Instant momnet, Order order,Double totalValue) {
        this.id = id;
        this.momnet = momnet;
        this.order = order;
        this.totalValue= totalValue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(Double totalValue) {
        this.totalValue = totalValue;
    }

    public Instant getMomnet() {
        return momnet;
    }

    public void setMomnet(Instant momnet) {
        this.momnet = momnet;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
