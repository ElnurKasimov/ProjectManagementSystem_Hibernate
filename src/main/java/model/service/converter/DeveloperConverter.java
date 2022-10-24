package model.service.converter;

import model.dao.DeveloperDao;
import model.dto.DeveloperDto;

public class DeveloperConverter {

    public static DeveloperDto from(DeveloperDao entity) {
        DeveloperDto developerDto = new DeveloperDto();
        developerDto.setDeveloper_id(entity.getDeveloper_id());
        developerDto.setLastName(entity.getLastName());
        developerDto.setFirstName(entity.getFirstName());
        developerDto.setAge(entity.getAge());
        developerDto.setCompanyDto(CompanyConverter.from(entity.getCompanyDao()));
        developerDto.setSalary(entity.getSalary());
        return developerDto;
    }

    public static DeveloperDao to(DeveloperDto entity) {
        DeveloperDao developerDao = new DeveloperDao();
        developerDao.setDeveloper_id(entity.getDeveloper_id());
        developerDao.setLastName(entity.getLastName());
        developerDao.setFirstName(entity.getFirstName());
        developerDao.setAge(entity.getAge());
        developerDao.setCompanyDao(CompanyConverter.to(entity.getCompanyDto()));
        developerDao.setSalary(entity.getSalary());
        return developerDao;
    }
}

