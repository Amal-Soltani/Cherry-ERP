package com.cherry.erp.modules.v3.hse.controller;

import com.cherry.erp.common.model.dto.GenericResponse;
import com.cherry.erp.modules.v3.hse.controller.mapper.IncidentMapper;
import com.cherry.erp.modules.v3.hse.controller.mapper.RiskAssessmentMapper;
import com.cherry.erp.modules.v3.hse.model.dto.IncidentDto;
import com.cherry.erp.modules.v3.hse.model.dto.RiskAssessmentDto;
import com.cherry.erp.modules.v3.hse.model.persistent.Incident;
import com.cherry.erp.modules.v3.hse.model.persistent.RiskAssessment;
import com.cherry.erp.modules.v3.hse.service.IncidentService;
import com.cherry.erp.modules.v3.hse.service.RiskAssessmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.cherry.erp.common.model.enums.ResponseRestMsgCodeEnum.*;
import static com.cherry.erp.common.utils.ResponseFactory.success;

@RestController
@RequestMapping("/incident")
@Slf4j
public class IncidentController {

    private final IncidentService incidentService;
    private final IncidentMapper incidentMapper;

    @Autowired
    public IncidentController(IncidentService employeeBusiness, IncidentMapper incidentMapper) {
        this.incidentService = employeeBusiness;
        this.incidentMapper = incidentMapper;
    }

    @GetMapping
    public ResponseEntity<Page<IncidentDto>> getAllPage(@RequestParam("page") Integer page,
                                                        @RequestParam("pageSize") Integer pageSize) {
        return ResponseEntity.ok().body(incidentService.findByPage(page, pageSize).map(incidentMapper::toDto));
    }

    @GetMapping("/{id}")
    public GenericResponse find(@PathVariable("id") Integer id) {
        return success(FIND_ONE_OK_CODE, incidentMapper.toDto(incidentService.findOne(id)));
    }

    @PostMapping
    public GenericResponse save(@RequestBody IncidentDto incidentDto) {
        Incident incident = incidentMapper.toEntity(incidentDto);
        return success(ADD_OK_CODE, incidentMapper.toDto(incidentService.save(incident)));
    }

    @PutMapping("/{id}")
    public GenericResponse update(@PathVariable("id") Integer id, @RequestBody IncidentDto incidentDto) {
        Incident incident = incidentMapper.toEntity(incidentDto);
        incident.setId(id);
        return success(ADD_OK_CODE, incidentMapper.toDto(incidentService.save(incident)));
    }

    @DeleteMapping("/{id}")
    public GenericResponse delete(@PathVariable("id") Integer id) {
        incidentService.delete(id);
        return success(DELETE_OK_CODE, id);
    }
}
