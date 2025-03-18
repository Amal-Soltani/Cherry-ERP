import { Component, OnInit } from '@angular/core';
import { NgbModal, NgbModalOptions } from '@ng-bootstrap/ng-bootstrap';
import { ToastrService } from 'ngx-toastr';
import { Subject, takeUntil } from 'rxjs';
import { FNCService } from 'src/app/shared/services/fnc.service';
import { AddOrEditFNCComponent } from './add-or-edit-fnc/add-or-edit-fnc.component';
import { ConfirmDeleteComponent } from 'src/app/shared/component/confirm-delete/confirm-delete.component';
import { AuthenticationService } from 'src/app/shared/services/authentication.service';

@Component({
  selector: 'app-fnc',
  templateUrl: './fnc.component.html',
  styleUrls: ['./fnc.component.scss']
})
export class FNCComponent implements OnInit {

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


  ifEmpty: boolean;
  message = $localize`Pas de données à afficher`;

  FNCList: any[] = [];
  FNC: any[] = [];

  constructor(private modalService: NgbModal,
    private FNCService: FNCService,
    private toastr: ToastrService,
    public AuthenticationService: AuthenticationService) { }

  ngOnInit(): void {
    this.getPage(0);
  }

  getPage(page: number) {
    this.loading = true;
    this.FNCService.findByPage(page, this.pageSize).pipe(takeUntil(this.destroy$))
      .subscribe((res: any) => {
        if (res.content == 0 ){
          this.ifEmpty = true
        }
        this.loading = false;
        this.FNCList = res.content;
        this.FNC = this.FNCList
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
    this.FNCList = this.FNC
    this.FNCList = this.FNCList.filter(item =>
      (item.reference && item.reference.toLowerCase().includes(searchTerm)) ||
      (item.tache.reference && item.tache.reference.toLowerCase().includes(searchTerm)) ||
      (item.tache.titre && item.tache.titre.toLowerCase().includes(searchTerm)) ||
      (item.equipment && item.equipment.toLowerCase().includes(searchTerm)) ||
      (item.employee.firstname && item.employee.firstname.toLowerCase().includes(searchTerm)) ||
      (item.employee.lastName && item.employee.lastName.toLowerCase().includes(searchTerm)) ||
      (item.tache.projet.reference && item.tache.projet.reference.toLowerCase().includes(searchTerm)) ||
      (item.tache.projet.titre && item.tache.projet.titre.toLowerCase().includes(searchTerm)) ||
      (item.modificationDate && item.modificationDate.includes(searchTerm))
    )
    console.log(this.FNCList)

  }

  add() {
    const modalRef = this.modalService.open(AddOrEditFNCComponent, this.ngbModalOptions);
    modalRef.result.then(() => {
      this.getPage(0);
    });
  }

  update(fiche: any) {
    const modalRef = this.modalService.open(AddOrEditFNCComponent, this.ngbModalOptions);
    modalRef.componentInstance.fiche = fiche;
    modalRef.result.then(() => {
      this.getPage(this.currentPage);
    });
  }
  delete(fiche) {
    const modalRef = this.modalService.open(ConfirmDeleteComponent);
    modalRef.result.then((res: any) => {
      if (res === true) {
        this.FNCService.delete(fiche.id).subscribe(() => {
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
