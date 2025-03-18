
import {GenericEntity} from './genericEntity';

export class CompanySetting {
  name: string;
  value: string;
  description: string;

  constructor(name: string, value: any) {
      this.name = name;
      this.value = value;
  }
}

export class CompanySettings {
  settings: CompanySetting[];
  signatureImage: string;
}

export class DocumentTax {
  name: string;
  defaultValue: number;
  positiveValue = true;
}

export class Company extends GenericEntity {

  name: string;
  logo: string;
  slogan: string;
  adresse: string;
  registreDeCommerce: string;
  matriculeFiscale: string;
  codeDouane: string;
  email: string;
  tel: string;
  fax: string;
  mobile: string;
  taxes: any[];
  documentTaxes: DocumentTax[];
  currencies: any[];
  license: any;
  settings: CompanySettings;
  support: any;
  language: string;
  theme: string;
  localize: boolean;

  businessDays: any;

  constructor(values: object = {}) {
    super();
    Object.assign(this, values);
  }
}
