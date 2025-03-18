import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PERFECT_SCROLLBAR_CONFIG, PerfectScrollbarConfigInterface, PerfectScrollbarModule } from 'ngx-perfect-scrollbar';
import { RouterModule, Routes } from '@angular/router';
import { SpiSharedModule } from 'src/app/shared/spi.shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatTabsModule } from '@angular/material/tabs';
import { Ng2SearchPipeModule } from 'ng2-search-filter';
import { SettingsComponent } from './settings.component';
import { PhaseComponent } from '../phase/phase.component';
import { ProductionLineComponent } from '../production-line/production-line.component';


const DEFAULT_PERFECT_SCROLLBAR_CONFIG: PerfectScrollbarConfigInterface = {
  wheelPropagation: true
};

const routes: Routes = [
  { path: '', component: SettingsComponent,
  children: [ 
    {
      path: '', redirectTo: 'product/list', pathMatch: 'full'},
    { path: 'product', loadChildren: () => import('../product/product.module').then(m => m.ProductModule) },
    { path: 'production-lines', component : ProductionLineComponent},
    { path: 'manufacturing-steps', component : PhaseComponent},
  ] }
];

@NgModule({
  declarations: [
    SettingsComponent,

  ],
  imports: [
    CommonModule,
    PerfectScrollbarModule,
    RouterModule.forChild(routes),
    SpiSharedModule,
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
export class SettingsModule { }
