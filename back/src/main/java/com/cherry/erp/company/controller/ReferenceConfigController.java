package com.cherry.erp.company.controller;

import com.cherry.erp.common.model.dto.GenericResponse;
import com.cherry.erp.company.business.ReferenceConfigService;
import com.cherry.erp.company.controller.mapper.ReferenceConfigMapper;
import com.cherry.erp.company.model.dto.ReferenceConfigDto;
import com.cherry.erp.company.model.persistent.ReferenceConfig;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.cherry.erp.common.model.enums.ResponseRestMsgCodeEnum.*;
import static com.cherry.erp.common.utils.ResponseFactory.success;

@RestController
@RequestMapping("/reference-configs")
@AllArgsConstructor
public class ReferenceConfigController {

    private final ReferenceConfigService referenceConfigService;
    private final ReferenceConfigMapper referenceConfigMapper;

    @GetMapping("/all")
    public GenericResponse getAll() {
        return success(FIND_ALL_OK_CODE, referenceConfigMapper.toDtoList(referenceConfigService.findAll()));
    }

    @GetMapping("/year/{year}")
    public GenericResponse getByYear(@PathVariable("year") Integer year) {
        return success(FIND_ALL_OK_CODE, referenceConfigMapper.toDtoList(referenceConfigService.getByYear(year)));
    }

    @GetMapping("/without-year")
    public GenericResponse getWithoutYear() {
        return success(FIND_ALL_OK_CODE, referenceConfigMapper.toDtoList(referenceConfigService.getWithoutYear()));
    }

    @GetMapping("/{id}")
    public GenericResponse find(@PathVariable("id") Integer id) {
        return success(FIND_ONE_OK_CODE, referenceConfigMapper.toDto(referenceConfigService.findOne(id)));
    }

    @PostMapping
    public GenericResponse save(@RequestBody ReferenceConfigDto referenceConfigRDto) {
        ReferenceConfig referenceConfig = referenceConfigMapper.toEntity(referenceConfigRDto);
        return success(ADD_OK_CODE, referenceConfigMapper.toDto(referenceConfigService.save(referenceConfig)));
    }

    @PutMapping("/{id}")
    public GenericResponse update(@PathVariable("id") Integer id, @RequestBody ReferenceConfigDto referenceConfigRDto) {
        ReferenceConfig referenceConfig = referenceConfigMapper.toEntity(referenceConfigRDto);
        referenceConfig.setId(id);
        return success(ADD_OK_CODE, referenceConfigMapper.toDto(referenceConfigService.save(referenceConfig)));
    }

    @DeleteMapping("/{id}")
    public GenericResponse delete(@PathVariable("id") Integer id) {
        referenceConfigService.delete(id);
        return success(DELETE_OK_CODE, id);
    }

}
