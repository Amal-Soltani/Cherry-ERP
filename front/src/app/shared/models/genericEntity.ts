export class GenericEntity {
  id: number;
  version: number;
  createdBy: string;
  creationDate: Date;
  modificationDate: Date;
  modifiedBy: string;

  constructor(values: object = {}) {
    Object.assign(this, values);
  }
}


