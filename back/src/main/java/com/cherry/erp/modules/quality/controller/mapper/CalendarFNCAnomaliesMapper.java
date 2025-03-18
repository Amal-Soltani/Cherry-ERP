package com.cherry.erp.modules.quality.controller.mapper;

import com.cherry.erp.modules.quality.model.dto.FNCAnomaliesCountByMonthDTO;
import com.cherry.erp.modules.quality.model.dto.FNCAnomaliesCountByWeekDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class CalendarFNCAnomaliesMapper {

    public FNCAnomaliesCountByMonthDTO toDTOMonth(Object[] result) {
        Integer month = ((Number) result[0]).intValue();
        Long nc = ((Number) result[1]).longValue();
        Long rebut = ((Number) result[2]).longValue();
        Long cout = ((Number) result[3]).longValue();
        Long fncCount = ((Number) result[4]).longValue();
        return new FNCAnomaliesCountByMonthDTO(month, nc, rebut, cout, fncCount);
    }

    public List<FNCAnomaliesCountByMonthDTO> toDTOListMonth(List<Object[]> results) {
        List<FNCAnomaliesCountByMonthDTO> dtos = new ArrayList<>();
        for (Object[] result : results) {
            dtos.add(toDTOMonth(result));
        }
        return dtos;
    }

    public FNCAnomaliesCountByWeekDTO toDTOWeek(Object[] result) {
        Integer week = ((Number) result[0]).intValue();
        Long nc = ((Number) result[1]).longValue();
        Long rebut = ((Number) result[2]).longValue();
        Long cout = ((Number) result[3]).longValue();
        Long fncCount = ((Number) result[4]).longValue();
        return new FNCAnomaliesCountByWeekDTO(week, nc, rebut, cout, fncCount);
    }

    public List<FNCAnomaliesCountByWeekDTO> toDTOListWeek(List<Object[]> results) {
        List<FNCAnomaliesCountByWeekDTO> dtos = new ArrayList<>();
        for (Object[] result : results) {
            dtos.add(toDTOWeek(result));
        }
        return dtos;
    }
}
