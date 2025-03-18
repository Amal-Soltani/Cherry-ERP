package com.cherry.erp.modules.common.mail.controller.dto;

import lombok.Data;

import javax.mail.internet.MimeMultipart;
import java.util.List;

@Data
public class Mail {

    private List<String> to;
    private List<String> cc;
    private List<String> bcc;
    private String subject;
    private String content;
    private List<MimeMultipart> attach;

}
