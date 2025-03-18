package com.cherry.erp.common.controller;

import com.cherry.erp.common.business.GenericParameterService;
import com.cherry.erp.common.controller.mapper.GenericParameterMapper;
import com.cherry.erp.common.model.dto.GenericParameterDto;
import com.cherry.erp.common.model.persistent.GenericParameter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/generic-parameters")
@Slf4j
public class GenericParameterController {

    private final GenericParameterService genericParameterService;
    private final GenericParameterMapper genericParameterMapper;

    @Autowired
    public GenericParameterController(GenericParameterService genericParameterService, GenericParameterMapper genericParameterMapper) {
        this.genericParameterService = genericParameterService;
        this.genericParameterMapper = genericParameterMapper;
    }

    @GetMapping
    public ResponseEntity<GenericParameterDto> findByCompany() {
        return ResponseEntity.ok().body(genericParameterMapper.toDto(genericParameterService.findByCompany()));
    }

    @PostMapping
    public ResponseEntity<GenericParameterDto> save(@RequestBody GenericParameterDto genericParameterDto) {
        return ResponseEntity.ok().body(genericParameterMapper.toDto(genericParameterService.save(genericParameterMapper.toEntity(genericParameterDto))));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GenericParameterDto> update(@PathVariable("id") Integer id, @RequestBody GenericParameterDto genericParameterDto) {
        GenericParameter genericParameter = genericParameterMapper.toEntity(genericParameterDto);
        genericParameter.setId(id);
        return ResponseEntity.ok().body(genericParameterMapper.toDto(genericParameterService.update(id, genericParameter)));
    }

}
