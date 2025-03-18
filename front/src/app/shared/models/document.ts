import { Company } from "./company";
import { GenericEntity } from "./genericEntity";
import { Product } from "./product";

export class Document extends GenericEntity {
    reference : string;
    indice : string;
    data : any;
    name : string;
    size : number | null;
    type : string;

    product : Product;
    company: Company;

    constructor(values: object = {}) {
        super();
        Object.assign(this, values);
      }
}