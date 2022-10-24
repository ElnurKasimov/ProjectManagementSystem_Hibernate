package model.dao;

import lombok.Data;

@Data
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

}

