package com.cherry.erp.account.security.service;

import com.cherry.erp.account.model.persistent.SpiUser;
import com.cherry.erp.account.repository.UserRepository;
import com.cherry.erp.account.security.domain.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.cherry.erp.common.model.enums.ResponseRestMsgCodeEnum.RESOURCE_NOT_FOUND;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) {
        SpiUser spiUser = userRepository.findByEmailIgnoreCaseAndEnabledTrue(userName).orElseThrow(() -> new UsernameNotFoundException(RESOURCE_NOT_FOUND.getCode()));
        return new MyUserDetails(spiUser);
    }

    public SpiUser findCurrentUser() {
        MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return findUserByUsername(myUserDetails.getUsername());
    }

    public SpiUser findUserByUsername(String username) {
        return userRepository.findByEmailIgnoreCaseAndEnabledTrue(username).orElseThrow(() -> new UsernameNotFoundException(RESOURCE_NOT_FOUND.getCode()));

    }
}
