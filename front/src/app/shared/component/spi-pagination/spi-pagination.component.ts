import {Component, EventEmitter, Input, OnChanges, OnDestroy, OnInit, Output} from '@angular/core';

@Component({
  selector: 'app-spi-pagination',
  templateUrl: './spi-pagination.component.html',
  styleUrls: ['./spi-pagination.component.scss']
})
export class SpiPaginationComponent implements OnInit {

  @Input() currentPage: number;
  @Input() lastPage: number;
  @Input()  pageSize: number;
  @Input() totalElements: number;

  @Input() listPages: number[];

  @Output() onGetPage = new EventEmitter<any>();

  constructor() { }

  ngOnInit() {
    if (!this.listPages || (this.listPages && this.listPages.length === 0)) {
      this.listPages = [5, 10, 20, 50, 100];
    }
  }

  getPage(nb: number) {
    this.onGetPage.emit({pageSize : this.pageSize, currentPage: nb });
  }

  onChangePageSize(pageSize) {
    this.pageSize = pageSize;
    this.getPage(0);
  }
}
