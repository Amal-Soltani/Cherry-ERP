import { GenericEntity } from "./genericEntity";
import { Product } from "./product";

export class QuantityProduct extends GenericEntity{
    qteParAssemblage: number | null;
    parent: Product;
    child: Product;


    constructor(values: object = {}) {
        super();
        Object.assign(this, values);
      }
}
