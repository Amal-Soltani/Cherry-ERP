package com.cherry.erp.modules.quality.controller;

import com.cherry.erp.common.model.dto.GenericResponse;
import com.cherry.erp.common.utils.RolesConstant;
import com.cherry.erp.modules.quality.controller.mapper.CalendarFNCAnomaliesMapper;
import com.cherry.erp.modules.quality.controller.mapper.FNCAnomaliesMapper;
import com.cherry.erp.modules.quality.model.dto.FNCAnomaliesCountByMonthDTO;
import com.cherry.erp.modules.quality.model.dto.FNCAnomaliesCountByWeekDTO;
import com.cherry.erp.modules.quality.model.dto.FNCAnomaliesDto;
import com.cherry.erp.modules.quality.model.persistent.FNCAnomalies;
import com.cherry.erp.modules.quality.service.FNCAnomaliesBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.cherry.erp.common.model.enums.ResponseRestMsgCodeEnum.*;
import static com.cherry.erp.common.utils.ResponseFactory.success;

@RestController
@RequestMapping("/fncAnomalie")
public class FNCAnomaliesController {
    @Autowired
    FNCAnomaliesBusiness fncAnomaliesBusiness;

    @Autowired
    FNCAnomaliesMapper fncAnomaliesMapper;
    @Autowired
    CalendarFNCAnomaliesMapper CalendarFNCAnomaliesMapperMapper;

    @GetMapping("/{fnc}")
    public GenericResponse FindAll(@PathVariable("fnc") Integer fnc) {
        return success(FIND_ONE_OK_CODE, fncAnomaliesMapper.toDtoList(fncAnomaliesBusiness.FindAll(fnc)));
    }

    @PostMapping("/{fnc}")
    public GenericResponse Add(@RequestBody FNCAnomaliesDto ficheAnDto, @PathVariable("fnc") Integer fnc) {
        FNCAnomalies fNCAnomalies = fncAnomaliesMapper.toEntity(ficheAnDto);
        return success(ADD_OK_CODE, fncAnomaliesMapper.toDto(fncAnomaliesBusiness.Add(fNCAnomalies,fnc)));
    }

    @DeleteMapping("/{fiche}/{id}")
    public GenericResponse Delete(@PathVariable("fiche") Integer fiche,@PathVariable("id") Integer id) {
         fncAnomaliesBusiness.Delete(fiche,id);
        return success(DELETE_OK_CODE, id);
    }

    @GetMapping("/month/{year}")
    public GenericResponse GetFNCCountByYearAndMonth(@RequestParam("year") Integer year) {
        return success(FIND_ONE_OK_CODE, CalendarFNCAnomaliesMapperMapper.toDTOListMonth(fncAnomaliesBusiness.GetFNCCountByYearAndMonth(year)));
    }
    @GetMapping("/week/{year}")
    public GenericResponse GetFNCCountByYearAndWeek(@RequestParam("year") Integer year) {
        return success(FIND_ONE_OK_CODE, CalendarFNCAnomaliesMapperMapper.toDTOListWeek(fncAnomaliesBusiness.GetFNCCountByYearAndWeek(year)));
    }
}
