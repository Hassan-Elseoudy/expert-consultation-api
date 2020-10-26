package com.code4ro.legalconsultation.mail.service;

import com.code4ro.legalconsultation.core.exception.LegalValidationException;
import com.code4ro.legalconsultation.invitation.model.persistence.Invitation;
import com.code4ro.legalconsultation.user.model.persistence.User;
import com.code4ro.legalconsultation.i18n.service.I18nService;
import com.code4ro.legalconsultation.mail.service.impl.MailService;
import com.code4ro.legalconsultation.core.factory.RandomObjectFiller;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.util.ReflectionTestUtils;

import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MailServiceTest {

    @Mock
    private JavaMailSender mailSender;
    @Mock
    private I18nService i18nService;
    @Mock
    private Configuration freemarkerConfig;

    @InjectMocks
    private MailService mailService;

    @Captor
    private ArgumentCaptor<Map<String, String>> modelCaptor;

    @Test
    public void sendRegisterMail() throws IOException, TemplateException {
        ReflectionTestUtils.setField(mailService, "signupUrl", "signupurl");
        ReflectionTestUtils.setField(mailService, "from", "email@legalconsultingtest.ro");
        final User user = Objects.requireNonNull(RandomObjectFiller.createAndFill(User.class));
        final Invitation invitation = Objects.requireNonNull(RandomObjectFiller.createAndFill(Invitation.class));
        invitation.setUser(user);
        final MimeMessage message = mock(MimeMessage.class);
        when(mailSender.createMimeMessage()).thenReturn(message);
        final Template template = mock(Template.class);
        when(freemarkerConfig.getTemplate(anyString())).thenReturn(template);
        when(i18nService.translate("register.User.confirmation.subject")).thenReturn("subject");

        mailService.sendRegisterMail(Collections.singletonList(invitation));

        verify(mailSender).send(message);
        verify(template).process(modelCaptor.capture(), any());
        assertThat(modelCaptor.getValue().get("username")).contains(user.getFirstName() + ' ' + user.getLastName());
        assertThat(modelCaptor.getValue().get("signupurl")).contains("signupurl" + '/' + invitation.getCode());
    }

    @Test(expected = LegalValidationException.class)
    public void sendRegisterMailFailed() {
        final User user = Objects.requireNonNull(RandomObjectFiller.createAndFill(User.class));
        final Invitation invitation = Objects.requireNonNull(RandomObjectFiller.createAndFill(Invitation.class));
        invitation.setUser(user);
        final MimeMessage message = mock(MimeMessage.class);
        when(mailSender.createMimeMessage()).thenReturn(message);

        mailService.sendRegisterMail(Collections.singletonList(invitation));
    }
}
