package model.service;

import model.dao.SkillDao;
import model.dto.SkillDto;
import model.service.converter.SkillConverter;
import model.storage.SkillStorage;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SkillService {
    private SkillStorage skillStorage;

public SkillService(SkillStorage skillStorage) {
    this.skillStorage = skillStorage;
}

    public SkillDto findByLanguageAndLevel(String language, String level) {
        Optional<SkillDao> skillDao = skillStorage.findByName(language, level);
        return SkillConverter.from(skillDao.orElseGet(() -> skillStorage.save(new SkillDao(language, level))));
    };

//    public SkillDto save (SkillDto skillDto) {
//        List<String> result = new ArrayList<>();
//        skillDto = SkillConverter.from(skillStorage.save(SkillConverter.to(skillDto)));
//        result.add(String.format("\tSkill %s - %s successfully added to the database",
//                skillDto.getLanguage(), skillDto.getLevel()));
//       // Output.getInstance().print(result);
//        return skillDto;
//    }

    public List<Long> getSkillIdsByDeveloperId(long id) {
        return skillStorage.getSkillIdsByDeveloperId(id);
    }

    public List<String > getSkillSetByDeveloperId(long id) {
        return skillStorage.getSkillSetByDeveloperId(id);
    }

}
