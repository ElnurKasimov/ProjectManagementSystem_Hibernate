package model.service.converter;

import model.dao.SkillDao;
import model.dto.SkillDto;

public class SkillConverter{

    public static SkillDto from(SkillDao entity) {
        SkillDto  skillDto = new SkillDto();
        skillDto.setSkillId(entity.getSkillId());
        skillDto.setLanguage(entity.getLanguage());
        skillDto.setLevel(entity.getLevel());
        return skillDto;
    }

    public static SkillDao to(SkillDto entity) {
        SkillDao  skillDao = new SkillDao();
        skillDao.setSkillId(entity.getSkillId());
        skillDao.setLanguage(entity.getLanguage());
        skillDao.setLevel(entity.getLevel());
        return skillDao;
    }
}

