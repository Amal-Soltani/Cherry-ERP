import {Component, HostListener, OnInit} from '@angular/core';


@Component({
    selector: 'app-pages',
    templateUrl: './pages.component.html',
    styleUrls: ['./pages.component.scss']
})
export class PagesComponent implements OnInit {

    getScreenHeight: number;
    getScreenWidth: number;

    constructor() { }

    ngOnInit() {
      this.onResize(event);
    }

    @HostListener('window:resize', ['$event'])
    onResize(event) {
      this.getScreenWidth = window.innerWidth;
      this.getScreenHeight = window.innerHeight + 5;
      $('.page-login-image').css('height',  this.getScreenHeight + 'px');
      console.log(this.getScreenHeight);
    }

}
