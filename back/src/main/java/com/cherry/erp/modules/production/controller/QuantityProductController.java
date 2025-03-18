package com.cherry.erp.modules.production.controller;

import com.cherry.erp.common.model.dto.GenericResponse;
import com.cherry.erp.common.utils.RolesConstant;
import com.cherry.erp.modules.production.controller.Mapper.QuantityProductMapper;
import com.cherry.erp.modules.production.model.dto.QuantityProductDto;
import com.cherry.erp.modules.production.model.persistent.QuantityProduct;
import com.cherry.erp.modules.production.service.QuantityProductBusniss;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.cherry.erp.common.model.enums.ResponseRestMsgCodeEnum.*;
import static com.cherry.erp.common.utils.ResponseFactory.success;

@RestController
@RequestMapping("/quantityProduct")
public class QuantityProductController {

    @Autowired
    QuantityProductBusniss quantityProductBusniss;
    @Autowired
    QuantityProductMapper quantityProductMapper;

    @PostMapping("/add/{parent}/{child}/{qte}")
    public GenericResponse Add(@PathVariable("parent") Integer parent,
                               @PathVariable("child") Integer child,
                               @PathVariable("qte") int qte) {
        return success(ADD_OK_CODE, quantityProductMapper.toDto(quantityProductBusniss.Add(parent, child, qte)));
    }

    @GetMapping("/getAll/{parent}")
    public GenericResponse FindByParent(@PathVariable("parent") Integer parent) {
        return success(FIND_ONE_OK_CODE, quantityProductMapper.toDtoList(quantityProductBusniss.FindByParent(parent)));

    }

    @PutMapping("/update/{id}/{child}/{qte}")
    public GenericResponse Update(@PathVariable("id") Integer id,
                                  @PathVariable("child") Integer child,
                                  @PathVariable("qte") int qte) {
        return success(ADD_OK_CODE, quantityProductMapper.toDto(quantityProductBusniss.Update(id, child,qte)));
    }

    @DeleteMapping("/delete/{parent}/{child}")
    public GenericResponse Delete(@PathVariable("parent") Integer parent,
                       @PathVariable("child") Integer child) {
        quantityProductBusniss.Delete(parent, child);
        return success(DELETE_OK_CODE, parent);
    }

    @DeleteMapping("/deleteByParent/{parent}")
    public GenericResponse DeleteByParent(@PathVariable("parent") Integer parent) {
        quantityProductBusniss.DeleteByParent(parent);
        return success(DELETE_OK_CODE, parent);
    }
}
