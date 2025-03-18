package com.cherry.erp.common.mail;

public class EmailConstant {

    private EmailConstant() {
        throw new IllegalStateException("Utility Class");
    }

    public static final String HELLO_MESSAGE = "hello";
    public static final String CLICK_MESSAGE = "clickMessage";
    public static final String REGARDS = "regards";
    public static final String SIGNATURE = "signature";
    public static final String SLOGAN = "slogan";
    public static final String FROM = "contact@cherry-erp.com";

    public static final String TOKEN = "&token=";
    public static final String USER_FULL_NAME = "userFullName";
    public static final String ACTIVATION_LINK = "activationLink";

    public static final String VALIDATION_URL = "/page/validate-account?email=";
    public static final String VALIDATION_TEMPLATE = "emails/account-validation.ftl";

    public static final String INVITATION_URL = "/page/accept-invitation?email=";
    public static final String COMPANY_ID = "&companyId=";
    public static final String USER_ID = "&userId=";

    public static final String RESET_PASS_URL = "/page/reset-password?email=";

    public static final String MAINTENANCE_REMINDER_URL = "/cmms/preventive-maintenance/plans/";

    public static final String NOTIFICATION_TEMPLATE = "emails/notification.ftl";
    public static final String NOTIFICATION_LINK = "link";
    public static final String NOTIFICATION_DETAILS = "details";
    public static final String NOTIFICATION_MESSAGE = "message";

    public static final String ALERT_TEMPLATE = "emails/alert-template.ftl";
    public static final String ALERT_SUBJECT = "CHERRY ALERT";
}
