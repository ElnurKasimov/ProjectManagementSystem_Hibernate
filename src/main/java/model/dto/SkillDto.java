package model.dto;

import lombok.Data;

@Data
public class SkillDto {
    private long skill_id;
    private String language;
    private String level;

    
    public SkillDto(String language, String level) {
        this.language = language;
        this.level = level;
    }

    public SkillDto() {
    }

}

