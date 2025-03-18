import { GenericEntity } from "./genericEntity";

export class BOM extends GenericEntity {
    reference : string;
    designation : string;
    indice : string;
    manufacturingType : ManufacturingTypeEnum;
    manufacturingProcesses : string[];

    constructor(values: object = {}) {
        super();
        Object.assign(this, values);
      }

}

export enum ManufacturingTypeEnum {
  unitaire = "Unitaire",
  serie = "Série"
  }


