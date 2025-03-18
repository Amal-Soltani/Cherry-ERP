import { FNC } from "./FNC";
import { Anomalies } from "./anomalies";
import { GenericEntity } from "./genericEntity";

export class FNCAnomalies extends GenericEntity{
    nc : number | null;
    rebut : number | null;
    cout: number | null;
    description: string;
    cause: string;

    decision : DecsionEnum;
    
    anomalies: Anomalies;
    FNC: FNC;


    constructor(values: object = {}) {
        super();
        Object.assign(this, values);
      }
}

export enum DecsionEnum {
    destruction = "Destruction",
    recuperation = "Récupération",
    derogation = "Dérogation"
}