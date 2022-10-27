package model.dto;

import model.dao.CompanyDao;
import model.dao.CustomerDao;
import model.dao.DeveloperDao;

import java.sql.Date;
import java.util.Set;

public class ProjectDto {
    private long project_id;
    private String projectName;
    private  int cost;
    private Date startDate;
    private CompanyDto company;
    private CustomerDto customer;
    private Set<DeveloperDto> developers;

    public ProjectDto (String projectName, CompanyDto company, CustomerDto customer, int cost, Date startDate) {
        this.projectName = projectName;
        this.company = company;
        this.customer = customer;
        this.cost = cost;
        this.startDate = startDate;
    }

    public ProjectDto (long project_id, String projectName) {
        this.project_id = project_id;
        this.projectName = projectName;
    }
    public ProjectDto () {
    }

    public ProjectDto(long project_id, String projectName, CompanyDto company, CustomerDto customer, int cost, Date startDate) {
        this.project_id = project_id;
        this.projectName = projectName;
        this.company = company;
        this.customer = customer;
        this.cost = cost;
        this.startDate = startDate;
    }

    public ProjectDto(long project_id, String projectName, int cost, Date startDate, CompanyDto company, CustomerDto customer, Set<DeveloperDto> developers) {
        this.project_id = project_id;
        this.projectName = projectName;
        this.cost = cost;
        this.startDate = startDate;
        this.company = company;
        this.customer = customer;
        this.developers = developers;
    }

    public Set<DeveloperDto> getDevelopers() {
        return developers;
    }

    public void setDevelopers(Set<DeveloperDto> developers) {
        this.developers = developers;
    }

    public long getProject_id() {
        return project_id;
    }

    public void setProject_id(long project_id) {
        this.project_id = project_id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public CompanyDto getCompany() {
        return company;
    }

    public void setCompany(CompanyDto company) {
        this.company = company;
    }

    public CustomerDto getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDto customer) {
        this.customer = customer;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
}
