package com.cherry.erp.modules.projet.controller.Mapper;

import com.cherry.erp.modules.projet.model.dto.ProjectsDto;
import com.cherry.erp.modules.projet.model.persistent.Projects;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract  class ProjetMapper {
    public abstract Projects toEntity(ProjectsDto projectsDto);
    public abstract ProjectsDto toDto(Projects projects);

    public abstract List<ProjectsDto> toDtoList(List<Projects> projectsList);

    @AfterMapping
    public void mapExtraAttributes(Projects projects, @MappingTarget ProjectsDto projectsDto) {}
}
