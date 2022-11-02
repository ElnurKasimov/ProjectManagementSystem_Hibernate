package controller.projectController;

import model.config.HibernateProvider;
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

@WebServlet(urlPatterns = "/project/list_project_developers")
public class ListProjectDevelopers extends HttpServlet {
    private static DeveloperService developerService;
    private static HibernateProvider connectionProvider;
    private static DeveloperStorage developerStorage;
    private static CompanyStorage companyStorage;
    private static CompanyService companyService;
    private static CustomerStorage customerStorage;
    private static CustomerService customerService;
    private static ProjectStorage projectStorage;
    private static ProjectService projectService;
    private static SkillStorage skillStorage;
    private static SkillService skillService;

    @Override
    public void init() throws ServletException {
        connectionProvider = new HibernateProvider();
        try {
            skillStorage = new SkillStorage(connectionProvider);
            skillService = new SkillService(skillStorage);
            companyStorage = new CompanyStorage(connectionProvider);
            companyService = new CompanyService(companyStorage);
            customerStorage = new CustomerStorage(connectionProvider);
            customerService = new CustomerService(customerStorage);
            developerStorage = new DeveloperStorage(connectionProvider, companyStorage);
            projectStorage = new ProjectStorage(connectionProvider, companyStorage, customerStorage);
            projectService = new ProjectService(projectStorage, developerStorage, companyService, customerService);
            developerService = new DeveloperService(developerStorage, companyStorage, skillService);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String projectName = req.getParameter("projectName");
        String result = "";
        List<String> projects = new ArrayList<>();
        if(projectService.findByName(projectName).isPresent()) {
            result = "Such developers develop the project '" + projectName + "' :";
            projects = developerService.getDevelopersNamesByProjectName(projectName);
        } else {
            result = "There is no project with such  mane in the database. Please enter correct name.";
        }
        req.setAttribute("projects", projects);
        req.setAttribute("result", result);
        req.getRequestDispatcher("/WEB-INF/view/project/listProjectDevelopers.jsp").forward(req, resp);
    }

}
