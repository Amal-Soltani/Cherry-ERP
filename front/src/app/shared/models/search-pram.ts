interface ISearchParam {
    name: string;
    type: string;
    possibleValues: any;
    defaultValue: any;
}

export class SearchParam {

    name: string;
    field: any;
    type: string;
    possibleValues: any;
    defaultValue: any;
    operator: string;
    value: any;
    id: boolean = false;

    constructor(name: string,
        type: string,
        field: string,
        possibleValues: any,
        defaultValue: any,
        operator: string,
        value: any) {
        this.name = name
        this.field = field
        this.type = type
        this.possibleValues = possibleValues
        this.defaultValue = defaultValue
        this.operator = operator
        this.value = value
    }
}

export enum ConditionOperator {
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
    EQUAL, // used in dates
    BEFORE,
    AFTER,
    NOTLIKE
}
