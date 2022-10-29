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
    public CompanyDao save(CompanyDao entity) {
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
    public CompanyDao update(CompanyDao entity) {
       CompanyDao updatedCompanyDao = null;
        try (Session session = connectionProvider.openSession()) {
            Transaction transaction = session.beginTransaction();
            updatedCompanyDao = session.merge(entity);
//            session.createQuery("update CompanyDao c set c.companyName=:name,  c.rating=:rating  WHERE c.company_id=:id", CompanyDao.class)
//                    .setParameter("name", entity.getCompanyName())
//                    .setParameter("rating", entity.getRating())
//                    .setParameter("id", entity.getCompany_id())
//                    .executeUpdate();
            transaction.commit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return updatedCompanyDao;
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
