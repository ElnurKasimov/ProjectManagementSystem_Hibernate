package model.dao;

import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.Set;

@Entity
@Table(name = "company")
public class CompanyDao {
    private long company_id;
    private String companyName;
    private Rating rating;
    private Set<DeveloperDao> developers;
    private Set<ProjectDao> projects;

    public enum Rating {
        high,
        middle,
        low
    }

    public CompanyDao (String companyName, Rating rating) {
        this.companyName = companyName;
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
    public String getCompanyName() {
        return companyName;
    }

    @Enumerated(EnumType.STRING)
    public Rating getRating() {
        return rating;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy="company")
    @Fetch(FetchMode.SUBSELECT)
    public Set<DeveloperDao> getDevelopers() {
        return developers;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "company")
    @Fetch(FetchMode.SUBSELECT)
    public Set<ProjectDao> getProjects() {
        return projects;
    }

    public void setCompany_id(long company_id) {
        this.company_id = company_id;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public void setDevelopers(Set<DeveloperDao> developers) {
        this.developers = developers;
    }

    public void setProjects(Set<ProjectDao> projects) {
        this.projects = projects;
    }
}

