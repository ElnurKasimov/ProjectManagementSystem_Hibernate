package model.dao;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "company")
public class CompanyDao {
    private long company_id;
    private String company_name;
    private Rating rating;
    private Set<ProjectDao> projects;

    public enum Rating {
        high,
        middle,
        low
    }

    public CompanyDao (String company_name, Rating rating) {
        this.company_name=company_name;
        this.rating=rating;
    }

    public CompanyDao () {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getCompany_id() {
        return company_id;
    }
    @Column (name = "company_name", length = 100)
    public String getCompany_name() {
        return company_name;
    }
    @Column (name = "rating", length = 100)
    public Rating getRating() {
        return rating;
    }
    @OneToMany(mappedBy = "company")
    public Set<ProjectDao> getProjects() {
        return projects;
    }

    public void setCompany_id(long company_id) {
        this.company_id = company_id;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public void setProjects(Set<ProjectDao> projects) {
        this.projects = projects;
    }
}

