import { GmPhase } from "./GmPhase";
import { Company } from "./company";
import { GenericEntity } from "./genericEntity";

export class Phase extends GenericEntity {
  name: string;
  description : string;

  company: Company;
  gmPhase : Set<GmPhase>;



  constructor(values: object = {}) {
    super();
    Object.assign(this, values);
  }
}
