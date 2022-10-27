package model.dao;


import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.Set;

@Entity
@Table (name = "customer")
public class CustomerDao {
    private long customer_id;
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
    public long getCustomer_id() {
        return customer_id;
    }

    @Column(name = "customer_name", length = 100)
    public String getCustomerName() {
        return customerName;
    }

    @Enumerated(EnumType.STRING)
    public Reputation getReputation() {
        return reputation;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
    @Fetch(FetchMode.SELECT)
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

    public void setCustomer_id(long customer_id) {
        this.customer_id = customer_id;
    }

}
