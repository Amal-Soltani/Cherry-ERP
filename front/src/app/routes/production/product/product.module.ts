import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { RouterModule, Routes } from '@angular/router';
import { PERFECT_SCROLLBAR_CONFIG, PerfectScrollbarConfigInterface, PerfectScrollbarModule } from 'ngx-perfect-scrollbar';

import { AngularEditorModule } from '@kolkov/angular-editor';
import { SpiSharedModule } from 'src/app/shared/spi.shared.module';
import { MatExpansionModule } from '@angular/material/expansion';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { FullCalendarModule } from '@fullcalendar/angular';
import { Ng2SearchPipeModule } from 'ng2-search-filter';
import { MatRadioModule,MAT_RADIO_DEFAULT_OPTIONS } from '@angular/material/radio';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { NgSelectModule } from '@ng-select/ng-select';


import { ProductComponent } from './product.component';
import { ListComponent } from './list/list.component';
import { AddOrEditProductComponent } from './list/add-or-edit-product/add-or-edit-product.component';
import { BOMComponent } from './BOM/BOM.component';
import { AddOrEditBOMComponent } from './BOM/add-or-edit-BOM/add-or-edit-BOM.component';


const DEFAULT_PERFECT_SCROLLBAR_CONFIG: PerfectScrollbarConfigInterface = {
  wheelPropagation: true
};


const routes: Routes = [
  { path: '', component: ProductComponent,
  children: [
    { path: 'list', component: ListComponent },
    { path: 'list/:id',loadChildren: () => import('./list/show-product/show-product.module').then(m => m.ShowProductModule)},
  ] }
];


@NgModule({
  declarations: [
    ProductComponent,
    BOMComponent,
    ListComponent,
    AddOrEditBOMComponent,
    AddOrEditProductComponent,

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
    MatProgressBarModule,
    NgSelectModule

    
  ],
  exports: [
    RouterModule
  ],
  providers: [
    {
      provide: PERFECT_SCROLLBAR_CONFIG, 
      useValue: DEFAULT_PERFECT_SCROLLBAR_CONFIG,
    },
    {
      provide:  MAT_RADIO_DEFAULT_OPTIONS,
      useValue: {color : 'warm'}
    }
  ]
})
export class ProductModule { }
