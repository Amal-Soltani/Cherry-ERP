import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { SpiSharedModule } from '../../shared/spi.shared.module';
import { LoginComponent } from './login/login.component';
import { Error404Component } from './error404/error404.component';
import {
    PerfectScrollbarConfigInterface,
    PERFECT_SCROLLBAR_CONFIG
} from 'ngx-perfect-scrollbar';
import { PagesComponent } from './pages.component';
import { NgxCaptchaModule } from 'ngx-captcha';

const DEFAULT_PERFECT_SCROLLBAR_CONFIG: PerfectScrollbarConfigInterface = {
    wheelPropagation: true
};

/* Use this routes definition in case you want to make them lazy-loaded */
const routes: Routes = [
  { path: '', component: PagesComponent,
    children: [
      { path: 'login', component: LoginComponent },
      { path: '404', component: Error404Component }
    ]
  }
];

@NgModule({
    imports: [
        SpiSharedModule,
        RouterModule.forChild(routes),
        NgxCaptchaModule
    ],
    declarations: [
      PagesComponent,
        LoginComponent,
        Error404Component
    ],
    exports: [
        RouterModule,
        LoginComponent,
        Error404Component,
    ],
    providers: [
        {
            provide: PERFECT_SCROLLBAR_CONFIG,
            useValue: DEFAULT_PERFECT_SCROLLBAR_CONFIG
        }
    ]
})
export class PagesModule { }
