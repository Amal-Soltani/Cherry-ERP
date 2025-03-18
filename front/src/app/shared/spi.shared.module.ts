import { Injector, ModuleWithProviders, NgModule } from '@angular/core';
import { CommonModule, DatePipe, DecimalPipe } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { TranslateModule } from '@ngx-translate/core';

import { AccordionModule } from 'ngx-bootstrap/accordion';
import { AlertModule } from 'ngx-bootstrap/alert';
import { ButtonsModule } from 'ngx-bootstrap/buttons';
import { CarouselModule } from 'ngx-bootstrap/carousel';
import { CollapseModule } from 'ngx-bootstrap/collapse';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { ModalModule } from 'ngx-bootstrap/modal';
import { PaginationModule } from 'ngx-bootstrap/pagination';
import { ProgressbarModule } from 'ngx-bootstrap/progressbar';
import { RatingModule } from 'ngx-bootstrap/rating';
import { TabsModule } from 'ngx-bootstrap/tabs';
import { TimepickerModule } from 'ngx-bootstrap/timepicker';
import { TooltipModule } from 'ngx-bootstrap/tooltip';
import { PopoverModule } from 'ngx-bootstrap/popover';
import { TypeaheadModule } from 'ngx-bootstrap/typeahead';
import { BsDatepickerModule, DatepickerModule } from 'ngx-bootstrap/datepicker';

import { SparklineDirective } from './directives/sparkline/sparkline.directive';
import { ColorsService } from './colors/colors.service';
import { NowDirective } from './directives/now/now.directive';
import { UserService } from './services/user.service';
import { AuthenticationService } from './services/authentication.service';
import { AuthGuard } from './guards/auth.guard';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { AdminHttpInterceptor } from './utils/admin.http.interceptor';
import { HttpErrorInterceptor } from './utils/error.interceptor';
import { EchartsUtils } from './utils/echarts.utils';

import { SpiUtils } from './services/spi.utils';
import { SpiFilterPipe } from './pipes/spi-filter.pipe';
import { CanAcessDirective } from './guards/can-access.Directive';
import { AccordionGroupComponent } from './component/accordion/accordion-group.component';
import { AccordionComponent } from './component/accordion/accordion.component';
import { ClickOutsideDirective } from './directives/click-outside.directive';

import { NgSelectModule } from '@ng-select/ng-select';
import { Ng2SearchPipeModule } from 'ng2-search-filter';
import { NgxPaginationModule } from 'ngx-pagination';
import { OrderModule } from 'ngx-order-pipe';
import { ToastrModule, ToastrService } from 'ngx-toastr';
import { PERFECT_SCROLLBAR_CONFIG, PerfectScrollbarConfigInterface, PerfectScrollbarModule } from 'ngx-perfect-scrollbar';
import { ConfirmDeleteComponent } from './component/confirm-delete/confirm-delete.component';
import { ArchwizardModule } from 'angular-archwizard';
import { ConfirmActionComponent } from './component/confirm-action/confirm-action.component';
import { ErrorDialogComponent } from './component/error-dialog/error-dialog.component';
import { ErrorDialogService } from './services/error-dialog.service';
import { User2LettrePipe } from './pipes/user2Lettre.pipe';
import { CompanyService } from './services/company.service';
import { EmptyDataInfoComponent } from './component/empty-data-info/empty-data-info.component';
import { IconFilePipe } from './pipes/iconFile.pipe';
import { FileSizePipe } from './pipes/fileSize.pipe';
import { TimeAgoPipe } from './pipes/atime.pipe';
import { EmployeeService } from './services/employee.service';
import { FileUploadModule } from 'ng2-file-upload';
import { SearchEmployeeComponent } from './component/search-employee/search-employee.component';
import { WebSocketService } from './services/websocket.service';
import { SearchEquipmentComponent } from './component/search-equipment/search-equipment.component';
import { ColorPickerModule } from 'ngx-color-picker';
import { RequiredModulesDirective } from './guards/required-modules.directive';
import { ValidatorUtils } from './utils/validator.utils';
import { SpiPaginationComponent } from './component/spi-pagination/spi-pagination.component';
import { CronJobsModule } from 'ngx-cron-jobs';
import { QueryBuilderModule } from 'angular2-query-builder';
import { DndModule } from 'ng2-dnd';
import { SearchUsersComponent } from './component/search-users/search-users.component';
import { MessagingFirebaseService } from './services/messaging-firebase.service';
import { ResponseRestMessageService } from './services/response-rest-message.service';
import { TowLettrePipe } from './pipes/towLettre.pipe';
import { RestMsgPipe } from './pipes/rest-msg.pipe';
import { PickerModule } from '@ctrl/ngx-emoji-mart';
import { NgxEditorModule } from 'ngx-editor';
import { ReferencesConfigService } from './services/referencesConfig.service';
import {NbhoursPipe, NbminutesPipe} from './pipes/nbhours.pipe';
import { SafeHtmlPipe } from './pipes/safe-html.pipe';
import { LightboxModule } from 'ngx-lightbox';
import {ResizableModule} from './component/resizable/resizable.module';
import { ReversePipe } from './pipes/reverse.pipe';
import {ExistInArrayPipe} from './pipes/existInArray.pipe';
import {IconConnectorPipe} from './pipes/iconConnector.pipe';
import {PostAttributePipe} from './pipes/attribute.pipe';
import {NgxBarcode6Module} from 'ngx-barcode6';
import {QRCodeModule} from 'angularx-qrcode';
import {GenericParametersService} from './services/generic-parameters.service';
import {NgxIntlTelInputModule} from 'ngx-intl-tel-input';
import {ImageCropperModule} from 'ngx-image-cropper';
import {GenericGridService} from './services/generic-grid.service';
import {IncidentService} from "./services/incident.service";
import { TacheService } from './services/tache.service';
import { ProjetService } from './services/projet.service';
import { ProductService } from './services/product.service';
import { DocumentService } from './services/document.service';
import { PhaseService } from './services/phase.service';
import { BOMService } from './services/b-o-m.service';
import { GammeService } from './services/gamme.service';
import { ArticleService } from './services/article.service';
import { SearchProductComponent } from './component/search-product/search-product.component';
import { RawMaterialService } from './services/raw-material.service';


const DEFAULT_PERFECT_SCROLLBAR_CONFIG: PerfectScrollbarConfigInterface = {
  wheelPropagation: true
};

export let appInjector: Injector;


@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    TranslateModule,
    AccordionModule.forRoot(),
    AlertModule.forRoot(),
    ButtonsModule.forRoot(),
    CarouselModule.forRoot(),
    CollapseModule.forRoot(),
    DatepickerModule.forRoot(),
    BsDatepickerModule.forRoot(),
    BsDropdownModule.forRoot(),
    ModalModule.forRoot(),
    PaginationModule.forRoot(),
    ProgressbarModule.forRoot(),
    RatingModule.forRoot(),
    TabsModule.forRoot(),
    TimepickerModule.forRoot(),
    TooltipModule.forRoot(),
    PopoverModule.forRoot(),
    TypeaheadModule.forRoot(),

    // Addes by SPIDASH TEAM
    Ng2SearchPipeModule,
    OrderModule,
    NgxPaginationModule,
    NgSelectModule,
    ArchwizardModule,
    PerfectScrollbarModule,
    FileUploadModule,
    ColorPickerModule,
    DndModule,
    CronJobsModule,
    QueryBuilderModule,
    NgxEditorModule,
    PickerModule,
    ResizableModule,
    NgxBarcode6Module,
    QRCodeModule,
    NgxIntlTelInputModule,

  ],
  providers: [
    ColorsService,
    ToastrService,
    UserService,
    AuthenticationService,
    AuthGuard,
    EchartsUtils,
    AuthGuard,
    CanAcessDirective,
    RequiredModulesDirective,
    SpiUtils,
    ErrorDialogService,
    CompanyService,
    EmployeeService,
    GenericGridService,
    DatePipe, IconFilePipe, FileSizePipe, TimeAgoPipe,
    RestMsgPipe,
    WebSocketService,
    ValidatorUtils,
    DecimalPipe,
    MessagingFirebaseService,
    ResponseRestMessageService,
    ReferencesConfigService,
    GenericParametersService,
    IncidentService,
    TacheService,
    ProjetService,
    ProductService,
    DocumentService,
    PhaseService,
    BOMService,
    RawMaterialService,
    GammeService,
    ArticleService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AdminHttpInterceptor,
      multi: true
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: HttpErrorInterceptor,
      multi: true,
    },
    {
      provide: PERFECT_SCROLLBAR_CONFIG,
      useValue: DEFAULT_PERFECT_SCROLLBAR_CONFIG
    },
  ],
  declarations: [
    SparklineDirective,
    NowDirective,
    AccordionGroupComponent,
    AccordionComponent,
    SpiFilterPipe,
    IconConnectorPipe, IconFilePipe, FileSizePipe, TimeAgoPipe, NbhoursPipe, NbminutesPipe, ExistInArrayPipe,
    RestMsgPipe,
    ReversePipe,
    User2LettrePipe,
    TowLettrePipe,
    CanAcessDirective,
    SafeHtmlPipe, PostAttributePipe,
    RequiredModulesDirective,
    ClickOutsideDirective,
    ConfirmDeleteComponent, ConfirmActionComponent, ErrorDialogComponent,
    EmptyDataInfoComponent,
    SearchEmployeeComponent,
    SearchEquipmentComponent,
    // reporting
    SpiPaginationComponent,
    SearchUsersComponent,
    SearchProductComponent
  ],
  exports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    TranslateModule,
    RouterModule,
    AccordionModule,
    AlertModule,
    ButtonsModule,
    CarouselModule,
    CollapseModule,
    DatepickerModule,
    BsDatepickerModule,
    BsDropdownModule,
    ModalModule,
    PaginationModule,
    ProgressbarModule,
    RatingModule,
    TabsModule,
    TimepickerModule,
    TooltipModule,
    PopoverModule,
    TypeaheadModule,
    ToastrModule,
    SparklineDirective,
    NowDirective,
    OrderModule,
    ArchwizardModule,
    LightboxModule,
    NgxBarcode6Module,
    QRCodeModule,
    ImageCropperModule,
    // -- Modules
    NgSelectModule,
    Ng2SearchPipeModule, //including into imports
    NgxPaginationModule,
    DndModule,
    CronJobsModule,
    QueryBuilderModule,
    FileUploadModule,
    ResizableModule,
    SpiFilterPipe,
    ReversePipe,
    User2LettrePipe, IconConnectorPipe, TowLettrePipe, IconFilePipe, FileSizePipe, TimeAgoPipe, NbhoursPipe, NbminutesPipe, ExistInArrayPipe,
    RestMsgPipe,
    RestMsgPipe,
    SafeHtmlPipe, PostAttributePipe,
    // -- Directive
    CanAcessDirective,
    RequiredModulesDirective,
    ClickOutsideDirective,
    // -- Component
    AccordionGroupComponent,
    AccordionComponent,
    ConfirmDeleteComponent,
    EmptyDataInfoComponent,
    SearchEmployeeComponent,
    SearchEquipmentComponent,

    SpiPaginationComponent,
    SearchUsersComponent,
    SearchProductComponent
  ]
})

// https://github.com/ocombe/ng2-translate/issues/209
export class SpiSharedModule {
  constructor(private injector: Injector) {
    appInjector = this.injector;
  }
  static forRoot(): ModuleWithProviders<SpiSharedModule> {
    return {
      ngModule: SpiSharedModule
    };
  }
}






