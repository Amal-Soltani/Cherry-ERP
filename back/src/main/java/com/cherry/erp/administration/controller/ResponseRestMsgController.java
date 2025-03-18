package com.cherry.erp.administration.controller;

import com.cherry.erp.administration.business.ResponseRestMsgService;
import com.cherry.erp.administration.model.persistent.ResponseRestMsg;
import com.cherry.erp.common.model.dto.GenericResponse;
import com.cherry.erp.common.utils.RolesConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.cherry.erp.common.model.enums.ResponseRestMsgCodeEnum.*;
import static com.cherry.erp.common.utils.ResponseFactory.success;

@RestController
@RequestMapping("/response-rest-msgs")
@Slf4j
public class ResponseRestMsgController {

    private final ResponseRestMsgService responseRestMsgService;

    public ResponseRestMsgController(ResponseRestMsgService responseRestMsgService) {
        this.responseRestMsgService = responseRestMsgService;
    }

    @GetMapping("/all")
    public GenericResponse getAll() {
        return success(FIND_ALL_OK_CODE, responseRestMsgService.findAll());
    }

    @GetMapping
    public ResponseEntity<Page<ResponseRestMsg>> getAllPage(@RequestParam("page") Integer page,
                                                            @RequestParam("pageSize") Integer pageSize) {
        return ResponseEntity.ok().body(responseRestMsgService.findByPage(page, pageSize));
    }

    @GetMapping("/{id}")
    public GenericResponse find(@PathVariable("id") Integer id) {
        return success(FIND_ONE_OK_CODE, responseRestMsgService.findOne(id));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('" + RolesConstant.ROLE_ERP_ADMIN + "')")
    public GenericResponse save(@RequestBody ResponseRestMsg responseRestMsg) {
        return success(ADD_OK_CODE, responseRestMsgService.save(responseRestMsg));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('" + RolesConstant.ROLE_ERP_ADMIN + "')")
    public GenericResponse update(@PathVariable("id") Integer id, @RequestBody ResponseRestMsg responseRestMsg) {
        responseRestMsg.setId(id);
        return success(ADD_OK_CODE, responseRestMsgService.save(responseRestMsg));
    }

    @PreAuthorize("hasAuthority('" + RolesConstant.ROLE_ERP_ADMIN + "')")
    @DeleteMapping("/{id}")
    public GenericResponse delete(@PathVariable("id") Integer id) {
        responseRestMsgService.delete(id);
        return success(DELETE_OK_CODE, id);
    }

}
