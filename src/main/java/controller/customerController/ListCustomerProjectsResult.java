package controller.customerController;

import controller.customerController.config.HibernateProvider;
import model.dto.CompanyDto;
import model.dto.CustomerDto;
import model.dto.ProjectDto;
import model.service.CompanyService;
import model.service.CustomerService;
import model.storage.CompanyStorage;
import model.storage.CustomerStorage;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Set;

@WebServlet(urlPatterns = "/customer/list_projects")
public class ListCustomerProjectsResult extends HttpServlet {
    private static HibernateProvider connectionProvider;
    private static CustomerStorage customerStorage;
    private static CustomerService customerService;

    @Override
    public void init() throws ServletException {
        connectionProvider = new HibernateProvider();
        try {
            customerStorage = new CustomerStorage(connectionProvider);
            customerService = new CustomerService(customerStorage);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String customerName = req.getParameter("customerName");
        Optional<CustomerDto> customerDto = customerService.findByName(customerName);
        boolean isPresent = customerDto.isPresent();
        req.setAttribute("isPresent", isPresent);
        Set<ProjectDto> projects = customerService.getCustomerProjects(customerName);
        req.setAttribute("projects", projects);
        req.getRequestDispatcher("/WEB-INF/view/customer/listCustomerProjects.jsp").forward(req, resp);

    }




}
