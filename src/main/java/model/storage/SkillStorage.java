package model.storage;

import controller.customerController.config.HibernateProvider;
import model.dao.CompanyDao;
import model.dao.SkillDao;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class SkillStorage implements Storage<SkillDao> {
    private static HibernateProvider connectionProvider;

    private final String INSERT = "INSERT INTO skill(language, level) VALUES (?, ?)";
    private final String FIND_BY_NAME = "SELECT * FROM skill WHERE language LIKE ? and level LIKE ? ";
    private final String GET_SKILLS_BY_DEVELOPER_ID =
     "SELECT  language, level FROM developer JOIN developer_skill ON developer_skill.developer_id = developer.developer_id " +
     "JOIN skill ON skill.skill_id = developer_skill.skill_id WHERE developer.developer_id = ?";
    private final String COUNT_BY_LANGUAGE = "SELECT COUNT(skill_id) FROM skill WHERE language LIKE ?";
    private final String COUNT_BY_LEVEL = "SELECT COUNT(skill_id) FROM skill WHERE level LIKE ?";
    private  final String  GET_SKILL_IDS_BY_DEVELOPER_ID =
            "SELECT  skill_id FROM  developer_skill  WHERE developer_id = ?";

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

    public List<Long> getSkillIdsByDeveloperId (long id) {
        List<Long> skillIds = new ArrayList<>();
//        try (Connection connection = manager.getConnection();
//             PreparedStatement statement = connection.prepareStatement(GET_SKILL_IDS_BY_DEVELOPER_ID)) {
//            statement.setLong(1, id);
//            ResultSet rs = statement.executeQuery();
//            while (rs.next()) {
//                skillIds.add(rs.getLong("skill_id"));
//            }
//        }
//        catch (SQLException exception) {
//            exception.printStackTrace();
//        }
        return skillIds;
    }

    @Override
    public Optional<SkillDao> findById(long id) {
        return Optional.empty();
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

    public long countByLanguage(String language) {
//        try(Connection connection = manager.getConnection();
//            PreparedStatement statement = connection.prepareStatement(COUNT_BY_LANGUAGE)) {
//            statement.setString(1, language);
//            ResultSet rs = statement.executeQuery();
//            int quantity = 0;
//            while (rs.next()) {
//              quantity = rs.getInt("count");
//            }
//            return quantity;
//        }
//        catch (SQLException exception) {
//            exception.printStackTrace();
//        }
        return 0;
    }
    public long countByLevel(String level) {
//        try(Connection connection = manager.getConnection();
//            PreparedStatement statement = connection.prepareStatement(COUNT_BY_LEVEL)) {
//            statement.setString(1, level);
//            ResultSet rs = statement.executeQuery();
//            int quantity = 0;
//            while (rs.next()) {
//                quantity = rs.getInt("count");
//            }
//            return quantity;
//        }
//        catch (SQLException exception) {
//            exception.printStackTrace();
//        }
        return 0;
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
