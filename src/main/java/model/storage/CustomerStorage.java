package model.storage;

import model.config.HibernateProvider;
import model.dao.CustomerDao;
import model.dao.ProjectDao;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class CustomerStorage implements Storage<CustomerDao> {
    private static HibernateProvider connectionProvider;

    public CustomerStorage(HibernateProvider connectionProvider) throws SQLException {
        this.connectionProvider = connectionProvider;
    }

    @Override
    public Optional<CustomerDao> findByName(String name) {
        try (Session session = connectionProvider.openSession()) {
            Transaction transaction = session.beginTransaction();
            return session.createQuery("FROM CustomerDao as c WHERE c.customerName like :name"
                            , CustomerDao.class)
                           .setParameter("name", name).uniqueResultOptional();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Set<CustomerDao> findAll() {
        try (Session session = connectionProvider.openSession()) {
            Transaction transaction = session.beginTransaction();
            return session.createQuery("select c FROM CustomerDao c", CustomerDao.class)
                    .getResultStream().collect(Collectors.toSet());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return new HashSet<>();
    }

    @Override
    public CustomerDao save(CustomerDao entity) {
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
    public CustomerDao update(CustomerDao entity) {
        CustomerDao updatedCustomerDao = null;
        try (Session session = connectionProvider.openSession()) {
            Transaction transaction = session.beginTransaction();
            updatedCustomerDao = session.merge(entity);
            transaction.commit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return updatedCustomerDao;
    }

    @Override
    public List<String>  delete(CustomerDao entity) {
        List<String> result = new ArrayList<>();
        try (Session session = connectionProvider.openSession()) {
            Transaction transaction = session.beginTransaction();
            Set<ProjectDao> projects = entity.getProjects();
            if (projects.isEmpty()) {
                session.remove(entity);
                transaction.commit();
                result.add("Customer " + entity.getCustomerName() + " successfully deleted from the database");
            } else {
                result.add("Please note that " + entity.getCustomerName() + "ordered such projects :");
                projects.forEach(project -> result.add(project.getProjectName()));
                result.add("Before deleting the customer change the data of the relevant projects first.");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return result;
    }


}
