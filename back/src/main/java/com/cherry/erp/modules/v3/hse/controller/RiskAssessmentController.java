package com.cherry.erp.modules.v3.hse.controller;

import com.cherry.erp.common.model.dto.GenericResponse;
import com.cherry.erp.modules.v3.hse.controller.mapper.RiskAssessmentMapper;
import com.cherry.erp.modules.v3.hse.model.dto.RiskAssessmentDto;
import com.cherry.erp.modules.v3.hse.model.persistent.RiskAssessment;
import com.cherry.erp.modules.v3.hse.service.RiskAssessmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.cherry.erp.common.model.enums.ResponseRestMsgCodeEnum.*;
import static com.cherry.erp.common.utils.ResponseFactory.success;

@RestController
@RequestMapping("/risk-assessment")
@Slf4j
public class RiskAssessmentController {

    private final RiskAssessmentService riskAssessmentService;
    private final RiskAssessmentMapper riskAssessmentMapper;

    @Autowired
    public RiskAssessmentController(RiskAssessmentService employeeBusiness, RiskAssessmentMapper riskAssessmentMapper) {
        this.riskAssessmentService = employeeBusiness;
        this.riskAssessmentMapper = riskAssessmentMapper;
    }

    @GetMapping
    public ResponseEntity<Page<RiskAssessmentDto>> getAllPage(@RequestParam("page") Integer page,
                                                              @RequestParam("pageSize") Integer pageSize) {
        return ResponseEntity.ok().body(riskAssessmentService.findByPage(page, pageSize).map(riskAssessmentMapper::toDto));
    }

    @GetMapping("/{id}")
    public GenericResponse find(@PathVariable("id") Integer id) {
        return success(FIND_ONE_OK_CODE, riskAssessmentMapper.toDto(riskAssessmentService.findOne(id)));
    }

    @PostMapping
    public GenericResponse save(@RequestBody RiskAssessmentDto riskAssessmentDto) {
        RiskAssessment riskAssessment = riskAssessmentMapper.toEntity(riskAssessmentDto);
        return success(ADD_OK_CODE, riskAssessmentMapper.toDto(riskAssessmentService.save(riskAssessment)));
    }

    @PutMapping("/{id}")
    public GenericResponse update(@PathVariable("id") Integer id, @RequestBody RiskAssessmentDto riskAssessmentDto) {
        RiskAssessment riskAssessment = riskAssessmentMapper.toEntity(riskAssessmentDto);
        riskAssessment.setId(id);
        return success(ADD_OK_CODE, riskAssessmentMapper.toDto(riskAssessmentService.save(riskAssessment)));
    }

    @DeleteMapping("/{id}")
    public GenericResponse delete(@PathVariable("id") Integer id) {
        riskAssessmentService.delete(id);
        return success(DELETE_OK_CODE, id);
    }

}
