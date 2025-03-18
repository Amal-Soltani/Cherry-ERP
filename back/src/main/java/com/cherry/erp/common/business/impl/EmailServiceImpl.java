package com.cherry.erp.common.business.impl;

import com.cherry.erp.common.business.EmailService;
import com.cherry.erp.common.mail.MailModel;
import com.cherry.erp.company.model.persistent.Company;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

    @Value("${platform.url}")
    private String platformUrl;

    @Value("${platform.email.enabled}")
    private boolean emailEnabled;

    private final JavaMailSender emailSender;
    private final Configuration emailConfig;

    @Autowired
    public EmailServiceImpl(@Qualifier("emailSenderBean") JavaMailSender emailSender, @Qualifier("emailConfigBean") Configuration emailConfig) {
        this.emailSender = emailSender;
        this.emailConfig = emailConfig;
    }

    @Override
    public String createLink(String link, Company company) {
        if (Objects.nonNull(company.getLanguage())) {
            String locale = "/" + company.getLanguage();
            return this.platformUrl + locale + link;
        }
        return this.platformUrl + "/fr" + link;
    }

    @Override
    @Async
    public void sendEmailAndSave(MailModel mailModel, Integer companyId) throws MessagingException, IOException, TemplateException {

        if(Objects.nonNull(mailModel.getTo())) {
            Template template = emailConfig.getTemplate(mailModel.getTemplate());
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, mailModel.getModel());

            MimeMessage message = getMimeMessage(mailModel, html);

            try {
                if (emailEnabled) {
                    emailSender.send(message);
                }
            } catch (MailException e) {
                log.error("Error while sending email to {} : {}", mailModel.getTo(), e.getCause().getMessage());
                throw e;
            }
        }

    }

    private MimeMessage getMimeMessage(MailModel mailModel, String html) throws MessagingException {
        mailModel.getModel().put("signature", "Cherry ERP team");
        log.info("Sending Email to: " + mailModel.getTo());

        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

        String emailsTo = mailModel.getTo().replace("\n", ",");
        mimeMessageHelper.setTo(InternetAddress.parse(emailsTo));
        mimeMessageHelper.setText(html, true);

        mimeMessageHelper.setSubject(mailModel.getSubject());
        mimeMessageHelper.setFrom(mailModel.getFrom());
        return message;
    }

}
