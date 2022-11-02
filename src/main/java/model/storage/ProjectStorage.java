package model.storage;

import model.config.HibernateProvider;
import model.dao.CompanyDao;
import model.dao.CustomerDao;
import model.dao.DeveloperDao;
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


    public ProjectStorage (HibernateProvider connectionProvider, CompanyStorage companyStorage,
                                             CustomerStorage customerStorage) throws SQLException {
        this.connectionProvider = connectionProvider;
        this.companyStorage = companyStorage;
        this.customerStorage = customerStorage;
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
