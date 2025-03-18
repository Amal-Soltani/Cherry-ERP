package com.cherry.erp.account.controller;

import com.cherry.erp.account.controller.mapper.UserMapper;
import com.cherry.erp.account.model.dto.*;
import com.cherry.erp.account.model.persistent.SpiUser;
import com.cherry.erp.account.service.UserService;
import com.cherry.erp.common.utils.RolesConstant;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @Autowired
    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('" + RolesConstant.ROLE_GLOBAL_PARAM_ADMIN + "')")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok().body(userService.findAll().stream()
                .map(userMapper::entityToDto)
                .collect(Collectors.toList()));
    }

    @GetMapping("/all-basic")
    public ResponseEntity<List<BasicUserDto>> getAllBasicUsers() {
        return ResponseEntity.ok().body(userService.findAllActive().stream()
                .map(userMapper::entityToBasicDto)
                .collect(Collectors.toList()));
    }

    @GetMapping("/all-by-company/{companyId}")
    @PreAuthorize("hasAuthority('" + RolesConstant.ROLE_GLOBAL_PARAM_ADMIN + "')")
    public ResponseEntity<Page<BasicUserDto>> getAllByCompany(@PathVariable("companyId") Integer companyId,
                                                              @RequestParam("page") Integer page,
                                                              @RequestParam("pageSize") Integer pageSize) {
        return ResponseEntity.ok().body(userService.findByCompany(companyId, page, pageSize).map(userMapper::entityToBasicDto));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('" + RolesConstant.ROLE_GLOBAL_PARAM_ADMIN + "')")
    public ResponseEntity<Page<UserDto>> getAllPage(@RequestParam("page") Integer page,
                                                    @RequestParam("pageSize") Integer pageSize) {
        return ResponseEntity.ok().body(userService.findByPage(page, pageSize).map(userMapper::entityToDto));
    }

    @GetMapping("/basic-by-page")
    public ResponseEntity<Page<BasicUserDto>> getAllBasicPage(@RequestParam("page") Integer page,
                                                              @RequestParam("pageSize") Integer pageSize) {
        return ResponseEntity.ok().body(userService.findByPage(page, pageSize).map(userMapper::entityToBasicDto));
    }

    @GetMapping("/basic")
    public ResponseEntity<List<BasicUserDto>> getAllBasicPage() {
        return ResponseEntity.ok().body(userService.findAllActive().stream().map(userMapper::entityToBasicDto).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('" + RolesConstant.ROLE_GLOBAL_PARAM_ADMIN + "')")
    public ResponseEntity<UserDto> getSingleUser(@PathVariable Integer id) {
        SpiUser spiUser = userService.findOne(id);
        return ResponseEntity.ok().body(userMapper.entityToDto(spiUser));
    }

    @GetMapping("/login-as/{id}")
    @PreAuthorize("hasAuthority('" + RolesConstant.ROLE_GLOBAL_PARAM_ADMIN + "')")
    public ResponseEntity<Void> loginAs(@PathVariable Integer id) {
        userService.loginAs(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/end-login-as")
    public ResponseEntity<Void> endLoginAs() {
        userService.endLoginAs();
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('" + RolesConstant.ROLE_GLOBAL_PARAM_ADMIN + "')")
    public ResponseEntity<UserDto> updateUser(@PathVariable Integer id) {
        SpiUser spiUser = userService.findOne(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userMapper.entityToDto(userService.save(spiUser)));
    }

    @PostMapping("/enable/{userId}")
    @PreAuthorize("hasAuthority('" + RolesConstant.ROLE_GLOBAL_PARAM_ADMIN + "')")
    public ResponseEntity<Void> enableUser(@PathVariable("userId") Integer userId,
                                           @RequestParam(value = "enable") Boolean enable
    ) {
        userService.setEnable(userId, enable);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('" + RolesConstant.ROLE_GLOBAL_PARAM_ADMIN + "')")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        userService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/findByIds")
    public ResponseEntity<List<BasicUserDto>> findByIds(@RequestParam(value = "ids") Integer[] ids) {
        return ResponseEntity.ok().body(userService.findByIds(ids).stream()
                .map(userMapper::entityToBasicDto)
                .collect(Collectors.toList()));
    }

    @GetMapping(value = "/logout")
    public void logout() {
        userService.logout();
    }





}
