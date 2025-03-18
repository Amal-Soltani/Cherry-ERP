import { Injectable } from '@angular/core';

@Injectable()
export class ColorsService {

    APP_COLORS = {
        'primary': '#5d9cec',
        'success': '#27c24c',
        'info': '#23b7e5',
        'warning': '#ff902b',
        'danger': '#f05050',
        'inverse': '#131e26',
        'green': '#37bc9b',
        'pink': '#f532e5',
        'purple': '#7266ba',
        'dark': '#3a3f51',
        'yellow': '#fad732',
        'gray-darker': '#232735',
        'gray-dark': '#3a3f51',
        'gray': '#dde6e9',
        'gray-light': '#e4eaec',
        'gray-lighter': '#edf1f2'
    };

    COLORS_BY_CHART = {
      'a': '#514e9c',
      'b': '#1c97b4',
      'c': '#e95384',
      'd': '#8f3b79',
      'e': '#c66910',
      'f': '#d02222',
      'g': '#057a5e',
      'h': '#940534',
      'i': '#7266ba',
      'j': '#0e5b18',
      'k': '#0d9474',
      'l': '#103b72',
      'm': '#5d665e',
      'n': '#087091',
      'o': '#8c0385',
      'p': '#f05050',
      'q': '#145445',
      'r': '#772e70',
      's': '#7266ba',
      't': '#d77f1d',
      'u': '#10812c',
      'v': '#593f8a',
      'w': '#393267',
      'x': '#37bc9b',
      'y': '#5b1856',
      'z': '#3e3862'
    };

    constructor() { }

    byName(name) {
        return (this.APP_COLORS[name] || '#fff');
    }

    byChart(ch) {
        return this.COLORS_BY_CHART[ch];
    }

}
