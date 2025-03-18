package com.cherry.erp.modules.v2.hr.controller;

import com.cherry.erp.common.model.dto.GenericResponse;
import com.cherry.erp.common.utils.RolesConstant;
import com.cherry.erp.modules.v2.hr.controller.mapper.EmployeeMapper;
import com.cherry.erp.modules.v2.hr.model.dto.BasicEmployeeDto;
import com.cherry.erp.modules.v2.hr.model.dto.EmployeeDto;
import com.cherry.erp.modules.v2.hr.model.persistent.Employee;
import com.cherry.erp.modules.v2.hr.repository.EmployeeRepository;
import com.cherry.erp.modules.v2.hr.service.EmployeeBusiness;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.cherry.erp.common.model.enums.ResponseRestMsgCodeEnum.*;
import static com.cherry.erp.common.utils.ResponseFactory.success;

@RestController
@RequestMapping("/employees")
@Slf4j
public class EmployeeController {

    private final EmployeeBusiness employeeBusiness;
    private final EmployeeMapper employeeMapper;

    @Autowired
    public EmployeeController(EmployeeBusiness employeeBusiness, EmployeeMapper employeeMapper) {
        this.employeeBusiness = employeeBusiness;
        this.employeeMapper = employeeMapper;
    }

    public GenericResponse getAll() {
        return success(FIND_ALL_OK_CODE, employeeMapper.toDtoList(employeeBusiness.findAll()));
    }

    @GetMapping("/all-basic")
    public GenericResponse getAllBasicEmployees() {
        return success(FIND_ALL_OK_CODE, employeeMapper.toBasicDtoList(employeeBusiness.findActiveByCompanyId()));
    }

    @GetMapping("/{id}")
    public GenericResponse find(@PathVariable("id") Integer id) {
        return success(FIND_ONE_OK_CODE, employeeMapper.toDto(employeeBusiness.findOne(id)));
    }

    @PostMapping
    public GenericResponse save(@RequestBody EmployeeDto employeeDto) {
        return success(ADD_OK_CODE, employeeMapper.toDto(employeeBusiness.save(employeeMapper.toEntity(employeeDto))));
    }

    @PutMapping("/{id}")
    public GenericResponse update(@PathVariable("id") Integer id, @RequestBody EmployeeDto employeeDto) {
        Employee employee = employeeMapper.toEntity(employeeDto);
        employee.setId(id);
        return success(ADD_OK_CODE, employeeMapper.toDto(employeeBusiness.save(employee)));
    }

    @DeleteMapping("/{id}")
    public GenericResponse delete(@PathVariable("id") Integer id) {
        employeeBusiness.delete(id);
        return success(DELETE_OK_CODE, id);
    }

    @GetMapping("/findByIds")
    public ResponseEntity<List<BasicEmployeeDto>> findByIds(@RequestParam(value = "ids") Integer[] ids) {
        return ResponseEntity.ok().body(employeeBusiness.findByIds(ids).stream()
                .map(employeeMapper::toBasicDto)
                .collect(Collectors.toList()));
    }

    @GetMapping("/client")
    public GenericResponse getClient() {
        return success(FIND_ALL_OK_CODE, employeeMapper.toDtoList(employeeBusiness.getClient()));
    }

    @GetMapping("/findBytache/{tacheId}")
    public GenericResponse findBytache(@PathVariable("tacheId") Integer tacheId) {
        return success(FIND_ALL_OK_CODE, employeeMapper.toDtoList(employeeBusiness.findBytache(tacheId)));
    }

    @GetMapping("/byTache/{tache}")
    public GenericResponse FindByTache (@PathVariable("tache")Integer idTache){
        return success(FIND_ONE_OK_CODE, employeeMapper.toDto(employeeBusiness.FindByTache(idTache)));
    }

}
