package model.dao;


import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table (name = "customer")
public class CustomerDao {
    private long customerId;
    private String customerName;
    private Reputation reputation;
    private Set<ProjectDao> projects;

    public enum Reputation {
        respectable,
        trustworthy,
        insolvent
    }

    public CustomerDao (String customerName, Reputation reputation) {
        this.customerName = customerName;
        this.reputation = reputation;
    }

    public CustomerDao () {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getCustomerId() {
        return customerId;
    }

    @Column(name = "customer_name", length = 100)
    public String getCustomerName() {
        return customerName;
    }

    @Column(name = "reputation", length = 100)
    public Reputation getReputation() {
        return reputation;
    }

    @OneToMany(mappedBy = "customer")
    public Set<ProjectDao> getProjects() {
        return projects;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setReputation(Reputation reputation) {
        this.reputation = reputation;
    }

    public void setProjects(Set<ProjectDao> projects) {
        this.projects = projects;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

}
