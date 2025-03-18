import { Component, OnInit } from '@angular/core';
import { AddOrEditProductionLineComponent } from './add-or-edit-production-line/add-or-edit-production-line.component';
import { ConfirmDeleteComponent } from 'src/app/shared/component/confirm-delete/confirm-delete.component';
import { NgbModal, NgbModalOptions } from '@ng-bootstrap/ng-bootstrap';
import { ToastrService } from 'ngx-toastr';
import { Subject, takeUntil } from 'rxjs';
import { AuthenticationService } from 'src/app/shared/services/authentication.service';
import { ProductionLigneService } from 'src/app/shared/services/production-ligne.service';

@Component({
  selector: 'app-production-line',
  templateUrl: './production-line.component.html',
  styleUrls: ['./production-line.component.scss']
})
export class ProductionLineComponent implements OnInit {

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
  
  ifEmpty : boolean;
  message = $localize `Pas de données à afficher`;

  ligneProductionList: any[] = [];
  ligneProductions: any[] = [];

  constructor(private modalService: NgbModal,
    private ligneProductionService: ProductionLigneService,
    private toastr: ToastrService,
    public AuthenticationService: AuthenticationService) { }

  ngOnInit(): void {
    this.getPage(0);
  }

  getPage(page: number) {
    this.loading = true;
    this.ligneProductionService.findByPage(page, this.pageSize).pipe(takeUntil(this.destroy$))
      .subscribe((res: any) => {
        if (res.content == 0 ){
          this.ifEmpty = true
        }
        this.loading = false;
        this.ligneProductionList = res.content;
        this.ligneProductions = this.ligneProductionList
        this.currentPage = page;
        this.lastPage = res.totalPages - 1;
        this.totalElements = res.totalElements;
        this.ifEmpty = false

      }, err => {
        console.log(err);
      });
  }

  onSearchChange(searchTerm: string) {
    searchTerm = searchTerm.toLowerCase();
    this.ligneProductionList = this.ligneProductions
    this.ligneProductionList = this.ligneProductionList.filter(item =>
      (item.reference && item.reference.toLowerCase().includes(searchTerm)) ||
      (item.name && item.name.toLowerCase().includes(searchTerm)) ||
      (item.employee.firstName && item.employee.firstName.toLowerCase().includes(searchTerm)) ||
      (item.libelle && item.libelle.toLowerCase().includes(searchTerm)) ||
      (item.creationDate && item.creationDate.includes(searchTerm) )||
      (item.modificationDate && item.modificationDate.includes(searchTerm))
    )
  }

  add() {
    const modalRef = this.modalService.open(AddOrEditProductionLineComponent, this.ngbModalOptions);
    modalRef.result.then(() => {
      this.getPage(0);
    });
  }

  update(ligneProduction: any) {
    const modalRef = this.modalService.open(AddOrEditProductionLineComponent, this.ngbModalOptions);
    modalRef.componentInstance.ligneProduction = ligneProduction;
    modalRef.result.then(() => {
      this.getPage(this.currentPage);
    });
  }
  delete(ligneProduction) {
    const modalRef = this.modalService.open(ConfirmDeleteComponent);
    modalRef.result.then((res) => {
      if (res === true) {
        this.ligneProductionService.delete(ligneProduction.id).subscribe(() => {
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

}
