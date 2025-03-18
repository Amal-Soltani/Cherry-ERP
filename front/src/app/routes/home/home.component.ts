import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subject} from 'rxjs';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import { Router } from '@angular/router';

@Component({
    selector: 'app-home',
    templateUrl: './home.component.html',
    styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit, OnDestroy {

  destroy$: Subject<boolean> = new Subject<boolean>();
  currentUser: any;
  helpModelShowed: any;

  constructor(private modalService: NgbModal, private router: Router) {
  }

  ngOnInit() {
    this.currentUser = JSON.parse(localStorage.getItem('currentUser'));

    if (this.currentUser.employee.external) {
      this.router.navigate(['partners']);
    }

    this.helpModelShowed = JSON.parse(localStorage.getItem('helpModelShowed'));
    if (this.currentUser && this.currentUser.connection === 1 && this.helpModelShowed === 'false' ) {
      this.showHelp();
    }
  }

  ngOnDestroy(): void {
    if (this.destroy$) {
      this.destroy$.next(true);
      this.destroy$.unsubscribe();
    }
  }

  showHelp() {

  }

}
