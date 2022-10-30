package model.service;

import model.dao.CompanyDao;
import model.dao.ProjectDao;
import model.dto.CompanyDto;
import model.dto.ProjectDto;
import model.service.converter.CompanyConverter;
import model.service.converter.ProjectConverter;
import model.storage.CompanyStorage;

import java.util.*;
import java.util.stream.Collectors;

public class CompanyService {
    private CompanyStorage companyStorage;

public  CompanyService (CompanyStorage companyStorage) {
    this.companyStorage = companyStorage;
}

    public Set<CompanyDto> findAllCompanies() {
        return companyStorage.findAll().stream()
                .map(CompanyConverter::from)
                .collect(Collectors.toSet());
    }

    public Optional<CompanyDto> findById(long id) {
    Optional<CompanyDao> companyDaoFromDb = companyStorage.findById(id);
    return companyDaoFromDb.map(CompanyConverter::from);
    }

    public Optional<CompanyDto> findByName(String name) {
        Optional<CompanyDao> companyDaoFromDb = companyStorage.findByName(name);
        return companyDaoFromDb.map(CompanyConverter::from);
    }

    public String save (CompanyDto companyDto) {
        String result = "";
        Optional<CompanyDao> companyFromDb = companyStorage.findByName(companyDto.getCompanyName());
        if (companyFromDb.isPresent()) {
            result = validateByName(companyDto, CompanyConverter.from(companyFromDb.get()));
        } else {
            companyStorage.save(CompanyConverter.to(companyDto));
            result = "Company " + companyDto.getCompanyName() + " successfully added to the database";
        };
        return result;
    }

    public String  validateByName(CompanyDto companyDto, CompanyDto companyFromDb) {
        if (!companyDto.getRating().toString().equals(companyFromDb.getRating().toString())) {
            return String.format("Company with name '%s' already exist with different " +
                            "rating '%s'. Please enter correct data.",
                    companyDto.getCompanyName(), companyFromDb.getRating().toString());
        } else return "Ok. A company with such parameters is present in the database already.";
    }

    public String updateCompany(CompanyDto companyDto) {
        CompanyDto companyDtoToUpdate = null;
        Optional<CompanyDto>  companyDtoFromDb = findByName(companyDto.getCompanyName());
        if (companyDtoFromDb.isEmpty()) {
            return "Unfortunately, there is no company with such name in the database.  Please enter correct company name.";
        } else {
            companyDtoToUpdate = companyDtoFromDb.get();
            companyDtoToUpdate.setRating(companyDto.getRating());
            CompanyDto updatedCompanyDto = CompanyConverter.from(companyStorage.update(CompanyConverter.to(companyDtoToUpdate)));
            return String.format("Company %s successfully updated.", updatedCompanyDto.getCompanyName());
        }
    }

    public List<String> deleteCompany (String name) {
        List<String> result = new ArrayList<>();
        Optional<CompanyDao> companyDaoFromDb = companyStorage.findByName(name);
        if(companyDaoFromDb.isPresent()) {
            result =  companyStorage.delete(companyDaoFromDb.get());
        } else { result.add("There is no company with such name in the database. Please enter correct data.");}
        return result;
    }

    public Set<ProjectDto> getCompanyProjects (String name) {
        Optional<CompanyDao>  companyDaoFromDb = companyStorage.findByName(name);
        return companyDaoFromDb.map(companyDao -> companyDao.getProjects().stream().map(ProjectConverter::from).collect(Collectors.toSet()))
                .orElseGet(HashSet::new);
    }

}
