
import {GenericEntity} from './genericEntity';
import {Employee} from "./employee";

export class User extends GenericEntity {

  email: string;
  firstName: string;
  lastName: string;
  password: string;
  phone: string;
  roles: string[];
  stores: number[];
  connection: number;
  enabled: boolean;
  lastConnexion: Date;
  companyId: number;
  employee: Employee;

  constructor(values: object = {}) {
    super();
    Object.assign(this, values);
  }
}
