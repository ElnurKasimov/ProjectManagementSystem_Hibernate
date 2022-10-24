package model.service;

import model.dto.DeveloperDto;
import model.dto.ProjectDto;
import model.dto.SkillDto;
import model.service.converter.DeveloperConverter;
import model.service.converter.ProjectConverter;
import model.service.converter.SkillConverter;
import model.storage.RelationStorage;

import java.util.Set;
import java.util.stream.Collectors;

public class RelationService {
    private RelationStorage relationStorage;

    public RelationService(RelationStorage relationStorage) {
        this.relationStorage = relationStorage;
    }

    public void saveProjectDeveloper(Set<ProjectDto> projectsDto, DeveloperDto developerDto) {
        relationStorage.saveProjectDeveloper(
                projectsDto.stream().map(ProjectConverter::to).collect(Collectors.toSet()),
                DeveloperConverter.to(developerDto)
        );
    }


    public void saveDeveloperSkill(DeveloperDto developerDto, Set<SkillDto> skillsDto) {
        relationStorage.saveDeveloperSkill(
                DeveloperConverter.to(developerDto),
                skillsDto.stream().map(SkillConverter::to).collect(Collectors.toSet())
        );
    }

    public void deleteAllProjectsOfDeveloper(DeveloperDto developerDto) {
        relationStorage.deleteAllProjectsOfDeveloper(DeveloperConverter.to(developerDto));
    }

    public void deleteAllSkillsOfDeveloper(DeveloperDto developerDto) {
        relationStorage.deleteAllSkillsOfDeveloper(DeveloperConverter.to(developerDto));
    }

    public void deleteAllDevelopersOfProject(ProjectDto projectDto) {
        relationStorage.deleteAllDevelopersOfProject(ProjectConverter.to(projectDto));
    }

    public void deleteDeveloperFromDeveloperSkill(DeveloperDto developerDto) {
        relationStorage.deleteDeveloperFromDeveloperSkill(DeveloperConverter.to(developerDto));
    }

    public void deleteDeveloperFromProjectDeveloper(DeveloperDto developerDto) {
        relationStorage.deleteDeveloperFromProjectDeveloper(DeveloperConverter.to(developerDto));
    }

}
