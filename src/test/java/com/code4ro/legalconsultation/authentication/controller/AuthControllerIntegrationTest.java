package com.code4ro.legalconsultation.authentication.controller;


import com.code4ro.legalconsultation.authentication.model.dto.LoginRequest;
import com.code4ro.legalconsultation.authentication.model.dto.SignUpRequest;
import com.code4ro.legalconsultation.authentication.repository.ApplicationUserRepository;
import com.code4ro.legalconsultation.core.controller.AbstractControllerIntegrationTest;
import com.code4ro.legalconsultation.core.factory.RandomObjectFiller;
import com.code4ro.legalconsultation.invitation.model.persistence.Invitation;
import com.code4ro.legalconsultation.invitation.model.persistence.InvitationStatus;
import com.code4ro.legalconsultation.invitation.repository.InvitationRepository;
import com.code4ro.legalconsultation.user.model.persistence.User;
import com.code4ro.legalconsultation.user.repository.UserRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthControllerIntegrationTest extends AbstractControllerIntegrationTest {

    @Autowired
    private ApplicationUserRepository applicationUserRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private InvitationRepository invitationRepository;

    @Test
    @Transactional
    public void signUp() throws Exception {
        final User user = userRepository.save(Objects.requireNonNull(RandomObjectFiller.createAndFill(User.class)));
        final Invitation invitation = Objects.requireNonNull(RandomObjectFiller.createAndFill(Invitation.class));
        invitation.setUser(user);
        invitation.setStatus(InvitationStatus.PENDING);
        invitationRepository.save(invitation);

        final SignUpRequest signUpRequest = Objects.requireNonNull(RandomObjectFiller.createAndFill(SignUpRequest.class));
        signUpRequest.setInvitationCode(invitation.getCode());
        signUpRequest.setEmail(user.getEmail());
        String json = objectMapper.writeValueAsString(signUpRequest);

        // register successfully
        mvc.perform(post("/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        assertThat(applicationUserRepository.existsByUsername(signUpRequest.getUsername())).isNotNull();

        // fail to register with same username
        mvc.perform(post("/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.i18nFieldErrors.username.i18nErrorKey")
                        .value("register.Duplicate.username"))
                .andExpect(status().isConflict());

        // fail to register with same email
        signUpRequest.setUsername("userName2");
        json = objectMapper.writeValueAsString(signUpRequest);
        mvc.perform(post("/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.i18nFieldErrors.email.i18nErrorKey")
                        .value("register.Duplicate.email"))
                .andExpect(status().isConflict());
    }

    @Test
    @Transactional
    public void login() throws Exception {
        final User user = userRepository.save(Objects.requireNonNull(RandomObjectFiller.createAndFill(User.class)));
        final Invitation invitation = Objects.requireNonNull(RandomObjectFiller.createAndFill(Invitation.class));
        invitation.setUser(user);
        invitation.setStatus(InvitationStatus.PENDING);
        invitationRepository.save(invitation);

        // register user
        final SignUpRequest signUpRequest = Objects.requireNonNull(RandomObjectFiller.createAndFill(SignUpRequest.class));
        signUpRequest.setInvitationCode(invitation.getCode());
        signUpRequest.setEmail(user.getEmail());
        String json = objectMapper.writeValueAsString(signUpRequest);
        mvc.perform(post("/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        final LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsernameOrEmail(signUpRequest.getUsername());
        loginRequest.setPassword(signUpRequest.getPassword());
        json = objectMapper.writeValueAsString(loginRequest);

        // login successfully with existing user
        mvc.perform(post("/api/auth/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // fail to login
        loginRequest.setUsernameOrEmail("differentUsername");
        json = objectMapper.writeValueAsString(loginRequest);
        mvc.perform(post("/api/auth/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.i18nErrors[0].i18nErrorKey")
                        .value("login.Bad.credentials"))
                .andExpect(status().isUnauthorized());
    }
}
