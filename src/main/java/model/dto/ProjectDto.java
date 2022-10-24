package model.dto;

import lombok.Data;

import java.sql.Date;

@Data
public class ProjectDto {
    private long project_id;
    private String project_name;
    private CompanyDto companyDto;
    private CustomerDto customerDto;
    private  int cost;
    private Date start_date;

    public ProjectDto (String project_name, CompanyDto companyDto, CustomerDto customerDto, int cost,
                       Date start_date) {
        this.project_name = project_name;
        this.companyDto = companyDto;
        this.customerDto =  customerDto;
        this.cost = cost;
        this.start_date = start_date;
    }

    public ProjectDto (long project_id, String project_name) {
        this.project_id = project_id;
        this.project_name = project_name;
    }
    public ProjectDto () {
    }
}
