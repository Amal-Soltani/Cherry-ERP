import { Pipe, PipeTransform } from '@angular/core';
@Pipe({
  name: 'spifilter'
})
export class SpiFilterPipe implements PipeTransform {
  transform(items: any[], searchText: string, field: string): any[] {
    if(!items) return [];
    if(!searchText) return items;
    searchText = searchText.toLowerCase();
    return items.filter( it => {
      let aux = it[field];
      if (field.toLowerCase().includes('\.')) {
        let filedArray = field.split('\.');
        aux = it[filedArray[0]];
        aux = aux[filedArray[1]];
      }

      if (aux) {
        return aux.toLowerCase().includes(searchText);
      } else {
        return false;
      }

    });
  }
}
