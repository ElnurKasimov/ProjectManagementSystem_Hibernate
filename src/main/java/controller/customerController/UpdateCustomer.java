package controller.customerController;

import controller.customerController.config.HibernateProvider;
import model.dto.CustomerDto;
import model.service.CustomerService;
import model.storage.CustomerStorage;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/customer/update")
public class UpdateCustomer extends HttpServlet {
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
        CustomerDto customerDtoToUpdate = new CustomerDto(customerName, CustomerDto.Reputation.valueOf(reputation));
        String result = customerService.updateCustomer(customerDtoToUpdate);
        req.setAttribute("result", result);
        req.getRequestDispatcher("/WEB-INF/view/customer/updateCustomer.jsp").forward(req, resp);

    }
}
