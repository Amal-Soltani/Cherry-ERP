import { Article } from "./article";
import { GenericEntity } from "./genericEntity";
import { Product } from "./product";

export class RawMaterial extends GenericEntity{
    rawDimension : string;
    grossQuantity : number | null;

    article : Article;

    product : Product;


    constructor(values: object = {}) {
        super();
        Object.assign(this, values);
      }
}
