import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PERFECT_SCROLLBAR_CONFIG, PerfectScrollbarConfigInterface, PerfectScrollbarModule } from 'ngx-perfect-scrollbar';
import { RouterModule, Routes } from '@angular/router';
import { SpiSharedModule } from 'src/app/shared/spi.shared.module';
import { FullCalendarModule } from '@fullcalendar/angular';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatTabsModule } from '@angular/material/tabs';
import { ShowProjectComponent } from './show-project.component';
import { TaskComponent } from '../../task/task.component';

import { Ng2SearchPipeModule } from 'ng2-search-filter';
import { BOMComponent } from 'src/app/routes/production/product/BOM/BOM.component';
import { PilotageComponent } from '../../pilotage/pilotage.component';
import { GeneralInfoComponent } from './general-info/general-info.component';




const DEFAULT_PERFECT_SCROLLBAR_CONFIG: PerfectScrollbarConfigInterface = {
  wheelPropagation: true
};

const routes: Routes = [
  { path: '', component: ShowProjectComponent,
  children: [ 
    { path: 'task', component: TaskComponent},
    { path: 'nomenclatures', component: BOMComponent},
    { path: 'pilotage', component: PilotageComponent},
    { path: 'general-info', component: GeneralInfoComponent}
  ] }
];



@NgModule({
  declarations: [
    ShowProjectComponent,
    GeneralInfoComponent,

  ],
  imports: [
    CommonModule,
    PerfectScrollbarModule,
    RouterModule.forChild(routes),
    SpiSharedModule,
    FullCalendarModule,
    FormsModule,
    ReactiveFormsModule,
    MatTabsModule,
    Ng2SearchPipeModule

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
export class ShowProjectModule { }
