package com.code4ro.legalconsultation.authentication.controller;

import com.code4ro.legalconsultation.authentication.model.dto.JwtAuthenticationResponse;
import com.code4ro.legalconsultation.authentication.model.dto.LoginRequest;
import com.code4ro.legalconsultation.authentication.model.dto.SignUpRequest;
import com.code4ro.legalconsultation.authentication.model.persistence.ApplicationUser;
import com.code4ro.legalconsultation.authentication.service.ApplicationUserService;
import com.code4ro.legalconsultation.core.exception.LegalValidationException;
import com.code4ro.legalconsultation.core.model.dto.ApiResponse;
import com.code4ro.legalconsultation.security.jwt.JwtTokenProvider;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final ApplicationUserService userService;

    @Autowired
    public AuthController(final AuthenticationManager authenticationManager,
                          final JwtTokenProvider tokenProvider,
                          final ApplicationUserService userService) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.userService = userService;
    }

    @ApiOperation(value = "Call for retrieving a JWT Token using username and password",
            response = JwtAuthenticationResponse.class,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> authenticateUser(
            @ApiParam("Object containing the login request DTO") @Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @ApiOperation(value = "Saving the sign in information for a user",
            response = ApiResponse.class,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> registerUser(
            @ApiParam("Object containing the sign-up request DTO") @Valid @RequestBody SignUpRequest signUpRequest)
            throws LegalValidationException {
        final ApplicationUser result = userService.save(signUpRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true,
                "register.Success"));
    }
}
