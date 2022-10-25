package model.dto;

import lombok.Data;

@Data
public class DeveloperDto {
    private long developerId;
    private String lastName;
    private String firstName;
    private int age;
    private CompanyDto company;
    private  int salary;


    public DeveloperDto(String lastName, String firstName, int age, CompanyDto company, int salary) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.age = age;
        this.company = company;
        this.salary = salary;
    }

     public DeveloperDto() {
     }

}

