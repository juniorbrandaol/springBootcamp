package com.eblj.catalog.rest.DTO;
import com.eblj.catalog.entities.OrderItem;
import com.eblj.catalog.entities.pk.OrderItemPK;
import jakarta.persistence.EmbeddedId;
import jakarta.validation.constraints.NotBlank;
public class OrderItemDTO {

    @EmbeddedId
    private OrderItemPK id = new OrderItemPK();
    @NotBlank(message = "Campo obrigatório.")
    private Integer quantity;
    @NotBlank(message = "Campo obrigatório.")
    private Double price;
    private Long product;
    public OrderItemDTO(){}
    public Long getProduct() {
        return product;
    }
    public void setProduct(Long product) {
        this.product = product;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    public Double getPrice() {
        return price;
    }

}
