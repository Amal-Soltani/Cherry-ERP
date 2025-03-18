import {Pipe, PipeTransform} from '@angular/core';
import {User} from '../models';

@Pipe({
  name: 'towLettre'
})
export class TowLettrePipe implements PipeTransform {
  transform(ch: string): string {
    if (!ch) {
      return '--';
    }
    if (ch && ch.length === 1) {
      return ch.charAt(0) + ch.charAt(0);
    }
    return ch.charAt(0) + ch.charAt(1);
  }
}
