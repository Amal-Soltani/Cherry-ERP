import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AnomaliesComponent } from './anomalies/anomalies.component';
import { FNCComponent } from './fnc/fnc.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { AddOrEditFNCComponent } from './fnc/add-or-edit-fnc/add-or-edit-fnc.component';
import { AddOrEditAnomaliesComponent } from './anomalies/add-or-edit-anomalies/add-or-edit-anomalies.component';
import { PERFECT_SCROLLBAR_CONFIG, PerfectScrollbarConfigInterface, PerfectScrollbarModule } from 'ngx-perfect-scrollbar';
import { QualityComponent } from './quality.component';
import { RouterModule, Routes } from '@angular/router';
import { SpiSharedModule } from 'src/app/shared/spi.shared.module';
import { AngularEditorModule } from '@kolkov/angular-editor';
import { MatExpansionModule } from '@angular/material/expansion';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import * as Highcharts from 'highcharts';
import HC_exporting from 'highcharts/modules/exporting';
import HC_exportData from 'highcharts/modules/export-data';
import { HighchartsChartModule } from 'highcharts-angular';
import { Ng2SearchPipeModule } from 'ng2-search-filter';
import { MatRadioModule } from '@angular/material/radio';
import { NgSelectModule } from '@ng-select/ng-select';


const DEFAULT_PERFECT_SCROLLBAR_CONFIG: PerfectScrollbarConfigInterface = {
  wheelPropagation: true
};

HC_exporting(Highcharts);
HC_exportData(Highcharts);

const routes: Routes = [
  { path: '', component: QualityComponent,
  children: [
    { path: 'FNC', component: FNCComponent },
    { path: 'anomalies', component : AnomaliesComponent},
    { path: 'dashboard', component : DashboardComponent}

  ] }
];


@NgModule({
  declarations: [
    QualityComponent,
    AnomaliesComponent,
    FNCComponent,
    DashboardComponent,
    AddOrEditFNCComponent,
    AddOrEditAnomaliesComponent
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
    HighchartsChartModule,
    Ng2SearchPipeModule,
    MatRadioModule,
    NgSelectModule,
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
export class QualityModule { }
