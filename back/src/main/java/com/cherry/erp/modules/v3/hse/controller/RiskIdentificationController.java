package com.cherry.erp.modules.v3.hse.controller;

import com.cherry.erp.common.model.dto.GenericResponse;
import com.cherry.erp.modules.v3.hse.controller.mapper.RiskAssessmentMapper;
import com.cherry.erp.modules.v3.hse.controller.mapper.RiskIdentificationMapper;
import com.cherry.erp.modules.v3.hse.model.dto.RiskAssessmentDto;
import com.cherry.erp.modules.v3.hse.model.dto.RiskIdentificationDto;
import com.cherry.erp.modules.v3.hse.model.persistent.RiskAssessment;
import com.cherry.erp.modules.v3.hse.model.persistent.RiskIdentification;
import com.cherry.erp.modules.v3.hse.service.RiskAssessmentService;
import com.cherry.erp.modules.v3.hse.service.RiskIdentificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.cherry.erp.common.model.enums.ResponseRestMsgCodeEnum.*;
import static com.cherry.erp.common.utils.ResponseFactory.success;

@RestController
@RequestMapping("/risk-identification")
@Slf4j
public class RiskIdentificationController {

    private final RiskIdentificationService riskIdentificationService;
    private final RiskIdentificationMapper riskIdentificationMapper;

    @Autowired
    public RiskIdentificationController(RiskIdentificationService riskIdentificationService, RiskIdentificationMapper riskIdentificationMapper) {
        this.riskIdentificationService = riskIdentificationService;
        this.riskIdentificationMapper = riskIdentificationMapper;
    }

    @GetMapping
    public ResponseEntity<Page<RiskIdentificationDto>> getAllPage(@RequestParam("page") Integer page,
                                                                  @RequestParam("pageSize") Integer pageSize) {
        return ResponseEntity.ok().body(riskIdentificationService.findByPage(page, pageSize).map(riskIdentificationMapper::toDto));
    }

    @GetMapping("/{id}")
    public GenericResponse find(@PathVariable("id") Integer id) {
        return success(FIND_ONE_OK_CODE, riskIdentificationMapper.toDto(riskIdentificationService.findOne(id)));
    }

    @PostMapping
    public GenericResponse save(@RequestBody RiskIdentificationDto riskIdentificationDto) {
        RiskIdentification riskIdentification = riskIdentificationMapper.toEntity(riskIdentificationDto);
        return success(ADD_OK_CODE, riskIdentificationMapper.toDto(riskIdentificationService.save(riskIdentification)));
    }

    @PutMapping("/{id}")
    public GenericResponse update(@PathVariable("id") Integer id, @RequestBody RiskIdentificationDto riskIdentificationDto) {
        RiskIdentification riskIdentification = riskIdentificationMapper.toEntity(riskIdentificationDto);
        riskIdentification.setId(id);
        return success(ADD_OK_CODE, riskIdentificationMapper.toDto(riskIdentificationService.save(riskIdentification)));
    }

    @DeleteMapping("/{id}")
    public GenericResponse delete(@PathVariable("id") Integer id) {
        riskIdentificationService.delete(id);
        return success(DELETE_OK_CODE, id);
    }

}
