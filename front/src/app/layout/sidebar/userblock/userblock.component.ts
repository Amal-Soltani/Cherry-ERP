import { Component, OnInit } from '@angular/core';

import { UserblockService } from './userblock.service';

@Component({
    selector: 'app-userblock',
    templateUrl: './userblock.component.html',
    styleUrls: ['./userblock.component.scss']
})
export class UserblockComponent implements OnInit {

    status = 'ONLINE';
    isConnected = true;
    currentCompany: any;

    constructor(public userblockService: UserblockService) {
      this.currentCompany = JSON.parse(localStorage.getItem('company'));
      window.addEventListener('online', () => {this.isConnected = false; this.status = 'YONLINE';});
      window.addEventListener('offline', () => {this.isConnected = true; this.status = 'YOUR ARE OFFLINE';});
    }

    ngOnInit() {
    }

    userBlockIsVisible() {
        return this.userblockService.getVisibility();
    }

}
