package model.dto;

import lombok.Data;
import model.dao.ProjectDao;

import java.util.Set;

@Data
public class CompanyDto {
    private long company_id;
    private String company_name;
    private Rating rating;
    private Set<ProjectDao> projects;
    public enum Rating {
        high,
        middle,
        low
    }

    public CompanyDto (String company_name, Rating rating) {
        this.company_name=company_name;
        this.rating=rating;
    }

    public CompanyDto (String company_name) {
        this.company_name=company_name;
    }
    public CompanyDto () {
    }

    public long getCompany_id() {
        return company_id;
    }

    public Rating getRating() {
        return rating;
    }

    public Set<ProjectDao> getProjects() {
        return projects;
    }

    public String getCompany_name() {
        return company_name;
    }

}

