package com.cherry.erp.modules.quality.controller.mapper;

import com.cherry.erp.modules.quality.model.dto.FNCPercentageDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class FNCPercentageMapper {

    public FNCPercentageDTO toDTO(Object[] result) {
        String categorie = (String) result[0];
        Long fncCount = ((Number) result[1]).longValue();
        Long totalFNC = ((Number) result[2]).longValue();
        Double percentage = ((Number) result[3]).doubleValue();
        return new FNCPercentageDTO(categorie, fncCount, totalFNC,percentage);
    }

    public List<FNCPercentageDTO> toDTOList(List<Object[]> results) {
        List<FNCPercentageDTO> dtos = new ArrayList<>();
        for (Object[] result : results) {
            dtos.add(toDTO(result));
        }
        return dtos;
    }
}
