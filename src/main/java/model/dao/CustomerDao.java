package model.dao;

import lombok.Data;

@Data
public class CustomerDao {
    private long customer_id;
    private String customer_name;
    private Reputation reputation;

    public enum Reputation {
        respectable,
        trustworthy,
        insolvent
    }

    public CustomerDao (String customer_name, Reputation reputation) {
        this.customer_name = customer_name;
        this.reputation = reputation;
    }

    public CustomerDao () {
    }
}
