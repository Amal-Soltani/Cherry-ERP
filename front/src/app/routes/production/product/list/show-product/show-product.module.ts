import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PERFECT_SCROLLBAR_CONFIG, PerfectScrollbarConfigInterface, PerfectScrollbarModule } from 'ngx-perfect-scrollbar';
import { BOMComponent } from '../../BOM/BOM.component';
import { ShowProductComponent } from './show-product.component';
import { RouterModule, Routes } from '@angular/router';
import { SpiSharedModule } from 'src/app/shared/spi.shared.module';
import { AngularEditorModule } from '@kolkov/angular-editor';
import { MatExpansionModule } from '@angular/material/expansion';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { FullCalendarModule } from '@fullcalendar/angular';
import { Ng2SearchPipeModule } from 'ng2-search-filter';
import { DocumentComponent } from 'src/app/routes/document/document.component';




const DEFAULT_PERFECT_SCROLLBAR_CONFIG: PerfectScrollbarConfigInterface = {
  wheelPropagation: true
};


const routes: Routes = [
  { path: '', component: ShowProductComponent,
  children: [
    { path: 'nomenclature', component: BOMComponent },
    { path: 'document', component: DocumentComponent }
  ] }
];



@NgModule({
  declarations: [
    ShowProductComponent,
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
export class ShowProductModule { }
