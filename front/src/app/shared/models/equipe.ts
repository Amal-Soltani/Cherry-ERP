import {GenericEntity} from './genericEntity';
import { User } from './user';

export class Equipe extends GenericEntity {


  name: string;
  managerId: number;
  manager: User;

  constructor(values: object = {}) {
    super();
    Object.assign(this, values);
  }
}
