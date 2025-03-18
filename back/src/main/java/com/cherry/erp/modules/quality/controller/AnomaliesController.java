package com.cherry.erp.modules.quality.controller;

import com.cherry.erp.common.model.dto.GenericResponse;
import com.cherry.erp.common.utils.RolesConstant;
import com.cherry.erp.modules.quality.controller.mapper.AnomaliesMapper;
import com.cherry.erp.modules.quality.model.dto.AnomaliesDto;
import com.cherry.erp.modules.quality.model.persistent.Anomalies;
import com.cherry.erp.modules.quality.service.AnomaliesBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.cherry.erp.common.model.enums.ResponseRestMsgCodeEnum.*;
import static com.cherry.erp.common.utils.ResponseFactory.success;

@RestController
@RequestMapping("/anomalie")
public class AnomaliesController {
    @Autowired
    AnomaliesBusiness anomaliesBusiness;
    @Autowired
    AnomaliesMapper anomaliesMapper;

    @PostMapping("/add")
    public GenericResponse Add(@RequestBody AnomaliesDto anomalieDto) {
        Anomalies anomalies = anomaliesMapper.toEntity(anomalieDto);
        return success(ADD_OK_CODE, anomaliesMapper.toDto(anomaliesBusiness.Add(anomalies)));
    }

    @GetMapping("/all")
    public ResponseEntity<Page<AnomaliesDto>> getAllPage(@RequestParam("page") Integer page,
                                                     @RequestParam("pageSize") Integer pageSize) {
        return ResponseEntity.ok().body(anomaliesBusiness.findByPage(page, pageSize).map(anomaliesMapper::toDto));
    }


    @GetMapping("/findAll")
    public GenericResponse FindAll(){
        return success(FIND_ONE_OK_CODE, anomaliesMapper.toDtoList(anomaliesBusiness.FindAll()));
    }

    @GetMapping("/findByType/{type}")
    public GenericResponse FindByType(@PathVariable("type") String type) {
        return success(FIND_ONE_OK_CODE, anomaliesMapper.toDto(anomaliesBusiness.FindByType(type)));
    }

    @DeleteMapping("/{id}")
    public GenericResponse DeleteBYId (@PathVariable("id") Integer id){
        anomaliesBusiness.DeleteBYId(id);
        return success(DELETE_OK_CODE, id);
    }


}
