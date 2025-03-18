package com.cherry.erp.common.business;

import com.cherry.erp.common.mail.MailModel;
import com.cherry.erp.company.model.persistent.Company;
import freemarker.template.TemplateException;

import javax.mail.MessagingException;
import java.io.IOException;

public interface EmailService {

    String createLink(String link, Company company);

    void sendEmailAndSave(MailModel mailModel, Integer companyId) throws MessagingException, IOException, TemplateException;

}
