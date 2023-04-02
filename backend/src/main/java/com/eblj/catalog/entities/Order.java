package com.eblj.catalog.entities;

import com.eblj.catalog.entities.enuns.OrderStatus;
import com.eblj.catalog.rest.DTO.InformationOrderDTO;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.*;

@Entity
@Table(name = "tb_order")
public class Order implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double orderAmount;
  //  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd'T'HH:mm:ss'Z'", timezone = "GMT")
    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private Instant moment;
    @ManyToOne
    @JoinColumn(name = "client_id")
    private User client;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @OneToMany(mappedBy = "id.order")
    private List<OrderItem> items = new ArrayList<>();

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private Payment payment;
    public Order() {
    }
    public Order(Long id, Instant moment, OrderStatus status,List<OrderItem> items, User client,Payment payment,Double orderAmount) {
        this.id = id;
        this.moment = moment;
        this.items= items;
        this.status = status;
        this.client = client;
        this.orderAmount = orderAmount;
        this.payment= payment;
    }

    public Order(InformationOrderDTO obj) {
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Instant getMoment() {
        return moment;
    }
    public void setMoment(Instant moment) {
        this.moment = moment;
    }
    public OrderStatus getOrderStatus() {
        return status;
    }

    public void setOrderStatus(OrderStatus status) {
        if (status != null) {
            this.status = OrderStatus.valueOf(status.getCode());
        }
    }
    public User getClient() {
        return client;
    }
    public void setClient(User client) {
        this.client = client;
    }
    public Payment getPayment() {
        return payment;
    }
    public void setPayment(Payment payment) {
        this.payment = payment;
    }
    public OrderStatus getStatus() {
        return status;
    }
    public void setStatus(OrderStatus status) {
        this.status = status;
    }
    public List<OrderItem> getItems(){
       return items;
    }
    public void setItems(List<OrderItem> items) {
        this.items = items;
    }
    public Double getOrderAmount() {
        return orderAmount;
    }
    public void setOrderAmount(Double orderAmount) {
        this.orderAmount = orderAmount;
    }
    public Double getTotal() {
        double sum =0.0;
        for(OrderItem x : items) {
            sum += x.getSubTotal();
        }
        return sum;
    }
    public List<Product> getProduct(){
        return items.stream().map(obj -> obj.getProduct()).toList();
    }
    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderAmount=" + orderAmount +
                ", moment=" + moment +
                ", client=" + client +
                ", status=" + status +
                ", items=" + items +
                ", payment=" + payment +
                '}';
    }
}
