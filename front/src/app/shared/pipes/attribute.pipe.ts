import {Pipe, PipeTransform} from '@angular/core';
@Pipe ({
  name : 'postAttributePipe'
})
export class PostAttributePipe implements PipeTransform {
  transform(attribute: string): string {
    switch( attribute ) {
      case 'CELEBRATE': {
        return '<em class="fas fa-trophy"></em>';
      }
      case 'PUSH': {
        return '<em class="fas fa-bicycle"></em>';
      }
      case 'GATHER': {
        return '<em class="fas fa-procedures"></em>';
      }
      case 'WARN': {
        return '<em class="fas fa-bullhorn"></em>';
      }
      case 'CONGRATULATE': {
        return '<em class="fas fa-crown"></em>';
      }
      default: {
        return '<em class="icon-bulb"></em>';
      }
    }

  }
}
