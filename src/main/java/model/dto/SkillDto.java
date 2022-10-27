package model.dto;

import model.dao.DeveloperDao;

import java.util.Set;

public class SkillDto {
    private long skillId;
    private String language;
    private String level;
    private Set<DeveloperDto> developers;
    
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

    public SkillDto(long skillId, String language, String level, Set<DeveloperDto> developers) {
        this.skillId = skillId;
        this.language = language;
        this.level = level;
        this.developers = developers;
    }

    public Set<DeveloperDto> getDevelopers() {
        return developers;
    }

    public void setDevelopers(Set<DeveloperDto> developers) {
        this.developers = developers;
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

