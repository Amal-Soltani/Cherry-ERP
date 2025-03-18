package com.cherry.erp.common.model.enums;


public enum GenericParameterEnum {

    STOCK_SHOW_LAERT_IN_FEED ("STOCK_SHOW_LAERT_IN_FEED");

    private final String name;

    GenericParameterEnum(String name) {
        this.name = name;
    }

    public static GenericParameterEnum getValue(String type) {
        for (GenericParameterEnum status : GenericParameterEnum.values()) {
            if (status.getName().equals(type)) {
                return status;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }
}
