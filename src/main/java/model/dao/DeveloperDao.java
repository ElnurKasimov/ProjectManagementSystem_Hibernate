package model.dao;

import lombok.Data;

@Data
public class DeveloperDao {
    private long developer_id;
    private String lastName;
    private String firstName;
    private int age;
    private CompanyDao companyDao;
    private int salary;

    public DeveloperDao (String lastName, String firstName, int age, CompanyDao companyDao, int salary) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.age = age;
        this.companyDao = companyDao;
        this.salary=salary;

    }

     public DeveloperDao () {
     }

}

