package com.cherry.erp.modules.production.controller;

import com.cherry.erp.common.model.dto.GenericResponse;
import com.cherry.erp.common.utils.RolesConstant;
import com.cherry.erp.modules.production.controller.Mapper.PhaseMapper;
import com.cherry.erp.modules.production.model.dto.PhaseDto;
import com.cherry.erp.modules.production.model.persistent.Phase;
import com.cherry.erp.modules.production.service.PhaseBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.cherry.erp.common.model.enums.ResponseRestMsgCodeEnum.*;
import static com.cherry.erp.common.utils.ResponseFactory.success;

@RestController
@RequestMapping("/phase")
public class PhaseController {
    @Autowired
    PhaseBusiness phaseBusiness;

    @Autowired
    PhaseMapper phaseMapper;

    @PostMapping("/add")
    public GenericResponse Add(@RequestBody PhaseDto phaseDto) {
        Phase phase = phaseMapper.toEntity(phaseDto);
        return success(ADD_OK_CODE, phaseMapper.toDto(phaseBusiness.Add(phase)));
    }

    @GetMapping("/all")
    public ResponseEntity<Page<PhaseDto>> getAllPage(@RequestParam("page") Integer page,
                                                               @RequestParam("pageSize") Integer pageSize) {
        return ResponseEntity.ok().body(phaseBusiness.findByPage(page, pageSize).map(phaseMapper::toDto));
    }

    @GetMapping("/{id}")
    public GenericResponse FindById(@PathVariable("id") Integer id){
        return success(FIND_ONE_OK_CODE, phaseMapper.toDto(phaseBusiness.FindById(id)));
    }

    @GetMapping("/find/{name}")
    public GenericResponse FindByName(@PathVariable("name") String name){
        return success(FIND_ONE_OK_CODE, phaseMapper.toDto(phaseBusiness.FindByName(name)));
    }

    @GetMapping("/findAll")
    public GenericResponse FindAll(){
        return success(FIND_ALL_OK_CODE, phaseMapper.toDtoList(phaseBusiness.FindAll()));
    }

    @GetMapping("/phases/{tacheId}")
    public GenericResponse getPhaseByTache (@PathVariable("tacheId")Integer tacheId){
        return success(FIND_ALL_OK_CODE, phaseMapper.toDtoList(phaseBusiness.getPhaseByTache(tacheId)));
    }

    @DeleteMapping("/{id}")
    GenericResponse DeleteBYId (@PathVariable("id") Integer id){
        phaseBusiness.DeleteBYId(id);
        return success(DELETE_OK_CODE, id);

    }

}
