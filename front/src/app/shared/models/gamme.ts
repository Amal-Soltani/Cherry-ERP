import { GmPhase } from "./GmPhase";
import { Company } from "./company";
import { GenericEntity } from "./genericEntity";
import { Product } from "./product";

export class Gamme extends GenericEntity {

    gmPhase : Set<GmPhase>;
    company: Company;

    constructor(values: object = {}) {
        super();
        Object.assign(this, values);
      }
      
}
