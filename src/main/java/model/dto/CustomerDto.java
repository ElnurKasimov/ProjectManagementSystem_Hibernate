package model.dto;

public class CustomerDto {
    private long customer_id;
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

    public CustomerDto(long customer_id, String customerName, Reputation reputation) {
        this.customer_id = customer_id;
        this.customerName = customerName;
        this.reputation = reputation;
    }

    public long getCustomer_id() {
        return customer_id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public Reputation getReputation() {
        return reputation;
    }

    public void setCustomer_id(long customer_id) {
        this.customer_id = customer_id;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setReputation(Reputation reputation) {
        this.reputation = reputation;
    }
}

