package model.storage;

import controller.customerController.config.HibernateProvider;
import model.dao.ProjectDao;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class ProjectStorage implements Storage<ProjectDao> {

    private static HibernateProvider connectionProvider;
    private CompanyStorage companyStorage;
    private CustomerStorage customerStorage;

    private final String GET_ALL_INFO = "SELECT * FROM project";
    private final String GET_COMPANY_PROJECTS =
     "SELECT * FROM project JOIN company ON project.company_id = company.company_id WHERE company_name LIKE ?";
    private final String GET_CUSTOMER_PROJECTS =
            "SELECT * FROM project JOIN customer ON project.customer_id = customer.customer_id WHERE customer_name LIKE ?";
    private final String GET_ID_BY_NAME =
     "SELECT project_id FROM project WHERE project_name LIKE ?";
    private final String INSERT_PROJECT_DEVELOPER =
     "INSERT INTO project_developer(project_id, developer_id) VALUES (?, ?)";
    private final String FIND_BY_NAME = "SELECT * FROM project WHERE project_name  LIKE  ?";
    private final String FIND_BY_ID = "SELECT * FROM project WHERE project_id = ?";
    private final String INSERT = "INSERT INTO project(project_name, company_id, customer_id, cost, start_date) VALUES (?, ?, ?, ?, ?)";
    private  final String  GET_PROJECTS_NAME_BY_DEVELOPER_ID =
    "SELECT  project_name FROM project JOIN   project_developer ON project_developer.project_id = project.project_id " +
    "JOIN developer ON developer.developer_id = project_developer.developer_id WHERE developer.developer_id = ?";
    private  final String  GET_PROJECTS_IDS_BY_DEVELOPER_ID =
            "SELECT  project_id FROM  project_developer  WHERE developer_id = ?";
    private final String GET_PROJECT_EXPENCES =
            "SELECT SUM(salary) FROM project JOIN project_developer " +
                    "ON project.project_id = project_developer.project_id " +
                    "JOIN developer ON project_developer.developer_id = developer.developer_id " +
                    " WHERE project_name  LIKE  ?";
    private final String UPDATE =
            "UPDATE project SET company_id=?, customer_id=?, cost=?, start_date=? WHERE project_name LIKE ? RETURNING *";
    private  final String DELETE = "DELETE FROM project WHERE project_name LIKE  ?";

    public ProjectStorage (HibernateProvider connectionProvider, CompanyStorage companyStorage,
                                             CustomerStorage customerStorage) throws SQLException {
        this.connectionProvider = connectionProvider;
        this.companyStorage = companyStorage;
        this.customerStorage = customerStorage;
    }

    @Override
    public Optional<ProjectDao> findById(long id) {
//        try(Connection connection = manager.getConnection();
//            PreparedStatement statement = connection.prepareStatement(FIND_BY_ID)) {
//            statement.setLong(1, id);
//            ResultSet resultSet = statement.executeQuery();
//            ProjectDao projectDao = mapProjectDao(resultSet);
//            return Optional.ofNullable(projectDao);
//        }
//        catch (SQLException exception) {
//            exception.printStackTrace();
//        }
        return Optional.empty();
    }

    @Override
    public Optional<ProjectDao> findByName(String name) {
        try (Session session = connectionProvider.openSession()) {
            Transaction transaction = session.beginTransaction();
            return session.createQuery("FROM ProjectDao as p WHERE p.projectName like :name"
                            , ProjectDao.class)
                    .setParameter("name", name).uniqueResultOptional();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Set<ProjectDao> findAll() {
        try (Session session = connectionProvider.openSession()) {
            Transaction transaction = session.beginTransaction();
            return session.createQuery("select p FROM ProjectDao p", ProjectDao.class)
                    .getResultStream().collect(Collectors.toSet());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return new HashSet<>();
    }

    @Override
    public ProjectDao save(ProjectDao entity) {
        try (Session session = connectionProvider.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return entity;
    }

    @Override
    public ProjectDao update(ProjectDao entity) {
        ProjectDao updatedProjectDao = null;
        try (Session session = connectionProvider.openSession()) {
            Transaction transaction = session.beginTransaction();
            updatedProjectDao = session.merge(entity);
            transaction.commit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return updatedProjectDao;
    }

    @Override
    public List<String>  delete(ProjectDao entity) {
//        try (Connection connection = manager.getConnection();
//             PreparedStatement statement = connection.prepareStatement(DELETE)) {
//            statement.setString(1, entity.getProject_name());
//            statement.executeUpdate();
//        }
//        catch (SQLException exception) {
//            exception.printStackTrace();
//        }
        return null;
    }

    public List<ProjectDao> getCompanyProjects (String companyName) {
        List<ProjectDao> companyProjectList = new ArrayList<>();
//        try (Connection connection = manager.getConnection();
//            PreparedStatement statement = connection.prepareStatement(GET_COMPANY_PROJECTS)) {
//            statement.setString(1, companyName);
//            ResultSet rs = statement.executeQuery();
//            while (rs.next()) {
//                ProjectDao projectDao = new ProjectDao();
//                projectDao.setProject_id(rs.getLong("project_id"));
//                projectDao.setProject_name(rs.getString("project_name"));
//                projectDao.setCompanyDao(companyStorage.findById(rs.getLong("company_id")).get());
//                projectDao.setCustomerDao(customerStorage.findById(rs.getLong("customer_id")).get());
//                projectDao.setCost(rs.getInt("cost"));
//                projectDao.setStart_date(Date.valueOf(LocalDate.parse(rs.getString("start_date"),
//                        DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
//                companyProjectList.add(projectDao);
//            }
//        }
//        catch (SQLException exception) {
//            exception.printStackTrace();
//        }
        return companyProjectList;
    }

    public List<ProjectDao> getCustomerProjects (String customerName) {
        List<ProjectDao> customerProjectList = new ArrayList<>();
//        try (Connection connection = manager.getConnection();
//            PreparedStatement statement = connection.prepareStatement(GET_CUSTOMER_PROJECTS)) {
//            statement.setString(1, customerName);
//            ResultSet rs = statement.executeQuery();
//            while (rs.next()) {
//                ProjectDao projectDao = new ProjectDao();
//                projectDao.setProject_id(rs.getLong("project_id"));
//                projectDao.setProject_name(rs.getString("project_name"));
//                projectDao.setCompanyDao(companyStorage.findById(rs.getLong("company_id")).get());
//                projectDao.setCustomerDao(customerStorage.findById(rs.getLong("customer_id")).get());
//                projectDao.setCost(rs.getInt("cost"));
//                projectDao.setStart_date(Date.valueOf(LocalDate.parse(rs.getString("start_date"),
//                        DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
//                customerProjectList.add(projectDao);
//            }
//        }
//        catch (SQLException exception) {
//            exception.printStackTrace();
//        }
        return customerProjectList;
    }
    public Optional<Long> getIdByName (String name) {
        Optional<Long> id = Optional.empty();
//        try (Connection connection = manager.getConnection();
//            PreparedStatement statement = connection.prepareStatement(GET_ID_BY_NAME)) {
//            statement.setString(1, name);
//            ResultSet rs = statement.executeQuery();
//            while (rs.next()) {
//                id = Optional.of(rs.getLong("project_id"));
//            }
//        }
//        catch (SQLException exception) {
//            exception.printStackTrace();
//        }
        return id;
    }

    public Set<ProjectDao> getProjectsNameByDeveloperId (long id) {
        try (Session session = connectionProvider.openSession()) {
            Transaction transaction = session.beginTransaction();
            return session.createQuery(
                   "SELECT p FROM ProjectDao p JOIN p.developers d WHERE d.developer_id = :developer_id",
                   ProjectDao.class).setParameter("developer_id", id).getResultStream().collect(Collectors.toSet());
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        return new HashSet<>();
    }

    public List<Long> getProjectIdsByDeveloperId (long id) {
        List<Long> projectIds = new ArrayList<>();
//        try (Connection connection = manager.getConnection();
//            PreparedStatement statement = connection.prepareStatement(GET_PROJECTS_IDS_BY_DEVELOPER_ID)) {
//            statement.setLong(1, id);
//            ResultSet rs = statement.executeQuery();
//            while (rs.next()) {
//                projectIds.add(rs.getLong("project_id"));
//            }
//        }
//        catch (SQLException exception) {
//            exception.printStackTrace();
//        }
        return projectIds;
    }

    public long getProjectExpences(String name) {
        long expences = 0;
        try (Session session = connectionProvider.openSession()) {
            Transaction transaction = session.beginTransaction();
            expences = session.createQuery(
                    "SELECT SUM(d.salary) FROM DeveloperDao d JOIN d.projects p WHERE p.projectName = :name",
                    Long.class).setParameter("name", name).getSingleResult();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        return expences;
    }

}
