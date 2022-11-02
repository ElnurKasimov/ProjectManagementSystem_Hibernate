package controller.projectController;

import model.config.HibernateProvider;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/project/project_expenses/form")
public class ProjectExpencesForm extends HttpServlet {
    private static HibernateProvider connectionProvider;

    @Override
    public void init() throws ServletException {
        connectionProvider = new HibernateProvider();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
               req.getRequestDispatcher("/WEB-INF/view/project/projectExpensesForm.jsp").forward(req, resp);
    }

}
