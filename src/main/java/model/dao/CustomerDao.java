package model.dao;


import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table (name = "customer")
public class CustomerDao {
    private long customer_id;
    private String customer_name;
    private Reputation reputation;
    private Set<ProjectDao> projects;

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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getCustomer_id() {
        return customer_id;
    }
    @Column(name = "customer_name", length = 100)
    public String getCustomer_name() {
        return customer_name;
    }
    @Column(name = "reputation", length = 100)
    public Reputation getReputation() {
        return reputation;
    }
    @OneToMany(mappedBy = "customer")
    public Set<ProjectDao> getProjects() {
        return projects;
    }

    public void setCustomer_id(long customer_id) {
        this.customer_id = customer_id;
    }
}
