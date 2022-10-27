package model.service.converter;

import model.dao.CompanyDao;
import model.dto.CompanyDto;

import java.util.stream.Collectors;

public class CompanyConverter{

    public static CompanyDto from(CompanyDao entity) {
        CompanyDto companyDto = new CompanyDto();
        companyDto.setCompany_id(entity.getCompany_id());
        companyDto.setCompanyName(entity.getCompanyName());
        companyDto.setRating(CompanyDto.Rating.valueOf(entity.getRating().toString()));
        //companyDto.setDevelopers(entity.getDevelopers().stream().map(DeveloperConverter::from).collect(Collectors.toSet()));
        //companyDto.setProjects(entity.getProjects().stream().map(ProjectConverter::from).collect(Collectors.toSet()));
        return companyDto;
    }

    public static CompanyDao to(CompanyDto entity) {
        CompanyDao companyDao = new CompanyDao();
        companyDao.setCompany_id(entity.getCompany_id());
        companyDao.setCompanyName(entity.getCompanyName());
        companyDao.setRating(CompanyDao.Rating.valueOf(entity.getRating().toString()));
//        companyDao.setDevelopers(entity.getDevelopers().stream().map(DeveloperConverter::to).collect(Collectors.toSet()));
//        companyDao.setProjects(entity.getProjects().stream().map(ProjectConverter::to).collect(Collectors.toSet()));
        return companyDao;
    }
}

