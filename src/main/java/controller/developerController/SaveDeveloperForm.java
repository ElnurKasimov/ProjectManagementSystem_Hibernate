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

@WebServlet(urlPatterns = "/developer/save/form")
public class SaveDeveloperForm extends HttpServlet {
    private static HibernateProvider connectionProvider;
    private static CompanyStorage companyStorage;
    private static CompanyService companyService;

    @Override
    public void init() throws ServletException {
        connectionProvider = new HibernateProvider();
        try {
            companyStorage = new CompanyStorage(connectionProvider);
            companyService = new CompanyService(companyStorage);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HashMap<String, Set<ProjectDto>> fullCompanies = companyService.findAllCompaniesForMenu();
        req.setAttribute("fullCompanies", fullCompanies);
        req.getRequestDispatcher("/WEB-INF/view/developer/saveDeveloperForm.jsp").forward(req, resp);
    }

}
