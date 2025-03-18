import { FNCAnomalies } from "./FNCAnomalies";
import { Company } from "./company";
import { GenericEntity } from "./genericEntity";

export class Anomalies extends GenericEntity {
    categorie: string;
    type: string;

    FNCAnomalies: Set<FNCAnomalies>;
    company: Company;


    constructor(values: object = {}) {
        super();
        Object.assign(this, values);
    }
}
