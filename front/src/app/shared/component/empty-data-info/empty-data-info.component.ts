import {Component, Input, OnDestroy, OnInit} from '@angular/core';

@Component({
  selector: 'app-empty-data-info',
  templateUrl: './empty-data-info.component.html',
  styleUrls: ['./empty-data-info.component.scss']
})
export class EmptyDataInfoComponent implements OnInit, OnDestroy {

  @Input() ifEmpty = false;
  @Input() message = $localize `Pas de données à afficher`;

  constructor() { }

  ngOnInit() {
  }

  ngOnDestroy() { }

}
