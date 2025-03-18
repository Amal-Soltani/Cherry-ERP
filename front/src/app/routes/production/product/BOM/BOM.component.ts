import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgbModal, NgbModalOptions } from '@ng-bootstrap/ng-bootstrap';
import { ToastrService } from 'ngx-toastr';
import { Subject, takeUntil } from 'rxjs';
import { ProductService } from 'src/app/shared/services/product.service';
import { ConfirmDeleteComponent } from 'src/app/shared/component/confirm-delete/confirm-delete.component';
import { AddOrEditBOMComponent } from './add-or-edit-BOM/add-or-edit-BOM.component';
import { QuantityProductService } from 'src/app/shared/services/quantity-product.service';
import { AuthenticationService } from 'src/app/shared/services/authentication.service';
import { RawMaterialService } from 'src/app/shared/services/raw-material.service';
import { BOMService } from 'src/app/shared/services/b-o-m.service';
import { DocumentService } from 'src/app/shared/services/document.service';
import { saveAs } from 'file-saver';



@Component({
  selector: 'app-BOM',
  templateUrl: './BOM.component.html',
  styleUrls: ['./BOM.component.scss']
})
export class BOMComponent implements OnInit {

  ngbModalOptions: NgbModalOptions = {
    size: 'xl',
    backdrop: 'static',
    keyboard: false
  };


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

  nomenclatureList: any[] = [];
  nomenclatures: any[] = [];
  ProductList: any[] = [];
  articleList: any[] = [];

  documents : any[]=[]
  nomenclature: any;


  idProjet: any

  numProduct: any

  info: boolean

  constructor(private nomenclatureService: BOMService,
    private documentService: DocumentService,
    private productService: ProductService,
    private quantityProductService: QuantityProductService,
    private matierePremiereService: RawMaterialService,
    private modalService: NgbModal,
    private toastr: ToastrService,
    private activateRoute: ActivatedRoute,
    public AuthenticationService: AuthenticationService) { }

  ngOnInit(): void {
    this.getPage(0);
  }

  onSearchChange(searchTerm: string) {
    searchTerm = searchTerm.toLowerCase();
    this.nomenclatureList = this.nomenclatures
    this.nomenclatureList = this.nomenclatureList.filter(item =>
      (item.reference && item.reference.toLowerCase().includes(searchTerm)) ||
      (item.name && item.name.toLowerCase().includes(searchTerm.toLowerCase())) ||
      (item.matiere && item.matiere.toLowerCase().includes(searchTerm.toLowerCase())) ||
      (item.BOM.reference && item.BOM.reference.toLowerCase().includes(searchTerm.toLowerCase())));
  }

  getPage(page: number) {
    this.numProduct = this.activateRoute.snapshot.parent.params.id
    this.idProjet = this.activateRoute.snapshot.parent.params.idProjet
    this.nomenclatureList = []
    this.nomenclatures = []
    if (this.numProduct != null) {
      this.productService.getNomenclature(this.numProduct).subscribe(
        (res: any) => {
          if (res.result != null) {
            this.nomenclatureList.length = 0
            this.nomenclatureList.push(res.result)
            this.nomenclatures = this.nomenclatureList
            this.ifEmpty = false

            for (let n of this.nomenclatureList) {
              n.info = false
              n.detailBtn = false
              n.sousProduitBtn = false
            }
          } else {
            this.nomenclatureList.length = 0
            this.nomenclatures.length = 0
            this.ifEmpty = true

          }
        }
      )
    } else if (this.idProjet != null) {
      this.productService.findByPageAndProject(page, this.pageSize, this.idProjet).pipe(takeUntil(this.destroy$))
        .subscribe((res: any) => {
          if (res.content == 0 ){
            this.ifEmpty = true
          }
          this.loading = false;
          this.nomenclatureList = res.content;
          this.nomenclatures = this.nomenclatureList
          this.currentPage = page;
          this.lastPage = res.totalPages - 1;
          this.totalElements = res.totalElements;
          this.ifEmpty = false

        }, err => {
          console.log(err);
        });
    }
  }

  add() {
    const modalRef = this.modalService.open(AddOrEditBOMComponent, this.ngbModalOptions);
    modalRef.componentInstance.numProduct = this.numProduct;
    modalRef.componentInstance.idProjet = this.idProjet;
    modalRef.result.then(() => {
      this.getPage(0);
    });
  }

  update(nomenclature: any, product: any) {
    const modalRef = this.modalService.open(AddOrEditBOMComponent, this.ngbModalOptions);
    modalRef.componentInstance.nomenclature = nomenclature;
    modalRef.componentInstance.numProduct = product;
    modalRef.componentInstance.idProjet = this.idProjet;
    modalRef.result.then(() => {
      this.getPage(this.currentPage);
    });
  }

  delete(nomenclature: any, numProduct: any) {
    const modalRef = this.modalService.open(ConfirmDeleteComponent);
    modalRef.result.then((res) => {
      if (res === true) {
        this.nomenclatureService.deleteNomenclature(nomenclature.id).subscribe(
          () => {
            this.quantityProductService.deleteByParent(numProduct).subscribe()
            this.matierePremiereService.deleteByProduct(numProduct).subscribe()
            this.getPage(this.currentPage);
            this.toastr.success($localize`Succès`, $localize`Supprimé avec succès`);
          }, err => {
            console.log(err);
            this.toastr.error($localize`Erreur`, $localize`Une erreur s'est produite. Veuillez essayer de nouveau.`);
          })
      }
    });
  }

  download(indice : any){
    console.log(indice)
    this.documentService.getOne(indice).subscribe((res:any)=>{
      this.documentService.download(res.result.id).subscribe(
        (res: any) => {
          let blob = new Blob([res.body], { type: res.headers.get('content-type') })
          let fileName = this.getFileNameFromResponse(res);
          saveAs(blob, fileName)
        })
    })
  }

  getFileNameFromResponse(response: any): string {
    let contentDisposition = response.headers.get('content-disposition');
    let matches = /filename="(.+)"/.exec(contentDisposition);
    if (matches && matches.length > 1) {
      return matches[1];
    }
    return 'downloaded_file';
  }

  detail(nmcl: any,) {
    if (nmcl.info == false) {
      nmcl.info = true
      this.quantityProductService.getAll(nmcl.id).subscribe((res: any) => {
        this.ProductList = res.result
      })
      this.matierePremiereService.getAll(nmcl.id).subscribe((res: any) => {
        this.articleList = res.result
      })
    } else {
      nmcl.info = false
    }
  }

  ngOnDestroy(): void {
    this.destroy$.next(true);
    // Unsubscribe from the subject
    this.destroy$.unsubscribe();
  }

}

