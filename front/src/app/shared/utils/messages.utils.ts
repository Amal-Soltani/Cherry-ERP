import {Injectable} from "@angular/core";

@Injectable()
export class MessagesUtils {

  errors: any = {
    credit: {
      'OBJECT_NOT_FOUND': 'Aucun credit trouvé'
    },
    farmer: {
      'OBJECT_NOT_FOUND': 'Aucun agriculteur trouvé',
      'OBJECT_EXISTS': 'Il existe déjà un agriculteur avec ce N° de CIN'
    },
    product: {
      'OBJECT_NOT_FOUND': 'Aucun produit trouvé',
      'OBJECT_EXISTS': 'Il existe déjà un produit avec ce code'
    },
    centre: {
      'OBJECT_NOT_FOUND': 'Aucun centre trouvé',
      'OBJECT_EXISTS': 'Il existe déjà un centre avec ce code'
    },
    sequence: {
      'OBJECT_NOT_FOUND': 'Aucune séquence trouvé',
      'OBJECT_EXISTS': 'Il existe déjà une séquence active pour ce centre pour ce type',
      'COULD_NOT_BE_DELETED': 'Vous ne pouvez pas supprimer une séquence utilisée non femrée.'
    }
    ,
    bonReception: {
      'OBJECT_NOT_FOUND': 'Aucun bon de réception trouvé',
      'OBJECT_EXISTS': 'Il existe déjà un bon de réception ',
      'INVALID_NUM': 'Num déjà utilisé',
      'COULD_NOT_BE_UPDATED': 'Impossible de mettre à jour ce bon de réception'
    } ,
    demandeAnalyse: {
      'OBJECT_NOT_FOUND': 'Aucune demande d\'analyse trouvée',
      'OBJECT_EXISTS': 'Il existe déjà une demande d\'analyse',
      'INVALID_NUM': 'Num déjà utilisé',
      'NO_ANALYSE_IS_REQUIRED': 'Analyse n`\'est pas nécessaire pour ce type de produit',
      'ANALYSE_IS_REQUIRED': 'Une analyse est obligatoire pour ce type de produit.'
    },
    ficheAnalyse: {
      'OBJECT_NOT_FOUND': 'Aucune fiche d\'analyse trouvée',
      'OBJECT_EXISTS': 'Il existe déjà une fiche d\'analyse'
    }

  };

  public getCreditError(code: string) {
    return this.errors.credit[code];
  }

  public getFarmerError(code: string) {
    return this.errors.farmer[code];
  }

  public getProductError(code: string) {
    return this.errors.product[code];
  }

  public getCentreError(code: string) {
    return this.errors.centre[code];
  }

  public getSequenceError(code: string) {
    return this.errors.sequence[code];
  }
  public getBonReceptionError(code: string) {
    return this.errors.bonReception[code];
  }
  public getDemandeAnalayseError(code: string) {
    return this.errors.demandeAnalyse[code];
  }
  public getFicheAnalayseError(code: string) {
    return this.errors.ficheAnalyse[code];
  }


  constructor() {
  }
}
