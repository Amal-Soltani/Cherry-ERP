package com.cherry.erp.common.utils;

public class ModulesConstant {


    private ModulesConstant() {
        throw new IllegalStateException("Utility Class");
    }

    public static final String[] modules = new String[]{
            "STOCK", "ACHAT", "COMMERCIAL", "FINANCE",
            "CRM",  "SAV", "PROJET", "DOCUMENTAIRE", "ANALYSE", "CHAT",
            "HR", "ALERT",
            "CLIENT_MANAGEMENT",
            "PRODUCTION", "QUALITY", "GMAO",
            "PROJECT_MANAGEMENT",
            "HR_MANAGEMENT",
            "PARTIAL_PAYMENT",
            "SPIDASH"
    };
    public static final String[] standardModules = new String[]{
            "STOCK", "ACHAT", "COMMERCIAL", "FINANCE",
            "CRM",  "SAV", "PROJET", "DOCUMENTAIRE", "ANALYSE", "CHAT",
            "HR", "ALERT",
            "CLIENT_MANAGEMENT"
    };
    public static final String[] premiumModules = new String[]{
            "STOCK", "ACHAT", "COMMERCIAL", "FINANCE", "HR",
            "CRM", "SAV", "PROJET", "DOCUMENTAIRE", "ANALYSE", "CHAT",
            "ALERT",
            // v2
            "CLIENT_MANAGEMENT",
            "PROJECT_MANAGEMENT",
            "HR_MANAGEMENT",
            "PARTIAL_PAYMENT",
            "SPIDASH"
    };

}
