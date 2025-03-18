package com.cherry.erp.common.model.enums;

public enum ResponseRestMsgCodeEnum {

    REQUEST_OK_CODE("server.request.ok"),
    REQUEST_GENERATE_OBJECT_CODE("server.generate.ok"),
    DELETE_OK_CODE("server.delete.ok"),
    DELETE_KO_CODE("server.delete.ko"),
    DELETE_KO_NOT_DETACHED_ENTITY_CODE("server.delete.relatedEntityko"),

    ADD_OK_CODE("server.add.ok"),
    ADD_KO_CODE("server.add.ko"),

    REFERENCE_ALREADY_EXIST("server.reference.alreadyExist"),

    FIND_BYUSER_OK_CODE("server.find-byuser.ok"),
    FIND_ALL_OK_CODE("server.find-all.ok"),
    FIND_ALL_KO_CODE("server.find-all.ko"),

    FIND_ONE_OK_CODE("server.find-one.ok"),
    FIND_ONE_KO_CODE("server.find-one.ko"),

    CHECK_CNX_OK_CODE("server.cnx.ok"),
    CHECK_CNX_KO_CODE("server.cnx.ko"),

    SERVER_SIDE_OK_CODE("server.ok"),
    SERVER_SIDE_ERROR_CODE("server.ko"),

    RUN_AND_LOAD_DATA_OK("server.load-data.ok"),
    RUN_AND_LOAD_DATA_KO("server.load-data.ko"),
    PARSE_PARAM_KO("server.parse.ko"),
    RUN_QUERY_OK("server.run-query.ok"),
    SQL_EXCEPTION("server.sql.ko"),
    /************************************
     * USER MESSAGE CODE
     ************************************/
    USER_MAIL_UNIQUE_KO_CODE("server.uniquemail.ko"),

    RUNTIME_EXCEPTION("server.runtime.exception"),

    GROUP_NAME_UNIQUE_KO_CODE("server.uniquegroup.ko"),

    SEND_MAIL_OK("server.send.mail.ok"),
    SEND_MAIL_KO("server.send.mail.ko"),

    SERVER_OBJECT_ALREADY_EXIST_MSG("server.object.exist"),

    BC_GENERATED_OK_CODE("server.generation.bc.ok"),
    BC_GENERATED_KO_CODE("server.generation.bc.ko"),

    EXPORT_BES_KO("server.export.bonentreesortie.ko"),

    ACCOUNT_VALIDATION_OK_CODE("server.account.validation.ok"),
    ACCOUNT_VALIDATION_KO_CODE("server.account.validation.ko"),

    ACCOUNT_RESEND_TOKEN_OK_CODE("server.resend.token.ok"),
    ACCOUNT_RESEND_TOKEN_KO_CODE("server.resend.token.ko"),

    SEND_RESET_P_OK_CODE("server.send.reset.password.ok"),
    SEND_RESET_P_KO_CODE("server.send.reset.password.ko"),

    SEND_CHANGE_P_KO_CODE("server.change.password.ok"),
    SEND_CHANGE_P_OK_CODE("server.change.password.ko"),

    SEND_CHANGE_IMAGE_KO_CODE("server.change.image.ok"),
    SEND_CHANGE_IMAGE_OK_CODE("server.change.image.ko"),

    RESET_P_KO_CODE("server.reset.password.ok"),
    RESET_P_OK_CODE("server.change.password.ko"),

    EMPLOYEE_NUMBER_ALREADY_EXISTS("server.employee.number.already.exists"),

    FEEDBACK_BUG_RESOLVED_CODE("server.feedback.bug.resolved"),
    FEEDBACK_SENT_CODE("server.feedback.sent"),
    FEEDBACK_RECEIVED_CODE("server.feedback.received"),
    FEEDBACK_MESSAGE_ADDED_CODE("server.feedback.message.added"),

    EMAIL_HELLO_MESSAGE("email-hello-message"),
    EMAIL_CLICK_MESSAGE("email-click-message"),
    EMAIL_REGARDS_MESSAGE("email-regards-message"),
    EMAIL_SIGNATURE_MESSAGE("email-signature-message"),
    EMAIL_SLOGAN_MESSAGE("email-slogan-message"),

    EMAIL_WELCOME_SUBJECT("email-welcome-subject"),
    EMAIL_ACCOUNT_VALIDATION_SUBJECT("email-account-validation-subject"),
    EMAIL_RESET_PASSWORD_SUBJECT("email-reset-password-subject"),
    EMAIL_ACCEPT_INVITATION_SUBJECT("email-accept-invitation-subject"),
    EMAIL_NOTIFICATION_SUBJECT("email-notification-subject"),

    EMAIL_USER_INVITATION_MESSAGE("email-user-invitation-message"),
    EMAIL_VALIDATION_MAIL_MESSAGE("email-validation-mail-message"),
    EMAIL_RESET_PASSWORD_MESSAGE("email-reset-password-message"),

    BON_ENTREE_CREATED_CODE("server.bon.entree.created"),
    BON_SORTIE_CREATED_CODE("server.bon.sortie.created"),
    BON_ENTREE_UPDATED_CODE("server.bon.entree.updated"),
    BON_SORTIE_UPDATED_CODE("server.bon.sortie.updated"),

    STOCK_ARTICLE_OUT_OF_STOCK_CODE("server.article.out.of.stock"),

    PURCHASE_REQUEST_UPDATED("server.purchase.request.updated"),
    PURCHASE_REQUEST_RECEIVED("server.purchase.request.received"),
    PURCHASE_REQUEST_ACCEPTED("server.purchase.request.accepted"),
    PURCHASE_REQUEST_REJECTED("server.purchase.request.rejected"),

    LICENSE_MAX_DOCUMENTS_REACHED("license-max-documents-reached"),
    LICENSE_MAX_USERS_REACHED("license-max-users-reached"),
    LICENSE_MAX_STORAGE_REACHED("license-max-storage-reached"),
    LICENSE_EXPIRED("license-expired"),
    INVALID_LICENSE("invalid-license"),

    BAD_CREDENTIALS("bad-credentials"),
    UNAUTHORIZED_REQUEST("unauthorized-request"),
    RESOURCE_NOT_FOUND("resource-not-found"),
    ACCOUNT_NOT_ACTIVATED("account-not-activated"),
    INCORRECT_RESET_PASSWORD_TOKEN("incorrect-reset-password-token"),
    NEW_PASSWORD_MATCHES_OLD_PASSWORD("new-password-matches-old-password"),
    INCORRECT_PASSWORD("incorrect-password"),
    TOKEN_EXPIRED("token-expired"),
    ACTION_NOT_ALLOWED("action-not-allowed"),
    PASSWORD_MISMATCH("password-mismatch"),
    ALREADY_EXISTS("already-exists"),

    ACCESS_DENIED("access-denied"),
    VALIDATION_ERROR("validation-error"),
    UNEXPECTED_SERVER_ERROR("unexpected-server-error"),
    TRANSACTION_ERROR("transaction-error"),
    DATA_INTEGRITY_ERROR("data-integrity-error"),


    MAINTENANCE_OVERDUE_MESSAGE("maintenance-overdue-message"),
    MAINTENANCE_ALMOST_OVERDUE_MESSAGE("maintenance-almost-overdue-message"),

    POST_AUTOMATICALLY_SHARED("post-automatically-shared"),

    TIMESHEET_CREATED("timesheet-created"),
    TIMESHEET_SENT_FOR_VALIDATION("timesheet-sent-for-validation"),
    TIMESHEET_RECEIVED_FOR_VALIDATION("timesheet-received-for-validation"),
    TIMESHEET_VALIDATED("timesheet-validated"),
    TIMESHEET_REJECTED("timesheet-rejected"),

    DELETE_CURRENCY_NOT_ALLOWED("delete-currency-not-allowed"),

    START_PAYROLL_NOT_ALLOWED("start-payroll-not-allowed"),

    FUND_OPEN_CODE("fund-open"),
    FUND_WAITING_CLOSE_CODE("fund-waiting-close"),
    FUND_CLOSED_CODE("fund-closed"),

    // Excel columns
    // TaskProductionXLS
    XLS_START_DATE("xls-start-date"),
    XLS_END_DATE("xls-end-date"),
    XLS_LIGNE("xls-ligne"),
    XLS_OF("xls-of"),
    XLS_PRODUCT("xls-product"),
    XLS_TASK("xls-task"),
    XLS_OPERATOR("xls-operator"),
    XLS_MACHINE("xls-machine"),
    XLS_TEMPS_ARRET("xls-temps-arret"),
    XLS_QUANTITE_TOTALE("xls-quantite-totale"),
    XLS_QUANTITE_NC("xls-quantite-nc"),
    XLS_QUANTITE_REBUT("xls-quantite-rebut"),
    XLS_NOTE("xls-note"),
    // FundTransactionXLS
    XLS_TYPE("xls-type"),
    XLS_PAYMENT_MODE("xls-payment-mode"),
    XLS_SUPPLIER_CLIENT("xls-supplier-client"),
    XLS_EXPENSE_TYPE("xls-expense-type"),
    XLS_SETTLEMENT_DATE("xls-settlement-date"),
    XLS_AMOUNT("xls-amount"),
    XLS_CURRENCY("xls-currency"),
    XLS_HOLDER("xls-holder"),
    XLS_STATUS("xls-status"),
    XLS_CHECK_NUMBER("xls-check-number"),
    XLS_PROJECT("xls-project"),
    XLS_FUND("xls-fund"),
    XLS_BANK("xls-bank"),
    XLS_CREATION_DATE("xls-creation-date"),
    // StoreArticleXLS
    XLS_ITEM("xls-item"),
    XLS_POSITION("xls-position"),
    XLS_QUANTITY_MIN("xls-quantity-min"),
    XLS_QUANTITY_MAX("xls-quantity-max"),
    XLS_BUYING_PRICE("xls-buying-price"),
    XLS_SELL_PRICE("xls-sell-price"),

    XLS_DESCRIPTION("xls-description"),
    XLS_DEBIT("xls-debit"),
    XLS_CREDIT("xls-credit"),


    PO_ITEM_PARTIAL_INVOICE("po-item-partial-invoice");

    private final String code;

    ResponseRestMsgCodeEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static ResponseRestMsgCodeEnum getByCode(String code) {
        for (ResponseRestMsgCodeEnum en : ResponseRestMsgCodeEnum.values()) {
            if (en.getCode().equals(code)) {
                return en;
            }
        }
        return null;
    }

}
