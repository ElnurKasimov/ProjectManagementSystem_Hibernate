package controller.developerController;

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
import java.util.HashMap;
import java.util.Set;

@WebServlet(urlPatterns = "/developer/update_find")
public class UpdateDeveloperFind extends HttpServlet {
    private static HibernateProvider connectionProvider;
    private static DeveloperStorage developerStorage;
    private static DeveloperService developerService;
    private static CompanyStorage companyStorage;
    private static CompanyService companyService;
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
            developerStorage = new DeveloperStorage(connectionProvider, companyStorage);
            developerService = new DeveloperService(developerStorage, companyStorage, skillService);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String result = "";
        String lastName = req.getParameter("lastName");
        String firstName = req.getParameter("firstName");
        result = developerService.findDeveloperForUpdate(lastName, firstName);
        if( result.equals("")) {
            req.setAttribute("lastName", lastName);
            req.setAttribute("firstName", firstName);
            HashMap<String, Set<ProjectDto>> fullCompanies = companyService.findAllCompaniesForMenu();
            req.setAttribute("fullCompanies", fullCompanies);
            req.getRequestDispatcher("/WEB-INF/view/developer/updateDeveloperForm.jsp").forward(req, resp);
        } else {
            req.setAttribute("result", result);
            req.getRequestDispatcher("/WEB-INF/view/developer/updateDeveloperFind.jsp").forward(req, resp);
        }
    }

}
