import { Component, OnInit } from '@angular/core';
import { NgbModal, NgbModalOptions } from '@ng-bootstrap/ng-bootstrap';
import { Subject, takeUntil } from 'rxjs';
import { AddOrEditProductComponent } from './add-or-edit-product/add-or-edit-product.component';
import { ConfirmDeleteComponent } from 'src/app/shared/component/confirm-delete/confirm-delete.component';
import { ToastrService } from 'ngx-toastr';
import { ProductService } from 'src/app/shared/services/product.service';
import { AuthenticationService } from 'src/app/shared/services/authentication.service';

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.scss']
})
export class ListComponent implements OnInit {

  ngbModalOptions: NgbModalOptions = {
    size: 'xl',
    backdrop: 'static',
    keyboard: false
  };
  activeTab: string = 'tous';
  products: any[] = [];


  productList: any[] = [];
  produits: any[] = [];

  destroy$: Subject<boolean> = new Subject<boolean>();
  loading: boolean;
  totalElements: number;
  pageSize = 10;
  currentPage: number;
  lastPage: number;
  type: string;
  filter: string;

  ifEmpty : boolean;
  message = $localize `Pas de données à afficher`;


  constructor(private modalService: NgbModal,
    private productService: ProductService,
    private toastr: ToastrService,
    public AuthenticationService: AuthenticationService) { }

  ngOnInit(): void {
    this.getPage(0)
  }


  onSearchChange(searchTerm: string) {
    searchTerm = searchTerm.toLowerCase();
    this.productList = this.produits
    this.productList = this.productList.filter(item =>
      (item.reference && item.reference.toLowerCase().includes(searchTerm)) ||
      (item.name && item.name.toLowerCase().includes(searchTerm)) ||
      (item.matiere && item.matiere.toLowerCase().includes(searchTerm)) ||
      (item.libelle && item.libelle.toLowerCase().includes(searchTerm)) ||
      (item.creationDate && item.creationDate.includes(searchTerm)) ||
      (item.modificationDate && item.modificationDate.includes(searchTerm))
    )
  }

  getPage(page: number) {
    this.loading = true;
    this.productList = []
    this.produits = []
    if (this.activeTab === "tous") {
      this.productService.findByPage(page, this.pageSize).pipe(takeUntil(this.destroy$))
      .subscribe((res: any) => {
        if (res.content == 0 ){
          this.ifEmpty = true
        }
        this.loading = false;
        this.productList = res.content;
        this.produits = this.productList
        this.currentPage = page;
        this.lastPage = res.totalPages - 1;
        this.totalElements = res.totalElements;
        this.ifEmpty = false
      }, err => {
        console.log(err);
      });
    }else{
      this.productService.findByPageAndType(page, this.pageSize, this.activeTab).pipe(takeUntil(this.destroy$))
        .subscribe((res: any) => {
          if (res.content == 0 ){
            this.ifEmpty = true
          }
          this.loading = false;
          this.productList = res.content;
          this.produits = this.productList
          this.currentPage = page;
          this.lastPage = res.totalPages - 1;
          this.totalElements = res.totalElements;
          this.ifEmpty = false

        }, err => {
          console.log(err);
        });
    }
  }

  setActiveTab(tab: string): void {
    this.activeTab = tab;
    this.getPage(0);
  }

  add() {
    const modalRef = this.modalService.open(AddOrEditProductComponent, this.ngbModalOptions);
    modalRef.result.then(() => {
      this.getPage(this.currentPage);
    });
  }

  update(product: any) {
    const modalRef = this.modalService.open(AddOrEditProductComponent, this.ngbModalOptions);
    modalRef.componentInstance.product = product;
    modalRef.result.then(() => {
      this.getPage(0);
    });
  }
  delete(product) {
    const modalRef = this.modalService.open(ConfirmDeleteComponent);
    modalRef.result.then((res) => {
      if (res === true) {
        this.productService.delete(product.id).subscribe(() => {
          this.toastr.success($localize`Succès`, $localize`Supprimé avec succès`);
          this.getPage(this.currentPage);
        }, err => {
          console.log(err);
          this.toastr.error($localize`Erreur`, $localize`Une erreur s'est produite. Veuillez essayer de nouveau.`);
        }
        )
      }
    });
  }

  ngOnDestroy(): void {
    this.destroy$.next(true);
    // Unsubscribe from the subject
    this.destroy$.unsubscribe();
  }

}
