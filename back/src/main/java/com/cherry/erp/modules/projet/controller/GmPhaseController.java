package com.cherry.erp.modules.projet.controller;

import com.cherry.erp.common.model.dto.GenericResponse;
import com.cherry.erp.common.utils.RolesConstant;
import com.cherry.erp.modules.projet.controller.Mapper.GmPhaseMapper;
import com.cherry.erp.modules.projet.model.dto.GmPhaseDto;
import com.cherry.erp.modules.projet.model.persistent.GmPhase;
import com.cherry.erp.modules.projet.service.GmPhaseBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.cherry.erp.common.model.enums.ResponseRestMsgCodeEnum.*;
import static com.cherry.erp.common.utils.ResponseFactory.success;

@RestController
@RequestMapping("/gmPhase")
public class GmPhaseController {

    @Autowired
    GmPhaseBusiness gmPhaseBusiness;
    @Autowired
    GmPhaseMapper gmPhaseMapper;
    @PostMapping("/{gammeId}")

    public GenericResponse Add(@RequestBody GmPhaseDto gmPhasedto, @PathVariable("gammeId") Integer gammeId) {
        GmPhase gmPhase = gmPhaseMapper.toEntity(gmPhasedto);
        return success(ADD_OK_CODE, gmPhaseMapper.toDto(gmPhaseBusiness.Add(gmPhase,gammeId)));
    }

    @GetMapping("/{gammeId}")
    public GenericResponse FindAll(@PathVariable("gammeId") Integer gammeId){
        return success(FIND_ALL_OK_CODE, gmPhaseMapper.toDtoList(gmPhaseBusiness.FindAll(gammeId)));
    }

    @GetMapping("/{gammeId}/{phaseId}")
    public GenericResponse FindOne(@PathVariable("gammeId") Integer gammeId, @PathVariable("phaseId") Integer phaseId){
        return success(FIND_ONE_OK_CODE, gmPhaseMapper.toDto(gmPhaseBusiness.FindOne(gammeId,phaseId)));
    }

    @DeleteMapping("/{id}/{gammeId}")
    public GenericResponse delete(@PathVariable("id") Integer gammeId, @PathVariable("gammeId") Integer gmphaseId){
        gmPhaseBusiness.delete(gammeId,gmphaseId);
        return success(DELETE_OK_CODE, gmphaseId);
    }

}
