package controller.customerController;

import model.config.HibernateProvider;
import model.dto.CustomerDto;
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

@WebServlet(urlPatterns = "/customer/list_all_customers")
public class ListOfAllCustomer extends HttpServlet {
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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<CustomerDto> customers = customerService.findAllCustomers();
        req.setAttribute("customers", customers);
        req.getRequestDispatcher("/WEB-INF/view/customer/listAllCustomers.jsp").forward(req, resp);
    }

}
