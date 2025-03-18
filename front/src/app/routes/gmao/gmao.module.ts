import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { GmaoComponent } from './gmao.component';
import { AddOrEditEquipmentComponent } from './add-or-edit-equipment/add-or-edit-equipment.component';
import { PERFECT_SCROLLBAR_CONFIG, PerfectScrollbarConfigInterface, PerfectScrollbarModule } from 'ngx-perfect-scrollbar';
import { AngularEditorModule } from '@kolkov/angular-editor';


import { SpiSharedModule } from 'src/app/shared/spi.shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ListComponent } from './list/list.component';
import { RouterModule, Routes } from '@angular/router';

const DEFAULT_PERFECT_SCROLLBAR_CONFIG: PerfectScrollbarConfigInterface = {
  wheelPropagation: true
};


const routes: Routes = [
  { path: '', component: GmaoComponent,
  children: [
    { path: 'list', component: ListComponent },
  ] }
];

@NgModule({
  declarations: [
    GmaoComponent,
    AddOrEditEquipmentComponent,
    ListComponent
  ],
  imports: [
    CommonModule,
    PerfectScrollbarModule,
    RouterModule.forChild(routes),
    FormsModule, 
    ReactiveFormsModule,
    SpiSharedModule,
    AngularEditorModule
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
export class GmaoModule { }
