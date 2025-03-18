package com.cherry.erp.modules.production.controller;

import com.cherry.erp.common.model.dto.GenericResponse;
import com.cherry.erp.common.utils.RolesConstant;
import com.cherry.erp.modules.production.controller.Mapper.RawMaterialMapper;
import com.cherry.erp.modules.production.service.RawMaterialBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.cherry.erp.common.model.enums.ResponseRestMsgCodeEnum.*;
import static com.cherry.erp.common.utils.ResponseFactory.success;

@RestController
@RequestMapping("/rawMaterial")
public class RawMaterialController {

    @Autowired
    RawMaterialBusiness rawMaterialBusiness;
    @Autowired
    RawMaterialMapper rawMaterialMapper;

    @PostMapping("/add/{idProduct}/{idArticle}/{dimensionBrut}/{qteBrut}")
    public GenericResponse Add(@PathVariable("idProduct") Integer idProduct,
                               @PathVariable("idArticle") Integer idArticle,
                               @PathVariable("dimensionBrut") String dimensionBrut,
                               @PathVariable("qteBrut") double qteBrut) {
        return success(ADD_OK_CODE, rawMaterialMapper.toDto(rawMaterialBusiness.Add(idProduct, idArticle, dimensionBrut, qteBrut)));
    }

    @GetMapping("/getAll/{idProduct}")
    public GenericResponse getAll(@PathVariable("idProduct") Integer idProduct) {
        return success(FIND_ALL_OK_CODE, rawMaterialMapper.toDtoList(rawMaterialBusiness.FindByProduct(idProduct)));

    }

    @PutMapping("/update/{id}/{idProduct}/{dimensionBrut}/{qteBrut}")
    public GenericResponse Update(@PathVariable("id") Integer id,
                                  @PathVariable("idProduct") Integer idProduct,
                                  @PathVariable("dimensionBrut") String dimensionBrut,
                                  @PathVariable("qteBrut") double qteBrut) {
        return success(ADD_OK_CODE, rawMaterialMapper.toDto(rawMaterialBusiness.Update(id, idProduct, dimensionBrut, qteBrut)));
    }

    @DeleteMapping("/delete/{idProduct}/{idArticle}")
    public GenericResponse Delete(@PathVariable("idProduct") Integer idProduct,
                       @PathVariable("idArticle") Integer idArticle) {
        rawMaterialBusiness.Delete(idProduct, idArticle);
        return success(DELETE_OK_CODE, idProduct);
    }

    @DeleteMapping("/deleteByProduct/{idProduct}")
    public GenericResponse DeleteByProduct(@PathVariable("idProduct") Integer idProduct) {
        rawMaterialBusiness.DeleteByProduct(idProduct);
        return success(DELETE_OK_CODE, idProduct);
    }
}


