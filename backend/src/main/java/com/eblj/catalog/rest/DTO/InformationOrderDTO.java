package com.eblj.catalog.rest.DTO;
import com.eblj.catalog.entities.Order;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
public class InformationOrderDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private  Long code;
    private String cpf;
    private String clientName;
    private Double totalOrder;
    private String dateOrder ;
    private String status;
    private List<InformationItemOrderDTO> items =new ArrayList<>(); ;

    public InformationOrderDTO(){}

    public InformationOrderDTO(Long code, String cpf, String clientName, Double totalOrder, String dateOrder, List<InformationItemOrderDTO> items,String status) {
        this.code = code;
        this.cpf = cpf;
        this.clientName = clientName;
        this.totalOrder = totalOrder;
        this.dateOrder = dateOrder;
        this.items = items;
        this.status = status;
    }
    public InformationOrderDTO(Order entity){
       entity= entity;
    }
    public String getDateOrder() {
        return dateOrder;
    }

    public void setDateOrder(String dateOrder) {
        this.dateOrder = dateOrder;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Double getTotalOrder() {
        return totalOrder;
    }

    public void setTotalOrder(Double totalOrder) {
        this.totalOrder = totalOrder;
    }

    public List<InformationItemOrderDTO> getItems() {
        return items;
    }

    public void setItems(List<InformationItemOrderDTO> items) {
       this.items = items;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
