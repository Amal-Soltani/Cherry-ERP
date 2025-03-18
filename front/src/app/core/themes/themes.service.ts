import { Injectable } from '@angular/core';

const themeA = require('../../shared/styles/themes/theme-a.scss');
const themeB = require('../../shared/styles/themes/theme-b.scss');
const themeC = require('../../shared/styles/themes/theme-c.scss');
const themeD = require('../../shared/styles/themes/theme-d.scss');
const themeE = require('../../shared/styles/themes/theme-e.scss');
const themeF = require('../../shared/styles/themes/theme-f.scss');
const themeG = require('../../shared/styles/themes/theme-g.scss');
const themeH = require('../../shared/styles/themes/theme-h.scss');
const themeBLACK = require('../../shared/styles/themes/theme-black.scss');
const themeDefault = require('../../shared/styles/themes/theme-default.scss');

const themeI = require('../../shared/styles/themes/theme-i.scss');
const themeJ = require('../../shared/styles/themes/theme-j.scss');
const themeK = require('../../shared/styles/themes/theme-k.scss');
const themeL = require('../../shared/styles/themes/theme-l.scss');
const themeERP = require('../../shared/styles/themes/theme-ERP.scss');
const themeERPLight = require('../../shared/styles/themes/theme-ERP-light.scss');
const themeERPBlueLight = require('../../shared/styles/themes/theme-ERP-blue-light.scss');
const themeERPAdmin = require('../../shared/styles/themes/theme-ERP-admin.scss');
@Injectable()
export class ThemesService {

  styleTag: any;
  defaultTheme = 'ERP-blue-light';
  loggedAsTheme = 'ERP-admin';

  constructor() {
    this.createStyle();
    this.setTheme(this.defaultTheme);
  }

  private createStyle() {
    const head = document.head || document.getElementsByTagName('head')[0];
    this.styleTag = document.createElement('style');
    this.styleTag.type = 'text/css';
    this.styleTag.id = 'appthemes';
    head.appendChild(this.styleTag);
  }

  setThemeForUser(user: any, loggedAs: any) {
    if (loggedAs != null) {
      this.setTheme('D');
    } else {
      this.setTheme(this.defaultTheme);
    }
  }

  setTheme(name) {
    switch (name) {
      case 'A':
        this.injectStylesheet(themeA);
        break;
      case 'B':
        this.injectStylesheet(themeB);
        break;
      case 'C':
        this.injectStylesheet(themeDefault);
        break;
      case 'D':
        this.injectStylesheet(themeD);
        break;
      case 'E':
        this.injectStylesheet(themeE);
        break;
      case 'F':
        this.injectStylesheet(themeF);
        break;
      case 'G':
        this.injectStylesheet(themeG);
        break;
      case 'H':
        this.injectStylesheet(themeH);
        break;
      case 'BLACK':
        this.injectStylesheet(themeBLACK);
        break;
      case 'default':
        this.injectStylesheet(themeDefault);
        break;
      case 'I':
        this.injectStylesheet(themeI);
        break;
      case 'J':
        this.injectStylesheet(themeJ);
        break;
      case 'K':
        this.injectStylesheet(themeK);
        break;
      case 'L':
        this.injectStylesheet(themeL);
        break;
      case 'ERP':
        this.injectStylesheet(themeERP);
        break;
      case 'ERP-light':
        this.injectStylesheet(themeERPLight);
        break;
      case 'ERP-blue-light':
        this.injectStylesheet(themeERPBlueLight);
        break;
      case 'ERP-admin':
        this.injectStylesheet(themeERPAdmin);
        break;
    }
  }

  injectStylesheet(css) {
    this.styleTag.innerHTML = css;
  }

  getDefaultTheme() {
    return this.defaultTheme;
  }

}
