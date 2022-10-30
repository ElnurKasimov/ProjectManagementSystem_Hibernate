package controller.companyController;

import controller.customerController.config.HibernateProvider;
import model.dto.CompanyDto;
import model.dto.ProjectDto;
import model.service.CompanyService;
import model.storage.CompanyStorage;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Set;

@WebServlet(urlPatterns = "/company/list_projects")
public class ListCompanyProjectsResult extends HttpServlet {
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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String companyName = req.getParameter("companyName");
        Optional<CompanyDto> companyDto = companyService.findByName(companyName);
        boolean isPresent = companyDto.isPresent();
        req.setAttribute("isPresent", isPresent);
        Set<ProjectDto> projects = companyService.getCompanyProjects(companyName);
        req.setAttribute("projects", projects);
        req.getRequestDispatcher("/WEB-INF/view/company/listCompanyProjects.jsp").forward(req, resp);
    }




}
