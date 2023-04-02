package com.eblj.catalog.rest.DTO;

import com.eblj.catalog.entities.Order;
import com.eblj.catalog.entities.enuns.OrderStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;
public class OrderDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Instant moment;
    @NotNull(message = "Informe o código do cliente")
    private Long client;

    private String cpf;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    private Double orderAmount;
    private List<OrderItemDTO> items;

    public OrderDTO(){}

    public OrderDTO(Long id, Long client,String cpf,OrderStatus status, List<OrderItemDTO> items,Instant moment,Double orderAmount) {
        this.id = id;
        this.client = client;
        this.cpf = cpf;
        this.moment = moment;
        this.orderAmount = orderAmount;
        this.status = status;
        this.items = items;
    }

    public OrderDTO(Order order) {
        client = order.getClient().getId();
        moment = order.getMoment();
        orderAmount = order.getOrderAmount();
        status = order.getOrderStatus();
        id = order.getId();
        cpf = order.getClient().getCpf();
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getClient() {
        return client;
    }

    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    public Instant getMoment() {
        return moment;
    }
    public void setClient(Long client) {
        this.client = client;
    }
    public OrderStatus getStatus() {
        return status;
    }
    public void setStatus(OrderStatus status) {
        this.status = status;
    }
    public void setCliente(Long client) {
        this.client = client;
    }
    public List<OrderItemDTO> getItems() {
        return items;
    }
    public void setMoment(Instant moment) {
        this.moment = moment;
    }
    public Double getOrderAmount() {
        return orderAmount;
    }
    public void setOrderAmount(Double orderAmount) {
        this.orderAmount = orderAmount;
    }
    public void setItems(List<OrderItemDTO> items) {
        this.items = items;
    }
    @Override
    public String toString() {
        return "OrderDTO{" +
                "moment=" + moment +
                ", client=" + client +
                ", orderAmount=" + orderAmount +
                ", items=" + items +
                '}';
    }
}
