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
import java.util.Properties;

@WebServlet(urlPatterns = "/customer/add")
public class AddCustomer extends HttpServlet {
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
        String reputation = req.getParameter("reputation");
        CustomerDto newCustomerDto = new CustomerDto(customerName, CustomerDto.Reputation.valueOf(reputation));
        String result = customerService.save(newCustomerDto);
        req.setAttribute("result", result);
        req.getRequestDispatcher("/WEB-INF/view/customer/addCustomer.jsp").forward(req, resp);

    }
}
