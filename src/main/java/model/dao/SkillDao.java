package model.dao;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "skill")
public class SkillDao {
    private long skill_id;
    private String language;
    private String level;

    public SkillDao(String language, String level) {
        this.language = language;
        this.level = level;
    }

    public SkillDao() {
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getSkill_id() {
        return skill_id;
    }
    @Column(name = "language")
    public String getLanguage() {
        return language;
    }
    @Column(name = "level")
    public String getLevel() {
        return level;
    }

    public void setSkill_id(long skill_id) {
        this.skill_id = skill_id;
    }


    public void setLanguage(String language) {
        this.language = language;
    }

    public void setLevel(String level) {
        this.level = level;
    }

}


