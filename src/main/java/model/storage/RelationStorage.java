package model.storage;

import controller.customerController.config.HibernateProvider;
import model.dao.DeveloperDao;
import model.dao.ProjectDao;
import model.dao.SkillDao;

import java.sql.SQLException;
import java.util.Set;

public class RelationStorage {
    private static HibernateProvider connectionProvider;

    public RelationStorage(HibernateProvider connectionProvider) throws SQLException {
        this.connectionProvider = connectionProvider;
    }

    public void saveProjectDeveloper(Set<ProjectDao> projectsDao, DeveloperDao developerDao) {
//        StringBuilder insertSql = new StringBuilder();
//        insertSql.append("INSERT INTO project_developer (project_id, developer_id)  VALUES ");
//        for (ProjectDao projectDao : projectsDao) {
//            insertSql.append("(?,?),");
//        }
//        insertSql.deleteCharAt(insertSql.length() - 1);
//        try (Connection connection = manager.getConnection();
//             PreparedStatement statement = connection.prepareStatement(insertSql.toString())) {
//            int index = 1;
//            for (ProjectDao projectDao : projectsDao) {
//                statement.setLong(index++, projectDao.getProject_id());
//                statement.setLong(index++, developerDao.getDeveloper_id());
//            }
//            statement.execute();
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//            throw new RuntimeException("Relation project_developer not created");
//        }
    }

    public void saveDeveloperSkill(DeveloperDao developerDao, Set<SkillDao> skillsDao) {
//        StringBuilder insertSql = new StringBuilder();
//        insertSql.append("INSERT INTO developer_skill (developer_id, skill_id)  VALUES ");
//        for (SkillDao skillDao : skillsDao) {
//            insertSql.append("(?,?),");
//        }
//        insertSql.deleteCharAt(insertSql.length() - 1);
//        try (Connection connection = manager.getConnection();
//             PreparedStatement statement = connection.prepareStatement(insertSql.toString())) {
//            int index = 1;
//            for (SkillDao skillDao : skillsDao) {
//                statement.setLong(index++, developerDao.getDeveloper_id());
//                statement.setLong(index++, skillDao.getSkill_id());
//            }
//            statement.execute();
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//            throw new RuntimeException("Relation project_developer not created");
//        }
    }

    public void deleteAllProjectsOfDeveloper(DeveloperDao developerDao){
//        try (Connection connection = manager.getConnection();
//        PreparedStatement statement = connection.prepareStatement("DELETE FROM project_developer WHERE developer_id =?")){
//            statement.setLong(1, developerDao.getDeveloper_id());
//            statement.execute();
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//            throw new RuntimeException("Relations haven't been deleted from project_developer");
//        }
    }

    public void deleteAllSkillsOfDeveloper(DeveloperDao developerDao){
//        try (Connection connection = manager.getConnection();
//             PreparedStatement statement = connection.prepareStatement("DELETE FROM developer_skill WHERE developer_id =?")){
//            statement.setLong(1, developerDao.getDeveloper_id());
//            statement.execute();
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//            throw new RuntimeException("Relations haven't been deleted from developer_skill");
//        }
    }

    public void  deleteAllDevelopersOfProject(ProjectDao projectDao){
//        try (Connection connection = manager.getConnection();
//             PreparedStatement statement = connection.prepareStatement("DELETE FROM project_developer WHERE project_id =?")){
//            statement.setLong(1, projectDao.getProject_id());
//            statement.execute();
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//            throw new RuntimeException("Relations haven't been deleted from project_developer");
//        }
    }

    public void  deleteDeveloperFromProjectDeveloper(DeveloperDao developerDao){
//        try (Connection connection = manager.getConnection();
//             PreparedStatement statement = connection.prepareStatement("DELETE FROM project_developer WHERE developer_id =?")){
//            statement.setLong(1,developerDao.getDeveloper_id());
//            statement.execute();
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//            throw new RuntimeException("Relations haven't been deleted from project_developer");
//        }
    }

    public void  deleteDeveloperFromDeveloperSkill(DeveloperDao developerDao){
//        try (Connection connection = manager.getConnection();
//             PreparedStatement statement = connection.prepareStatement("DELETE FROM developer_skill WHERE developer_id =?")){
//            statement.setLong(1, developerDao.getDeveloper_id());
//            statement.execute();
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//            throw new RuntimeException("Relations haven't been deleted from developer_skill");
//        }
    }

}
