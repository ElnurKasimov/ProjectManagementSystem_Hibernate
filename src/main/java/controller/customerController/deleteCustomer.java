package controller.customerController;

import controller.customerController.config.HibernateProvider;
import model.service.CustomerService;
import model.storage.CustomerStorage;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(urlPatterns = "/customer/delete")
public class deleteCustomer extends HttpServlet {
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
        List<String> result = customerService.deleteCustomer(customerName);
        req.setAttribute("result", result);
        req.getRequestDispatcher("/WEB-INF/view/customer/deleteCustomer.jsp").forward(req, resp);

    }
}
