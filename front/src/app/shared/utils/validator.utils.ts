import {Injectable} from '@angular/core';


@Injectable()
export class ValidatorUtils {

  static phone_regex = /^[\-\+\(\)0-9\s]+$/;
  static alphanumeric_regex = /^[a-z0-9'àáâãäåçèéêëìíîïðòóôõöùúûüýÿ\-\s]+$/i;
  static title_regex = /^[a-z0-9'\.,;_{}@*\[\]àáâãäåçèéêëìíîïðòóôõöùúûüýÿ\(\)\-\s]+$/i;
  static address_regex = /^[a-z0-9',;_àáâãäåçèéêëìíîïðòóôõöùúûüýÿ\-\s]+$/i;
  static reference_regex = /^[a-z0-9\\\/\-_]+$/i;
  static email_regex = /^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3})+$/;
  static cherry_date_format = 'dd-MM-yyyy';
  static cherry_date_heure_format = 'dd-MM-yyyy HH:mm';
   number_regex = '(?:NaN|-?(?:(?:\d+|\d*\.\d+)(?:[E|e][+| -]?\d+)?|Infinity))';

  constructor() {
  }

  isEmailAddress(str: string): boolean {
    return ValidatorUtils.email_regex.test(str);  // returns a boolean
  }

  isNotEmpty(str: string): boolean {
    let pattern = /\S+/;
    return pattern.test(str);  // returns a boolean
  }

  isNumber(str: string): boolean {
    let matchOnlyNumberRe = new RegExp('^(' + this.number_regex + ')$');
    return matchOnlyNumberRe.test(str);
  }

  isSame(str1: string, str2: string): boolean {
    return str1 === str2;
  }

}
