import {BrowserAnimationsModule} from '@angular/platform-browser/animations'; // this is needed!
import {APP_INITIALIZER, NgModule} from '@angular/core';
import {HttpClientModule} from '@angular/common/http';

import {AppComponent} from './app.component';

import {CoreModule} from './core/core.module';
import {LayoutModule} from './layout/layout.module';
import {RoutesModule} from './routes/routes.module';
import {SpiSharedModule} from './shared/spi.shared.module';
import {SortablejsModule} from 'ngx-sortablejs';
import {BlockUIModule} from 'ng-block-ui';
import {ToastrModule} from 'ngx-toastr';
import {environment} from '../environments/environment';
import {DndModule} from 'ng2-dnd';
import {CalendarModule, DateAdapter} from 'angular-calendar';
import {adapterFactory} from 'angular-calendar/date-adapters/date-fns';
import {FlatpickrModule} from 'angularx-flatpickr';
import { ConfigService } from './core/services/config.service';
import {NgxIntlTelInputModule} from 'ngx-intl-tel-input';
import {AngularFireMessagingModule} from '@angular/fire/compat/messaging';
import {AngularFireModule} from '@angular/fire/compat';

export const configFactory = (configService: ConfigService) => () => configService.loadConfig();

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    HttpClientModule,
    BrowserAnimationsModule, // required for ng2-tag-input
    CoreModule,
    LayoutModule,
    SpiSharedModule.forRoot(),
    DndModule.forRoot(),
    BlockUIModule.forRoot(),
    FlatpickrModule.forRoot(),
    CalendarModule.forRoot({
      provide: DateAdapter,
      useFactory: adapterFactory,
    }),
    ToastrModule.forRoot({
      timeOut: 10000,
      positionClass: 'toast-bottom-right',
      preventDuplicates: true,
    }), // ToastrModule added
    RoutesModule,
    SortablejsModule.forRoot({animation: 150}),
    AngularFireMessagingModule,
    AngularFireModule.initializeApp(environment.firebase),
    NgxIntlTelInputModule
  ],
  providers: [
    {
      provide: APP_INITIALIZER,
      useFactory: configFactory,
      deps: [ConfigService],
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})

export class AppModule {
}
