package com.cherry.erp.modules.production.controller;

import com.cherry.erp.common.model.dto.GenericResponse;
import com.cherry.erp.common.utils.RolesConstant;
import com.cherry.erp.modules.production.controller.Mapper.BOMMapper;
import com.cherry.erp.modules.production.model.dto.BOMDto;
import com.cherry.erp.modules.production.model.persistent.BOM;
import com.cherry.erp.modules.production.service.BOMBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static com.cherry.erp.common.model.enums.ResponseRestMsgCodeEnum.ADD_OK_CODE;
import static com.cherry.erp.common.model.enums.ResponseRestMsgCodeEnum.DELETE_OK_CODE;
import static com.cherry.erp.common.utils.ResponseFactory.success;

@RestController
@RequestMapping("/bom")
public class BOMController {
    @Autowired
    BOMBusiness BOMBusiness;
    @Autowired
    BOMMapper BOMMapper;

    @PostMapping("/add/{idProduct}")
    public GenericResponse Add (@RequestBody BOMDto BOMDto, @PathVariable("idProduct") Integer idProduct){
        BOM BOM = BOMMapper.toEntity(BOMDto);
        return success(ADD_OK_CODE, BOMMapper.toDto(BOMBusiness.Add(BOM,idProduct)));
    }

    @GetMapping("/generateReference")
    public ResponseEntity<Map<String, String>> generateReference() {
        String reference = BOMBusiness.generateReference();  // Generate the reference
        Map<String, String> response = new HashMap<>();
        response.put("reference", reference);  // Wrap it in a JSON object
        return ResponseEntity.ok().body(response);    // Return it as a JSON response
    }

    @DeleteMapping("/deleteBYId/{id}")
    public GenericResponse DeleteBYId(@PathVariable("id")Integer id){
        BOMBusiness.DeleteBYId(id);
        return success(DELETE_OK_CODE, id);
    }


}
