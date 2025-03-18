import { AuthenticationService } from 'src/app/shared/services/authentication.service';
import {Component, HostBinding, OnInit} from '@angular/core';

import { SettingsService } from './core/settings/settings.service';
import { MessagingFirebaseService } from './shared/services/messaging-firebase.service';
import { ResponseRestMessageService } from './shared/services/response-rest-message.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {

  @HostBinding('class.layout-fixed') get isFixed() { return this.settings.getLayoutSetting('isFixed'); };
  @HostBinding('class.aside-collapsed') get isCollapsed() { return this.settings.getLayoutSetting('isCollapsed'); };
  @HostBinding('class.layout-boxed') get isBoxed() { return this.settings.getLayoutSetting('isBoxed'); };
  @HostBinding('class.layout-fs') get useFullLayout() { return this.settings.getLayoutSetting('useFullLayout'); };
  @HostBinding('class.hidden-footer') get hiddenFooter() { return this.settings.getLayoutSetting('hiddenFooter'); };
  @HostBinding('class.layout-h') get horizontal() { return this.settings.getLayoutSetting('horizontal'); };
  @HostBinding('class.aside-float') get isFloat() { return this.settings.getLayoutSetting('isFloat'); };
  @HostBinding('class.offsidebar-open') get offsidebarOpen() { return this.settings.getLayoutSetting('offsidebarOpen'); };
  @HostBinding('class.aside-toggled') get asideToggled() { return this.settings.getLayoutSetting('asideToggled'); };
  @HostBinding('class.aside-collapsed-text') get isCollapsedText() { return this.settings.getLayoutSetting('isCollapsedText'); };

  isAthenticated: any;

  constructor(private messagingService: MessagingFirebaseService,
              public settings: SettingsService,
              private authenticationService: AuthenticationService,
              private responseRestMessageService: ResponseRestMessageService) { }

  ngOnInit() {
    this.isAthenticated = this.authenticationService.isAthenticated();

    // prevent empty links to reload the page
    document.addEventListener('click', e => {
      const target = e.target as HTMLElement;
      if (target.tagName === 'A' && ['', '#'].indexOf(target.getAttribute('href')) > -1) {
        e.preventDefault();
      }
    });
    // FCM PUSH NOTIFICATION
    this.messagingService.requestPermission();
    this.messagingService.receiveMessage();
    this.initData();

    this.listen();
  }

  listen() {

  }

  initData() {
    // get rest messages
    this.responseRestMessageService.findAll().subscribe((res: any) => {
      sessionStorage.setItem('restMessages', JSON.stringify(res.result));
    }, err => {
      console.log(err);
    });


  }

}
