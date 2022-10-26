package model.service;

import model.dao.CompanyDao;
import model.dto.CompanyDto;
import model.service.converter.CompanyConverter;
import model.storage.CompanyStorage;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class CompanyService {
    private CompanyStorage companyStorage;

public  CompanyService (CompanyStorage companyStorage) {
    this.companyStorage = companyStorage;
}

public String save (CompanyDto companyDto) {
    String result = "";
    Optional<CompanyDao> companyFromDb = companyStorage.findByName(companyDto.getCompanyName());
    System.out.println(companyFromDb);
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

    public CompanyDto createCompany() {
        System.out.print("Enter company name : ");
        Scanner sc = new Scanner(System.in);
        String newCompanyName = sc.nextLine();
        System.out.print("Enter company rating (high, middle, low) : ");
        String newCompanyRating = sc.nextLine();
        return new CompanyDto(newCompanyName, CompanyDto.Rating.valueOf(newCompanyRating));
    }

//    public CompanyDto checkByName (String name) {
//        CompanyDto companyDto = findByName(name).orElseGet(this::createCompany);
//        return  save(companyDto);
//    }

    public String deleteCompany (String name) {
        String result = "";
        Optional<CompanyDao> companyDaoFromDb = companyStorage.findByName(name);
        if(companyDaoFromDb.isPresent()) {
            companyStorage.delete(companyDaoFromDb.get());
            result = "Company " + name + " successfully deleted from the database";
        } else { result = "There is no company with such name in the database. Please enter correct data.";}
        return result;
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

}
