package model.dao;

import jakarta.persistence.*;

import java.util.Set;


public class DeveloperDao {
    private long developer_id;
    private String lastName;
    private String firstName;
    private int age;
    private CompanyDao companyDao;
    private int salary;
    private Set<ProjectDao> projects;

    public DeveloperDao (String lastName, String firstName, int age, CompanyDao companyDao, int salary) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.age = age;
        this.companyDao = companyDao;
        this.salary=salary;

    }

     public DeveloperDao () {
     }
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    public long getDeveloper_id() {
        return developer_id;
    }

    @Column(name = "lastname", length = 200)
    public String getLastName() {
        return lastName;
    }
    @Column(name = "firstname", length = 200)
    public String getFirstName() {
        return firstName;
    }
    @Column(name = "age")
    public int getAge() {
        return age;
    }
    @OneToOne
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    public CompanyDao getCompanyDao() {
        return companyDao;
    }
    @Column (name = "salary")
    public int getSalary() {
        return salary;
    }

    public Set<ProjectDao> getProjects() {
        return projects;
    }

    public void setDeveloper_id(long developer_id) {
        this.developer_id = developer_id;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setCompanyDao(CompanyDao companyDao) {
        this.companyDao = companyDao;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public void setProjects(Set<ProjectDao> projects) {
        this.projects = projects;
    }
}

