package com.cherry.erp.modules.quality.controller;

import com.cherry.erp.common.model.dto.GenericResponse;
import com.cherry.erp.common.utils.RolesConstant;
import com.cherry.erp.modules.production.model.dto.PhaseDto;
import com.cherry.erp.modules.projet.model.dto.ProjectsDto;
import com.cherry.erp.modules.projet.model.persistent.Projects;
import com.cherry.erp.modules.quality.controller.mapper.FNCMApper;
import com.cherry.erp.modules.quality.controller.mapper.FNCPercentageMapper;
import com.cherry.erp.modules.quality.model.dto.FNCDto;
import com.cherry.erp.modules.quality.model.dto.FNCPercentageDTO;
import com.cherry.erp.modules.quality.model.persistent.FNC;
import com.cherry.erp.modules.quality.service.FNCBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.cherry.erp.common.model.enums.ResponseRestMsgCodeEnum.*;
import static com.cherry.erp.common.utils.ResponseFactory.success;

@RestController
@RequestMapping("/fnc")
public class FNCController {
    @Autowired
    FNCBusiness fncBusiness;
    @Autowired
    FNCMApper fNCMApper;
    @Autowired
    FNCPercentageMapper FNCPercentageMapper;

    @PostMapping("/add")
    public GenericResponse Add(@RequestBody FNCDto fNCDto) {
        FNC fNC = fNCMApper.toEntity(fNCDto);
        return success(ADD_OK_CODE, fNCMApper.toDto(fncBusiness.Add(fNC)));
    }


    @GetMapping("/generateReference")
    public ResponseEntity<Map<String, String>> generateReference() {
        String reference = fncBusiness.generateReference();  // Generate the reference
        Map<String, String> response = new HashMap<>();
        response.put("reference", reference);  // Wrap it in a JSON object
        return ResponseEntity.ok().body(response);    // Return it as a JSON response
    }

    @GetMapping("/all")
    public ResponseEntity<Page<FNCDto>> getAllPage(@RequestParam("page") Integer page,
                                                     @RequestParam("pageSize") Integer pageSize) {
        return ResponseEntity.ok().body(fncBusiness.findByPage(page, pageSize).map(fNCMApper::toDto));
    }

    @GetMapping("/findAll")
    public GenericResponse FindAll(){
        return success(FIND_ONE_OK_CODE, fNCMApper.toDtoList(fncBusiness.FindAll()));
    }

    @GetMapping("/year/{year}")
    public GenericResponse FindByYear(@PathVariable("year") Integer year){
        return success(FIND_ONE_OK_CODE, fNCMApper.toDtoList(fncBusiness.FindByYear(year)));
    }
    @GetMapping("/first")
    public GenericResponse FindTheFirstDate(){
        return success(FIND_ONE_OK_CODE, fNCMApper.toDto(fncBusiness.FindTheFirstDate()));
    }

    @DeleteMapping("/{id}")
    public GenericResponse DeleteBYId (@PathVariable("id") Integer id){
        fncBusiness.DeleteBYId(id);
        return success(DELETE_OK_CODE, id);
    }

    @GetMapping("/fnc-percentage/{year}")
    public GenericResponse findFNCPercentageByYear(@PathVariable("year") Integer year) {
        return success(FIND_ONE_OK_CODE, FNCPercentageMapper.toDTOList(fncBusiness.findFNCPercentageByYear(year)));

    }

}

