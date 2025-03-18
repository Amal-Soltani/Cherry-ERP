package com.cherry.erp.account.service.impl;

import com.cherry.erp.account.model.persistent.SpiUser;
import com.cherry.erp.account.repository.UserRepository;
import com.cherry.erp.account.service.UserService;
import com.cherry.erp.common.exception.SpiAlreadyExistsException;
import com.cherry.erp.common.exception.SpiException;
import com.cherry.erp.common.exception.SpiNotFoundException;
import com.cherry.erp.common.utils.RolesConstant;
import com.cherry.erp.company.model.persistent.Company;
import com.cherry.erp.modules.v2.hr.repository.EmployeeRepository;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static com.cherry.erp.common.model.enums.ResponseRestMsgCodeEnum.*;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           EmployeeRepository employeeRepository) {
        this.userRepository = userRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public SpiUser create(SpiUser spiUser, boolean invitation) throws MessagingException, IOException, TemplateException {
        return null;
    }

    @Override
    public SpiUser save(SpiUser spiUser) {
        String currentUserName;
        try {
            currentUserName = getCurrentUserName();
        } catch (Exception e) {
            currentUserName = "anonymous";
        }
        spiUser.setModifiedBy(currentUserName);
        if (spiUser.getId() != null && spiUser.getId() == 0) {
            spiUser.setCreatedBy(currentUserName);
        }
        try {
            return userRepository.save(spiUser);
        } catch (Exception e) {
            log.error("Error while saving User : {}", e.getMessage());
            throw new SpiException(HttpStatus.BAD_REQUEST, ADD_KO_CODE);
        }
    }

    @Override
    public List<SpiUser> findAll() {
        SpiUser currentUser = getCurrentUser();
        if (Objects.nonNull(currentUser)) {
            return userRepository.findByEmployeeCompanyIdByOrderByModificationDateDesc(currentUser.getEmployee().getCompany().getId());
        }
        return Collections.emptyList();
    }

    @Override
    public List<SpiUser> findAllActive() {
        SpiUser currentUser = getCurrentUser();
        if (Objects.nonNull(currentUser)) {
            return userRepository.findByEmployeeCompanyIdAndEnabledByOrderByModificationDateDesc(currentUser.getEmployee().getCompany().getId(),true);
        }
        return Collections.emptyList();
    }

    @Override
    public Page<SpiUser> findByPage(Integer page, Integer pageSize) {
        Pageable pageParam = PageRequest.of(page, pageSize);
        return userRepository.findByEmployeeCompanyIdByOrderByModificationDateDesc(getCurrentUser().getEmployee().getCompany().getId(), pageParam);
    }

    @Override
    public SpiUser findOne(Integer id) {
        return userRepository.findByIdAndEmployeeCompanyId(id, getCurrentUser().getEmployee().getCompany().getId())
                .orElseThrow(() -> new SpiNotFoundException(RESOURCE_NOT_FOUND.getCode()));
    }

    @Override
    public void delete(Integer id) {
        try {
            userRepository.deleteById(findOne(id).getId());
        } catch (Exception e) {
            log.error("Error while deleting User : {}", e.getMessage());
            throw new SpiException(HttpStatus.BAD_REQUEST, DELETE_KO_CODE);
        }
    }

    @Override
    public String getCurrentUserName() {
        SpiUser user = getCurrentUser();
        return user.getEmployee().getFirstName() + " " + user.getEmployee().getLastName();
    }

    private SpiUser getConnectedUser() {
        String userName;
        if (!Objects.nonNull(SecurityContextHolder.getContext()) ||
                !Objects.nonNull(SecurityContextHolder.getContext().getAuthentication()) ||
                !Objects.nonNull(SecurityContextHolder.getContext().getAuthentication().getPrincipal())) {
            return null;
        }
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            userName = ((UserDetails) principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userRepository.findByEmailIgnoreCaseAndEnabledTrue(userName)
                .orElseThrow(() -> new SpiException(HttpStatus.UNAUTHORIZED, UNAUTHORIZED_REQUEST));
    }

    @Override
    public SpiUser getCurrentUser() {
        SpiUser connectedUser = getConnectedUser();
        if (connectedUser != null && connectedUser.getLoggedAs() == null) {
            return connectedUser;
        } else if (connectedUser != null && connectedUser.getLoggedAs() != null) {
            return userRepository.findById(connectedUser.getLoggedAs())
                    .orElseThrow(() -> new SpiException(HttpStatus.UNAUTHORIZED, UNAUTHORIZED_REQUEST));
        }
        return null;
    }

    @Override
    public Boolean isConnectedUser() {
        return Objects.nonNull(SecurityContextHolder.getContext()) &&
                Objects.nonNull(SecurityContextHolder.getContext().getAuthentication()) &&
                Objects.nonNull(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

    @Override
    public boolean isAdmin(Integer userId) {
        SpiUser spiUser = findOne(userId);
        return spiUser.hasRole(RolesConstant.ROLE_GLOBAL_PARAM_ADMIN);
    }

    @Override
    public Integer getConnectedUserId() {
        SpiUser connectedUser = getConnectedUser();
        if (Objects.nonNull(connectedUser)) {
            return connectedUser.getId();
        }
        return null;
    }

    @Override
    public boolean setEnable(Integer userId, Boolean enable) {
        SpiUser user = this.findOne(userId);
        // check if there is another enabled user with the same email
        if (Boolean.TRUE.equals(enable)
                && !userRepository.findByEmailIgnoreCaseAndEnabledTrueAndCompanyId(user.getEmail(), user.getEmployee().getCompany().getId()).isEmpty()) {
            throw new SpiAlreadyExistsException();
        }
        user.setEnabled(enable);
        userRepository.save(user);
        return true;
    }

    @Override
    public List<SpiUser> findAllByRole(String role) {
        return userRepository.findAllByRole(role, getCurrentUser().getEmployee().getCompany().getId());
    }

    @Override
    public Optional<SpiUser> getUserByEmployeeId(Integer employeeId) {
        return userRepository.findByEmployeeId(employeeId);
    }

    @Override
    public Page<SpiUser> findByCompany(Integer companyId, Integer page, Integer pageSize) {
        Pageable pageParam = PageRequest.of(page, pageSize);
        return userRepository.findByEmployeeCompanyId(companyId, pageParam);
    }

    @Override
    public void loginAs(Integer id) {
        SpiUser user = getConnectedUser();
        if (Objects.nonNull(user)) {
            user.setLoggedAs(id);
            userRepository.save(user);
        }
    }

    @Override
    public void endLoginAs() {
        SpiUser user = getConnectedUser();
        if (Objects.nonNull(user)) {
            user.setLoggedAs(null);
            userRepository.save(user);
        }
    }

    @Override
    public List<SpiUser> findByIds(Integer[] ids) {
        return userRepository.findByIds(ids);
    }

    @Override
    public List<SpiUser> getUsersByEmployeeIds(Integer[] employeeIds) {
        List<Integer> employeeIdList = Arrays.stream(employeeIds).collect(Collectors.toList());
        List<SpiUser> result = new ArrayList<>();
        employeeIdList.forEach(
                employeeId -> getUserByEmployeeId(employeeId).ifPresent(result::add)
        );
        return result;
    }

    @Override
    public void logout() {
        Integer userId = getConnectedUserId();
    }

    @Override
    public List<SpiUser> findAllInEquipe(Integer equipeId) {
        return userRepository.findAllInEquipe(equipeId);
    }
}
