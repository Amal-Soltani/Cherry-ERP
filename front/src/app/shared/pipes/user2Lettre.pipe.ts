import {Pipe, PipeTransform} from '@angular/core';
import {User} from '../models';

@Pipe({
  name: 'user2Lettre'
})
export class User2LettrePipe implements PipeTransform {
  transform(firstName: string, lastName: string): string {
    let lettre1 = '';
    let lettre2 = '';
    if (firstName) {
      lettre1 = firstName.charAt(0);
    }
    if (lastName) {
      lettre2 = lastName.charAt(0);
    }
    return lettre1 + lettre2;
  }
}
