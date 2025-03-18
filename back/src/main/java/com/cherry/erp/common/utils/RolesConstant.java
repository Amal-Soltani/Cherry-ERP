package com.cherry.erp.common.utils;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class RolesConstant {

    private RolesConstant() {
        throw new IllegalStateException("Utility Class");
    }

    protected static final String[] roles = new String[]{
            "GL_A",
            "ST_R", "ST_W", "ST_A", "ST_S","ST_Q",
            "CO_R", "CO_W", "CO_A", "CO_S","CO_Q",
            "FI_R", "FI_W", "FI_A", "FI_S","FI_Q",
            "PR_M_R", "PR_M_W", "PR_M_A", "PR_A_R", "PR_A_W", "PR_A_A",
            "HR_R", "HR_W", "HR_A", "HR_S", "HR_Q",
            "MS_R", "MS_W", "MS_A", "MS_S", "MS_Q",
            "QT_R", "QT_W", "QT_A", "QT_S", "QT_Q",
            "AC_R", "AC_W", "AC_A", "AC_S", "AC_Q",
            "CR_R", "CR_W", "CR_A", "CR_S", "CR_Q",
            "PJ_R", "PJ_W", "PJ_A", "PJ_S",
            "AN_R", "AN_W", "AN_A","AN_S",
            "AL_R", "AL_W", "AL_A", "AL_S",
            "SV_R", "SV_W", "SV_A", "SV_S", "SV_Q",
            "EXT"
    };

    protected static final String[] GLOBAL_PARAM = new String[]{"GL_A"};
    protected static final String[] STOCK = new String[]{"ST_R", "ST_W", "ST_A", "ST_S", "ST_Q"};
    protected static final String[] ACHAT = new String[]{"AC_R", "AC_W", "AC_A", "AC_S", "AC_Q"};
    protected static final String[] COMMERCIAL = new String[]{"CO_R", "CO_W", "CO_A", "CO_S", "CO_Q"};
    protected static final String[] FINANCE = new String[]{"FI_R", "FI_W", "FI_A", "FI_S", "FI_Q"};
    protected static final String[] CRM = new String[]{"CR_R", "CR_W", "CR_A", "CR_S", "CR_Q"};
    protected static final String[] PROJET = new String[]{"PJ_R", "PJ_W", "PJ_A", "PJ_S"};
    protected static final String[] ANALYSE = new String[]{"AN_R", "AN_W", "AN_A", "AN_S"};
    protected static final String[] ALERT = new String[]{"AL_R", "AL_W", "AL_A", "AL_S"};
    protected static final String[] PRODUCTION = new String[]{"PR_M_R", "PR_M_W", "PR_M_A", "PR_A_R", "PR_A_W", "PR_A_A"};
    protected static final String[] QUALITY = new String[]{"QT_R", "QT_W", "QT_A", "QT_S"};
    protected static final String[] HR = new String[]{"HR_R", "HR_W", "HR_A", "HR_S", "HR_Q"};
    protected static final String[] GMAO = new String[]{"MS_R", "MS_W", "MS_A", "MS_S", "MS_Q"};
    protected static final String[] SAV = new String[]{"SV_R", "SV_W", "SV_A", "SV_S", "SV_Q"};
    public static final String[] EXTERNAL = new String[]{"EXT"};

    public static final String ROLE_GLOBAL_PARAM_ADMIN = "GL_A";

    public static final String ROLE_STOCK_READ = "ST_R";
    public static final String ROLE_STOCK_WRITE = "ST_W";
    public static final String ROLE_STOCK_ADMIN = "ST_A";
    public static final String ROLE_STOCK_STATS = "ST_S";
    public static final String ROLE_STOCK_QUALITY = "ST_Q";

    public static final String ROLE_ACHAT_READ = "AC_R";
    public static final String ROLE_ACHAT_WRITE = "AC_W";
    public static final String ROLE_ACHAT_ADMIN = "AC_A";
    public static final String ROLE_ACHAT_STATS = "AC_S";
    public static final String ROLE_ACHAT_QUALITY = "AC_Q";

    public static final String ROLE_COMMERCIAL_READ = "CO_R";
    public static final String ROLE_COMMERCIAL_WRITE = "CO_W";
    //parametrage des clients et fournisseurs
    public static final String ROLE_COMMERCIAL_ADMIN = "CO_A";
    public static final String ROLE_COMMERCIAL_STATS = "CO_S";
    public static final String ROLE_COMMERCIAL_QUALITY = "CO_Q";

    public static final String ROLE_FINANCE_READ = "FI_R";
    public static final String ROLE_FINANCE_WRITE = "FI_W";
    public static final String ROLE_FINANCE_ADMIN = "FI_A";
    public static final String ROLE_FINANCE_STATS = "FI_S";
    public static final String ROLE_FINANCE_QUALITY = "FI_Q";

    public static final String ROLE_CRM_READ = "CR_R";
    public static final String ROLE_CRM_WRITE = "CR_W";
    public static final String ROLE_CRM_ADMIN = "CR_A";
    public static final String ROLE_CRM_STATS = "CR_S";
    public static final String ROLE_CRM_QUALITY = "CR_Q";

    public static final String ROLE_PROJET_READ = "PJ_R";
    public static final String ROLE_PROJET_WRITE = "PJ_W";
    public static final String ROLE_PROJET_ADMIN = "PJ_A";
    public static final String ROLE_PROJET_STATS = "PJ_S";
    public static final String ROLE_PROJET_QUALITY = "PJ_S";

    public static final String ROLE_ANALYSE_READ = "AN_R";
    public static final String ROLE_ANALYSE_WRITE = "AN_W";
    public static final String ROLE_ANALYSE_ADMIN = "AN_A";

    public static final String ROLE_ALERT_READ = "AL_R";
    public static final String ROLE_ALERT_WRITE = "AL_W";
    public static final String ROLE_ALERT_ADMIN = "AL_A";


    public static final String ROLE_PRODUCTION_METHODE_READ = "PR_M_R";
    public static final String ROLE_PRODUCTION_METHODE_WRITE = "PR_M_W";
    public static final String ROLE_PRODUCTION_METHODE_ADMIN = "PR_M_A";

    public static final String ROLE_PRODUCTION_ATELIER_READ = "PR_A_R";
    public static final String ROLE_PRODUCTION_ATELIER_WRITE = "PR_A_W";
    public static final String ROLE_PRODUCTION_ATELIER_ADMIN = "PR_A_A";


    public static final String ROLE_QUALITY_READ = "QT_R";
    public static final String ROLE_QUALITY_WRITE = "QT_W";
    public static final String ROLE_QUALITY_ADMIN = "QT_A";
    public static final String ROLE_QUALITY_STATS = "QT_S";
    public static final String ROLE_QUALITY_QUALITY = "QT_Q";

    public static final String ROLE_HR_READ = "HR_R";
    public static final String ROLE_HR_WRITE = "HR_W";
    public static final String ROLE_HR_ADMIN = "HR_A";
    public static final String ROLE_HR_STATS = "HR_S";
    public static final String ROLE_HR_QUALITY = "HR_Q";

    public static final String ROLE_CMMS_READ = "MS_R";
    public static final String ROLE_CMMS_WRITE = "MS_W";
    public static final String ROLE_CMMS_ADMIN = "MS_A";
    public static final String ROLE_CMMS_STATS = "MS_S";
    public static final String ROLE_CMMS_QUALITY = "MS_Q";

    public static final String ROLE_ERP_ADMIN = "ERP_A";

    public static final String ROLE_EXTERNAL = "EXT";

    public static final String ROLE_SAV_READ = "SV_R";
    public static final String ROLE_SAV_WRITE = "SV_W";
    public static final String ROLE_SAV_ADMIN = "SV_A";
    public static final String ROLE_SAV_STATS = "SV_S";
    public static final String ROLE_SAV_QUALITY = "SV_Q";


    public static final String ROLE_GMAO_READ = "MS_R";
    public static final String ROLE_GMAO_WRITE = "MS_W";
    public static final String ROLE_GMAO_ADMIN = "MS_A";
    public static final String ROLE_GMAO_STATS = "MS_S";
    public static final String ROLE_GMAO_QUALITY = "MS_Q";


    public static String[] getRolesByModules(String[] modules) {
        List<String> roles = new LinkedList<>(Arrays.asList(GLOBAL_PARAM));
        List<String> modulesList = Arrays.stream(modules).collect(Collectors.toList());
        if (modulesList.contains("STOCK")) {
            roles.addAll(Arrays.asList(STOCK));
        }
        if (modulesList.contains("ACHAT")) {
            roles.addAll(Arrays.asList(ACHAT));
        }
        if (modulesList.contains("COMMERCIAL")) {
            roles.addAll(Arrays.asList(COMMERCIAL));
        }
        if (modulesList.contains("FINANCE")) {
            roles.addAll(Arrays.asList(FINANCE));
        }
        if (modulesList.contains("CRM")) {
            roles.addAll(Arrays.asList(CRM));
        }
        if (modulesList.contains("PROJET")) {
            roles.addAll(Arrays.asList(PROJET));
        }
        if (modulesList.contains("ANALYSE")) {
            roles.addAll(Arrays.asList(ANALYSE));
        }
        if (modulesList.contains("PRODUCTION")) {
            roles.addAll(Arrays.asList(PRODUCTION));
        }
        if (modulesList.contains("QUALITY")) {
            roles.addAll(Arrays.asList(QUALITY));
        }
        if (modulesList.contains("HR")) {
            roles.addAll(Arrays.asList(HR));
        }
        if (modulesList.contains("GMAO")) {
            roles.addAll(Arrays.asList(GMAO));
        }
        if (modulesList.contains("ALERT")) {
            roles.addAll(Arrays.asList(ALERT));
        }
        if (modulesList.contains("SAV")) {
            roles.addAll(Arrays.asList(SAV));
        }
        return roles.toArray(new String[0]);
    }
}
