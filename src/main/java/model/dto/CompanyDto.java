package model.dto;

import lombok.Data;

@Data
public class CompanyDto {
    private long company_id;
    private String company_name;
    private Rating rating;

    public enum Rating {
        high,
        middle,
        low
    }

    public CompanyDto (String company_name, Rating rating) {
        this.company_name=company_name;
        this.rating=rating;
    }

    public CompanyDto (String company_name) {
        this.company_name=company_name;
    }
    public CompanyDto () {
    }

}

