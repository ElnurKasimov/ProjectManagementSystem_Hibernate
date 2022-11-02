package model.storage;


import model.config.HibernateProvider;
import model.dao.CompanyDao;
import model.dao.DeveloperDao;
import model.dao.ProjectDao;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class CompanyStorage implements Storage<CompanyDao> {
    private static HibernateProvider connectionProvider;

    public CompanyStorage (HibernateProvider connectionProvider) throws SQLException {
        this.connectionProvider = connectionProvider;
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
            transaction.commit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return updatedCompanyDao;
    }

    @Override
    public List<String> delete(CompanyDao entity) {
        List<String> result = new ArrayList<>();
        try (Session session = connectionProvider.openSession()) {
            Transaction transaction = session.beginTransaction();
            Set<DeveloperDao> developers = entity.getDevelopers();
            Set<ProjectDao> projects = entity.getProjects();
            if (developers.isEmpty() && projects.isEmpty()) {
                session.remove(entity);
                transaction.commit();
                result.add("Company " + entity.getCompanyName() + " successfully deleted from the database");
            } else {
                result.add("Please note that " + entity.getCompanyName() + ":");
                result.add("- develops such projects :");
                projects.forEach(project -> result.add(project.getProjectName()));
                result.add(" - employs the following developers:");
                developers.forEach(dev -> result.add(dev.getLastName() + " " +dev.getFirstName()));
                result.add("Before deleting the company change the data of the relevant projects and developers first.");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return result;
    }

}
