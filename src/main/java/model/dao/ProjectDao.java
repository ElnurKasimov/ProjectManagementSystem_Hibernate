package model.dao;

import jakarta.persistence.*;
import java.sql.Date;
import java.util.Set;

@Entity
@Table(name = "project")
public class ProjectDao {
    private long project_id;
    private String projectName;
    private int cost;
    private Date startDate;
    private CompanyDao company;
    private CustomerDao customer;
    private Set<DeveloperDao> developers;


    public ProjectDao(String projectName, CompanyDao company, CustomerDao customer, int cost,
                      Date startDate) {
        this.projectName = projectName;
        this.company = company;
        this.customer = customer;
        this.cost = cost;
        this.startDate = startDate;
    }

    public ProjectDao() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getProject_id() {
        return project_id;
    }
    @Column (name = "project_name", length = 100)
    public String getProjectName() {
        return projectName;
    }
    @Column(name = "cost")
    public int getCost() {
        return cost;
    }
    @Column(name = "start_date", length = 50)
    public Date getStartDate() {
        return startDate;
    }
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    public CompanyDao getCompany() {
        return company;
    }
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    public CustomerDao getCustomer() {
        return customer;
    }
    @ManyToMany(mappedBy = "projects", fetch = FetchType.LAZY)
    public Set<DeveloperDao> getDevelopers() {
        return developers;
    }

    public void setProject_id(long project_id) {
        this.project_id = project_id;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setCompany(CompanyDao company) {
        this.company = company;
    }

    public void setCustomer(CustomerDao customer) {
        this.customer = customer;
    }

    public void setDevelopers(Set<DeveloperDao> developers) {
        this.developers = developers;
    }

}

