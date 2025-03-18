package com.cherry.erp.company.model.enums;

import com.cherry.erp.common.utils.ModulesConstant;
import lombok.Getter;

@Getter
public enum BusinessPlanEnum {
    TRIAL(30, 2, 50, 1, ModulesConstant.modules),
    STANDARD(365, 3, 200, 1, ModulesConstant.standardModules),
    PREMIUM(365, 5, 2000, 5, ModulesConstant.premiumModules),
    ENTREPRISE(365, 9999, 99999, 10, ModulesConstant.modules);

    private final int days;
    private final int nbUsers;
    private final int nbDocuments;
    private final int storage;
    private final String[] modules;

    BusinessPlanEnum(int days, int nbUsers, int nbDocuments, int storage, String[] modules) {
        this.days = days;
        this.nbUsers = nbUsers;
        this.nbDocuments = nbDocuments;
        this.storage = storage;
        this.modules = modules;
    }
}
