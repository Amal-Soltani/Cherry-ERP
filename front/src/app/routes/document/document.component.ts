import { Component, OnInit } from '@angular/core';
import { DocumentService } from 'src/app/shared/services/document.service';
import { NgbModal, NgbModalOptions } from '@ng-bootstrap/ng-bootstrap';
import { Subject, takeUntil } from 'rxjs';
import { saveAs } from 'file-saver';
import { ToastrService } from 'ngx-toastr';
import { ActivatedRoute } from '@angular/router';
import { ConfirmDeleteComponent } from 'src/app/shared/component/confirm-delete/confirm-delete.component';
import { AddOrEditFileComponent } from './add-or-edit-file/add-or-edit-file.component';


@Component({
  selector: 'app-document',
  templateUrl: './document.component.html',
  styleUrls: ['./document.component.scss']
})
export class DocumentComponent implements OnInit {
  ngbModalOptions: NgbModalOptions = {
    size: 'lg',
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

  
  ifEmpty: boolean;
  message = $localize`Pas de données à afficher`;

  documentList: any[] = [];
  documents: any[] = [];

  idProjet: any

  numProduct: any

  filenames: string[] = []
  fileStatus = { status: '', requestType: '', percent: 0 }

  constructor(private documentService: DocumentService,
    private modalService: NgbModal,
    private toastr: ToastrService,
    private activateRoute: ActivatedRoute) { }

  ngOnInit(): void {
    this.getPage(0)
  }

  getPage(page: number) {
    this.loading = true;
    this.numProduct = this.activateRoute.snapshot.parent.params.id
    this.idProjet = this.activateRoute.snapshot.parent.params.idProjet
    if (this.numProduct != null) {
      this.documentService.findByPageAndProduct(page, this.pageSize,this.numProduct).pipe(takeUntil(this.destroy$))
      .subscribe((res: any) => {
        if (res.content == 0 ){
          this.ifEmpty = true
        }
        this.loading = false;
        this.documentList = res.content;
        this.documents = res.content;
        this.currentPage = page;
        this.lastPage = res.totalPages - 1;
        this.totalElements = res.totalElements;
        this.ifEmpty = false
      }, err => {
        console.log(err);
      });
    }
  }

  onSearchChange(searchTerm: string) {
    searchTerm = searchTerm.toLowerCase();
    this.documentList = this.documents
    this.documentList = this.documentList.filter(item =>
      (item.reference && item.reference.toLowerCase().includes(searchTerm)) ||
      (item.name && item.name.toLowerCase().includes(searchTerm)) ||
      (item.indice && item.indice.toLowerCase().includes(searchTerm))
    )
  }

  addFile() {
    const modalRef = this.modalService.open(AddOrEditFileComponent, this.ngbModalOptions);
    modalRef.componentInstance.numProduct = this.numProduct;
    modalRef.componentInstance.idProjet = this.idProjet;
    modalRef.result.then(() => {
      this.getPage(0);
    });
  }

  update(document){
    const modalRef = this.modalService.open(AddOrEditFileComponent, this.ngbModalOptions);
    modalRef.componentInstance.doc = document;
    modalRef.componentInstance.numProduct = this.numProduct;
    modalRef.result.then(() => {
      this.getPage(0);
    });
  }

  delete(document) {
    const modalRef = this.modalService.open(ConfirmDeleteComponent);
    modalRef.result.then((res) => {
      if (res === true) {
        this.documentService.delete(document.id).subscribe(
          () => {
            this.getPage(this.currentPage);
            this.toastr.success($localize`Succès`, $localize`Supprimé avec succès`);
          }, err => {
            console.log(err);
            this.toastr.error($localize`Erreur`, $localize`Une erreur s'est produite. Veuillez essayer de nouveau.`);
          })
      }
    });
  }

  download(document) {
    this.documentService.download(document.id).subscribe(
      (res: any) => {
        let blob = new Blob([res.body], { type: res.headers.get('content-type') })
        let fileName = this.getFileNameFromResponse(res);
        saveAs(blob, fileName)
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


  ngOnDestroy(): void {
    this.destroy$.next(true);
    // Unsubscribe from the subject
    this.destroy$.unsubscribe();
  }
}
