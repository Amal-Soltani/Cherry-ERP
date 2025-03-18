import { Gamme } from "./gamme";
import { GenericEntity } from "./genericEntity";
import { Phase } from "./phase";

export class GmPhase extends GenericEntity{
    temps : number | null;
    article :string[];
    produits :string[];
    equipment:string;
    gamme:Gamme
    phase:Phase
    
    constructor(values: object = {}) {
        super();
        Object.assign(this, values);
      }
}
