package com.cherry.erp.modules.production.controller;

import com.cherry.erp.common.model.dto.GenericResponse;
import com.cherry.erp.common.utils.RolesConstant;
import com.cherry.erp.modules.production.controller.Mapper.ProductionLigneMapper;
import com.cherry.erp.modules.production.model.dto.ProductionLigneDto;
import com.cherry.erp.modules.production.model.persistent.ProductionLigne;
import com.cherry.erp.modules.production.service.ProductionLigneBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static com.cherry.erp.common.model.enums.ResponseRestMsgCodeEnum.*;
import static com.cherry.erp.common.utils.ResponseFactory.success;

@RestController
@RequestMapping("/productionLigne")
public class ProductionLigneController {

    @Autowired
    ProductionLigneBusiness productionLigneBusiness;
    @Autowired
    ProductionLigneMapper productionLigneMapper;

    @PostMapping("/add")
    public GenericResponse Add(@RequestBody ProductionLigneDto productionLigneDto) {
        ProductionLigne productionLigne = productionLigneMapper.toEntity(productionLigneDto);
        return success(ADD_OK_CODE, productionLigneMapper.toDto(productionLigneBusiness.Add(productionLigne)));
    }

    @GetMapping("/generateReference")
    public ResponseEntity<Map<String, String>> generateReference() {
        String reference = productionLigneBusiness.generateReference();  // Generate the reference
        Map<String, String> response = new HashMap<>();
        response.put("reference", reference);  // Wrap it in a JSON object
        return ResponseEntity.ok().body(response);    // Return it as a JSON response
    }

    @GetMapping("/all")
    public ResponseEntity<Page<ProductionLigneDto>> getAllPage(@RequestParam("page") Integer page,
                                                               @RequestParam("pageSize") Integer pageSize) {
        return ResponseEntity.ok().body(productionLigneBusiness.findByPage(page, pageSize).map(productionLigneMapper::toDto));
    }

    @GetMapping("/{id}")
    public GenericResponse FindById(@PathVariable("id") Integer id){
        return success(FIND_ONE_OK_CODE, productionLigneMapper.toDto(productionLigneBusiness.FindById(id)));
    }

    @GetMapping("/findAll")
    public GenericResponse FindAll(){
        return success(FIND_ALL_OK_CODE, productionLigneMapper.toDtoList(productionLigneBusiness.FindAll()));
    }

    @DeleteMapping("/{id}")
    GenericResponse DeleteBYId (@PathVariable("id") Integer id){
        productionLigneBusiness.DeleteBYId(id);
        return success(DELETE_OK_CODE, id);
    }

}
