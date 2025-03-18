package com.cherry.erp.modules.v3.hse.controller;

import com.cherry.erp.common.model.dto.GenericResponse;
import com.cherry.erp.modules.v3.hse.controller.mapper.PermitWorkMapper;
import com.cherry.erp.modules.v3.hse.controller.mapper.RiskAssessmentMapper;
import com.cherry.erp.modules.v3.hse.model.dto.PermitWorkDto;
import com.cherry.erp.modules.v3.hse.model.dto.RiskAssessmentDto;
import com.cherry.erp.modules.v3.hse.model.persistent.PermitWork;
import com.cherry.erp.modules.v3.hse.model.persistent.RiskAssessment;
import com.cherry.erp.modules.v3.hse.service.PermitWorkService;
import com.cherry.erp.modules.v3.hse.service.RiskAssessmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.cherry.erp.common.model.enums.ResponseRestMsgCodeEnum.*;
import static com.cherry.erp.common.utils.ResponseFactory.success;

@RestController
@RequestMapping("/permit-work")
@Slf4j
public class PermitWorkController {

    private final PermitWorkService permitWorkService;
    private final PermitWorkMapper permitWorkMapper;

    @Autowired
    public PermitWorkController(PermitWorkService permitWorkService, PermitWorkMapper permitWorkMapper) {
        this.permitWorkService = permitWorkService;
        this.permitWorkMapper = permitWorkMapper;
    }

    @GetMapping
    public ResponseEntity<Page<PermitWorkDto>> getAllPage(@RequestParam("page") Integer page,
                                                          @RequestParam("pageSize") Integer pageSize) {
        return ResponseEntity.ok().body(permitWorkService.findByPage(page, pageSize).map(permitWorkMapper::toDto));
    }

    @GetMapping("/{id}")
    public GenericResponse find(@PathVariable("id") Integer id) {
        return success(FIND_ONE_OK_CODE, permitWorkMapper.toDto(permitWorkService.findOne(id)));
    }

    @PostMapping
    public GenericResponse save(@RequestBody PermitWorkDto permitWorkDto) {
        PermitWork permitWork = permitWorkMapper.toEntity(permitWorkDto);
        return success(ADD_OK_CODE, permitWorkMapper.toDto(permitWorkService.save(permitWork)));
    }

    @PutMapping("/{id}")
    public GenericResponse update(@PathVariable("id") Integer id, @RequestBody PermitWorkDto permitWorkDto) {
        PermitWork permitWork = permitWorkMapper.toEntity(permitWorkDto);
        permitWork.setId(id);
        return success(ADD_OK_CODE, permitWorkMapper.toDto(permitWorkService.save(permitWork)));
    }

    @DeleteMapping("/{id}")
    public GenericResponse delete(@PathVariable("id") Integer id) {
        permitWorkService.delete(id);
        return success(DELETE_OK_CODE, id);
    }

}
