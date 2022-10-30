package controller.projectController;

import controller.customerController.config.HibernateProvider;
import model.service.CompanyService;
import model.service.CustomerService;
import model.service.ProjectService;
import model.service.RelationService;
import model.storage.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/project/project_expenses")
public class ProjectExpences extends HttpServlet {
    private static HibernateProvider connectionProvider;
    private static DeveloperStorage developerStorage;
    private static CompanyStorage companyStorage;
    private static CompanyService companyService;
    private static CustomerStorage customerStorage;
    private static CustomerService customerService;
    private static ProjectStorage projectStorage;
    private static ProjectService projectService;
    private static SkillStorage skillStorage;
    private static RelationStorage relationStorage;
    private static RelationService relationService;

    @Override
    public void init() throws ServletException {
        connectionProvider = new HibernateProvider();
        try {
            skillStorage = new SkillStorage(connectionProvider);
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String projectName = req.getParameter("projectName");
        String result = "";
        long expenses = 0;
        if(projectService.findByName(projectName).isPresent()) {
            result = "Sum of salary all developers participate in the project  '" + projectName + "'  -  ";
            expenses = projectService.getProjectExpences(projectName);
        } else {
            result = "There is no project with name '" + projectName + "' in the database. Please enter correct name.";
        }
        req.setAttribute("expenses", expenses);
        req.setAttribute("result", result);
        req.getRequestDispatcher("/WEB-INF/view/project/projectExpenses.jsp").forward(req, resp);
    }

}
