package model.service.converter;

import model.dao.ProjectDao;
import model.dto.ProjectDto;

import java.util.stream.Collectors;

public class ProjectConverter{

    public static ProjectDto from(ProjectDao entity) {
        ProjectDto projectDto = new ProjectDto();
        projectDto.setProject_id(entity.getProject_id());
        projectDto.setProjectName(entity.getProjectName());
        projectDto.setCost(entity.getCost());
        projectDto.setStartDate(entity.getStartDate());
        projectDto.setCompany(CompanyConverter.from(entity.getCompany()));
        projectDto.setCustomer(CustomerConverter.from(entity.getCustomer()));
        projectDto.setDevelopers(entity.getDevelopers().stream().map(DeveloperConverter::from).collect(Collectors.toSet()));
        return projectDto;
    }

    public static ProjectDao to(ProjectDto entity) {
        ProjectDao projectDao = new ProjectDao();
        projectDao.setProject_id(entity.getProject_id());
        projectDao.setProjectName(entity.getProjectName());
        projectDao.setCost(entity.getCost());
        projectDao.setStartDate(entity.getStartDate());
        projectDao.setCompany(CompanyConverter.to(entity.getCompany()));
        projectDao.setCustomer(CustomerConverter.to(entity.getCustomer()));
        projectDao.setDevelopers(entity.getDevelopers().stream().map(DeveloperConverter::to).collect(Collectors.toSet()));
        return projectDao;
    }
}

