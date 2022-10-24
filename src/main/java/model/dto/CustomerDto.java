package model.dto;

import lombok.Data;

@Data
public class CustomerDto {
    private long customer_id;
    private String customer_name;
    private Reputation reputation;

    public enum Reputation {
        respectable,
        trustworthy,
        insolvent
    }

    public CustomerDto (String customer_name, Reputation reputation) {
        this.customer_name = customer_name;
        this.reputation = reputation;
    }

    public CustomerDto () {
    }

}

