package com.microservice.jsontopdfsb.model;

import lombok.Data;

@Data
public class Invoice {
    private Long id;
    private String customerName;
    private String customerAddress;
    private String invoiceNumber;
    private Double amount;
}