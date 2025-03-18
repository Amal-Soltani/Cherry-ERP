/*!
 *
 * Angle - Bootstrap Admin Template
 *
 * Version: 4.7.1
 * Author: @themicon_co
 * Website: http://themicon.co
 * License: https://wrapbootstrap.com/help/licenses
 *
 */

import './vendor.ts';
import { enableProdMode } from '@angular/core';
import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';

import { AppModule } from './app/app.module';
import { environment } from './environments/environment';

/** echarts extensions: */
//import 'echarts-gl';
import 'echarts/theme/macarons.js';
import '../src/assets/js/echarts/default.js';
import '../src/assets/js/echarts/blue.js';
import '../src/assets/js/echarts/dark.js';
import '../src/assets/js/echarts/helianthus.js';
import '../src/assets/js/echarts/infographic.js';
import '../src/assets/js/echarts/mint.js';
import '../src/assets/js/echarts/red.js';
import '../src/assets/js/echarts/roma.js';
import '../src/assets/js/echarts/macarons.js';
import '../src/assets/js/echarts/sakura.js';
import '../src/assets/js/echarts/shine.js';
import '../src/assets/js/echarts/vintage.js';
import '../src/assets/js/echarts/green.js';

//import 'echarts/dist/extension/bmap.min.js';
//import 'echarts-leaflet';
//import 'echarts-liquidfill';
//import 'echarts-stat';

if (environment.production) {
    enableProdMode();
}

let p = platformBrowserDynamic().bootstrapModule(AppModule);
p.then(() => { (<any>window).appBootstrap && (<any>window).appBootstrap(); })
// .catch(err => console.error(err));
