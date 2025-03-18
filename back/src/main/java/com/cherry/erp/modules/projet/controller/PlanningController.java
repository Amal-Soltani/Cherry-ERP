package com.cherry.erp.modules.projet.controller;

import com.cherry.erp.common.model.dto.GenericResponse;
import com.cherry.erp.common.utils.RolesConstant;
import com.cherry.erp.modules.projet.controller.Mapper.PlanningMapper;
import com.cherry.erp.modules.projet.controller.Mapper.TRSByProjectMapper;
import com.cherry.erp.modules.projet.model.dto.*;
import com.cherry.erp.modules.projet.model.persistent.Planning;
import com.cherry.erp.modules.projet.service.PlanningBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.cherry.erp.common.model.enums.ResponseRestMsgCodeEnum.*;
import static com.cherry.erp.common.utils.ResponseFactory.success;

@RestController
@RequestMapping("/planning")
public class PlanningController {

    @Autowired
    PlanningBusiness planningBusiness;
    @Autowired
    TRSByProjectMapper trsByProjectMapper;
    @Autowired
    PlanningMapper planningMapper;

    @PostMapping
    public GenericResponse Add(@RequestBody PlanningDto planningDto) {
        Planning pilotage = planningMapper.toEntity(planningDto);
        return success(ADD_OK_CODE, planningMapper.toDto(planningBusiness.Add(pilotage)));
    }


    @PutMapping("{projectId}")
    public GenericResponse AffecterProjet(@RequestBody PlanningDto planningDto, @PathVariable("projectId") Integer projectId) {
        Planning pilotage = planningMapper.toEntity(planningDto);
        return success(ADD_OK_CODE, planningMapper.toDto(planningBusiness.AffecterProjet(pilotage,projectId)));
    }


    @PutMapping("{id}/{dateStart}/{dateEnd}")
    public GenericResponse UpdateDates(@PathVariable("id") Integer id,
                                @PathVariable("dateStart") String dateStart,
                                @PathVariable("dateEnd") String dateEnd) {
        return success(ADD_OK_CODE, planningMapper.toDto(planningBusiness.UpdateDates(id,dateStart,dateEnd)));
    }


    @GetMapping("/all")
    public ResponseEntity<Page<PlanningDto>> getAllPage(@RequestParam("page") Integer page,
                                                        @RequestParam("pageSize") Integer pageSize) {
        return ResponseEntity.ok().body(planningBusiness.findByPage(page, pageSize).map(planningMapper::toDto));
    }

    @GetMapping("/allByProject")
    public ResponseEntity<Page<PlanningDto>>  getAllPageAndProject(@RequestParam("projectId") Integer projectId,
                                                                   @RequestParam("page") Integer page,
                                                                   @RequestParam("pageSize") Integer pageSize) {
        return ResponseEntity.ok().body(planningBusiness.findByPageAndProject(projectId,page, pageSize).map(planningMapper::toDto));
    }

    @GetMapping("/allByTache")
    public ResponseEntity<Page<PlanningDto>>  getAllPageAndTahce(@RequestParam("tacheId") Integer tacheId,
                                                                 @RequestParam("page") Integer page,
                                                                 @RequestParam("pageSize") Integer pageSize) {
        return ResponseEntity.ok().body(planningBusiness.findByPageAndTache(tacheId,page, pageSize).map(planningMapper::toDto));
    }
    @GetMapping("/byId/{id}")
    public GenericResponse FindByID(@PathVariable("id") Integer id){
        return success(FIND_ONE_OK_CODE, planningMapper.toDto(planningBusiness.FindById(id)));
    }

    @GetMapping("/{projectId}")
    public GenericResponse FindByProject(@PathVariable("projectId") Integer projectId){
        return success(FIND_ALL_OK_CODE, planningMapper.toDtoList(planningBusiness.FindByProject(projectId)));
    }

    @GetMapping("/findAll")
    public GenericResponse FindAll(){
        return success(FIND_ALL_OK_CODE, planningMapper.toDtoList(planningBusiness.FindAll()));
    }

    @GetMapping("/byOF/{of}")
    public GenericResponse FindByIdAndTache(@PathVariable("of") Integer idOF){
        return success(FIND_ALL_OK_CODE, planningMapper.toDtoList(planningBusiness.FindByTache(idOF)));
    }

    @GetMapping("/byTache/{id}/{refOF}")
    public GenericResponse FindByIdAndTache(@PathVariable("id") Integer id,@PathVariable("refOF") String refOF){
        return success(FIND_ONE_OK_CODE, planningMapper.toDto(planningBusiness.FindByIdAndTache(id,refOF)));
    }

    @DeleteMapping("/{id}/{employeeId}")
    public GenericResponse Delete(@PathVariable("id") Integer id,@PathVariable("employeeId") Integer employeeId){
        planningBusiness.Delete(id,employeeId);
        return success(DELETE_OK_CODE, id);
    }

    @GetMapping("/TRSByProject/{id}")
    public GenericResponse TRSByProject(@PathVariable("id") Integer id) {
        return success(FIND_ONE_OK_CODE, trsByProjectMapper.toTRSByProjectDTOList(planningBusiness.TRSByProject(id)));
    }

    @GetMapping("/TRSByPhaseProject/{id}")
    public GenericResponse TRSByPhaseProject(@PathVariable("id") Integer id) {
        return success(FIND_ONE_OK_CODE,trsByProjectMapper.toTRSByPhaseDTOList(planningBusiness.TRSByPhaseProject(id)));
    }

    @GetMapping("/TRSByEmpProject/{id}")
    public GenericResponse TRSByEmpProject(@PathVariable("id") Integer id) {
        return success(FIND_ONE_OK_CODE,trsByProjectMapper.toTRSByEmpDTOList(planningBusiness.TRSByEmpProject(id)));
    }

    @GetMapping("/TRSByEquipProject/{id}")
    public GenericResponse TRSByEquipProject(@PathVariable("id") Integer id) {
        return success(FIND_ONE_OK_CODE,trsByProjectMapper.toTRSByEquipDTOList(planningBusiness.TRSByEquipProject(id)));
    }

    @GetMapping("/TRSByLigneProject/{id}")
    public GenericResponse TRSByLigneProject(@PathVariable("id") Integer id) {
        return success(FIND_ONE_OK_CODE,trsByProjectMapper.toTRSByLigneDTOList(planningBusiness.TRSByLigneProject(id)));
    }

    @GetMapping("/TRSByAllProjects")
    public GenericResponse TRSByAllProjects() {
        return success(FIND_ONE_OK_CODE,trsByProjectMapper.toTRSByProjectDTOList(planningBusiness.TRSByAllProjects()));
    }

    @GetMapping("/TRSByPhase")
    public GenericResponse TRSByPhase() {
        return success(FIND_ONE_OK_CODE,trsByProjectMapper.toTRSByPhaseDTOList(planningBusiness.TRSByPhase()));
    }

    @GetMapping("/TRSByEmp")
    public GenericResponse TRSByEmp() {
        return success(FIND_ONE_OK_CODE, trsByProjectMapper.toTRSByEmpDTOList(planningBusiness.TRSByEmp()));
    }

    @GetMapping("/TRSByEquip")
    public GenericResponse TRSByEquip() {
        return success(FIND_ONE_OK_CODE,trsByProjectMapper.toTRSByEquipDTOList(planningBusiness.TRSByEquip()));
    }

    @GetMapping("/TRSByLigne")
    public GenericResponse TRSByLigne() {
        return success(FIND_ONE_OK_CODE,trsByProjectMapper.toTRSByLigneDTOList(planningBusiness.TRSByLigne()));
    }

    @GetMapping("/StaticOF/{id}")
    public GenericResponse StaticOF(@PathVariable("id") Integer id) {
        return success(FIND_ONE_OK_CODE,trsByProjectMapper.toStaticOFDTOList(planningBusiness.StaticOF(id)));
    }

    @GetMapping("/QteProduiteGammeEmp/{id}")
    public GenericResponse QteProduiteGammeEmp(@PathVariable("id") Integer id) {
        return success(FIND_ONE_OK_CODE,trsByProjectMapper.toQteProduiteList(planningBusiness.QteProduiteGammeEmp(id)));
    }

}
