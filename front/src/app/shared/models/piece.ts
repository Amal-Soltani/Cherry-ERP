import {GenericEntity} from './genericEntity';

export class Piece extends GenericEntity {

  reference: string;
  label: string;
  quantity: number;

  constructor(values: object = {}) {
    super();
    Object.assign(this, values);
  }
}
