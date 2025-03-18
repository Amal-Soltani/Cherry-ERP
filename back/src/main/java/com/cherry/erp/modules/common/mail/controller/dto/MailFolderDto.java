package com.cherry.erp.modules.common.mail.controller.dto;

import lombok.Data;

@Data
public class MailFolderDto {
    private String name;
    private Integer count;
    private Integer unreadMessageCount;
    private Integer newMessageCount;
}
