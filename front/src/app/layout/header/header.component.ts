import {AfterViewInit, Component, EventEmitter, HostListener, Inject, LOCALE_ID, OnDestroy, OnInit, Output, ViewChild} from '@angular/core';
const screenfull = require('screenfull');

import { SettingsService } from '../../core/settings/settings.service';
import { MenuService } from '../../core/menu/menu.service';
import { Router } from '@angular/router';
import { AuthenticationService } from '../../shared/services/authentication.service';
import { BsModalRef } from 'ngx-bootstrap';
import { UserService } from '../../shared/services/user.service';
import { ThemesService } from 'src/app/core/themes/themes.service';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import { ConfigService } from 'src/app/core/services/config.service';
import {Subject} from 'rxjs';
import {BlockUI, NgBlockUI} from 'ng-block-ui';
import {ConfirmActionComponent} from '../../shared/component/confirm-action/confirm-action.component';
declare let moment: any;

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit, AfterViewInit, OnDestroy {

  @BlockUI() blockUI: NgBlockUI;
  @Output() public updateVerticalMenuEvt = new EventEmitter();

  localize = ConfigService.config.localize;
  localesList = ConfigService.config.locales;
  currentLocale = this.localesList[0];

  currentCompany: any;
  currentUser: any;
  loggedAs = false;
  imageUser: any;
  ip: string;
  week: any;

  modalRef: BsModalRef;

  isNavSearchVisible: boolean;
  @ViewChild('fsbutton', { static: true }) fsbutton;  // the fullscreen button

  userActivity;
  userInactive: Subject<any> = new Subject();
  inactiveMouse = false;

  constructor(public router: Router,
              private authenticationService: AuthenticationService,
              private modalService: NgbModal,
              public menu: MenuService,
              public themes: ThemesService,
              public userService: UserService,
              public settings: SettingsService,
              @Inject(LOCALE_ID) public locale: string) {
  }

  ngOnInit() {
    this.currentCompany = JSON.parse(localStorage.getItem('company'));
    this.localize = this.localize && this.currentCompany.localize;
    //get current locale
    this.currentLocale = this.localesList.find(item => item.code === this.locale);
    this.localesList = this.localesList.filter(item => item.code !== this.locale);

    this.currentUser = JSON.parse(localStorage.getItem('currentUser'));
    if (this.currentCompany && this.currentCompany.theme) {
      this.themes.setTheme(this.currentCompany.theme);
    }
    if (localStorage.getItem('loggedAs') !== 'null') {
      this.loggedAs = true;
      this.themes.setTheme(this.themes.loggedAsTheme);
    }
    this.isNavSearchVisible = false;
    if (this.fsbutton) {
      let ua = window.navigator.userAgent;
      if (ua.indexOf('MSIE ') > 0 || !!ua.match(/Trident.*rv\:11\./)) { // Not supported under IE
        this.fsbutton.nativeElement.style.display = 'none';
      }
      // Switch fullscreen icon indicator
      const el = this.fsbutton.nativeElement.firstElementChild;
      screenfull.on('change', () => {
        if (el)
          el.className = screenfull.isFullscreen ? 'fa fa-compress' : 'fa fa-expand';
      });
    }
    this.currentWeek();

    this.setTimeout();
    this.userInactive.subscribe(() => {
      console.log('user has been inactive for 30s');
      this.inactiveMouse = true;
    });
  }

  ngAfterViewInit(): void {
  }

  ngOnDestroy() { }

  logout() {
    this.authenticationService.logout();
    // reset theme to default
    this.themes.setTheme(this.themes.defaultTheme);
    this.router.navigate(['/page/login']);
  }

  setNavSearchVisible(stat: boolean) {
    // console.log(stat);
    this.isNavSearchVisible = stat;
  }

  isCollapsedText() {
    return this.settings.getLayoutSetting('isCollapsedText');
  }

  navigateTo(event, link) {
    if (!this.currentUser.employee.external) {
      this.router.navigate([link]);
    }
  }

  toggleCollapsedSideabar() {
    this.settings.toggleLayoutSetting('isCollapsed');
  }

  currentWeek() {
    this.week = moment(new Date()).isoWeek();
  }


  setTimeout() {
    this.userActivity = setTimeout(() => this.userInactive.next(undefined), 30000);
    this.inactiveMouse = false;
  }

  @HostListener('window:mousemove') refreshUserState() {
    clearTimeout(this.userActivity);
    this.setTimeout();
  }


}
