package com.cherry.erp.modules.projet.controller.Mapper;

import com.cherry.erp.modules.projet.model.dto.ProjectsDto;
import com.cherry.erp.modules.projet.model.dto.TacheDto;
import com.cherry.erp.modules.projet.model.persistent.Projects;
import com.cherry.erp.modules.projet.model.persistent.Tache;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class TacheMapper {
    public abstract Tache toEntity(TacheDto tacheDto);
    public abstract TacheDto toDto(Tache tache);

    public abstract List<TacheDto> toDtoList(List<Tache> tacheList);


    @AfterMapping
    public void mapExtraAttributes(Tache tache, @MappingTarget TacheDto tacheDto) {}
}
