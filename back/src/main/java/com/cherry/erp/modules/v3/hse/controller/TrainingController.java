package com.cherry.erp.modules.v3.hse.controller;

import com.cherry.erp.common.model.dto.GenericResponse;
import com.cherry.erp.modules.v3.hse.controller.mapper.RiskAssessmentMapper;
import com.cherry.erp.modules.v3.hse.controller.mapper.TrainingMapper;
import com.cherry.erp.modules.v3.hse.model.dto.RiskAssessmentDto;
import com.cherry.erp.modules.v3.hse.model.dto.TrainingDto;
import com.cherry.erp.modules.v3.hse.model.persistent.RiskAssessment;
import com.cherry.erp.modules.v3.hse.model.persistent.Training;
import com.cherry.erp.modules.v3.hse.service.RiskAssessmentService;
import com.cherry.erp.modules.v3.hse.service.TrainingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.cherry.erp.common.model.enums.ResponseRestMsgCodeEnum.*;
import static com.cherry.erp.common.utils.ResponseFactory.success;

@RestController
@RequestMapping("/training")
@Slf4j
public class TrainingController {

    private final TrainingService trainingService;
    private final TrainingMapper trainingMapper;

    @Autowired
    public TrainingController(TrainingService trainingService, TrainingMapper trainingMapper) {
        this.trainingService = trainingService;
        this.trainingMapper = trainingMapper;
    }

    @GetMapping
    public ResponseEntity<Page<TrainingDto>> getAllPage(@RequestParam("page") Integer page,
                                                        @RequestParam("pageSize") Integer pageSize) {
        return ResponseEntity.ok().body(trainingService.findByPage(page, pageSize).map(trainingMapper::toDto));
    }

    @GetMapping("/{id}")
    public GenericResponse find(@PathVariable("id") Integer id) {
        return success(FIND_ONE_OK_CODE, trainingMapper.toDto(trainingService.findOne(id)));
    }

    @PostMapping
    public GenericResponse save(@RequestBody TrainingDto trainingDto) {
        Training training = trainingMapper.toEntity(trainingDto);
        return success(ADD_OK_CODE, trainingMapper.toDto(trainingService.save(training)));
    }

    @PutMapping("/{id}")
    public GenericResponse update(@PathVariable("id") Integer id, @RequestBody TrainingDto trainingDto) {
        Training training = trainingMapper.toEntity(trainingDto);
        training.setId(id);
        return success(ADD_OK_CODE, trainingMapper.toDto(trainingService.save(training)));
    }

    @DeleteMapping("/{id}")
    public GenericResponse delete(@PathVariable("id") Integer id) {
        trainingService.delete(id);
        return success(DELETE_OK_CODE, id);
    }

}
