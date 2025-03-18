import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CommonModule } from '@angular/common';
import { PERFECT_SCROLLBAR_CONFIG, PerfectScrollbarConfigInterface, PerfectScrollbarModule } from 'ngx-perfect-scrollbar';
import {SpiSharedModule} from "../../shared/spi.shared.module";
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { DocumentComponent } from './document.component';
import { AddOrEditFileComponent } from './add-or-edit-file/add-or-edit-file.component';



const DEFAULT_PERFECT_SCROLLBAR_CONFIG: PerfectScrollbarConfigInterface = {
  wheelPropagation: true
};

const routes: Routes = [
  {
    path: '', component: DocumentComponent,
    children: [
    ]
  }
];

@NgModule({
  declarations: [
    DocumentComponent,
    AddOrEditFileComponent
  ],
  imports: [
    CommonModule,
    PerfectScrollbarModule,
    RouterModule.forChild(routes),
    SpiSharedModule,
    FormsModule,
    ReactiveFormsModule,
    PerfectScrollbarModule
  ],
  exports: [
    RouterModule,
   
  ],
  providers: [
    {
      provide: PERFECT_SCROLLBAR_CONFIG,
      useValue: DEFAULT_PERFECT_SCROLLBAR_CONFIG
    }
  ]
})
export class DocumentModule { }
