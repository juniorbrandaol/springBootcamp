package com.eblj.catalog.entities.pk;

import com.eblj.catalog.entities.Order;
import com.eblj.catalog.entities.Product;
import jakarta.persistence.*;

import java.io.Serializable;

/*
 CLASSE AUXILIAR
 */
@Embeddable
public class OrderItemPK implements Serializable {
    private static final long serialVersionUID = 1L;
    @ManyToOne()
    @JoinColumn(name="order_id")
    private Order order;
    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;

    public OrderItemPK(){}
    public Order getOrder() {
        return order;
    }
    public void setOrder(Order order) {
        this.order = order;
    }
    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }

}
