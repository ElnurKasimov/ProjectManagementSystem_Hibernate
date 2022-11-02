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

    public List<String> getListNamesDevelopersWithCertainLevel(String level) {
        List<String> result = new ArrayList<>();
        Set<DeveloperDao> developersFromDb = developerStorage.getDevelopersWithCertainLevel(level);
        developersFromDb.forEach(dev -> result.add(dev.getLastName() + " " + dev.getFirstName()));
        return  result;
    }

    public List<String> getDevelopersNamesByProjectName(String name) {
        List<String> result = new ArrayList<>();
        Set<DeveloperDao> developersFromDB = developerStorage.getDevelopersByProjectName(name);
        developersFromDB.forEach(dev -> result.add(dev.getLastName() + " " + dev.getFirstName()));
        return result;
    }

     public String saveDeveloper(String lastName, String firstName,  int age, int salary, String companyName,
                                Set<ProjectDto> developerProjects, String language, String level) {
        String result = "";
        DeveloperDao developerToSave = new DeveloperDao();
        developerToSave.setLastName(lastName);
        developerToSave.setFirstName(firstName);
        developerToSave.setAge(age);
        developerToSave.setSalary(salary);
        developerToSave.setCompany(companyStorage.findByName(companyName).get());
        if(developerProjects.isEmpty()) {developerToSave.setProjects(new HashSet<>());}
        else {developerToSave.setProjects(developerProjects.stream().map(ProjectConverter::to).collect(Collectors.toSet()));}
        SkillDto skillDto = skillService.findByLanguageAndLevel(language, level);
        Set<SkillDto> skills = new HashSet<>();
        skills.add(skillDto);
        developerToSave.setSkills(skills.stream().map(SkillConverter::to).collect(Collectors.toSet()));
        Optional<DeveloperDao> developerFromDb = developerStorage.findByName(lastName, firstName);
        if (developerFromDb.isPresent()) {
            result = validateByName(developerToSave, developerFromDb.get());
        } else {
            developerStorage.save(developerToSave);
            result = String.format("Developer %s %s successfully added into database with all necessary relations."
                    , lastName, firstName);
        }
        return result;
    }

    public String validateByName(DeveloperDao developerToSave, DeveloperDao developerFromDb) {
        if ((developerToSave.getAge() == developerFromDb.getAge()) &&
                (developerToSave.getCompany().getCompanyName().equals(developerFromDb.getCompany().getCompanyName()))
                && (developerToSave.getSalary() == developerFromDb.getSalary())) {
            return "This developer exists in the database already. If You want change his data - use menu Update.";
        } else return String.format("\tDeveloper  %s %s  already exists with different another data." +
                " Please enter correct data", developerToSave.getLastName(), developerToSave.getFirstName());
    }

    public String findDeveloperForUpdate(String lastName, String firstName) {
        return developerStorage.findByName(lastName, firstName).isPresent() ?
                "" : "There is no developer with such name in the database. Please, input correct data.";
    }

    public String updateDeveloper(String lastName, String firstName,  int age, int salary, String companyName,
                                  Set<ProjectDto> developerProjects, String language, String level) {
        String result = "";
        DeveloperDao developerToUpdate = developerStorage.findByName(lastName, firstName).get();
        developerToUpdate.setAge(age);
        developerToUpdate.setSalary(salary);
        developerToUpdate.setCompany(companyStorage.findByName(companyName).get());
        if(developerProjects.isEmpty()) {developerToUpdate.setProjects(new HashSet<>());}
        else {developerToUpdate.setProjects(developerProjects.stream().map(ProjectConverter::to).collect(Collectors.toSet()));}
        SkillDto skillDto = skillService.findByLanguageAndLevel(language, level);
        Set<SkillDto> skills = new HashSet<>();
        skills.add(skillDto);
        developerToUpdate.setSkills(skills.stream().map(SkillConverter::to).collect(Collectors.toSet()));

        DeveloperDao updatedDeveloper = developerStorage.update(developerToUpdate);


//        Set<ProjectDto> projects = Stream.of(projectsNames)
//                .map(name -> projectService.findByName(name).get())
//                .collect(Collectors.toSet());
//
//        relationService.deleteAllProjectsOfDeveloper(updatedDeveloperDto);
//        relationService.saveProjectDeveloper(projects, updatedDeveloperDto);
//
//        relationService.deleteAllSkillsOfDeveloper(updatedDeveloperDto);
//        relationService.saveDeveloperSkill(updatedDeveloperDto, skillsDto);

        return String.format("Developer %s %s successfully updated with all necessary relations.",
                lastName, firstName);
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

