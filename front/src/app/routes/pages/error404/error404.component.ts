import { Component, OnInit } from '@angular/core';
import { SettingsService } from '../../../core/settings/settings.service';

@Component({
  selector: 'app-error404',
  templateUrl: './error404.component.html',
  styleUrls: ['../pages.component.scss']
})
export class Error404Component implements OnInit {

  settings: any;

  constructor(settingsService: SettingsService) {
    this.settings = settingsService.getAppSetting(null);
  }

  ngOnInit() {
  }

}
