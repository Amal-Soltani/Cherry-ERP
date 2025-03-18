import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { SpiSharedModule } from '../shared/spi.shared.module';

import { menu } from './menu';
import { MenuService } from '../core/menu/menu.service';
import { RoutesRoutingModule } from './routes-routing.module';



@NgModule({
  imports: [
    RoutesRoutingModule,
    SpiSharedModule,

  ],
  declarations: [

  ],
  providers: [],
  exports: [
    RouterModule
  ]
})

export class RoutesModule {
  constructor(public menuService: MenuService) {
    menuService.addMenu(menu);
  }
}
