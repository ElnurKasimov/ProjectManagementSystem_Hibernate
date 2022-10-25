package model.dto;

import lombok.Data;

import java.sql.Date;

@Data
public class ProjectDto {
    private long projectId;
    private String projectName;
    private CompanyDto company;
    private CustomerDto customer;
    private  int cost;
    private Date start_date;

    public ProjectDto (String projectName, CompanyDto company, CustomerDto customer, int cost,
                       Date start_date) {
        this.projectName = projectName;
        this.company = company;
        this.customer = customer;
        this.cost = cost;
        this.start_date = start_date;
    }

    public ProjectDto (long projectId, String projectName) {
        this.projectId = projectId;
        this.projectName = projectName;
    }
    public ProjectDto () {
    }
}
