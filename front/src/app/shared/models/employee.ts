import {GenericEntity} from './genericEntity';
import {Company} from './company';
import { Tache } from './tache';

export class Employee extends GenericEntity {

  employeeNumber: string;
  firstName: string;
  lastName: string;
  post: string;
  image: string;
  imageUrl: string;
  phone: string;
  company: Company;

  hiringDate: Date;
  birthDate: Date;
  children: number;
  emailPerso: string;
  emailPro: string;
  employeeSalaryPackage: any;
  manager: any;
  managerId: number;
  titulaire: boolean;
  familySituation: string;
  gradeId: number;
  grade: string;
  leavingDate: Date;

  payModel: PayModelEnum;

  external: boolean;
  externalType: string;
  partnerId: number;

  
  constructor(values: object = {}) {
    super();
    Object.assign(this, values);
  }

  static salaryFormulaList = [
    {
      name: 'PACKAGE_A',
      fixedSalary: true,
      ordinaryExtraHour: true,
      holidayExtraHour: true,
      dayOffExtraHour: true,
      offSiteBonus: false,
      travelBonus: false,
      accommodationBonus: false
    },
    {
      name: 'PACKAGE_B',
      fixedSalary: true,
      ordinaryExtraHour: true,
      holidayExtraHour: true,
      dayOffExtraHour: true,
      offSiteBonus: true,
      travelBonus: false,
      accommodationBonus: false
    },
    {
      name: 'PACKAGE_C',
      fixedSalary: true,
      ordinaryExtraHour: true,
      holidayExtraHour: true,
      dayOffExtraHour: true,
      offSiteBonus: false,
      travelBonus: true,
      accommodationBonus: true
    }
  ];
}

export enum PayModelEnum {
  PACKAGE_A, PACKAGE_B, PACKAGE_C
}

