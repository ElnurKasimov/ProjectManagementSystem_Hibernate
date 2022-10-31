package controller.developerController;

import controller.customerController.config.HibernateProvider;
import model.dto.CompanyDto;
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
import java.util.List;
import java.util.Set;

@WebServlet(urlPatterns = "/developer/save")
public class SaveDeveloper extends HttpServlet {
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
        int age = Integer.parseInt(req.getParameter("age"));
        String companyName = req.getParameter("companyName");
        int salary = Integer.parseInt(req.getParameter("salary"));
        result = developerService.saveDeveloper(lastName, firstName, age, companyName, salary);
        if( result.equals("")) {
            Set<ProjectDto> projects = companyService.getCompanyProjects(companyName);
            req.setAttribute("projects", projects);
            req.setAttribute("lastName", lastName);
            req.setAttribute("firstName", firstName);
            req.setAttribute("age", age);
            req.setAttribute("salary", salary);
            req.setAttribute("companyName", companyName);
            req.getRequestDispatcher("/WEB-INF/view/developer/addDeveloperToDbForm.jsp").forward(req, resp);
        } else {
            req.setAttribute("result", result);
            req.getRequestDispatcher("/WEB-INF/view/developer/saveDeveloper.jsp").forward(req, resp);
        }

    }

}
