package model.dto;


import java.util.Set;


public class CompanyDto {
    private long company_id;
    private String companyName;
    private Rating rating;
    private Set<DeveloperDto> developers;
    private Set<ProjectDto> projects;


    public enum Rating {
        high,
        middle,
        low
    }

    public CompanyDto(String companyName, Rating rating) {
        this.companyName = companyName;
        this.rating = rating;
    }

    public CompanyDto(String company_name) {
        this.companyName = company_name;
    }

    public CompanyDto() {
    }

    public long getCompany_id() {
        return company_id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public Rating getRating() {
        return rating;
    }

    public Set<DeveloperDto> getDevelopers() {
        return developers;
    }

    public Set<ProjectDto> getProjects() {
        return projects;
    }

    public void setCompany_id(long company_id) {
        this.company_id = company_id;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public void setDevelopers(Set<DeveloperDto> developers) {
        this.developers = developers;
    }

    public void setProjects(Set<ProjectDto> projects) {
        this.projects = projects;
    }

}


