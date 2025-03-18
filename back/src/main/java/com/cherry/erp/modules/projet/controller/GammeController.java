package com.cherry.erp.modules.projet.controller;

import com.cherry.erp.common.model.dto.GenericResponse;
import com.cherry.erp.common.utils.RolesConstant;
import com.cherry.erp.modules.projet.controller.Mapper.GammeMapper;
import com.cherry.erp.modules.projet.service.GammeBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.cherry.erp.common.model.enums.ResponseRestMsgCodeEnum.*;
import static com.cherry.erp.common.utils.ResponseFactory.success;

@RestController
@RequestMapping("/gamme")
public class GammeController {
    @Autowired
    GammeBusiness gammeBusiness ;
    @Autowired
    GammeMapper gammeMapper;

    @PostMapping("/add/{productId}")
    public GenericResponse Add (@PathVariable("productId") Integer productId){
        return success(ADD_OK_CODE, gammeMapper.toDto(gammeBusiness.Add(productId)));
    }

    @GetMapping("/{id}")
    public GenericResponse FindByProduct(@PathVariable("id") Integer id){
        return success(FIND_ONE_OK_CODE, gammeMapper.toDto(gammeBusiness.FindByProduct(id)));
    }



}
