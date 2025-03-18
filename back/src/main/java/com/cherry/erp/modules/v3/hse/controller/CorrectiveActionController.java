package com.cherry.erp.modules.v3.hse.controller;

import com.cherry.erp.common.model.dto.GenericResponse;
import com.cherry.erp.modules.v3.hse.controller.mapper.CorrectiveActionMapper;
import com.cherry.erp.modules.v3.hse.controller.mapper.RiskAssessmentMapper;
import com.cherry.erp.modules.v3.hse.model.dto.CorrectiveActionDto;
import com.cherry.erp.modules.v3.hse.model.dto.RiskAssessmentDto;
import com.cherry.erp.modules.v3.hse.model.persistent.CorrectiveAction;
import com.cherry.erp.modules.v3.hse.model.persistent.RiskAssessment;
import com.cherry.erp.modules.v3.hse.service.CorrectiveActionService;
import com.cherry.erp.modules.v3.hse.service.RiskAssessmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.cherry.erp.common.model.enums.ResponseRestMsgCodeEnum.*;
import static com.cherry.erp.common.utils.ResponseFactory.success;

@RestController
@RequestMapping("/corrective-action")
@Slf4j
public class CorrectiveActionController {

    private final CorrectiveActionService correctiveActionService;
    private final CorrectiveActionMapper correctiveActionMapper;

    @Autowired
    public CorrectiveActionController(CorrectiveActionService correctiveActionService, CorrectiveActionMapper correctiveActionMapper) {
        this.correctiveActionService = correctiveActionService;
        this.correctiveActionMapper = correctiveActionMapper;
    }

    @GetMapping
    public ResponseEntity<Page<CorrectiveActionDto>> getAllPage(@RequestParam("page") Integer page,
                                                                @RequestParam("pageSize") Integer pageSize) {
        return ResponseEntity.ok().body(correctiveActionService.findByPage(page, pageSize).map(correctiveActionMapper::toDto));
    }

    @GetMapping("/{id}")
    public GenericResponse find(@PathVariable("id") Integer id) {
        return success(FIND_ONE_OK_CODE, correctiveActionMapper.toDto(correctiveActionService.findOne(id)));
    }

    @PostMapping
    public GenericResponse save(@RequestBody CorrectiveActionDto correctiveActionDto) {
        CorrectiveAction correctiveAction = correctiveActionMapper.toEntity(correctiveActionDto);
        return success(ADD_OK_CODE, correctiveActionMapper.toDto(correctiveActionService.save(correctiveAction)));
    }

    @PutMapping("/{id}")
    public GenericResponse update(@PathVariable("id") Integer id, @RequestBody CorrectiveActionDto correctiveActionDto) {
        CorrectiveAction correctiveAction = correctiveActionMapper.toEntity(correctiveActionDto);
        correctiveAction.setId(id);
        return success(ADD_OK_CODE, correctiveActionMapper.toDto(correctiveActionService.save(correctiveAction)));
    }
    @DeleteMapping("/{id}")
    public GenericResponse delete(@PathVariable("id") Integer id) {
        correctiveActionService.delete(id);
        return success(DELETE_OK_CODE, id);
    }

}
