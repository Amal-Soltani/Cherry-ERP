import {Component, EventEmitter, Input, OnChanges, OnDestroy, OnInit, Output, SimpleChanges} from '@angular/core';
import {findIndex} from 'lodash';
import { ProductService } from '../../services/product.service';

@Component({
  selector: 'app-search-product',
  templateUrl: './search-product.component.html',
  styleUrls: ['./search-product.component.scss']
})
export class SearchProductComponent implements OnInit {

  @Input() loadListOnInit = false;
  @Input() multiple = false;
  @Input() disabled = false;
  @Input() placeholder: string;
  @Input() product: any;
  @Input() selectedIds: number[];

  @Output() onSelectProduct = new EventEmitter<any>();
  @Output() onFinishLoadList = new EventEmitter<any>();

  products: any[] = [];
  loading = false;

  constructor(private productService: ProductService) { }

  ngOnInit() {
    if (this.loadListOnInit) {
      this.loadListProducts();
    }
  }


  onChange(event) {
    this.onSelectProduct.emit(event);
    this.product = event;
  }

  loadListProducts() {
    if (!this.products || this.products.length === 0) {
      this.loading = true;
      this.productService.getAll()
        .subscribe(res => {
          this.loading = false;
          this.products = res.result;
          this.selectDefaultValue();
          this.onFinishLoadList.emit(this.products);
        }, err => {
          this.loading = false;
          console.log(err);
        });
    }
  }

  selectDefaultValue() {
    if (this.selectedIds) {
      const i = findIndex(this.products, (u) => { return  this.selectedIds.includes(u.id); });
      this.products = this.products[i];
    }
  }

  customSearchFn(term: string, item: any) {
    term = term.toLowerCase();
    return (item.reference && item.reference.toLowerCase().indexOf(term) > -1 ) ||
      (item.name && item.name.toLowerCase().indexOf(term) > -1 );
  }


  ngOnChanges(changes: SimpleChanges) {
    if (changes['selectedIds']) {
      this.selectDefaultValue();
    }
  }

}
