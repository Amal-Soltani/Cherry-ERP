import {Pipe, PipeTransform} from '@angular/core';
import {User} from '../models';

@Pipe({
  name: 'nbhours'
})
export class NbhoursPipe implements PipeTransform {

  transform(date1: any, date2: any): number {

    if (!date2 || !date2) {
      return 0;
    }

    let diff = new Date(date1).getTime() - new Date(date2).getTime();
    let hours = Math.abs(diff) / 36e5;
    return Math.round((hours + Number.EPSILON) * 100) / 100;
  }

}

@Pipe({
  name: 'nbminutes'
})
export class NbminutesPipe implements PipeTransform {

  transform(date1: any, date2: any): number {

    if (!date2 || !date2) {
      return 0;
    }

    let diff = new Date(date1).getTime() - new Date(date2).getTime();
    let hours = Math.abs(diff) / 36e5;
    return Math.round((hours * 60 + Number.EPSILON) * 100) / 100;
  }

}
