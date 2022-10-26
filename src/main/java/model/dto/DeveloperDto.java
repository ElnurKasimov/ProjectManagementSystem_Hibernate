package model.dto;

public class DeveloperDto {
    private long developer_id;
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

    public DeveloperDto(long developer_id, String lastName, String firstName, int age, CompanyDto company, int salary) {
        this.developer_id = developer_id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.age = age;
        this.company = company;
        this.salary = salary;
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

