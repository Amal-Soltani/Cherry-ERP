import {GenericEntity} from './genericEntity';
import { Company } from "./company";
import { RawMaterial } from './raw-material';

export class Article extends GenericEntity{
    reference : string;
    name : string;
    matiere: string;
    libelle: string;
    couleur: string;
    taille: number | null;
    longueur: number | null;
    largeur: number | null;
    hauteur: number | null;
    designation: string;
    uniteMesure: UniteMesureEnum;
    
    rawMaterial : Set <RawMaterial>;
    company: Company;


    constructor(values: object = {}) {
        super();
        Object.assign(this, values);
      }
}

export enum UniteMesureEnum {
  piece = "Pièce",
  forfait ="Forfait",
  site = "Site",
  metre = "Mètre",
  metre_carre = "Mètre carré",
  metre_cube = "Mètre cube",
  centimetre= "Centimètre",
  kilogramme= "Kilogramme",
  jour= "Jour",
  heure= "Heure",
  mois= "Mois",
  annee= "Année",
  trimestre= "Trimèstre",
  semestre= "Semestre",
  litre = "Litre(l)",
  ecalitre= "écalitre(dal)",
  hectolitre= "hectolitre(hl)",
  kWh= "kWh",
  MWh= "MWh",
  GWh= "GWh",
  TWh= "TWh",
  unitaire= "Unitaire",
  projet= "Projet",
  devis= "Devis"
}
