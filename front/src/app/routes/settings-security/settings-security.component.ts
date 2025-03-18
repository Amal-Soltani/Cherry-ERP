import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';


@Component({
  selector: 'app-security-supply',
  templateUrl: './settings-security.component.html',
  styleUrls: ['./settings-security.component.scss']
})
export class SettingsSecurityComponent implements OnInit {

  currentOnglet: string;

  constructor(private router: Router) { }

  ngOnInit() {
    this.activeUrl(this.router.url);
  }

  navigateTo(url: string, currentOnglet: string) {
    this.router.navigate([url]);
    this.currentOnglet = currentOnglet;
  }

  activeUrl(path: string) {
    if (path.indexOf('employees') >= 0) {
      this.currentOnglet = 'employees';
    }
    if (path.indexOf('users') >= 0) {
      this.currentOnglet = 'users';
    }
    if (path.indexOf('communauty') >= 0) {
      this.currentOnglet = 'communauty';
    }
    if (path.indexOf('external') >= 0) {
      this.currentOnglet = 'external';
    }
  }

}
