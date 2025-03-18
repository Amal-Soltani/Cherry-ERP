import { Component, OnInit } from '@angular/core';
import { NgbModal, NgbModalOptions } from '@ng-bootstrap/ng-bootstrap';
import { ToastrService } from 'ngx-toastr';
import { ActivatedRoute } from '@angular/router';
import { Subject, takeUntil } from 'rxjs';
import { AddOrEditTaskComponent } from './add-or-edit-task/add-or-edit-task.component';
import { TacheService } from 'src/app/shared/services/tache.service';
import { ConfirmDeleteComponent } from 'src/app/shared/component/confirm-delete/confirm-delete.component';
import { GammeComponent } from '../gamme/gamme.component';
import { ProjetService } from 'src/app/shared/services/projet.service';
import { AuthenticationService } from 'src/app/shared/services/authentication.service';


@Component({
  selector: 'app-task',
  templateUrl: './task.component.html',
  styleUrls: ['./task.component.scss']
})
export class TaskComponent implements OnInit {

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

  designation: any;
  tacheList: any[] = [];
  taches: any[] = [];
  ProductList: any[] = [];
  tache: any;
  production: any;

  idProjet: any
  referenceNomenclature: any
  employee: any

  dateR: any
  datePr: any

  info = false
  sousProduct = false

  status = "tous"
  priority: any

  constructor(private tacheService: TacheService,
    private modalService: NgbModal,
    private toastr: ToastrService,
    private activateRoute: ActivatedRoute,
    private projetService: ProjetService,
    public AuthenticationService: AuthenticationService) {

  }

  ngOnInit(): void {
    this.getPage(0);
  }

  get(category: any) {
    this.status = category
    this.getPage(this.currentPage)
  }

  getPage(page: number) {
    this.idProjet = this.activateRoute.parent.snapshot.params.idProjet
    this.loading = true;
    this.tacheList = []
    this.taches = []
    if (this.idProjet == null) {
      if (this.status === "tous") {
        this.tacheService.findByPage(page, this.pageSize).pipe(takeUntil(this.destroy$))
          .subscribe((res: any) => {
            if (res.content == 0 ){
              this.ifEmpty = true
            }
            this.loading = false;
            this.tacheList = res.content;
            this.taches = this.tacheList
            this.currentPage = page;
            this.lastPage = res.totalPages - 1;
            this.totalElements = res.totalElements;
            this.ifEmpty = false

          }, err => {
            console.log(err);
          });
      } else {
        this.tacheService.findByPageAndStatus(page, this.pageSize, this.status).pipe(takeUntil(this.destroy$))
          .subscribe((res: any) => {
            if (res.content == 0 ){
              this.ifEmpty = true
            }
            this.loading = false;
            this.tacheList = res.content;
            this.taches = this.tacheList
            this.currentPage = page;
            this.lastPage = res.totalPages - 1;
            this.totalElements = res.totalElements;
            this.ifEmpty = false

          }, err => {
            console.log(err);
          });
      }
    } else {
      this.projetService.getById(this.idProjet).subscribe((res: any) => {
        this.production = res.result.production})
      if (this.status === "tous") {
        this.tacheService.findByPageAndProject(page, this.pageSize,this.idProjet).pipe(takeUntil(this.destroy$))
          .subscribe((res: any) => {
            if (res.content == 0 ){
              this.ifEmpty = true
            }
            this.loading = false;
            this.tacheList = res.content;
            this.taches = this.tacheList
            this.currentPage = page;
            this.lastPage = res.totalPages - 1;
            this.totalElements = res.totalElements;
            this.ifEmpty = false

          }, err => {
            console.log(err);
          });
      } else {
        this.tacheService.findByPageAndProjectAndStatus(page, this.pageSize, this.idProjet,this.status).pipe(takeUntil(this.destroy$))
          .subscribe((res: any) => {
            if (res.content == 0 ){
              this.ifEmpty = true
            }
            this.loading = false;
            this.tacheList = res.content;
            this.taches = this.tacheList
            this.currentPage = page;
            this.lastPage = res.totalPages - 1;
            this.totalElements = res.totalElements;
            this.ifEmpty = false

          }, err => {
            console.log(err);
          });
      }
    }
  }

  onSearchChange(searchTerm: string) {
    searchTerm = searchTerm.toLowerCase();
    this.tacheList = this.taches
    this.tacheList = this.tacheList.filter(item =>
      (item.reference && item.reference.toLowerCase().includes(searchTerm)) ||
      (item.titre && item.titre.toLowerCase().includes(searchTerm.toLowerCase())) ||
      (item.ofexterne && item.ofexterne.toLowerCase().includes(searchTerm.toLowerCase())) ||
      (item.bonCmd && item.bonCmd.toLowerCase().includes(searchTerm.toLowerCase())) ||
      (item.projet.reference && item.projet.reference.toLowerCase().includes(searchTerm.toLowerCase())) ||
      (item.projet.titre && item.projet.titre.toLowerCase().includes(searchTerm.toLowerCase())) ||
      (item.product.reference && item.product.reference.toLowerCase().includes(searchTerm.toLowerCase())) ||
      (item.product.libelle && item.product.libelle.toLowerCase().includes(searchTerm.toLowerCase())) ||
      (item.employee.employeeNumber && item.employee.employeeNumber.toLowerCase().includes(searchTerm.toLowerCase())) ||
      (item.employee.firstName && item.employee.firstName.toLowerCase().includes(searchTerm.toLowerCase())) ||
      (item.employee.lastName && item.employee.lastName.toLowerCase().includes(searchTerm.toLowerCase())) ||
      (item.creationDate && item.creationDate.includes(searchTerm))
    )
  }

  calculateTimeE(tache : any):number{
    return (new Date(tache.dateFinPrev).getTime() - new Date(tache.dateDebutPrev).getTime())/ (1000 * 3600)
  }

  calculateTimeR(tache : any):number{
    return (new Date(tache.dateFinReel).getTime() - new Date(tache.dateDebutReel).getTime())/ (1000 * 3600)
  }


  add() {
    const modalRef = this.modalService.open(AddOrEditTaskComponent, this.ngbModalOptions);
    modalRef.componentInstance.idProjet = this.idProjet;
    modalRef.result.then(() => {
      this.getPage(0);
    });
  }

  Gamme(tache) {
    const modalRef = this.modalService.open(GammeComponent, this.ngbModalOptions);
    modalRef.componentInstance.product = tache.product;
    modalRef.componentInstance.tacheParent = tache;
    modalRef.componentInstance.idProjet = this.idProjet;
    modalRef.result.then(() => {
      this.getPage(this.currentPage);
    });
  }

  delete(tache) {
    const modalRef = this.modalService.open(ConfirmDeleteComponent);
    modalRef.result.then((res) => {
      if (res === true) {
        this.tacheService.deleteTache(tache.id).subscribe(
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


  update(tache) {
    const modalRef = this.modalService.open(AddOrEditTaskComponent, this.ngbModalOptions);
    modalRef.componentInstance.tache = tache;
    modalRef.componentInstance.idProjet = this.idProjet;
    modalRef.result.then(() => {
      this.getPage(0);
    });
  }

  detail(tache: any) {
    if (this.info == false) {
      this.info = true
      this.tache = tache
    } else {
      this.info = false
    }
  }

  addSousTache(tache) {
    const modalRef = this.modalService.open(AddOrEditTaskComponent, this.ngbModalOptions);
    modalRef.componentInstance.tacheParent = tache;
    modalRef.componentInstance.idProjet = this.idProjet;
    modalRef.result.then((res) => {
      this.getPage(this.currentPage);
    });
  }


  ngOnDestroy(): void {
    this.destroy$.next(true);
    this.destroy$.unsubscribe();
  }

}

