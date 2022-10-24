package controller.developerController;

import model.config.HibernateProvider;
import model.dto.DeveloperDto;
import model.dto.SkillDto;
import model.service.*;
import model.storage.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

@WebServlet(urlPatterns = "/developer/update")
public class UpdateDeveloper extends HttpServlet {
    private static HibernateProvider connectionProvider;
    private static DeveloperStorage developerStorage;
    private static DeveloperService developerService;
    private static CompanyStorage companyStorage;
    private static CompanyService companyService;
    private static CustomerStorage customerStorage;
    private static CustomerService customerService;
    private static ProjectStorage projectStorage;
    private static ProjectService projectService;
    private static SkillService skillService;
    private static SkillStorage skillStorage;
    private static RelationStorage relationStorage;
    private static RelationService relationService;

    @Override
    public void init() throws ServletException {
        connectionProvider = new HibernateProvider();
        try {
            skillStorage = new SkillStorage(connectionProvider);
            skillService = new SkillService(skillStorage);
            relationStorage = new RelationStorage(connectionProvider);
            relationService = new RelationService(relationStorage);
            companyStorage = new CompanyStorage(connectionProvider);
            companyService = new CompanyService(companyStorage);
            customerStorage = new CustomerStorage(connectionProvider);
            customerService = new CustomerService(customerStorage);
            developerStorage = new DeveloperStorage(connectionProvider, companyStorage, skillStorage);
            projectStorage = new ProjectStorage(connectionProvider, companyStorage, customerStorage);
            projectService = new ProjectService(projectStorage, developerStorage, companyService,
                    customerService, relationService);
            developerService = new DeveloperService(developerStorage, projectService, projectStorage,
                    skillStorage, companyStorage, relationService, skillService);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String result = "";
        String companyName = req.getParameter("companyName");
        DeveloperDto developerDtoToUpdate = new DeveloperDto();
        developerDtoToUpdate.setLastName(req.getParameter("lastName"));
        developerDtoToUpdate.setFirstName(req.getParameter("firstName"));
        developerDtoToUpdate.setAge(Integer.parseInt(req.getParameter("age")));
        developerDtoToUpdate.setSalary(Integer.parseInt(req.getParameter("salary")));
        developerDtoToUpdate.setCompanyDto(companyService.findByName(companyName).get());
        String[]  projectsNames = req.getParameterValues("projectName");
        if(projectService.checkProjects(projectsNames, companyName)) {
            Set<SkillDto> skillsDto = new HashSet<>();
            SkillDto skillDto = skillService.findByLanguageAndLevel(
                   req.getParameter("language"), req.getParameter("level"));
            skillsDto.add(skillDto);
           result = developerService.updateDeveloper(developerDtoToUpdate,  projectsNames, skillsDto);
       } else { result = "The projects you have chosen do not match the company you have chosen. Enter the correct data.";}
        req.setAttribute("result", result);
        req.getRequestDispatcher("/WEB-INF/view/developer/updateDeveloperResult.jsp").forward(req, resp);

    }

}
