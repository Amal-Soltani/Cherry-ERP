import { Company } from "./company";
import { Gamme } from "./gamme";
import { GenericEntity } from "./genericEntity";
import { BOM } from "./b-o-m";
import { QuantityProduct } from "./quantityProduct";
import { RawMaterial } from "./raw-material";

export class Product extends GenericEntity {
  reference: string;
  name: string;
  matiere: string;
  libelle: string;
  typeProduct: TypeProductEnum;
  unitMeasurement: UnitMeasurementEnum;
  designation : string

  BOM: BOM;
  rawMaterial: Set<RawMaterial>
  QuantityProduct : Set<QuantityProduct>;
  company: Company;
  gamme : Gamme


  constructor(values: object = {}) {
    super();
    Object.assign(this, values);
  }
  
}

export enum TypeProductEnum {
  ASSEMBLAGE = "Produit Assemblé",
  CONSTRUIRE ="Produit à construire",
  MATERIEL ="Matériel"

}

export enum UnitMeasurementEnum {
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


  