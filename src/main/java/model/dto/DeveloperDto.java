package model.dto;

import lombok.Data;

@Data
public class DeveloperDto {
    private long developer_id;
    private String lastName;
    private String firstName;
    private int age;
    private CompanyDto companyDto;
    private  int salary;


    public DeveloperDto(String lastName, String firstName, int age, CompanyDto companyDto, int salary) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.age = age;
        this.companyDto = companyDto;
        this.salary = salary;
    }

     public DeveloperDto() {
     }

}

