import { Company } from "./company";
import { Employee } from "./employee";
import { GenericEntity } from "./genericEntity";
import { Product } from "./product";
import { Projects } from "./projects";

export class Tache extends GenericEntity{
    reference : string;
    etat : string ;
    titre : string;
    designation : string;
    quantite : number | null;
    ofexterne : string;
    dateDebutPrev : Date;
    dateFinPrev : Date;
    dateDebutReel : Date;
    dateFinReel : Date;
    bonCmd : string 
    parent : number | null

    priority : PriorityTacheEnum;
    status : StatutTacheEnum ;

    product : Product;
    projet : Projects;
    employee : Employee
    company: Company;



    constructor(values: object = {}) {
        super();
        Object.assign(this, values);
      }

}

export enum StatutTacheEnum {
    a_faire = "A faire",
    en_cours = "En cours",
    termine = "Terminé",
    bloque = "Bloqué",
    en_validation = "En validation",
    valide = "Validé",
    rejete = "Rejeté",
    annule = "Annulé"
}

export enum PriorityTacheEnum {
    haute = "Haute",
    tres_haute = "Trés haute",
    moyenne = "Moyenne",
    basse = "Basse",
    tres_basse = "Trés basse"
}

