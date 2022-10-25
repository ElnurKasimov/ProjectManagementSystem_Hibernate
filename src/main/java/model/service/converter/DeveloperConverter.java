package model.service.converter;

import model.dao.DeveloperDao;
import model.dto.DeveloperDto;

public class DeveloperConverter {

    public static DeveloperDto from(DeveloperDao entity) {
        DeveloperDto developerDto = new DeveloperDto();
        developerDto.setDeveloperId(entity.getDeveloperId());
        developerDto.setLastName(entity.getLastName());
        developerDto.setFirstName(entity.getFirstName());
        developerDto.setAge(entity.getAge());
        developerDto.setCompany(CompanyConverter.from(entity.getCompany()));
        developerDto.setSalary(entity.getSalary());
        return developerDto;
    }

    public static DeveloperDao to(DeveloperDto entity) {
        DeveloperDao developerDao = new DeveloperDao();
        developerDao.setDeveloperId(entity.getDeveloperId());
        developerDao.setLastName(entity.getLastName());
        developerDao.setFirstName(entity.getFirstName());
        developerDao.setAge(entity.getAge());
        developerDao.setCompany(CompanyConverter.to(entity.getCompany()));
        developerDao.setSalary(entity.getSalary());
        return developerDao;
    }
}

