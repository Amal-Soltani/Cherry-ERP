package com.cherry.erp.common.utils;

import java.util.UUID;

public class UUIDGenerator {

    private UUIDGenerator() {
        throw new IllegalStateException("Utility Class");
    }

    public static UUID generateType4UUID() {
        return UUID.randomUUID();
    }

}
