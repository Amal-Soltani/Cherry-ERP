package com.cherry.erp.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.quartz.CronExpression;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Slf4j
public class CronUtils {

    private CronUtils() {
        throw new IllegalStateException("Utility Class");
    }

    public static LocalDateTime getNextValidTime(String cron, Date startDate) {
        Date now = new Date();
        if (startDate.before(now)) {
            startDate = now;
        }
        Date nextValidTime = null;
        try {
            if (StringUtils.isNotBlank(cron)) {
                nextValidTime = new CronExpression(cron).getNextValidTimeAfter(startDate);
            }
        } catch (ParseException e) {
            log.warn("Unable to parse the given cron expression: " + cron, e);
        }
        return nextValidTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

    }

    public static LocalDateTime getNextValidTime(String cron, LocalDateTime startDate) {
        Date nextValidTime = null;
        try {
            if (StringUtils.isNotBlank(cron)) {
                nextValidTime = new CronExpression(cron).getNextValidTimeAfter(Date.from(startDate.atZone(ZoneId.systemDefault()).toInstant()));
            }
        } catch (ParseException e) {
            log.warn("Unable to parse the given cron expression: " + cron, e);
        }
        return nextValidTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

    }

}
