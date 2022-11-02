package model.service;


import model.dao.SkillDao;
import model.dto.SkillDto;
import model.service.converter.SkillConverter;
import model.storage.SkillStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;


public class SkillService {
    private SkillStorage skillStorage;

public SkillService(SkillStorage skillStorage) {
    this.skillStorage = skillStorage;
}

    public SkillDto findByLanguageAndLevel(String language, String level) {
        Optional<SkillDao> skillDao = skillStorage.findByName(language, level);
        return SkillConverter.from(skillDao.orElseGet(() -> skillStorage.save(new SkillDao(language, level))));
    };

    public List<String> getSkillSetByDeveloperId(long id) {
        Set<SkillDao> skillsFromDb = skillStorage.getSkillSetByDeveloperId(id);
        if (skillsFromDb.isEmpty()) {
            return new ArrayList<>();
        } else {
           List<String> result = new ArrayList<>();
           skillsFromDb.forEach(skillDao -> result.add(skillDao.getLanguage() + " - " + skillDao.getLevel() + ","));
           return result;
        }
    }

}
