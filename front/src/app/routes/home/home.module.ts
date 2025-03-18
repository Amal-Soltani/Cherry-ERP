import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CommonModule } from '@angular/common';
import { SpiSharedModule } from '../../shared/spi.shared.module';
import { ImageCropperModule } from 'ngx-image-cropper';
import { NgSelectModule } from '@ng-select/ng-select';
import { SortablejsModule } from 'ngx-sortablejs';
import {
  PerfectScrollbarModule, PerfectScrollbarConfigInterface,
  PERFECT_SCROLLBAR_CONFIG
} from 'ngx-perfect-scrollbar';
import { HomeComponent } from './home.component';
import { CronJobsModule } from 'ngx-cron-jobs';

import { NgxEditorModule } from 'ngx-editor';

import { PickerModule } from '@ctrl/ngx-emoji-mart';


const DEFAULT_PERFECT_SCROLLBAR_CONFIG: PerfectScrollbarConfigInterface = {
  wheelPropagation: true
};

const routes: Routes = [
  { path: '', component: HomeComponent }
];

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    SpiSharedModule,
    ImageCropperModule,
    NgSelectModule,
    SortablejsModule,
    PerfectScrollbarModule,
    SpiSharedModule,
    CronJobsModule,
    NgxEditorModule,
    PickerModule,

  ],
  declarations: [
    HomeComponent,

  ],
  exports: [
    RouterModule
  ],
  providers: [
    {
      provide: PERFECT_SCROLLBAR_CONFIG,
      useValue: DEFAULT_PERFECT_SCROLLBAR_CONFIG
    }
  ]
})
export class HomeModule { }
