import {Pipe, PipeTransform} from '@angular/core';
@Pipe ({
  name : 'iconFile'
})
export class IconFilePipe implements PipeTransform {
  transform(status: string): string {

    if(status === 'js' || status === 'js' || status === 'js'){

    }
    switch( status ) {
      case 'ppt': {
        return '<em class="fa-2x far fa-file-powerpoint text-danger"></em>';
      }
       case 'pdf': {
        return '<em class="fa-2x far fa-file-pdf text-danger"></em>';
      }
      case 'csv': {
        return '<em class="fa-2x far fa-file-alt text-success"></em>';
      }
      case 'txt': {
        return '<em class="fa-2x far fa-file-archive text-warning"></em>';
      }
      case 'jpg': {
        return '<em class="fa-2x far fa-file-image text-primary"></em>';
      }
      case 'png': {
        return '<em class="fa-2x far fa-file-image text-primary"></em>';
      }
      case 'svg': {
        return '<em class="fa-2x far fa-file-image text-primary"></em>';
      }
      case 'xlsx': {
        return '<em class="fa-2x far fa-file-excel text-success"></em>';
      }
      case 'xls': {
        return '<em class="fa-2x far fa-file-excel text-success"></em>';
      }
      case 'doc': {
        return '<em class="fa-2x far fa-file-word text-success"></em>';
      }
      case 'docs': {
        return '<em class="fa-2x far fa-file-word text-info"></em>';
      }
      case 'docx': {
        return '<em class="fa-2x far fa-file-word text-info"></em>';
      }
      case 'js': {
        return '<em class="fa-2x far fa-file-code text-purple"></em>';
      }
      case 'css': {
        return '<em class="fa-2x far fa-file-code text-purple"></em>';
      }
      case 'rar': {
        return '<em class="fa-2x far fa-file-archive text-purple"></em>';
      }
      case 'zip': {
        return '<em class="fa-2x far fa-file-archive text-purple"></em>';
      }
      default: {
        return '<em class="fa-2x far fa-file-alt text-success"></em>';
      }
    }

  }
}
