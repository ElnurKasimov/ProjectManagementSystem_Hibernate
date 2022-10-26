package model.dao;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "developer")
public class DeveloperDao {
    private long developer_id;
    private String lastName;
    private String firstName;
    private int age;
    private int salary;
    private CompanyDao company;
    private Set<ProjectDao> projects;
    private Set<SkillDao> skills;


    public DeveloperDao(String lastName, String firstName, int age, CompanyDao company, int salary) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.age = age;
        this.company = company;
        this.salary = salary;
    }

    public DeveloperDao() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    public CompanyDao getCompany() {
        return company;
    }

    @Column(name = "salary")
    public int getSalary() {
        return salary;
    }

    @ManyToMany
    @JoinTable (
            name = "project_developer",
            joinColumns = { @JoinColumn(name = "developer_id") },
            inverseJoinColumns = { @JoinColumn(name = "project_id") })
    public Set<ProjectDao> getProjects() {
        return projects;
    }

    @ManyToMany
    @JoinTable (
            name = "developer_skill",
            joinColumns = { @JoinColumn(name = "developer_id") },
            inverseJoinColumns = { @JoinColumn(name = "skill_id") })
    public Set<SkillDao> getSkills() {
        return skills;
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

    public void setCompany(CompanyDao company) {
        this.company = company;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public void setProjects(Set<ProjectDao> projects) {
        this.projects = projects;
    }

    public void setSkills(Set<SkillDao> skills) {
        this.skills = skills;
    }
}

