package controller.developerController;

import model.config.HibernateProvider;
import model.dto.DeveloperDto;
import model.service.*;
import model.storage.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = "/developer/developer_info")
public class DeveloperInfoResult extends HttpServlet {
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
        String lastName = req.getParameter("lastName");
        String firstName = req.getParameter("firstName");
        List<String> projects = new ArrayList<>();
        List<String> skills = new ArrayList<>();
        boolean isPresent = false;
        DeveloperDto developerDto = developerService.getByName(lastName, firstName);
        if (developerDto != null) {
            isPresent = true;
            projects = projectService.getProjectsNameByDeveloperId(developerDto.getDeveloper_id());
            skills =  skillService.getSkillSetByDeveloperId(developerDto.getDeveloper_id());
        }
        req.setAttribute("isPresent", isPresent);
        req.setAttribute("developer", developerDto);
        req.setAttribute("projects", projects);
        req.setAttribute("skills", skills);
        req.getRequestDispatcher("/WEB-INF/view/developer/developerInfo.jsp").forward(req, resp);
    }

}
