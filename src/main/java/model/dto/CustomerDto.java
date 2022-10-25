package model.dto;

import lombok.Data;

@Data
public class CustomerDto {
    private long customerId;
    private String customerName;
    private Reputation reputation;

    public enum Reputation {
        respectable,
        trustworthy,
        insolvent
    }

    public CustomerDto (String customerName, Reputation reputation) {
        this.customerName = customerName;
        this.reputation = reputation;
    }

    public CustomerDto () {
    }

}

