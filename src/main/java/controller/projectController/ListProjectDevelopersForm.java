package controller.projectController;

import controller.customerController.config.HibernateProvider;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/project/list_project_developers/form")
public class ListProjectDevelopersForm extends HttpServlet {
    private static HibernateProvider connectionProvider;

    @Override
    public void init() throws ServletException {
        connectionProvider = new HibernateProvider();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
               req.getRequestDispatcher("/WEB-INF/view/project/listProjectDevelopersForm.jsp").forward(req, resp);
    }

}
