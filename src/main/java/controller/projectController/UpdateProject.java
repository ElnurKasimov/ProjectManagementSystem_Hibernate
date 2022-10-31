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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@WebServlet(urlPatterns = "/project/update")
public class UpdateProject extends HttpServlet {
    private static HibernateProvider connectionProvider;
    private static DeveloperStorage developerStorage;
    private static CompanyStorage companyStorage;
    private static CompanyService companyService;
    private static CustomerStorage customerStorage;
    private static CustomerService customerService;
    private static ProjectStorage projectStorage;
    private static ProjectService projectService;
    private static SkillStorage skillStorage;

    @Override
    public void init() throws ServletException {
        connectionProvider = new HibernateProvider();
        try {
            skillStorage = new SkillStorage(connectionProvider);
            companyStorage = new CompanyStorage(connectionProvider);
            companyService = new CompanyService(companyStorage);
            customerStorage = new CustomerStorage(connectionProvider);
            customerService = new CustomerService(customerStorage);
            developerStorage = new DeveloperStorage(connectionProvider, companyStorage, skillStorage);
            projectStorage = new ProjectStorage(connectionProvider, companyStorage, customerStorage);
            projectService = new ProjectService(projectStorage, developerStorage, companyService, customerService);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<String> result = new ArrayList<>();
        String projectName = req.getParameter("projectName");
        String customerName = req.getParameter("customerName");
        int cost = Integer.parseInt(req.getParameter("cost"));
        String companyName = req.getParameter("companyName");
        String startDate = req.getParameter("startDate");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
        LocalDate startLocalDate = LocalDate.parse(startDate, dtf);
        java.sql.Date startSqlDate = java.sql.Date.valueOf(startLocalDate);
        result = projectService.updateProject(projectName, customerName, cost, companyName, startSqlDate);
        req.setAttribute("result", result);
        req.getRequestDispatcher("/WEB-INF/view/project/updateProject.jsp").forward(req, resp);

    }

}
