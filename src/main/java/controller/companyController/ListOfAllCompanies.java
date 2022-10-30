package controller.companyController;

import controller.customerController.config.HibernateProvider;;
import model.dto.CompanyDto;

import model.service.*;
import model.storage.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Set;

@WebServlet(urlPatterns = "/company/list_all_companies")
public class ListOfAllCompanies extends HttpServlet {
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
        Set<CompanyDto> companies = companyService.findAllCompanies();
        req.setAttribute("companies", companies);
        req.getRequestDispatcher("/WEB-INF/view/company/listAllCompanies.jsp").forward(req, resp);

    }
}
