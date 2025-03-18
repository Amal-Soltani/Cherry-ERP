import { Component, OnInit } from '@angular/core';
import { NgbModal, NgbModalOptions } from '@ng-bootstrap/ng-bootstrap';
import { ToastrService } from 'ngx-toastr';
import { AnomaliesService } from 'src/app/shared/services/anomalies.service';
import { AddOrEditAnomaliesComponent } from './add-or-edit-anomalies/add-or-edit-anomalies.component';
import { ConfirmDeleteComponent } from 'src/app/shared/component/confirm-delete/confirm-delete.component';
import { Subject, takeUntil } from 'rxjs';
import { AuthenticationService } from 'src/app/shared/services/authentication.service';

@Component({
  selector: 'app-anomalies',
  templateUrl: './anomalies.component.html',
  styleUrls: ['./anomalies.component.scss']
})
export class AnomaliesComponent implements OnInit {
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

  anomliesList: any[] = [];
  anomlies: any[] = [];

  constructor(private modalService: NgbModal,
    private anomaliesService: AnomaliesService,
    private toastr: ToastrService,
    public AuthenticationService: AuthenticationService) { }

  ngOnInit(): void {
    this.getPage(0);
  }

  getPage(page: number) {
    this.loading = true;
    this.anomaliesService.findByPage(page, this.pageSize).pipe(takeUntil(this.destroy$))
      .subscribe((res: any) => {
        if (res.content == 0 ){
          this.ifEmpty = true
        }
        this.loading = false;
        this.anomliesList = res.content;
        this.anomlies = this.anomliesList
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
    this.anomliesList = this.anomlies
    this.anomliesList = this.anomliesList.filter(item =>
      (item.categorie && item.categorie.toLowerCase().includes(searchTerm)) ||
      (item.type && item.type.toLowerCase().includes(searchTerm)) ||
      (item.creationDate && item.creationDate.includes(searchTerm)) ||
      (item.modificationDate && item.modificationDate.includes(searchTerm))
    )
  }

  add() {
    const modalRef = this.modalService.open(AddOrEditAnomaliesComponent, this.ngbModalOptions);
    modalRef.result.then(() => {
      this.getPage(0);
    });
  }

  update(anomalie: any) {
    const modalRef = this.modalService.open(AddOrEditAnomaliesComponent, this.ngbModalOptions);
    modalRef.componentInstance.anomalie = anomalie;
    modalRef.result.then(() => {
      this.getPage(this.currentPage);
    });
  }
  delete(anomalie) {
    const modalRef = this.modalService.open(ConfirmDeleteComponent);
    modalRef.result.then((res:any) => {
      if (res === true) {
        this.anomaliesService.delete(anomalie.id).subscribe(() => {
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
