import { Company } from "./company";
import { Employee } from "./employee";
import { GenericEntity } from "./genericEntity";

export class ProductionLigne extends GenericEntity {
    reference : string;
    name : string;
    
    company: Company;
    employee: Employee;

    constructor(values: object = {}) {
        super();
        Object.assign(this, values);
      }
}


