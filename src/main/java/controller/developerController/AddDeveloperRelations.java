package controller.developerController;

import controller.customerController.config.HibernateProvider;
import model.dto.DeveloperDto;
import model.dto.ProjectDto;
import model.service.*;
import model.storage.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = "/developer/add_developer_relations")
public class AddDeveloperRelations extends HttpServlet {
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
            projectService = new ProjectService(projectStorage, developerStorage, companyService, customerService);
            developerService = new DeveloperService(developerStorage, projectService, projectStorage,
                    skillStorage, companyStorage, relationService, skillService);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String result = "";
        String lastName = req.getParameter("lastName");
        String firstName = req.getParameter("firstName");
        String[]  projectsNames = req.getParameterValues("projectName");
        Set<ProjectDto> developerProjects = Arrays.stream(projectsNames)
                .map(name -> projectService.findByName(name))
                .map(Optional::get)
                .collect(Collectors.toSet());
        String language = req.getParameter("language");
        String level = req.getParameter("level");
        DeveloperDto developerDto = developerService.getByName(lastName, firstName);
        result = developerService.saveDeveloperRelations(developerDto, developerProjects, language, level);
        req.setAttribute("result", result);
        req.getRequestDispatcher("/WEB-INF/view/developer/addDeveloperRelations.jsp").forward(req, resp);

    }

}
