package com.eblj.catalog.rest.DTO;

public class UpdateStatusOrderDTO {

    private String newStatus;

    public UpdateStatusOrderDTO(){}

    public String getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(String novoStatus) {
        this.newStatus = novoStatus;
    }
}
