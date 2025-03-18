package com.cherry.erp.modules.projet.controller;

import com.cherry.erp.common.model.dto.GenericResponse;
import com.cherry.erp.common.utils.RolesConstant;
import com.cherry.erp.modules.projet.controller.Mapper.TacheMapper;
import com.cherry.erp.modules.projet.model.dto.TacheDto;
import com.cherry.erp.modules.projet.model.persistent.Tache;
import com.cherry.erp.modules.projet.service.TacheBusiness;
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
@RequestMapping("/task")
public class TacheController {
    @Autowired
    TacheBusiness tacheBusiness;
    @Autowired
    TacheMapper tacheMapper;

    @PostMapping("/add")
    public GenericResponse add (@RequestBody TacheDto tacheDto){
        Tache tache = tacheMapper.toEntity(tacheDto);
        return success(ADD_OK_CODE, tacheMapper.toDto(tacheBusiness.Add(tache)));
    }

    @PostMapping("/addSousTache/{idProjet}/{parent}/{libelleProduit}")
    public GenericResponse AddSousTache (@RequestBody TacheDto tacheDto,
                                         @PathVariable("idProjet") Integer idProjet,
                                         @PathVariable("parent") Integer parent,
                                         @PathVariable("libelleProduit") String libelleProduit){
        Tache tache = tacheMapper.toEntity(tacheDto);
        return success(ADD_OK_CODE, tacheMapper.toDto(tacheBusiness.AddSousTache(tache,idProjet,parent,libelleProduit)));
    }

    @GetMapping("/generateReference")
    public ResponseEntity<Map<String, String>> generateReference() {
        String reference = tacheBusiness.generateReference();  // Generate the reference
        Map<String, String> response = new HashMap<>();
        response.put("reference", reference);  // Wrap it in a JSON object
        return ResponseEntity.ok().body(response);    // Return it as a JSON response
    }

    @GetMapping("/all")
    public ResponseEntity<Page<TacheDto>> getAllPage(@RequestParam("page") Integer page,
                                                        @RequestParam("pageSize") Integer pageSize) {
        return ResponseEntity.ok().body(tacheBusiness.findByPage(page, pageSize).map(tacheMapper::toDto));
    }
    @GetMapping("/allByProject")
    public ResponseEntity<Page<TacheDto>>  getAllPageAndProject(@RequestParam("page") Integer page,
                                                                @RequestParam("pageSize") Integer pageSize,
                                                                @RequestParam("idProjet") Integer projectId) {
        return ResponseEntity.ok().body(tacheBusiness.findByPageAndProject(projectId,page, pageSize).map(tacheMapper::toDto));
    }

    @GetMapping("/allByStatus")
    public ResponseEntity<Page<TacheDto>>  getAllPageAndStatus( @RequestParam("page") Integer page,
                                                                @RequestParam("pageSize") Integer pageSize,
                                                                @RequestParam("status") String status) {
        return ResponseEntity.ok().body(tacheBusiness.findByPageAndStatus(status,page, pageSize).map(tacheMapper::toDto));
    }

    @GetMapping("/allByProjectAndStatus")
    public ResponseEntity<Page<TacheDto>>  getAllPageAndProjectAndStatus(@RequestParam("page") Integer page,
                                                                         @RequestParam("pageSize") Integer pageSize,
                                                                         @RequestParam("idProjet") Integer projectId,
                                                                         @RequestParam("status") String status) {
        return ResponseEntity.ok().body(tacheBusiness.findByPageAndProjectAndStatus(projectId,status,page, pageSize).map(tacheMapper::toDto));
    }

    @GetMapping("/findAll")
    public GenericResponse FindAll (){
        return success(FIND_ONE_OK_CODE, tacheMapper.toDtoList(tacheBusiness.FindAll()));
    }

    @GetMapping("/{id}")
    public GenericResponse FindById (@PathVariable("id")Integer id){
        return success(FIND_ONE_OK_CODE, tacheMapper.toDto(tacheBusiness.FindById(id)));
    }

    @GetMapping("/byProject/{idProjet}")
    public GenericResponse FindAllByProjet (@PathVariable("idProjet")Integer idProjet){
        return success(FIND_ONE_OK_CODE, tacheMapper.toDtoList(tacheBusiness.FindAllByProjet(idProjet)));
    }
    @GetMapping("/byClient/{tacheId}")
    public GenericResponse FindAllByClient (@PathVariable("tacheId")Integer tacheId){
        return success(FIND_ONE_OK_CODE, tacheMapper.toDtoList(tacheBusiness.FindAllByClient(tacheId)));

    }

    @GetMapping("/sousTache/{tacheId}/{projetId}")
    public GenericResponse FindSousTache (@PathVariable("tacheId")Integer tacheId,
                                      @PathVariable("projetId")Integer projetId){
        return success(FIND_ONE_OK_CODE, tacheMapper.toDtoList(tacheBusiness.FindSousTache(tacheId,projetId)));

    }


    @DeleteMapping("/{id}")
    public  GenericResponse DeleteBYId (@PathVariable("id")Integer id){
        tacheBusiness.DeleteBYId(id);
        return success(DELETE_OK_CODE, id);
    }

    @DeleteMapping("/{id}/{projetId}")
    public  GenericResponse DeleteBYIdAndProject (@PathVariable("id")Integer id, @PathVariable("projetId")Integer projetId){
        tacheBusiness.DeleteBYIdAndProject(id, projetId);
        return success(DELETE_OK_CODE, id);
    }


}
