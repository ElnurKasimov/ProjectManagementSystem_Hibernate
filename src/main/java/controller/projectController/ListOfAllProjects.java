package controller.projectController;

import model.config.HibernateProvider;
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

@WebServlet(urlPatterns = "/project/list_all_projects")
public class ListOfAllProjects extends HttpServlet {
    private static HibernateProvider connectionProvider;
    private static DeveloperStorage developerStorage;
    private static CompanyStorage companyStorage;
    private static CompanyService companyService;
    private static CustomerStorage customerStorage;
    private static CustomerService customerService;
    private static ProjectStorage projectStorage;
    private static ProjectService projectService;

    @Override
    public void init() throws ServletException {
        connectionProvider = new HibernateProvider();
        try {
            companyStorage = new CompanyStorage(connectionProvider);
            companyService = new CompanyService(companyStorage);
            customerStorage = new CustomerStorage(connectionProvider);
            customerService = new CustomerService(customerStorage);
            developerStorage = new DeveloperStorage(connectionProvider, companyStorage);
            projectStorage = new ProjectStorage(connectionProvider, companyStorage, customerStorage);
            projectService = new ProjectService(projectStorage, developerStorage, companyService, customerService);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<ProjectDto> projects = projectService.findAllProjects();
        req.setAttribute("projects", projects);
        req.getRequestDispatcher("/WEB-INF/view/project/listAllProjects.jsp").forward(req, resp);

    }
}
