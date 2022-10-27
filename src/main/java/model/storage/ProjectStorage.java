package model.storage;

import model.config.HibernateProvider;
import model.dao.CompanyDao;
import model.dao.CustomerDao;
import model.dao.DeveloperDao;
import model.dao.ProjectDao;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    public ProjectDao save(ProjectDao entity) {
//        try (Connection connection = manager.getConnection();
//            PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)){
//            statement.setString(1, entity.getProject_name());
//            statement.setLong(2, entity.getCompanyDao().getCompany_id());
//            statement.setLong(3, entity.getCustomerDao().getCustomer_id());
//            statement.setInt(4, entity.getCost());
//            statement.setDate(5,  entity.getStart_date());
//            statement.executeUpdate();
//            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
//                if (generatedKeys.next()) {
//                    entity.setProject_id(generatedKeys.getInt(1));
//                }
//                else {
//                    throw new SQLException("Project saving was interrupted, ID has not been obtained.");
//                }
//            }
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//            throw new RuntimeException("The project was not created");
//        }
        return entity;
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
//        try(Connection connection = manager.getConnection();
//            PreparedStatement statement = connection.prepareStatement(FIND_BY_NAME)) {
//            statement.setString(1, name);
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
    public boolean isExist(long id) {
        return false;
    }

    @Override
    public boolean isExist(String name) {return findByName(name).isPresent();}

    @Override
    public ProjectDao update(ProjectDao entity) {
        ProjectDao projectDao = null;
//        try (Connection connection = manager.getConnection();
//            PreparedStatement statement = connection.prepareStatement(UPDATE)) {
//            statement.setLong(1, entity.getCompanyDao().getCompany_id());
//            statement.setLong(2, entity.getCustomerDao().getCustomer_id());
//            statement.setInt(3, entity.getCost());
//            statement.setDate(4, entity.getStart_date());
//            statement.setString(5, entity.getProject_name());
//            ResultSet resultSet = statement.executeQuery();
//            projectDao = mapProjectDao(resultSet);
//        }
//        catch (SQLException exception) {
//            exception.printStackTrace();
//        }
        return projectDao;
    }

    @Override
    public void delete(ProjectDao entity) {
//        try (Connection connection = manager.getConnection();
//             PreparedStatement statement = connection.prepareStatement(DELETE)) {
//            statement.setString(1, entity.getProject_name());
//            statement.executeUpdate();
//        }
//        catch (SQLException exception) {
//            exception.printStackTrace();
//        }
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

    public List<String> getProjectsNameByDeveloperId (long id) {
        List<String> projectNames = new ArrayList<>();
//        try (Connection connection = manager.getConnection();
//             PreparedStatement statement = connection.prepareStatement(GET_PROJECTS_NAME_BY_DEVELOPER_ID)) {
//            statement.setLong(1, id);
//            ResultSet rs = statement.executeQuery();
//            while (rs.next()) {
//                projectNames.add(rs.getString("project_name"));
//            }
//        }
//        catch (SQLException exception) {
//            exception.printStackTrace();
//        }
        return projectNames;
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
//        try(Connection connection = manager.getConnection();
//            PreparedStatement statement = connection.prepareStatement(GET_PROJECT_EXPENCES)) {
//            statement.setString(1, name);
//            ResultSet rs = statement.executeQuery();
//            while (rs.next()) {
//                expences = rs.getLong("sum");
//            }
//        }
//        catch (SQLException exception) {
//            exception.printStackTrace();
//        }
        return expences;
    }

    private ProjectDao mapProjectDao(ResultSet resultSet) throws SQLException {
        ProjectDao projectDao = null;
//        while (resultSet.next()) {
//            projectDao = new ProjectDao();
//            projectDao.setProject_id(resultSet.getLong("project_id"));
//            projectDao.setProject_name(resultSet.getString("project_name"));
//            CompanyDao companyDao = companyStorage.findById(resultSet.getLong("company_id")).get();
//            projectDao.setCompanyDao(companyDao);
//            CustomerDao customerDao = customerStorage.findById(resultSet.getLong("customer_id")).get();
//            projectDao.setCustomerDao(customerDao);
//            projectDao.setCost(resultSet.getInt("cost"));
//            projectDao.setStart_date(Date.valueOf(LocalDate.parse(resultSet.getString("start_date"),
//                    DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
//        }
        return projectDao;
    }

}
