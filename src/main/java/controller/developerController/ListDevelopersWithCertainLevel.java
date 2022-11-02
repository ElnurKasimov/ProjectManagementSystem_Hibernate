package controller.developerController;

import model.config.HibernateProvider;
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

@WebServlet(urlPatterns = "/developer/level_developers")
public class ListDevelopersWithCertainLevel extends HttpServlet {
    private static HibernateProvider connectionProvider;
    private static DeveloperStorage developerStorage;
    private static DeveloperService developerService;
    private static CompanyStorage companyStorage;
    private static SkillService skillService;
    private static SkillStorage skillStorage;

    @Override
    public void init() throws ServletException {
        connectionProvider = new HibernateProvider();
        try {
            skillStorage = new SkillStorage(connectionProvider);
            skillService = new SkillService(skillStorage);
            companyStorage = new CompanyStorage(connectionProvider);
            developerStorage = new DeveloperStorage(connectionProvider, companyStorage);
            developerService = new DeveloperService(developerStorage, companyStorage, skillService);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String level = req.getParameter("level");
        List<String> developersList = developerService.getListNamesDevelopersWithCertainLevel(level);
        req.setAttribute("level", level);
        req.setAttribute("list", developersList);
        req.getRequestDispatcher("/WEB-INF/view/developer/listDevelopersWithCertainLevel.jsp").forward(req, resp);

    }
}
