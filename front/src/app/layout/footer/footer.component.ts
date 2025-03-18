import { Component, OnInit } from '@angular/core';
import { SettingsService } from '../../core/settings/settings.service';
import {RouteConfigLoadEnd, RouteConfigLoadStart, Router} from '@angular/router';
import {BlockUI, NgBlockUI} from 'ng-block-ui';
import {AppService} from "../../shared/services/app.service";

declare const window: any;

@Component({
    selector: '[app-footer]',
    templateUrl: './footer.component.html',
    styleUrls: ['./footer.component.scss']
})
export class FooterComponent implements OnInit {

    // Decorator wires up blockUI instance
    @BlockUI() blockUI: NgBlockUI;

    currentVersion = '-';
    currentEnv;

    status = 'ONLINE';
    isConnected = true;
    blocked: boolean;
    currentCompany: any;

    constructor(private router: Router, public settings: SettingsService, public appService: AppService) {
      this.currentCompany = JSON.parse(localStorage.getItem('company'));
      this.router.events.subscribe(event => {
        if (event instanceof RouteConfigLoadStart) {
          this.blocked = true;
          //this.blockUI.start('Loading...FOOTER'); // Start blocking
        } else if (event instanceof RouteConfigLoadEnd) {
          this.blocked = false;
          //this.blockUI.stop(); // Stop blocking
        }
      }, error => {
        this.blockUI.stop();
      });
      window.addEventListener('online', () => {this.isConnected = false; this.status = 'YOUR ARE ONLINE';});
      window.addEventListener('offline', () => {this.isConnected = true; this.status = 'YOUR ARE OFFLINE';});
    }

    ngOnInit() {
      this.appService.version()
        .subscribe(res => {
          this.currentVersion = res.version;
          this.currentEnv = res.environment;
        }, err => {
          console.log(err);
        });
    }

    get isOnline() {
      return !!window.navigator.onLine;
    }
}
