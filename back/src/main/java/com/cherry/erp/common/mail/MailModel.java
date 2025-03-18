package com.cherry.erp.common.mail;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Map;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MailModel {

    private String from;
    private String to;
    private String subject;
    private String content;
    private Map<String, String> model;
    private String template;
}
