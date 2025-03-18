package com.cherry.erp.company.model.enums;

import lombok.Getter;

@Getter
public enum SupportEnum {
    SILVER(72), GOLD(48), PLATINUM(24);

    private final int sla;

    SupportEnum(int sla) {
        this.sla = sla;
    }

    public int getSla() {
        return sla;
    }
}