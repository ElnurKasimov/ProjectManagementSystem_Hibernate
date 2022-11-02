package model.storage;

import controller.customerController.config.HibernateProvider;
import model.dao.CompanyDao;
import model.dao.CustomerDao;
import model.dao.DeveloperDao;
import model.dao.ProjectDao;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.*;
import java.util.stream.Collectors;


public class DeveloperStorage implements Storage<DeveloperDao>{
    private static HibernateProvider connectionProvider;
    private CompanyStorage companyStorage;
    private final String GET_ALL_INFO = "SELECT * FROM developer";
    private final String FIND_BY_NAME = "SELECT * FROM developer WHERE lastName LIKE ? and firstName LIKE ? ";
    private final String INSERT = "INSERT INTO developer(lastName, firstName, age, company_id, salary) VALUES (?, ?, ?, ?, ?)";

    private final String GET_LIST_LANGUAGE_DEVELOPERS =
            "SELECT lastName, firstName, level FROM developer JOIN developer_skill " +
             "ON developer.developer_id = developer_skill.developer_id " +
             "JOIN skill ON developer_skill.skill_id = skill.skill_id WHERE language LIKE ?";

    private final String GET_LIST_LEVEL_DEVELOPERS =
            "SELECT lastName, firstName, language FROM developer JOIN developer_skill " +
                    "ON developer.developer_id = developer_skill.developer_id " +
                    "JOIN skill ON developer_skill.skill_id = skill.skill_id WHERE level LIKE ?";
    private final String GET_PROJECT_DEVELOPERS =
            "SELECT lastname, firstname FROM project " +
            "JOIN project_developer ON project_developer.project_id = project.project_id " +
            "JOIN developer ON developer.developer_id = project_developer.developer_id " +
            "WHERE project_name LIKE ?";
    private final String GET_QUANTITY_PROJECT_DEVELOPERS =
            "SELECT COUNT(developer_id) FROM project JOIN project_developer " +
                    "ON project.project_id = project_developer.project_id " +
                    " WHERE project_name  LIKE  ?";
    private final String UPDATE =
            "UPDATE developer SET age=?, salary=?, company_id=? WHERE lastName LIKE ? AND firstName LIKE ? RETURNING *";
    private final String GET_ID_BY_NAME =
            "SELECT developer_id FROM developer WHERE  lastName LIKE ? AND firstName LIKE ?";
    private  final String DELETE = "DELETE FROM developer WHERE lastName LIKE ? AND firstName LIKE ?";

    public DeveloperStorage (HibernateProvider connectionProvider, CompanyStorage companyStorage,
                             SkillStorage skillStorage) {
        this.connectionProvider = connectionProvider;
        this.companyStorage = companyStorage;

    }



    @Override
    public Optional<DeveloperDao> findById(long id) {return null;
    }

   @Override
   public Optional<DeveloperDao> findByName(String name) {return null;}

    public Optional<DeveloperDao> findByName(String lastName, String firstName) {
        try (Session session = connectionProvider.openSession()) {
            Transaction transaction = session.beginTransaction();
            return session.createQuery("FROM DeveloperDao as d WHERE d.lastName LIKE :lastName AND d.firstName LIKE :firstName"
                            , DeveloperDao.class)
                    .setParameter("lastName", lastName).setParameter("firstName", firstName).uniqueResultOptional();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return Optional.empty();
    }

    public long getIdByName(String lastName, String firstName) {
//        try(Connection connection = manager.getConnection();
//            PreparedStatement statement = connection.prepareStatement(FIND_BY_NAME)) {
//            statement.setString(1, lastName);
//            statement.setString(2, firstName);
//            ResultSet resultSet = statement.executeQuery();
//            return resultSet.getLong("developer_id");
//        }
//        catch (SQLException exception) {
//            exception.printStackTrace();
//        }
        return 0;
    }
    @Override
    public Set<DeveloperDao> findAll() {
        try (Session session = connectionProvider.openSession()) {
            Transaction transaction = session.beginTransaction();
            return session.createQuery("select d FROM DeveloperDao d", DeveloperDao.class)
                    .getResultStream().collect(Collectors.toSet());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return new HashSet<>();
    }

    public Set<DeveloperDao> getDevelopersWithCertainLanguage(String language) {
        try (Session session = connectionProvider.openSession()) {
            Transaction transaction = session.beginTransaction();
            return session.createQuery(
                    "SELECT d FROM DeveloperDao d JOIN d.skills s WHERE s.language = :language", DeveloperDao.class)
                    .setParameter("language", language).getResultStream().collect(Collectors.toSet());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return new HashSet<>();
    }

    public Set<DeveloperDao> getDevelopersWithCertainLevel(String level) {
        try (Session session = connectionProvider.openSession()) {
            Transaction transaction = session.beginTransaction();
            return session.createQuery(
                            "SELECT d FROM DeveloperDao d JOIN d.skills s WHERE s.level = :level", DeveloperDao.class)
                    .setParameter("level", level).getResultStream().collect(Collectors.toSet());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return new HashSet<>();
    }

    public Set<DeveloperDao> getDevelopersByProjectName(String name) {
        try (Session session = connectionProvider.openSession()) {
            Transaction transaction = session.beginTransaction();
            ProjectDao projectDao = session.createQuery("FROM ProjectDao p WHERE p.projectName = :name", ProjectDao.class)
                    .setParameter("name", name).getSingleResult();
            return projectDao.getDevelopers();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return new HashSet<>();
    }

    public long getQuantityOfProjectDevelopers(String name) {
        long quantity = 0;
        try (Session session = connectionProvider.openSession()) {
            Transaction transaction = session.beginTransaction();
            quantity =  session.createQuery(
                            "SELECT COUNT(*) FROM DeveloperDao d JOIN d.projects p WHERE p.projectName = :name", Long.class)
                    .setParameter("name", name)
                    .getSingleResult()
            ;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return quantity;
    }

    @Override
    public DeveloperDao save(DeveloperDao entity) {
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
    public DeveloperDao update(DeveloperDao entity) {
        DeveloperDao updatedDeveloper=null;
        try (Session session = connectionProvider.openSession()) {
            Transaction transaction = session.beginTransaction();
            updatedDeveloper = session.merge(entity);
            transaction.commit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return updatedDeveloper;
    }


    @Override
    public List<String>  delete(DeveloperDao entity) {
        List<String> result = new ArrayList<>();
        try (Session session = connectionProvider.openSession()) {
            Transaction transaction = session.beginTransaction();
            String companyName = entity.getCompany().getCompanyName();
            CompanyDao companyDao = companyStorage.findByName(companyName).get();
            Set<DeveloperDao> companyDevelopers = companyDao.getDevelopers();
            companyDevelopers.remove(entity);
            session.merge(companyDao);
            session.clear();
            session.remove(entity);
            entity.getProjects().forEach(project -> project.getDevelopers().remove(entity));
            entity.getSkills().forEach(skill -> skill.getDevelopers().remove(entity));
            transaction.commit();
            result.add(String.format("Developer '%s %s' successfully deleted from the database with all nesessary relations.",
                    entity.getLastName(), entity.getFirstName()));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return result;
    }

}
