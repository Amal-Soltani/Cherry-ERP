package com.cherry.erp.modules.v3.hse.controller;

import com.cherry.erp.common.model.dto.GenericResponse;
import com.cherry.erp.modules.v3.hse.controller.mapper.TrainingMapper;
import com.cherry.erp.modules.v3.hse.controller.mapper.TrainingSessionMapper;
import com.cherry.erp.modules.v3.hse.model.dto.TrainingDto;
import com.cherry.erp.modules.v3.hse.model.dto.TrainingSessionDto;
import com.cherry.erp.modules.v3.hse.model.persistent.Training;
import com.cherry.erp.modules.v3.hse.model.persistent.TrainingSession;
import com.cherry.erp.modules.v3.hse.service.TrainingService;
import com.cherry.erp.modules.v3.hse.service.TrainingSessionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.cherry.erp.common.model.enums.ResponseRestMsgCodeEnum.*;
import static com.cherry.erp.common.utils.ResponseFactory.success;

@RestController
@RequestMapping("/training-session")
@Slf4j
public class TrainingSessionController {

    private final TrainingSessionService trainingSessionService;
    private final TrainingSessionMapper trainingSessionMapper;

    @Autowired
    public TrainingSessionController(TrainingSessionService trainingSessionService, TrainingSessionMapper trainingSessionMapper) {
        this.trainingSessionService = trainingSessionService;
        this.trainingSessionMapper = trainingSessionMapper;
    }

    @GetMapping
    public ResponseEntity<Page<TrainingSessionDto>> getAllPage(@RequestParam("page") Integer page,
                                                               @RequestParam("pageSize") Integer pageSize) {
        return ResponseEntity.ok().body(trainingSessionService.findByPage(page, pageSize).map(trainingSessionMapper::toDto));
    }

    @GetMapping("/{id}")
    public GenericResponse find(@PathVariable("id") Integer id) {
        return success(FIND_ONE_OK_CODE, trainingSessionMapper.toDto(trainingSessionService.findOne(id)));
    }

    @PostMapping
    public GenericResponse save(@RequestBody TrainingSessionDto trainingSessionDto) {
        TrainingSession trainingSession = trainingSessionMapper.toEntity(trainingSessionDto);
        return success(ADD_OK_CODE, trainingSessionMapper.toDto(trainingSessionService.save(trainingSession)));
    }

    @PutMapping("/{id}")
    public GenericResponse update(@PathVariable("id") Integer id, @RequestBody TrainingSessionDto trainingSessionDto) {
        TrainingSession trainingSession = trainingSessionMapper.toEntity(trainingSessionDto);
        trainingSession.setId(id);
        return success(ADD_OK_CODE, trainingSessionMapper.toDto(trainingSessionService.save(trainingSession)));
    }

    @DeleteMapping("/{id}")
    public GenericResponse delete(@PathVariable("id") Integer id) {
        trainingSessionService.delete(id);
        return success(DELETE_OK_CODE, id);
    }

}
