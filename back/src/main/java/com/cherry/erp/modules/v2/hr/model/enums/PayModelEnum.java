package com.cherry.erp.modules.v2.hr.model.enums;

import lombok.Getter;

@Getter
public enum PayModelEnum {

    PACKAGE_A(true, true, true, true, false, false, false),
    PACKAGE_B(true, true, true, true, true, false, false),
    PACKAGE_C(true, true, true, true, false, true, true);

    private final Boolean fixedSalary;
    private final Boolean ordinaryExtraHour;
    private final Boolean holidayExtraHour;
    private final Boolean dayOffExtraHour;
    private final Boolean offSiteBonus;
    private final Boolean travelBonus;
    private final Boolean accommodationBonus;


    PayModelEnum(Boolean fixedSalary, Boolean ordinaryExtraHour, Boolean holidayExtraHour, Boolean dayOffExtraHour, Boolean offSiteBonus, Boolean travelBonus, Boolean accommodationBonus) {
        this.fixedSalary = fixedSalary;
        this.ordinaryExtraHour = ordinaryExtraHour;
        this.holidayExtraHour = holidayExtraHour;
        this.dayOffExtraHour = dayOffExtraHour;
        this.offSiteBonus = offSiteBonus;
        this.travelBonus = travelBonus;
        this.accommodationBonus = accommodationBonus;
    }
}
