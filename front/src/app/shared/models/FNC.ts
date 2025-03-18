import { Company } from "./company";
import { Employee } from "./employee";
import { GenericEntity } from "./genericEntity";
import { FNCAnomalies } from "./FNCAnomalies";
import { Tache } from "./tache";

export class FNC extends GenericEntity {
  reference: string;
  projet: number | null;
  equipment: string;
  date: Date;

  FNCAnomalies: Set<FNCAnomalies>;
  employee: Employee;
  tache: Tache;
  company: Company;


  constructor(values: object = {}) {
    super();
    Object.assign(this, values);
  }
}
