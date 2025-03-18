package com.cherry.erp.modules.projet.controller;

import com.cherry.erp.common.model.dto.GenericResponse;
import com.cherry.erp.common.utils.RolesConstant;
import com.cherry.erp.modules.projet.controller.Mapper.ProjetMapper;
import com.cherry.erp.modules.projet.model.dto.ProjectsDto;
import com.cherry.erp.modules.projet.model.persistent.Projects;
import com.cherry.erp.modules.projet.service.ProjetBusiness;
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
@RequestMapping("/project")
public class ProjetController {

    @Autowired
    ProjetBusiness projetBusiness;
    @Autowired
    ProjetMapper projetMapper;

    @PostMapping
    public GenericResponse add (@RequestBody ProjectsDto projectsDto){
        Projects projects = projetMapper.toEntity(projectsDto);
        return success(ADD_OK_CODE, projetMapper.toDto(projetBusiness.add(projects)));
    }

    @GetMapping("/generateReference")
    public ResponseEntity<Map<String, String>> generateReference() {
        String reference = projetBusiness.generateReference();  // Generate the reference
        Map<String, String> response = new HashMap<>();
        response.put("reference", reference);  // Wrap it in a JSON object
        return ResponseEntity.ok().body(response);    // Return it as a JSON response
    }

    @GetMapping("/all")
    public ResponseEntity<Page<ProjectsDto>> getAllPage(@RequestParam("page") Integer page,
                                                     @RequestParam("pageSize") Integer pageSize) {
        return ResponseEntity.ok().body(projetBusiness.findByPage(page, pageSize).map(projetMapper::toDto));
    }
    @GetMapping("/allByStatus")
    public ResponseEntity<Page<ProjectsDto>>  getAllPageAndStatus(@RequestParam("page") Integer page,
                                                                   @RequestParam("pageSize") Integer pageSize,
                                                                  @RequestParam ("status") String status) {
        return ResponseEntity.ok().body(projetBusiness.findByPageAndStatus(page, pageSize, status).map(projetMapper::toDto));
    }

    @GetMapping
    public GenericResponse FindAll(){
        return success(FIND_ONE_OK_CODE, projetMapper.toDtoList(projetBusiness.FindAll()));

    }
    @GetMapping("/{id}")
    public GenericResponse FindById(@PathVariable("id") Integer id ) {
        return success(FIND_ONE_OK_CODE, projetMapper.toDto(projetBusiness.FindById(id)));
    }

    @DeleteMapping("/{id}")
    public GenericResponse Delete(@PathVariable("id")Integer id){
        projetBusiness.DeleteBYId(id);
        return success(DELETE_OK_CODE, id);
    }
}
