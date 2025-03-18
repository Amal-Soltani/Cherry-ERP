import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ProductService } from 'src/app/shared/services/product.service';
import { AddOrEditProductComponent } from '../add-or-edit-product/add-or-edit-product.component';
import { NgbModal, NgbModalOptions } from '@ng-bootstrap/ng-bootstrap';
import { AuthenticationService } from 'src/app/shared/services/authentication.service';

@Component({
  selector: 'app-show-product',
  templateUrl: './show-product.component.html',
  styleUrls: ['./show-product.component.scss']
})
export class ShowProductComponent implements OnInit {

  ngbModalOptions: NgbModalOptions = {
    size: 'xl',
    backdrop: 'static',
    keyboard: false
  };

  product : any
  info : boolean;
  activeTab: string = 'Nomenclature';



  constructor(private productService: ProductService,
    private activateRoute : ActivatedRoute,
    private modalService: NgbModal,
    public AuthenticationService: AuthenticationService) { }

  ngOnInit(): void {
    this.getProduct();
  }

  getProduct(){
    this.productService.getById(this.activateRoute.snapshot.params.id).subscribe((res : any)=>{
      this.product = res.result
    })
  }

  setActiveTab(tab: string): void {
    this.activeTab = tab;
  }

  
  Open(){
    if(this.info == false){
      this.info = true
    }else{
      this.info = false
    }
  }

  update() {
    const modalRef = this.modalService.open(AddOrEditProductComponent, this.ngbModalOptions);
    modalRef.componentInstance.product = this.product;
    modalRef.result.then(() => {
      this.info = false
      this.getProduct();
    });
  }

}
