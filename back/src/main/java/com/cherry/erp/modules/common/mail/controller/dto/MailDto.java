package com.cherry.erp.modules.common.mail.controller.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class MailDto {

    private Integer numero;
    private Integer messageNumber;
    private Integer size;
    private List<String> from;
    private List<String> replyTo;
    private List<String> allRecipients;
    private String subject;
    private Date receivedDate;
    private Date sentDate;
    private String content;
    private String contentType;
    private Boolean unread;
    private List<String> attach;

}
