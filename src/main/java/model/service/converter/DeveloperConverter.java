package model.service.converter;

import model.dao.DeveloperDao;
import model.dto.DeveloperDto;

import java.util.stream.Collectors;

public class DeveloperConverter {

    public static DeveloperDto from(DeveloperDao entity) {
        DeveloperDto developerDto = new DeveloperDto();
        developerDto.setDeveloper_id(entity.getDeveloper_id());
        developerDto.setLastName(entity.getLastName());
        developerDto.setFirstName(entity.getFirstName());
        developerDto.setAge(entity.getAge());
        developerDto.setSalary(entity.getSalary());
        developerDto.setCompany(CompanyConverter.from(entity.getCompany()));
        developerDto.setProjects(entity.getProjects().stream().map(ProjectConverter::from).collect(Collectors.toSet()));
        developerDto.setSkills(entity.getSkills().stream().map(SkillConverter::from).collect(Collectors.toSet()));
        return developerDto;
    }

    public static DeveloperDao to(DeveloperDto entity) {
        DeveloperDao developerDao = new DeveloperDao();
        developerDao.setDeveloper_id(entity.getDeveloper_id());
        developerDao.setLastName(entity.getLastName());
        developerDao.setFirstName(entity.getFirstName());
        developerDao.setAge(entity.getAge());
        developerDao.setSalary(entity.getSalary());
        developerDao.setCompany(CompanyConverter.to(entity.getCompany()));
        developerDao.setProjects(entity.getProjects().stream().map(ProjectConverter::to).collect(Collectors.toSet()));
        developerDao.setSkills(entity.getSkills().stream().map(SkillConverter::to).collect(Collectors.toSet()));
        return developerDao;
    }
}

