import { Company } from "./company";
import { Employee } from "./employee";
import { GenericEntity } from "./genericEntity";
import { Tache } from "./tache";

export class Projects extends GenericEntity{
    reference: string;
    titre: string;
    localisation: string;
    statut: StatutProjetEnum;
    designationProjet: Date;
    numBC: string;
    datePrevueLancement: Date;
    dateLancement: Date;
    datePrevueLivraison: Date;
    dateLivraison: Date;
    dateCloture: Date;
    production: boolean ;

    employee: Employee 
    company: Company;


    constructor(values: object = {}) {
        super();
        Object.assign(this, values);
      }
}

export enum StatutProjetEnum {
    brouillon = "Brouillon", 
    en_cours = "En cours",
    cloture = "Terminé",
}

