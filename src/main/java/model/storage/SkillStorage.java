package model.storage;

import model.config.HibernateProvider;
import model.dao.SkillDao;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class SkillStorage implements Storage<SkillDao> {
    private static HibernateProvider connectionProvider;

    public SkillStorage(HibernateProvider connectionProvider) throws SQLException {
        this.connectionProvider =connectionProvider;
    }

    @Override
    public SkillDao save(SkillDao entity) {
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
    public Optional<SkillDao> findByName(String name) {
        return Optional.empty();
    }

    public Optional<SkillDao> findByName(String language, String level) {
        try (Session session = connectionProvider.openSession()) {
            Transaction transaction = session.beginTransaction();
            return session.createQuery("FROM SkillDao s WHERE s.language LIKE :language AND s.level LIKE :level"
                            , SkillDao.class)
                    .setParameter("language", language).setParameter("level", level).uniqueResultOptional();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Set<SkillDao> findAll() {
        return null;
    }

    @Override
    public SkillDao update(SkillDao entity) {return new SkillDao();}

    @Override
    public List<String>  delete(SkillDao entity) {
        return null;
    }

    public Set<SkillDao> getSkillSetByDeveloperId(long id) {
        Set<SkillDao> skillDaoSet = new HashSet<>();
        try (Session session = connectionProvider.openSession()) {
            Transaction transaction = session.beginTransaction();
            return session.createQuery(
                    "SELECT s FROM SkillDao s JOIN s.developers d WHERE d.developer_id = :developer_id",
                    SkillDao.class).setParameter("developer_id", id).getResultStream().collect(Collectors.toSet());
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        return skillDaoSet;
    }


}
