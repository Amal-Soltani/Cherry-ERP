import { NgModule } from '@angular/core';

import { LayoutComponent } from './layout.component';
import { SidebarComponent } from './sidebar/sidebar.component';
import { HeaderComponent } from './header/header.component';
import { NavsearchComponent } from './header/navsearch/navsearch.component';
import { FooterComponent } from './footer/footer.component';

import { SpiSharedModule } from '../shared/spi.shared.module';
import {UserblockComponent} from './sidebar/userblock/userblock.component';
import {UserblockService} from './sidebar/userblock/userblock.service';
import {AppService} from '../shared/services/app.service';
import {PerfectScrollbarModule} from 'ngx-perfect-scrollbar';
import {BlockUIModule} from 'ng-block-ui';


@NgModule({
    imports: [
      SpiSharedModule,
      PerfectScrollbarModule,
      BlockUIModule
    ],
    providers: [
      UserblockService, AppService
    ],
    declarations: [
        LayoutComponent,
        SidebarComponent,
        HeaderComponent,
        NavsearchComponent,
        FooterComponent,
        UserblockComponent
    ],
    exports: [
        LayoutComponent,
        SidebarComponent,
        HeaderComponent,
        NavsearchComponent,
        FooterComponent
    ]
})
export class LayoutModule { }
