package com.cherry.erp.account.security.controller;

import com.cherry.erp.account.controller.mapper.UserMapper;
import com.cherry.erp.account.model.dto.UserDto;
import com.cherry.erp.account.model.persistent.SpiUser;
import com.cherry.erp.account.repository.UserRepository;
import com.cherry.erp.account.security.domain.AuthenticationRequest;
import com.cherry.erp.account.security.domain.AuthenticationResponse;
import com.cherry.erp.account.security.service.MyUserDetailsService;
import com.cherry.erp.account.security.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Objects;

@RestController
@RequestMapping("/login")
public class AuthenticationController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    MyUserDetailsService userDetailsService;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRepository userRepository;;

    @GetMapping("/current-user")
    public UserDto getCurrentUser() {
        return userMapper.entityToDto(userDetailsService.findCurrentUser());
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        try {
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect username or password", e);
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        SpiUser spiUser = userDetailsService.findUserByUsername(userDetails.getUsername());

        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(getResponse(jwt, spiUser));
    }

    private AuthenticationResponse getResponse(String jwt, SpiUser spiUser) {
        // if not logged as another user, return the user
        if (spiUser.getLoggedAs() == null) {
            spiUser.setLastConnexion(new Date());
            if (Objects.isNull(spiUser.getConnection())) {
                spiUser.setConnection(1);
            } else {
                spiUser.setConnection(spiUser.getConnection() + 1);
            }
            return AuthenticationResponse.builder()
                    .user(userMapper.entityToDto(userRepository.save(spiUser)))
                    .token(jwt)
                    .build();
        } else {
            // if logged as another user, return the user logged as
            return AuthenticationResponse.builder()
                    .user(userMapper.entityToDto(userRepository.findById(spiUser.getLoggedAs()).orElse(spiUser)))
                    .token(jwt)
                    .loggedAs(spiUser.getLoggedAs())
                    .build();
        }
    }

}
