package com.cherry.erp.modules.stock.controller;

import com.cherry.erp.common.model.dto.GenericResponse;
import com.cherry.erp.common.utils.RolesConstant;
import com.cherry.erp.modules.production.model.dto.ProductDto;
import com.cherry.erp.modules.production.model.persistent.Product;
import com.cherry.erp.modules.projet.model.dto.ProjectsDto;
import com.cherry.erp.modules.projet.model.persistent.Projects;
import com.cherry.erp.modules.stock.business.ArticleBusiness;
import com.cherry.erp.modules.stock.controller.Mapper.ArticleMapper;
import com.cherry.erp.modules.stock.model.dto.ArticleDto;
import com.cherry.erp.modules.stock.model.persistent.Article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.*;
import java.util.stream.Collectors;

import static com.cherry.erp.common.model.enums.ResponseRestMsgCodeEnum.*;
import static com.cherry.erp.common.utils.ResponseFactory.success;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    ArticleBusiness articleBusiness;
    @Autowired
    ArticleMapper articleMapper;

    @PostMapping("/add")
    public GenericResponse addArticle(@RequestBody ArticleDto articleDto){
        Article article = articleMapper.toEntity(articleDto);
        return success(ADD_OK_CODE, articleMapper.toDto(articleBusiness.add(article)));
    }

    @GetMapping("/generateReference")
    public ResponseEntity<Map<String, String>> generateReference() {
        String reference = articleBusiness.generateReference();  // Generate the reference
        Map<String, String> response = new HashMap<>();
        response.put("reference", reference);  // Wrap it in a JSON object
        return ResponseEntity.ok().body(response);    // Return it as a JSON response
    }

    @GetMapping("/all")
    public ResponseEntity<Page<ArticleDto>> getAllPage(@RequestParam("page") Integer page,
                                                       @RequestParam("pageSize") Integer pageSize) {
        return ResponseEntity.ok().body(articleBusiness.findByPage(page, pageSize).map(articleMapper::toDto));
    }

    @GetMapping("/findAll")
    public GenericResponse FindAll(){
        return success(FIND_ALL_OK_CODE, articleMapper.toDtoList(articleBusiness.FindAll()));
    }

    @GetMapping("/findAllExecptUsed/{idProduct}")
    public GenericResponse FindAllExecptUsed(@PathVariable("idProduct") Integer idProduct){
        return success(FIND_ALL_OK_CODE, articleMapper.toDtoList(articleBusiness.FindAllExecptUsed(idProduct)));

    }

    @GetMapping("/findByLibelle/{libelle}")
    public GenericResponse FindByLibelle(@PathVariable("libelle") String libelle){
        return success(FIND_ONE_OK_CODE, articleMapper.toDto(articleBusiness.FindByLibelle(libelle)));
    }

    @GetMapping("/byProduct/{id}")
    public GenericResponse FindByProduct(@PathVariable("id") Integer id){
        return success(FIND_ONE_OK_CODE, articleMapper.toDtoList(articleBusiness.FindByProduct(id)));
    }

    @DeleteMapping("/deleteById/{id}")
    public GenericResponse DeleteById(@PathVariable("id") Integer codeArticle){
        articleBusiness.DeleteById(codeArticle);
        return success(DELETE_OK_CODE, codeArticle);
    }
}
