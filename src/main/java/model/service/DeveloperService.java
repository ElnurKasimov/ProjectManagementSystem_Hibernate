package model.service;

import model.dao.DeveloperDao;
import model.dao.SkillDao;
import model.dto.*;
import model.service.converter.CompanyConverter;
import model.service.converter.DeveloperConverter;
import model.service.converter.ProjectConverter;
import model.service.converter.SkillConverter;
import model.storage.CompanyStorage;
import model.storage.DeveloperStorage;
import model.storage.ProjectStorage;
import model.storage.SkillStorage;


import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DeveloperService {
    private DeveloperStorage developerStorage;
    private ProjectService projectService;
    private ProjectStorage projectStorage;
    private SkillStorage skillStorage;
    private CompanyStorage companyStorage;
    private RelationService relationService;
    private SkillService skillService;

    public DeveloperService(DeveloperStorage developerStorage, ProjectService projectService, ProjectStorage projectStorage,
                            SkillStorage skillstorage, CompanyStorage companyStorage, RelationService relationService, SkillService skillService) {
        this.developerStorage = developerStorage;
        this.projectService = projectService;
        this.projectStorage = projectStorage;
        this.skillStorage = skillstorage;
        this.companyStorage = companyStorage;
        this.relationService = relationService;
        this.skillService = skillService;
    }

    public List<DeveloperDto> findAllDevelopers() {
        return developerStorage.findAll().stream()
                .map(DeveloperConverter::from)
                .collect(Collectors.toList());
    }

    public DeveloperDto getByName(String lastName, String firstName) {
        return developerStorage.findByName(lastName, firstName).map(DeveloperConverter::from).orElse(null);
    }

    public long getIdByName(String lastName, String firstName) {
        return developerStorage.getIdByName(lastName, firstName);
    }

    public List<String> getListNamesDevelopersWithCertainLanguage(String language) {
        List<String> result = new ArrayList<>();
        Set<DeveloperDao> developersFromDb = developerStorage.getDevelopersWithCertainLanguage(language);
        developersFromDb.forEach(dev -> result.add(dev.getLastName() + " " + dev.getFirstName()));
        return  result;
    }

    ;

    public List<String> getListNamesDevelopersWithCertainLevel(String level) {
        List<String> result = new ArrayList<>();
        Set<DeveloperDao> developersFromDb = developerStorage.getDevelopersWithCertainLevel(level);
        developersFromDb.forEach(dev -> result.add(dev.getLastName() + " " + dev.getFirstName()));
        return  result;
    }

    ;

    public List<String> getDevelopersNamesByProjectName(String name) {
        List<String> result = new ArrayList<>();
        Set<DeveloperDao> developersFromDB = developerStorage.getDevelopersByProjectName(name);
        developersFromDB.forEach(dev -> result.add(dev.getLastName() + " " + dev.getFirstName()));
        return result;
    }

    public String findDeveloperForUpdate(String lastName, String firstName) {
        return developerStorage.findByName(lastName, firstName).isPresent() ?
                "" : "There is no developer with such name in the database. Please, input correct data.";
    }

    public String saveDeveloper(String lastName, String firstName, int age, String companyName, int salary) {
        String result = "";
        CompanyDto companyDto = null;
        DeveloperDto developerDtoToSave = new DeveloperDto();
        developerDtoToSave.setLastName(lastName);
        developerDtoToSave.setFirstName(firstName);
        developerDtoToSave.setAge(age);
        developerDtoToSave.setSalary(salary);
        if (companyStorage.findByName(companyName).isPresent()) {
            developerDtoToSave.setCompany(CompanyConverter.from(companyStorage.findByName(companyName).get()));
            Optional<DeveloperDao> developerFromDb = developerStorage.findByName(lastName, firstName);
            if (developerFromDb.isPresent()) {
                result = validateByName(developerDtoToSave, DeveloperConverter.from(developerFromDb.get()));
            } else {
               // DeveloperDto savedDeveloperDto = DeveloperConverter.from(developerStorage.save(DeveloperConverter.to(developerDtoToSave)));
                //developerStorage.save(DeveloperConverter.to(developerDtoToSave));
            }
        } else {
            result = "There is no company with name '" + companyName + "' in the database. Please enter correct data.";
        }
        return result;
    }

    public String validateByName(DeveloperDto developerDto, DeveloperDto developerFromDb) {
        if ((developerDto.getAge() == developerFromDb.getAge()) &&
                (developerDto.getCompany().getCompanyName().equals(developerFromDb.getCompany().getCompanyName()))
                && (developerDto.getSalary() == developerFromDb.getSalary())) {
            return "";
        } else return String.format("\tDeveloper  %s %s  already exists with different another data." +
                " Please enter correct data", developerDto.getLastName(), developerDto.getFirstName());
    }

    public String saveDeveloper(String lastName, String firstName,  int age, int salary, String companyName,
                                Set<ProjectDto> developerProjects, String language, String level) {
        DeveloperDao developerDaoToAdd = new DeveloperDao();
        developerDaoToAdd.setLastName(lastName);
        developerDaoToAdd.setFirstName(firstName);
        developerDaoToAdd.setAge(age);
        developerDaoToAdd.setSalary(salary);
        developerDaoToAdd.setCompany(companyStorage.findByName(companyName).get());
        developerDaoToAdd.setProjects(developerProjects.stream().map(ProjectConverter::to).collect(Collectors.toSet()));
        SkillDto skillDto = skillService.findByLanguageAndLevel(language, level);
        Set<SkillDto> skills = new HashSet<>();
        skills.add(skillDto);
        developerDaoToAdd.setSkills(skills.stream().map(SkillConverter::to).collect(Collectors.toSet()));
        developerStorage.save(developerDaoToAdd);
        return String.format("Developer %s %s successfully added into database with all necessary relations."
            , lastName, firstName);
    }


    public String updateDeveloper(DeveloperDto developerDtoToUpdate, String[] projectsNames, Set<SkillDto> skillsDto) {

        DeveloperDto updatedDeveloperDto = DeveloperConverter.from(developerStorage.update(DeveloperConverter.to(developerDtoToUpdate)));
        Set<ProjectDto> projects = Stream.of(projectsNames)
                .map(name -> projectService.findByName(name).get())
                .collect(Collectors.toSet());
        relationService.deleteAllProjectsOfDeveloper(updatedDeveloperDto);
        relationService.saveProjectDeveloper(projects, updatedDeveloperDto);

        relationService.deleteAllSkillsOfDeveloper(updatedDeveloperDto);
        relationService.saveDeveloperSkill(updatedDeveloperDto, skillsDto);

        return String.format("Developer %s %s successfully updated with all necessary relations.",
                updatedDeveloperDto.getLastName(), updatedDeveloperDto.getFirstName());
    }

    public String deleteDeveloper(String lastName, String firstName) {
        String result = "";
        Optional<DeveloperDao> developerDaoFromDb = developerStorage.findByName(lastName, firstName);
        if (developerDaoFromDb.isPresent()) {
            DeveloperDto developerDtoToDelete = DeveloperConverter.from(developerDaoFromDb.get());
            relationService.deleteDeveloperFromDeveloperSkill(developerDtoToDelete);
            relationService.deleteDeveloperFromProjectDeveloper(developerDtoToDelete);
            developerStorage.delete(DeveloperConverter.to(developerDtoToDelete));
            result = String.format("Developer %s %s successfully deleted from the database with all necessary relations.",
                    developerDtoToDelete.getLastName(), developerDtoToDelete.getFirstName());
        } else {
            result = "There is no such developer in the database. Please enter correct data";
        }
        return result;
    }

}

