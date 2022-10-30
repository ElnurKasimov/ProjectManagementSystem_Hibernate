package model.service;

import model.dao.DeveloperDao;
import model.dao.ProjectDao;
import model.dto.CompanyDto;
import model.dto.CustomerDto;
import model.dto.ProjectDto;
import model.service.converter.ProjectConverter;
import model.storage.CompanyStorage;
import model.storage.DeveloperStorage;
import model.storage.ProjectStorage;


import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ProjectService {
    private ProjectStorage projectStorage;
    private DeveloperStorage developerStorage;
    private CompanyService companyService;
    private CustomerService customerService;
    private RelationService relationService;

    public ProjectService(ProjectStorage projectStorage, DeveloperStorage developerStorage,
                          CompanyService companyService, CustomerService customerService,
                          RelationService relationService) {
        this.projectStorage = projectStorage;
        this.developerStorage = developerStorage;
        this.companyService = companyService;
        this.customerService = customerService;
        this.relationService = relationService;
    }

    public List<ProjectDto> findAllProjects() {
         return projectStorage.findAll().stream()
                 .map(ProjectConverter::from)
                 .collect(Collectors.toList());
    }

    public Optional<ProjectDto> findByName(String name) {
        if(projectStorage.findByName(name).isPresent()) {
            return Optional.of(ProjectConverter.from(projectStorage.findByName(name).get()));
        }
        else return Optional.empty();
    }

    public Optional<ProjectDto> findById(Long id) {
        if(projectStorage.findById(id).isPresent()) {
            return Optional.of(ProjectConverter.from(projectStorage.findById(id).get()));
        }
        else return Optional.empty();
    }

    public List<String> getProjectsNameByDeveloperId(long id) {
        Set<ProjectDao> projectsFromDb = projectStorage.getProjectsNameByDeveloperId(id);
        if (projectsFromDb.isEmpty()) {
            return new ArrayList<>();
        } else {
            return projectsFromDb.stream().map(ProjectDao::getProjectName).collect(Collectors.toList());
        }
    }

    public List<Long> getProjectIdsByDeveloperId(long id) {
        return projectStorage.getProjectIdsByDeveloperId(id);
    }

    public List<ProjectDto>  getCompanyProjects(String companyName) {
        List<ProjectDto> projects = new ArrayList<>();
        List<ProjectDao> projectDaoList = projectStorage.getCompanyProjects(companyName);
        for (ProjectDao projectDao : projectDaoList) {
            projects.add(ProjectConverter.from(projectDao));
        }
        return projects;
    }

    public List<ProjectDto> getCustomerProjects(String customerName) {
        List<ProjectDto> projects = new ArrayList<>();
        List<ProjectDao> projectDaoList = projectStorage.getCustomerProjects(customerName);
        for (ProjectDao projectDao : projectDaoList) {
            projects.add(ProjectConverter.from(projectDao));
        }
        return projects;
    }

    public long getIdByName(String name) {
        return projectStorage.getIdByName(name).orElseGet(() -> {
            System.out.print("There is no project with such name. Please enter correct data");
            return (long) 0;
        });
    }

    public Set<ProjectDto> checkByCompanyName(String companyName) {
//        System.out.printf("\tCompany %s develops such projects : \n", companyName);
//        List<ProjectDto> projectDtoList = getCompanyProjects(companyName);
//        for (ProjectDto projectDto : projectDtoList) {
//            System.out.print(projectDto.getProject_name() + ",\n");
//        }
//        if (projectDtoList.isEmpty()) {
//            System.out.println("\nThere is no project in the company. Please create the one.");
//            ProjectDto newProjectDto;
//            do {
//                newProjectDto = createProject();
//                newProjectDto = save(newProjectDto);
//            } while (newProjectDto.getProject_id() == 0);
//            System.out.println("Company " + companyName + " develops project " + newProjectDto.getProject_name());
//        }
        Set<ProjectDto> projectsDto = new HashSet<>();
//        while (true) {
//            System.out.print("\tPlease enter project name the developers participate in : ");
//            Scanner sc = new Scanner(System.in);
//            String projectName = sc.nextLine();
//            Optional<ProjectDao> projectDaoFromDb = projectStorage.findByName(projectName);
//            if (projectDaoFromDb.isPresent()) {
//                ProjectDto selectedProject = ProjectConverter.from(projectDaoFromDb.get());
//                projectsDto.add(selectedProject);
//                System.out.print("One more project? (yes/no) : ");
//                String anotherLanguage = sc.nextLine();
//                if (anotherLanguage.equalsIgnoreCase("no")) break;
//            } else {
//                System.out.println("\tThere is no such project. Please enter correct data");
//            }
//        }
        return projectsDto;
    }

    public String saveProject(String projectName, String customerName, int cost, String companyName, Date startSqlDate) {
        CompanyDto companyDto = null;
        CustomerDto customerDto = null;
        ProjectDto savedProjectDto = null;
        String result = "";
        if (customerService.findByName(customerName).isPresent()) {
            customerDto = customerService.findByName(customerName).get();
            if (companyService.findByName(companyName).isPresent()) {
                companyDto = companyService.findByName(companyName).get();
                ProjectDto newProjectDto = new ProjectDto(projectName, companyDto, customerDto, cost, startSqlDate);
                Optional<ProjectDao> projectFromDb =
                        projectStorage.findByName(newProjectDto.getProjectName());
                if (projectFromDb.isPresent()) {
                    if (validateByName(newProjectDto, ProjectConverter.from(projectFromDb.get()))) {
                        savedProjectDto = ProjectConverter.from(projectFromDb.get()); // with id
                    } else {
                        result = (String.format("\tProject with name '%s ' already exist with different another data." +
                                " Please enter correct data", newProjectDto.getProjectName()));
                    }
                } else {
                    savedProjectDto = ProjectConverter.from(projectStorage.save(ProjectConverter.to(newProjectDto))); // with id
                    result = "Project " + newProjectDto.getProjectName() + " successfully added to the database";
                }
            } else {
                result = "There is no company with such name. Please enter correct one.";
            }
        } else {
            result = "There is no customer with such name. Please enter correct one.";
        }
        return result;
    }


    public boolean validateByName(ProjectDto projectDto, ProjectDto projectFromDb) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateFromProjectDto = dateFormat.format(projectDto.getStartDate());
        String dateFromProjectFromDb = dateFormat.format(projectFromDb.getStartDate());
        return (projectDto.getCompany().getCompanyName().equals(projectFromDb.getCompany().getCompanyName())) &&
                (projectDto.getCustomer().getCustomerName().equals(projectFromDb.getCustomer().getCustomerName())) &&
                (projectDto.getCost() == projectFromDb.getCost()) &&
                (dateFromProjectDto.equals(dateFromProjectFromDb));
    }

    public  ProjectDto getInfoByName(String name) {
        return ProjectConverter.from(projectStorage.findByName(name).get());
    }

    public long getProjectExpences(String projectName) {
        return  projectStorage.getProjectExpences(projectName);
    }

    public List<String> getProjectsListInSpecialFormat() {
        List<String> result = new ArrayList<>();
        if (!projectStorage.findAll().isEmpty()) {
            projectStorage.findAll().forEach(projectDao ->
                    result.add(String.format("%s - %s - %d,",
                            projectDao.getStartDate().toString(),
                            projectDao.getProjectName(),
                            developerStorage.getQuantityOfProjectDevelopers(projectDao.getProjectName())
                    )));
        }
     return result;
    }

    public List<String> updateProject(String projectName, String customerName, int cost, String companyName, Date startSqlDate) {
        ProjectDto updatedProjectDto = null;
        List<String> result = new ArrayList<>();
        Optional<ProjectDto> projectFromDb = findByName(projectName);
        if(projectFromDb.isPresent()) {
            CompanyDto companyFromDb = projectFromDb.get().getCompany();
            updatedProjectDto = projectFromDb.get();
            Optional<CustomerDto> customerDto = customerService.findByName(customerName);
            if (customerDto.isPresent()) {
                updatedProjectDto.setCustomer(customerDto.get());
                Optional<CompanyDto> companyDto = companyService.findByName(companyName);
                if (companyDto.isPresent()) {
                    if (companyDto.get().getCompanyName().equals(companyFromDb.getCompanyName())) {
                        updatedProjectDto.setCompany(companyDto.get());
                        updatedProjectDto.setCost(cost);
                        updatedProjectDto.setStartDate(startSqlDate);
                        projectStorage.update(ProjectConverter.to(updatedProjectDto));
                        result.add("Project " + updatedProjectDto.getProjectName() + " successfully updated.");
                    } else {
                        Set<DeveloperDao> involvedInTheProject = projectStorage.findByName(projectName).get().getDevelopers();
                        if (involvedInTheProject.isEmpty()) {
                            updatedProjectDto.setCompany(companyDto.get());
                            updatedProjectDto.setCost(cost);
                            updatedProjectDto.setStartDate(startSqlDate);
                            projectStorage.update(ProjectConverter.to(updatedProjectDto));
                            result.add("Project " + updatedProjectDto.getProjectName() + " successfully updated.");
                        } else {
                            result.add("Please note that You've changed company from " + companyFromDb.getCompanyName() + " to " + companyName + ".");
                            result.add("But " + companyFromDb.getCompanyName() + " employs the following developers who are involved it the project:");
                            for (DeveloperDao developer : involvedInTheProject) {
                                result.add(developer.getLastName() + " " + developer.getFirstName());
                            }
                            result.add("Before changing the company change the data of the relevant developers first.");
                            result.add("Because developer can work in one company only.");
                        }
                    }
                } else {
                    result.add( "There is no company with such name. Please enter correct one.");
                }
            } else {
                result.add("There is no customer with such name. Please enter correct one.");
            }
        } else {
            result.add("There is no project with such name. Please enter correct one.");
        }
        return result;
    }

    public String deleteProject(String projectName) {
        String result = "";
        Optional<ProjectDto> projectFromDb = findByName(projectName);
        if (projectFromDb.isPresent()) {
            relationService.deleteAllDevelopersOfProject(projectFromDb.get());
            projectStorage.delete(ProjectConverter.to(projectFromDb.get()));
            result = "Project " + projectFromDb.get().getProjectName() + " successfully deleted from the database";
        } else {
            result = "There is no project with such name. Please enter correct one.";
        }
        return result;
    }

    public boolean checkProjects(String[] projectsNames, String  companyName) {
        List<String >  companyProjectsNames = getCompanyProjects(companyName).stream()
                .map(ProjectDto::getProjectName).collect(Collectors.toList());
        return Stream.of(projectsNames).allMatch(companyProjectsNames::contains);
    }

}
