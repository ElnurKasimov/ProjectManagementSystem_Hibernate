package model.storage;


import model.config.HibernateProvider;
import model.dao.CompanyDao;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class CompanyStorage implements Storage<CompanyDao> {
    private static HibernateProvider connectionProvider;

    private final String GET_ALL_INFO = "SELECT * FROM company";
    private final String FIND_BY_NAME = "SELECT * FROM company WHERE company_name  LIKE  ?";
    private final String FIND_BY_ID = "SELECT * FROM company WHERE company_id = ?";
    private final String INSERT = "INSERT INTO company(company_name, rating) VALUES (?, ?)";
    private final String UPDATE =
            "UPDATE company SET rating=? WHERE company_name LIKE ? RETURNING *";
    private  final String DELETE = "DELETE FROM company WHERE company_name LIKE  ?";

    public CompanyStorage (HibernateProvider connectionProvider) throws SQLException {
        this.connectionProvider = connectionProvider;
    }

    @Override
    public CompanyDao save(CompanyDao entity) {
//        try (Connection connection = manager.getConnection();
//             PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)){
//            statement.setString(1, entity.getCompany_name());
//            statement.setString(2, entity.getRating().toString());
//            statement.executeUpdate();
//            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
//                if (generatedKeys.next()) {
//                    entity.setCompany_id(generatedKeys.getInt(1));
//                }
//                else {
//                    throw new SQLException("Company saving was interrupted, ID has not been obtained.");
//                }
//            }
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//            throw new RuntimeException("The company was not created");
//        }
        return entity;
    }

    @Override
    public Optional<CompanyDao> findById(long id) {
//        try(Connection connection = manager.getConnection();
//            PreparedStatement statement = connection.prepareStatement(FIND_BY_ID)) {
//            statement.setLong(1, id);
//            ResultSet resultSet = statement.executeQuery();
//            CompanyDao companyDao = mapCompanyDao(resultSet);
//            return Optional.ofNullable(companyDao);
//        }
//        catch (SQLException exception) {
//            exception.printStackTrace();
//        }
        return Optional.empty();
    }

    @Override
    public Optional<CompanyDao> findByName(String name) {
        try (Session session = connectionProvider.openSession()) {
            Transaction transaction = session.beginTransaction();
            return session.createQuery("FROM CompanyDao as c WHERE c.companyName like :name"
                            , CompanyDao.class)
                    .setParameter("name", name).uniqueResultOptional();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Set<CompanyDao> findAll() {
        try (Session session = connectionProvider.openSession()) {
            Transaction transaction = session.beginTransaction();
            return session.createQuery("select c FROM CompanyDao c", CompanyDao.class)
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
    public boolean isExist(String name) {
        return false;
    }

    @Override
    public CompanyDao update(CompanyDao entity) {
       CompanyDao companyDao = null;
//        try (Connection connection = manager.getConnection();
//            PreparedStatement statement = connection.prepareStatement(UPDATE)) {
//            statement.setString(1, entity.getRating().toString());
//            statement.setString(2, entity.getCompany_name());
//            ResultSet resultSet = statement.executeQuery();
//            companyDao = mapCompanyDao(resultSet);
//        }
//        catch (SQLException exception) {
//            exception.printStackTrace();
//        }
        return companyDao;
    }

    @Override
    public void delete(CompanyDao entity) {
//        try (Connection connection = manager.getConnection();
//             PreparedStatement statement = connection.prepareStatement(DELETE)) {
//            statement.setString(1, entity.getCompany_name());
//            statement.executeUpdate();
//        }
//        catch (SQLException exception) {
//            exception.printStackTrace();
//        }
    }

    private CompanyDao mapCompanyDao(ResultSet resultSet) throws SQLException {
        CompanyDao companyDao = null;
//        while (resultSet.next()) {
//            companyDao = new CompanyDao();
//            companyDao.setCompany_id(resultSet.getLong("company_id"));
//            companyDao.setCompany_name(resultSet.getString("company_name"));
//            companyDao.setRating(CompanyDao.Rating.valueOf(resultSet.getString("rating")));
//        }
        return companyDao;
    }
}
