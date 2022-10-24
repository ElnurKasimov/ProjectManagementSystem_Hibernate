package model.dao;

import lombok.Data;

@Data
public class CompanyDao {
    private long company_id;
    private String company_name;
    private Rating rating;

    public enum Rating {
        high,
        middle,
        low
    }

    public CompanyDao (String company_name, Rating rating) {
        this.company_name=company_name;
        this.rating=rating;
    }

    public CompanyDao () {
    }

}

