package com.cherry.erp.modules.production.controller;

import com.cherry.erp.common.model.dto.GenericResponse;
import com.cherry.erp.common.utils.RolesConstant;
import com.cherry.erp.modules.production.controller.Mapper.ProductMapper;
import com.cherry.erp.modules.production.model.dto.ProductDto;
import com.cherry.erp.modules.production.service.ProductBusiness;
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
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductBusiness productBusiness;
    @Autowired
    ProductMapper productMapper;

    @PostMapping("/add")
    public GenericResponse Add (@RequestBody ProductDto productDto){
        Product product = productMapper.toEntity(productDto);
        return success(ADD_OK_CODE, productMapper.toDto(productBusiness.Add(product)));
    }

    @GetMapping("/generateReference")
    public ResponseEntity<Map<String, String>> generateReference() {
        String reference = productBusiness.generateReference();  // Generate the reference
        Map<String, String> response = new HashMap<>();
        response.put("reference", reference);  // Wrap it in a JSON object
        return ResponseEntity.ok().body(response);    // Return it as a JSON response
    }

    @GetMapping("/all")
    public ResponseEntity<Page<ProductDto>> getAllPage(@RequestParam("page") Integer page,
                                                                  @RequestParam("pageSize") Integer pageSize) {
        return ResponseEntity.ok().body(productBusiness.findByPage(page, pageSize).map(productMapper::toDto));
    }

    @GetMapping("/allByType")
    public ResponseEntity<Page<ProductDto>> getAllPageAndType(@RequestParam("page") Integer page,
                                                                  @RequestParam("pageSize") Integer pageSize,
                                                                  @RequestParam("type") String type) {
        return ResponseEntity.ok().body(productBusiness.findByPageAndType(type,page, pageSize).map(productMapper::toDto));
    }

    @GetMapping("/allByProject")
    public ResponseEntity<Page<ProductDto>>  getAllPageAndProject(@RequestParam("page") Integer page,
                                                                  @RequestParam("pageSize") Integer pageSize,
                                                                  @RequestParam("idProjet") Integer projectId) {
        return ResponseEntity.ok().body(productBusiness.findByPageAndProject(projectId,page, pageSize).map(productMapper::toDto));
    }

    @PutMapping("/affecterNomenclature/{id}/{idNomenclature}")
    public GenericResponse AffecterNomenclature(@PathVariable("id")Integer id,
                                                @PathVariable("idNomenclature") Integer idNomenclature){
        return success(ADD_OK_CODE, productMapper.toDto(productBusiness.AffecterNomenclature(id,idNomenclature)));
    }

    @PutMapping("/updateByTache/{idTache}")
    public GenericResponse UpdateByTache(@RequestBody ProductDto productDto, @PathVariable("idTache")Integer idTache){
        Product product = productMapper.toEntity(productDto);
        return success(ADD_OK_CODE, productMapper.toDto(productBusiness.UpdateByTache(product,idTache)));
    }

    @GetMapping("/findAll")
    public GenericResponse FindAll(){
        return success(FIND_ALL_OK_CODE, productMapper.toDtoList(productBusiness.FindAllByCompany()));
    }

    @GetMapping("/findAvailableByProject/{idProjet}")
    public GenericResponse FindAvailableByProject(@PathVariable("idProjet") Integer idProjet){
        return success(FIND_ALL_OK_CODE, productMapper.toDtoList(productBusiness.FindAvailableByProject(idProjet)));
    }

    @GetMapping("/findAvailable")
    public GenericResponse FindAvailable(){
        return success(FIND_ALL_OK_CODE, productMapper.toDtoList(productBusiness.FindAvailable()));

    }

    @GetMapping("/findByTache/{idTache}")
    public GenericResponse FindByTache (@PathVariable("idTache") Integer idTache){
        return success(FIND_ONE_OK_CODE, productMapper.toDto(productBusiness.FindByTache(idTache)));
    }

    @GetMapping("/findByReference/{reference}")
    public GenericResponse FindByReference(@PathVariable("reference")String refrence){
        return success(FIND_ONE_OK_CODE, productMapper.toDto(productBusiness.FindByReference(refrence)));
    }

    @GetMapping("/findByLibelle/{libelle}")
    public GenericResponse FindByLibelle(@PathVariable("libelle")String libelle){
        return success(FIND_ONE_OK_CODE, productMapper.toDto(productBusiness.FindByLibelle(libelle)));

    }

    @GetMapping("/findById/{id}")
    public GenericResponse FindById (@PathVariable("id") Integer id){
        return success(FIND_ONE_OK_CODE, productMapper.toDto(productBusiness.FindById(id)));
    }

    @GetMapping("/findAllNomenclatureDesc")
    public GenericResponse findAllNomenclatureDesc(){
        return success(FIND_ONE_OK_CODE, productMapper.toDtoList(productBusiness.findAllNomenclatureDesc()));
    }

    @GetMapping("/findAllByProject/{ProjectId}")
    public GenericResponse FindAllByProject(@PathVariable("ProjectId") Integer ProjectId){
        return success(FIND_ONE_OK_CODE, productMapper.toDtoList(productBusiness.FindAllByProject(ProjectId)));
    }

    @GetMapping("/findNomenclature/{id}")
    public GenericResponse FindNomenclature(@PathVariable("id") Integer id){
        return success(FIND_ONE_OK_CODE, productMapper.toDto(productBusiness.FindNomenclature(id)));
    }

    @GetMapping("/findAllExceptParent/{id}")
    public GenericResponse FindAllExceptParent(@PathVariable("id") Integer id){
        return success(FIND_ONE_OK_CODE, productMapper.toDtoList(productBusiness.FindAllExceptParent(id)));
    }

    @GetMapping("/findByParent/{id}")
    public GenericResponse findByParent(@PathVariable("id") Integer productId){
        return success(FIND_ONE_OK_CODE, productMapper.toDtoList(productBusiness.findByParent(productId)));
    }

    @DeleteMapping("/deleteById/{id}")
    public GenericResponse DeleteById(@PathVariable("id") Integer id){
        productBusiness.DeleteById(id);
        return success(DELETE_OK_CODE, id);
    }
}
