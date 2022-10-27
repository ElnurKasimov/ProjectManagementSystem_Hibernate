package model.dto;

import model.dao.CompanyDao;
import model.dao.ProjectDao;
import model.dao.SkillDao;

import java.util.Set;

public class DeveloperDto {
    private long developer_id;
    private String lastName;
    private String firstName;
    private int age;
    private  int salary;
    private CompanyDto company;
    private Set<ProjectDto> projects;
    private Set<SkillDto> skills;


    public DeveloperDto(String lastName, String firstName, int age, CompanyDto company, int salary) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.age = age;
        this.company = company;
        this.salary = salary;
    }

     public DeveloperDto() {
     }

    public DeveloperDto(long developer_id, String lastName, String firstName, int age, CompanyDto company, int salary) {
        this.developer_id = developer_id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.age = age;
        this.company = company;
        this.salary = salary;
    }

    public DeveloperDto(long developer_id, String lastName, String firstName, int age, int salary, CompanyDto company, Set<ProjectDto> projects, Set<SkillDto> skills) {
        this.developer_id = developer_id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.age = age;
        this.salary = salary;
        this.company = company;
        this.projects = projects;
        this.skills = skills;
    }

    public Set<ProjectDto> getProjects() {
        return projects;
    }

    public void setProjects(Set<ProjectDto> projects) {
        this.projects = projects;
    }

    public Set<SkillDto> getSkills() {
        return skills;
    }

    public void setSkills(Set<SkillDto> skills) {
        this.skills = skills;
    }

    public long getDeveloper_id() {
        return developer_id;
    }

    public void setDeveloper_id(long developer_id) {
        this.developer_id = developer_id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public CompanyDto getCompany() {
        return company;
    }

    public void setCompany(CompanyDto company) {
        this.company = company;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }
}

