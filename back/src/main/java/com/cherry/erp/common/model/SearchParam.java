package com.cherry.erp.common.model;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class SearchParam implements Serializable {

    private String name;
    private String field;
    private String type;
    private List<Object> possibeValues;
    private Object defaultValue;
    private ConditionOperator operator;
    private Object value;

    public enum SearchParamTypeEnum {
        STRING("string"), NUMBER("number"), BOOLEAN("boolean"), DATE("date");

        private final String type;

        SearchParamTypeEnum(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }

    public enum ConditionOperator {
        E,// equal
        GT,// greater than
        GTE,// greate than or equal
        LT,// smaller than
        LTE,// smaller than or equal
        LIKE,
        IN, NOTIN,
        NE,// not equal
        TRUE,
        FALSE,
        EQUAL,
        BEFORE,
        AFTER,
        NOTLIKE
    }
}
