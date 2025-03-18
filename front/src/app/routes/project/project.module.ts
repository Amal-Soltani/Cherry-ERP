import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { RouterModule, Routes } from '@angular/router';
import { PERFECT_SCROLLBAR_CONFIG, PerfectScrollbarConfigInterface, PerfectScrollbarModule } from 'ngx-perfect-scrollbar';
import {MatProgressBarModule} from '@angular/material/progress-bar';

import { AngularEditorModule } from '@kolkov/angular-editor';
import { SpiSharedModule } from 'src/app/shared/spi.shared.module';
import { MatExpansionModule } from '@angular/material/expansion';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { FullCalendarModule } from '@fullcalendar/angular';
import { Ng2SearchPipeModule } from 'ng2-search-filter';
import { ListComponent } from './list/list.component';
import { ProjectComponent } from './project.component';
import { TaskComponent } from './task/task.component';
import { AddOrEditProjectComponent } from './list/add-or-edit-project/add-or-edit-project.component';
import { AddOrEditTaskComponent } from './task/add-or-edit-task/add-or-edit-task.component';
import { NgxEditorModule } from 'ngx-editor';
import { PilotageComponent } from './pilotage/pilotage.component';
import { AddOrEditComponent } from './pilotage/add-or-edit/add-or-edit.component';



const DEFAULT_PERFECT_SCROLLBAR_CONFIG: PerfectScrollbarConfigInterface = {
  wheelPropagation: true
};


const routes: Routes = [
  { path: '', component: ProjectComponent,
  children: [
    { path: 'list', component: ListComponent },
    { path: 'tasks', component: TaskComponent},
    { path: 'list/:idProjet',loadChildren: () => import('./list/show-project/show-project.module').then(m => m.ShowProjectModule)},
  ] }
];


@NgModule({
  declarations: [
    ProjectComponent,
    TaskComponent,
    ListComponent,
    AddOrEditProjectComponent,
    AddOrEditTaskComponent,
    PilotageComponent,
    AddOrEditComponent
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
    NgxEditorModule,
    MatProgressBarModule
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
export class ProjectModule { }
