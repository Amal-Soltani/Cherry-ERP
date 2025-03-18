package com.cherry.erp.modules.v3.hse.controller;

import com.cherry.erp.common.model.dto.GenericResponse;
import com.cherry.erp.modules.v3.hse.controller.mapper.CorrectiveActionMapper;
import com.cherry.erp.modules.v3.hse.controller.mapper.PlanActionMapper;
import com.cherry.erp.modules.v3.hse.model.dto.CorrectiveActionDto;
import com.cherry.erp.modules.v3.hse.model.dto.PlanActionDto;
import com.cherry.erp.modules.v3.hse.model.persistent.CorrectiveAction;
import com.cherry.erp.modules.v3.hse.model.persistent.PlanAction;
import com.cherry.erp.modules.v3.hse.service.CorrectiveActionService;
import com.cherry.erp.modules.v3.hse.service.PlanActionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.cherry.erp.common.model.enums.ResponseRestMsgCodeEnum.*;
import static com.cherry.erp.common.utils.ResponseFactory.success;
@RestController
@RequestMapping("/plan-action")
@Slf4j
public class PlanActionController {
    private final PlanActionService planActionService;
    private final PlanActionMapper planActionMapper;

    @Autowired
    public PlanActionController(PlanActionService planActionService, PlanActionMapper planActionMapper) {
        this.planActionService = planActionService;
        this.planActionMapper = planActionMapper;
    }

    @GetMapping
    public ResponseEntity<Page<PlanActionDto>> getAllPage(@RequestParam("page") Integer page,
                                                          @RequestParam("pageSize") Integer pageSize) {
        return ResponseEntity.ok().body(planActionService.findByPage(page, pageSize).map(planActionMapper::toDto));
    }

    @GetMapping("/{id}")
    public GenericResponse find(@PathVariable("id") Integer id) {
        return success(FIND_ONE_OK_CODE, planActionMapper.toDto(planActionService.findOne(id)));
    }

    @PostMapping
    public GenericResponse save(@RequestBody PlanActionDto planActionDto) {
        PlanAction planAction = planActionMapper.toEntity(planActionDto);
        return success(ADD_OK_CODE, planActionMapper.toDto(planActionService.save(planAction)));
    }

    @PutMapping("/{id}")
    public GenericResponse update(@PathVariable("id") Integer id, @RequestBody PlanActionDto planActionDto) {
        PlanAction planAction = planActionMapper.toEntity(planActionDto);
        planAction.setId(id);
        return success(ADD_OK_CODE, planActionMapper.toDto(planActionService.save(planAction)));
    }
    @DeleteMapping("/{id}")
    public GenericResponse delete(@PathVariable("id") Integer id) {
        planActionService.delete(id);
        return success(DELETE_OK_CODE, id);
    }
}
