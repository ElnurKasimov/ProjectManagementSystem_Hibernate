package model.service.converter;

import model.dao.SkillDao;
import model.dto.SkillDto;

public class SkillConverter{

    public static SkillDto from(SkillDao entity) {
        SkillDto  skillDto = new SkillDto();
        skillDto.setSkill_id(entity.getSkill_id());
        skillDto.setLanguage(entity.getLanguage());
        skillDto.setLevel(entity.getLevel());
        return skillDto;
    }

    public static SkillDao to(SkillDto entity) {
        SkillDao  skillDao = new SkillDao();
        skillDao.setSkill_id(entity.getSkill_id());
        skillDao.setLanguage(entity.getLanguage());
        skillDao.setLevel(entity.getLevel());
        return skillDao;
    }
}

