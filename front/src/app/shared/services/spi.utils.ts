import {Injectable} from '@angular/core';
import moment from 'moment';

declare let $: any;

@Injectable()
export class SpiUtils {
  constructor() {
  }

  // Convert to JSON using a replacer function to output
  // the string version of a function with /Function(
  // in front and )/ at the end.
  myStringify(option) {
    return JSON.stringify(option, function replacer(name, val) {
      if (typeof val === 'function') {
        return '/Function(' + val.toString() + ')/';
      } else {
        // convert RegExp to string
        if (val && val.constructor === RegExp) {
          return val.toString();
        } else if (name === 'str') { //
          return undefined; // remove from result
        } else {
          return val; // return as is
        }
      }
    }, 4);
  }

  clone(obj: any) {
    let copy;

    if (obj === null || typeof obj !== 'object') {
      return obj;
    }
    //Handle Date object
    if (obj instanceof Date) {
      copy = new Date();
      copy.setTime(obj.getTime());
      return copy;
    }

    // Handle Array
    if (obj instanceof Array) {
      copy = [];
      for (var i = 0, len = obj.length; i < len; i++) {
        copy[i] = this.clone(obj[i]);
      }
      return copy;
    }

    // Handle Object
    if (obj instanceof Object) {
      copy = {};
      for (var attr in obj) {
        if (obj.hasOwnProperty(attr)) {
          copy[attr] = this.clone(obj[attr]);
        }
      }
      return copy;
    }

    throw new Error('Unable to copy obj! Its type isn\'t supported.');
  }

  getDefinedStringValue() {
  }

  getDistinctArrayVals() {
  }

  validateJson() {
  }

  warningMessage() {
  }

  infoMessage() {
  }

  errorMessage() {
  }

  successMessage() {
  }

  onSuccessNotify() {
  }

  onErrorNotify() {
  }

  confirmAction() {
  }

  simpleConfirmAction() {
  }

  openDialogInfo() {
  }

  isDefined(obj: any) {
    return obj !== undefined && obj !== null && obj !== 'null' && obj !== '' && typeof obj !== 'undefined';
  }

  formatSQLCode() {
  }

  replaceAll(str, search, replace) {
    if (str) {
      return str.split(search).join(replace);
    } else {
      return str;
    }
  }

  compareJSONRecursive() {
  }

  converteMonthNumberToText(num: number) {
    switch (num) {
      case 1:
        return 'Jan';
        break;
      case 2:
        return 'Fev';
        break;
      case 3:
        return 'Mar';
        break;
      case 4:
        return 'Avr';
        break;
      case 5:
        return 'Mai';
        break;
      case 6:
        return 'Jun';
        break;
      case 7:
        return 'Jul';
        break;
      case 8:
        return 'Aout';
        break;
      case 9:
        return 'Sep';
        break;
      case 10:
        return 'Oct';
        break;
      case 11:
        return 'Nov';
        break;
      case 12:
        return 'Dec';
        break;
    }
  }

  /**
   * return the differences between two dates (without Time)
   * @param startDate the start date
   * @param endDate the end date
   */
  static diffDates(startDate, endDate) {
    const start = moment(startDate);
    const end = moment(endDate);
    return end.diff(start, 'days');
  }

}
