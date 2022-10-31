package model.storage;

import controller.customerController.config.HibernateProvider;
import model.dao.CompanyDao;
import model.dao.CustomerDao;
import model.dao.DeveloperDao;
import model.dao.ProjectDao;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        try (Session session = connectionProvider.openSession()) {
            Transaction transaction = session.beginTransaction();
            return session.get(DeveloperDao.class, id).getProjects().stream().map(ProjectDao::getProject_id).collect(Collectors.toList());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return new ArrayList<>();
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
        List<String> result = new ArrayList<>();
        try (Session session = connectionProvider.openSession()) {
            Transaction transaction = session.beginTransaction();
            String companyName = entity.getCompany().getCompanyName();
            CompanyDao companyDao = companyStorage.findByName(companyName).get();
            Set<ProjectDao> companyProjects = companyDao.getProjects();
            companyProjects.remove(entity);
            session.merge(companyDao);
            String customerName = entity.getCustomer().getCustomerName();
            CustomerDao customerDao = customerStorage.findByName(customerName).get();
            Set<ProjectDao> customerProjects = customerDao.getProjects();
            customerProjects.remove(entity);
            session.merge(customerDao);
            session.clear();
            session.remove(entity);
            entity.getDevelopers().forEach(dev -> dev.getProjects().remove(entity));
            transaction.commit();
            result.add("Project " + entity.getProjectName() + " successfully deleted from the database");

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return result;
    }

}
