import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { StockComponent } from './stock.component';
import { ArticleComponent } from './article/article.component';
import { AddOrEditArticleComponent } from './article/add-or-edit-article/add-or-edit-article.component';

import { RouterModule, Routes } from '@angular/router';
import { PERFECT_SCROLLBAR_CONFIG, PerfectScrollbarConfigInterface, PerfectScrollbarModule } from 'ngx-perfect-scrollbar';
import { AngularEditorModule } from '@kolkov/angular-editor';
import { SpiSharedModule } from 'src/app/shared/spi.shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { Ng2SearchPipeModule } from 'ng2-search-filter';

const DEFAULT_PERFECT_SCROLLBAR_CONFIG: PerfectScrollbarConfigInterface = {
  wheelPropagation: true
};


const routes: Routes = [
  {
    path: '', component: StockComponent,
    children: [
      {
        path: 'articles',component: ArticleComponent
      },
    ]
  }
];

@NgModule({
  declarations: [
    StockComponent,
    ArticleComponent,
    AddOrEditArticleComponent
  ],
  imports: [
    CommonModule,
    PerfectScrollbarModule,
    RouterModule.forChild(routes),
    SpiSharedModule,
    AngularEditorModule,
    FormsModule,
    ReactiveFormsModule,
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
export class StockModule { }
