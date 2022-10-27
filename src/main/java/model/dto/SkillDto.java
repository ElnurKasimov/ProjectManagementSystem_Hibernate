package model.dto;

import model.dao.DeveloperDao;

import java.util.Set;

public class SkillDto {
    private long skillId;
    private String language;
    private String level;

    public SkillDto(String language, String level) {
        this.language = language;
        this.level = level;
    }

    public SkillDto() {
    }

    public SkillDto(long skillId, String language, String level) {
        this.skillId = skillId;
        this.language = language;
        this.level = level;
    }

    public long getSkillId() {
        return skillId;
    }

    public void setSkillId(long skillId) {
        this.skillId = skillId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}

