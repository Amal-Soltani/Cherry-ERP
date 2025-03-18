import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { RouterModule, Routes } from '@angular/router';
import { AngularEditorModule } from '@kolkov/angular-editor';
import { SpiSharedModule } from 'src/app/shared/spi.shared.module';
import { MatExpansionModule } from '@angular/material/expansion';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { FullCalendarModule } from '@fullcalendar/angular';
import { Ng2SearchPipeModule } from 'ng2-search-filter';
import { MatRadioModule } from '@angular/material/radio';
import { NgSelectModule } from '@ng-select/ng-select';
import * as Highcharts from 'highcharts';
import HC_exporting from 'highcharts/modules/exporting';
import HC_exportData from 'highcharts/modules/export-data';
import { HighchartsChartModule } from 'highcharts-angular';

import { GammeComponent } from '../project/gamme/gamme.component';
import { ProductionComponent } from './production.component';


import { PERFECT_SCROLLBAR_CONFIG, PerfectScrollbarConfigInterface, PerfectScrollbarModule } from 'ngx-perfect-scrollbar';
import { ProductionLineComponent } from './production-line/production-line.component';
import { PlanningComponent } from './planning/planning.component';
import { OFComponent } from './of/of.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { PhaseComponent } from './phase/phase.component';
import { AddOrEditPhaseComponent } from './phase/add-or-edit-phase/add-or-edit-phase.component';
import { AddOrEditProductionLineComponent } from './production-line/add-or-edit-production-line/add-or-edit-production-line.component';
import { PilotageComponent } from '../project/pilotage/pilotage.component';

const DEFAULT_PERFECT_SCROLLBAR_CONFIG: PerfectScrollbarConfigInterface = {
  wheelPropagation: true
};

HC_exporting(Highcharts);
HC_exportData(Highcharts);

const routes: Routes = [
  { path: '', component: ProductionComponent,
  children: [
    { path: 'po', component: OFComponent },
    { path: 'product', loadChildren: () => import('./product/product.module').then(m => m.ProductModule) },
    { path: 'planning', component : PlanningComponent},
    { path: 'pilotage-production', component : PilotageComponent},
    { path: 'dashboard', component : DashboardComponent},
    { path: 'settings',loadChildren: () => import('./settings/settings.module').then(m => m.SettingsModule)},
  ] }
];

@NgModule({
  declarations: [
    ProductionComponent,
    GammeComponent,
    ProductionLineComponent,
    PlanningComponent,
    OFComponent,
    DashboardComponent,
    PhaseComponent,
    AddOrEditPhaseComponent,
    AddOrEditProductionLineComponent
    ],
  imports: [
    CommonModule,
    PerfectScrollbarModule,
    RouterModule.forChild(routes),
    SpiSharedModule,
    AngularEditorModule,
    MatExpansionModule,
    FormsModule,
    ReactiveFormsModule,
    FullCalendarModule,
    Ng2SearchPipeModule,
    MatRadioModule,
    NgSelectModule,
    HighchartsChartModule
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
export class ProductionModule { }
