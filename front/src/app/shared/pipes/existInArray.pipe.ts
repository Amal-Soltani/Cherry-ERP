import {Pipe, PipeTransform} from "@angular/core";
@Pipe({
  name:'existInArray'
})
export class ExistInArrayPipe implements PipeTransform {

  transform(element: number, arr: number[]): boolean {
    return arr.includes(element);
  }

}
