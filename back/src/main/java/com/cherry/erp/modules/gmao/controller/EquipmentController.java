package com.cherry.erp.modules.gmao.controller;

import com.cherry.erp.common.model.dto.GenericResponse;
import com.cherry.erp.common.utils.RolesConstant;
import com.cherry.erp.modules.gmao.controller.Mapper.EquipmentMapper;
import com.cherry.erp.modules.gmao.model.dto.EquipmentDto;
import com.cherry.erp.modules.gmao.model.persistent.Equipment;
import com.cherry.erp.modules.gmao.service.EquipmentBusiness;
import com.cherry.erp.modules.production.model.dto.ProductDto;
import com.cherry.erp.modules.production.model.persistent.Product;
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
@RequestMapping("/equipments")
public class EquipmentController {

    @Autowired
    EquipmentBusiness equipmentBusiness;
    @Autowired
    EquipmentMapper equipmentMapper;
    @PostMapping("/add")
    public GenericResponse add(@RequestBody EquipmentDto equipmentDto){
        Equipment equipment = equipmentMapper.toEntity(equipmentDto);
        return success(ADD_OK_CODE, equipmentMapper.toDto(equipmentBusiness.add(equipment)));
    }

    @GetMapping("/generateReference")
    public ResponseEntity<Map<String, String>> generateReference() {
        String reference = equipmentBusiness.generateReference();  // Generate the reference
        Map<String, String> response = new HashMap<>();
        response.put("reference", reference);  // Wrap it in a JSON object
        return ResponseEntity.ok().body(response);    // Return it as a JSON response
    }

    @GetMapping("/all")
    public GenericResponse FindAll(){
        return success(FIND_ALL_OK_CODE, equipmentMapper.toDtoList(equipmentBusiness.FindAll()));
    }

    @GetMapping("/findAll")
    public ResponseEntity<Page<EquipmentDto>> getAllPage(@RequestParam("page") Integer page,
                                                       @RequestParam("pageSize") Integer pageSize) {
        return ResponseEntity.ok().body(equipmentBusiness.findByPage(page, pageSize).map(equipmentMapper::toDto));
    }
    @DeleteMapping("/{id}")
    public GenericResponse DeleteById(@PathVariable("id") Integer id){
        equipmentBusiness.DeleteById(id);
        return success(DELETE_OK_CODE, id);
    }

}



