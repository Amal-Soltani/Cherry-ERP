import { Company } from "./company";
import { Gamme } from "./gamme";
import { GenericEntity } from "./genericEntity";


export class Equipment extends GenericEntity {
  reference: string;
  name: string;
  emplacement: string;
  purchaseDate: Date;
  releaseDate: Date;
  purchasePrice: number | null;

  description: string;
  stateEnum: StateEnum;
  categoryEnum: CategoryEnum;


  company: Company;


  constructor(values: object = {}) {
    super();
    Object.assign(this, values);
  }
}

export enum CategoryEnum {
  machine = "Machine",
  vehicule = "Véhicule",
  immovable = "Immovable",
  furniture = "Furniture",
  office_supplies = "Office supplies"
}

export enum StateEnum {
  actif = "Actif",
  en_panne = "En panne",
  vendu = "Vendu",
  perdu = "Perdu",
  deteruit = "Déteruit"
}
