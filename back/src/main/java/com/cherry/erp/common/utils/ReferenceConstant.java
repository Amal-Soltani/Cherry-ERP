package com.cherry.erp.common.utils;

public class ReferenceConstant {

    private ReferenceConstant() {
        throw new IllegalStateException("Utility Class");
    }

    public static final String PROJECT_REFERENCE_PREFIX = "P-";
    public static final String PROJECT_REFRENCE_FORMAT = "%06d";
    public static final String PROJECT_REFERENCE_QUERY = "SELECT CASE" +
            "       WHEN count(*)=0 THEN 1" +
            "       ELSE max(p.id) + 1 END AS max " +
            "       FROM projects p " +
            "           WHERE p.company_id = :companyId " ;



    public static final String WO_REFERENCE_PREFIX = "WO-";
    public static final String WO_REFRENCE_FORMAT = "%06d";
    public static final String WO_REFERENCE_QUERY = "SELECT CASE" +
            "       WHEN count(*)=0 THEN 1" +
            "       ELSE max(r.id) + 1 END AS max " +
            "       FROM maintenance_interventions r " +
            "           WHERE r.company_id = :companyId " +
            "           AND EXTRACT(YEAR FROM r.creation_date) = :year";


    public static final String ARTICLE_REFERENCE_PREFIX = "AR-";
    public static final String ARTICLE_REFRENCE_FORMAT = "%06d";
    public static final String ARTICLE_REFERENCE_QUERY = "SELECT CASE " +
            "WHEN count(*)=0 THEN 1 " +
            "ELSE max(a.id) + 1 END AS max " +
            "FROM article a " +
            "WHERE a.company_id = :companyId ";

    public static final String FNC_REFERENCE_PREFIX = "NCF-";
    public static final String FNC_REFRENCE_FORMAT = "%06d";
    public static final String FNC_REFERENCE_QUERY = "SELECT CASE " +
            "WHEN count(*)=0 THEN 1 " +
            "ELSE max(a.id) + 1 END AS max " +
            "FROM fnc a " +
            "WHERE a.company_id = :companyId ";

    public static final String PRODUCTION_LINE_REFERENCE_PREFIX = "PL-";
    public static final String PRODUCTION_LINE_REFRENCE_FORMAT = "%06d";
    public static final String PRODUCTION_LINE_REFERENCE_QUERY = "SELECT CASE " +
            "WHEN count(*)=0 THEN 1 " +
            "ELSE max(a.id) + 1 END AS max " +
            "FROM production_ligne a " +
            "WHERE a.company_id = :companyId ";

    public static final String PRODUCTION_ORDER_REFERENCE_PREFIX = "PO-";
    public static final String PRODUCTION_ORDER_REFRENCE_FORMAT = "%06d";
    public static final String PRODUCTION_ORDER_REFERENCE_QUERY = "SELECT CASE " +
            "WHEN count(*)=0 THEN 1 " +
            "ELSE max(a.id) + 1 END AS max " +
            "FROM tache a " +
            "WHERE a.company_id = :companyId ";

    public static final String PRODUCT_REFERENCE_PREFIX = "PR-";
    public static final String PRODUCT_REFRENCE_FORMAT = "%06d";
    public static final String PRODUCT_REFERENCE_QUERY = "SELECT CASE " +
            "WHEN count(*)=0 THEN 1 " +
            "ELSE max(a.id) + 1 END AS max " +
            "FROM product a " +
            "WHERE a.company_id = :companyId ";

    public static final String BOM_REFERENCE_PREFIX = "BOM-";
    public static final String BOM_REFRENCE_FORMAT = "%06d";
    public static final String BOM_REFERENCE_QUERY = "SELECT CASE " +
            "WHEN count(*)=0 THEN 1 " +
            "ELSE max(a.id) + 1 END AS max " +
            "FROM bom a " +
            "join product p on p.bom_id = a.id " +
            "WHERE p.company_id = :companyId ";

    public static final String EQUIPMENT_REFERENCE_PREFIX = "E-";
    public static final String EQUIPMENT_REFRENCE_FORMAT = "%06d";
    public static final String EQUIPMENT_REFERENCE_QUERY = "SELECT CASE " +
            "WHEN count(*)=0 THEN 1 " +
            "ELSE max(a.id) + 1 END AS max " +
            "FROM equipment a " +
            "WHERE a.company_id = :companyId ";

    public static final String DOCUMENT_REFERENCE_PREFIX = "D-";
    public static final String DOCUMENT_REFRENCE_FORMAT = "%06d";
    public static final String DOCUMENT_REFERENCE_QUERY = "SELECT CASE " +
            "WHEN count(*)=0 THEN 1 " +
            "ELSE max(a.id) + 1 END AS max " +
            "FROM document a " +
            "join product p on a.product_id = p.id " +
            "WHERE p.company_id = :companyId ";


    public static final String CLIENT_NUMBER_PREFIX = "CL-";
    public static final String CLIENT_NUMBER_FORMAT = "%06d";
    public static final String CLIENT_NUMBER_QUERY = "SELECT CASE " +
            "WHEN count(*)=0 THEN 1 " +
            "ELSE max(e.id) + 1 END AS max " +
            "FROM employees e " +
            "WHERE e.company_id = :companyId ";
}
