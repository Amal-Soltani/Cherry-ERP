import { Projects } from "./projects";
import { Employee } from "./employee";
import { GenericEntity } from "./genericEntity";
import { Tache } from "./tache";
import { ProductionLigne } from "./productionLigne";

export class Planning extends GenericEntity {

  dateDebut: any;
  dateFin: any;
  tempsArret: number | null;
  phase: string;
  raisonArret: string;
  tempsMachine: number | null;
  qteTotale: number | null;
  qteRebut: number | null;
  qteNC: number | null;
  note: string;
  equipment: string;


  tache: Tache;
  productionLigne: ProductionLigne;
  projects: Projects;
  employee: Employee;

  constructor(values: object = {}) {
    super();
    Object.assign(this, values);
  }
}