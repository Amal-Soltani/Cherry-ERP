import { Component, OnInit } from '@angular/core';
import { AddOrEditPhaseComponent } from './add-or-edit-phase/add-or-edit-phase.component';
import { ConfirmDeleteComponent } from 'src/app/shared/component/confirm-delete/confirm-delete.component';
import { NgbModal, NgbModalOptions } from '@ng-bootstrap/ng-bootstrap';
import { ToastrService } from 'ngx-toastr';
import { Subject, takeUntil } from 'rxjs';
import { PhaseService } from 'src/app/shared/services/phase.service';
import { AuthenticationService } from 'src/app/shared/services/authentication.service';

@Component({
  selector: 'app-phase',
  templateUrl: './phase.component.html',
  styleUrls: ['./phase.component.scss']
})
export class PhaseComponent implements OnInit {
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

  phaseList: any[] = [];
  phases: any[] = [];

  constructor(private modalService: NgbModal,
    private phaseService: PhaseService,
    private toastr: ToastrService,
    public AuthenticationService: AuthenticationService) { }

  ngOnInit(): void {
    this.getPage(0);
  }

  getPage(page: number) {
    this.loading = true;
    this.phaseService.findByPage(page, this.pageSize).pipe(takeUntil(this.destroy$))
      .subscribe((res: any) => {
        if (res.content == 0 ){
          this.ifEmpty = true
        }
        this.loading = false;
        this.phaseList = res.content;
        this.phases = this.phaseList
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
    this.phaseList = this.phases
    this.phaseList = this.phaseList.filter(item =>
      (item.reference && item.reference.toLowerCase().includes(searchTerm)) ||
      (item.name && item.name.toLowerCase().includes(searchTerm)) ||
      (item.matiere && item.matiere.toLowerCase().includes(searchTerm)) ||
      (item.libelle && item.libelle.toLowerCase().includes(searchTerm)) ||
      (item.creationDate && item.creationDate.includes(searchTerm)) ||
      (item.modificationDate && item.modificationDate.includes(searchTerm))
    )
  }

  add() {
    const modalRef = this.modalService.open(AddOrEditPhaseComponent, this.ngbModalOptions);
    modalRef.result.then(() => {
      this.getPage(0);
    });
  }

  update(phase: any) {
    const modalRef = this.modalService.open(AddOrEditPhaseComponent, this.ngbModalOptions);
    modalRef.componentInstance.phase = phase;
    modalRef.result.then(() => {
      this.getPage(this.currentPage);
    });
  }
  delete(phase) {
    const modalRef = this.modalService.open(ConfirmDeleteComponent);
    modalRef.result.then((res:any) => {
      if (res === true) {
        this.phaseService.delete(phase.id).subscribe(() => {
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
