import { UsersComponent } from './users/users.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CommonModule } from '@angular/common';
import { SpiSharedModule } from '../../shared/spi.shared.module';
import { ImageCropperModule } from 'ngx-image-cropper';
import { SortablejsModule } from 'ngx-sortablejs';
import { PERFECT_SCROLLBAR_CONFIG, PerfectScrollbarConfigInterface, PerfectScrollbarModule } from 'ngx-perfect-scrollbar';
import { SettingsSecurityComponent } from './settings-security.component';
import { AddUserComponent } from './users/add-user/add-user.component';
import { EmployeeComponent } from './employees/employee.component';
import { AddOrEditEmployeeComponentV0 } from './employees/add-or-edit-employee/add-or-edit-employee.component-v0';

const DEFAULT_PERFECT_SCROLLBAR_CONFIG: PerfectScrollbarConfigInterface = {
  wheelPropagation: true
};

const routes: Routes = [
  {
    path: '', component: SettingsSecurityComponent,
    children: [
      { path: '', redirectTo: 'employees-list' },
      { path: 'employees-list', component: EmployeeComponent }, // Employee Settings will be included in HR module
      { path: 'users', component: UsersComponent },
    ]
  }
];

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    SpiSharedModule,
    ImageCropperModule,
    SortablejsModule,
    PerfectScrollbarModule,

  ],
  declarations: [
    SettingsSecurityComponent,
    UsersComponent,
    AddUserComponent,
    EmployeeComponent,
    AddOrEditEmployeeComponentV0
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
export class SettingsSecurityModule { }
