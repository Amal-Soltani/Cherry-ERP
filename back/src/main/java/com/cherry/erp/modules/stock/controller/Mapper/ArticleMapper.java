package com.cherry.erp.modules.stock.controller.Mapper;

import com.cherry.erp.modules.stock.model.dto.ArticleDto;
import com.cherry.erp.modules.stock.model.persistent.Article;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class ArticleMapper {
    public abstract Article toEntity(ArticleDto articleDto);

    public abstract ArticleDto toDto(Article article);

    public abstract List<ArticleDto> toDtoList(List<Article> articleList);

    @AfterMapping
    public void mapExtraAttributes(Article article, @MappingTarget ArticleDto articleDto) {}
}
