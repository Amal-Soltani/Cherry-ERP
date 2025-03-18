package com.cherry.erp.modules.common.mail.controller.dto;

import lombok.Data;

import java.util.List;

@Data
public class FolderMailDto {

    private Integer messageCount;
    private Integer newMessageCount;
    private Integer unreadMessageCount;
    private String folderName;
    private String fullName;
    private List<MailDto> mailDtoList;

}
