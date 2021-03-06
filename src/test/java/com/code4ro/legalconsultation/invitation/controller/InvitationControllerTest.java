package com.code4ro.legalconsultation.invitation.controller;

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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class InvitationControllerTest extends AbstractControllerIntegrationTest {
    @Autowired
    private InvitationRepository invitationRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    @WithMockUser
    @Transactional
    public void testGetInvitationNotFound() throws Exception {
        mvc.perform(get(endpoint("/api/invitations/", UUID.randomUUID().toString())))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    @Transactional
    public void testGetUsedInvitation() throws Exception {
        final User user = userRepository.save(Objects.requireNonNull(RandomObjectFiller.createAndFill(User.class)));
        final Invitation invitation = Objects.requireNonNull(RandomObjectFiller.createAndFill(Invitation.class));
        invitation.setUser(user);
        invitation.setStatus(InvitationStatus.USED);
        invitationRepository.save(invitation);

        mvc.perform(get(endpoint("/api/invitations/", invitation.getCode())))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    @Transactional
    public void testGetExpiredInvitation() throws Exception {
        final User user = userRepository.save(Objects.requireNonNull(RandomObjectFiller.createAndFill(User.class)));
        final Invitation invitation = Objects.requireNonNull(RandomObjectFiller.createAndFill(Invitation.class));
        invitation.setUser(user);
        invitation.setStatus(InvitationStatus.EXPIRED);
        invitationRepository.save(invitation);

        mvc.perform(get(endpoint("/api/invitations/", invitation.getCode())))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    @Transactional
    public void testGetInvitation() throws Exception {
        final User user = userRepository.save(Objects.requireNonNull(RandomObjectFiller.createAndFill(User.class)));
        final Invitation invitation = Objects.requireNonNull(RandomObjectFiller.createAndFill(Invitation.class));
        invitation.setUser(user);
        invitation.setStatus(InvitationStatus.PENDING);
        invitationRepository.save(invitation);

        mvc.perform(get(endpoint("/api/invitations/", invitation.getCode()))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(invitation.getCode()))
                .andExpect(jsonPath("$.email").value(user.getEmail()))
                .andExpect(status().isOk());
    }
}
