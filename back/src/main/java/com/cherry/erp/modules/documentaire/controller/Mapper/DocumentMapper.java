package com.cherry.erp.modules.documentaire.controller.Mapper;

import com.cherry.erp.modules.documentaire.model.dto.DocumentDto;
import com.cherry.erp.modules.documentaire.model.persistent.Document;
import com.cherry.erp.modules.production.model.persistent.ProductionLigne;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class DocumentMapper {
    public abstract Document toEntity(DocumentDto documentDto);
    public abstract DocumentDto toDto(Document document);

    public abstract List<DocumentDto> toDtoList(List<Document> documentList);


    @AfterMapping
    public void mapExtraAttributes(Document document, @MappingTarget DocumentDto documentDto) {}
}
