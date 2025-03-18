import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgbModal, NgbModalOptions } from '@ng-bootstrap/ng-bootstrap';
import { ToastrService } from 'ngx-toastr';
import { StatutProjetEnum } from 'src/app/shared/models/projects';
import { ProjetService } from 'src/app/shared/services/projet.service';
import { TacheService } from 'src/app/shared/services/tache.service';
import { AddOrEditTaskComponent } from '../../../task/add-or-edit-task/add-or-edit-task.component';
import { ConfirmDeleteComponent } from 'src/app/shared/component/confirm-delete/confirm-delete.component';
import { GammeComponent } from 'src/app/routes/project/gamme/gamme.component';
import { AuthenticationService } from 'src/app/shared/services/authentication.service';
import { Subject, takeUntil } from 'rxjs';

@Component({
  selector: 'app-general-info',
  templateUrl: './general-info.component.html',
  styleUrls: ['./general-info.component.scss']
})
export class GeneralInfoComponent implements OnInit {
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

  projet: any
  info = false
  sousProduct = false
  tache: any;
  tacheList: any[] = [];
  status: any
  priority: any

  changeStatus: boolean
  statut = StatutProjetEnum;
  keys = Object.keys

  constructor(private projetService: ProjetService,
    private activateRoute: ActivatedRoute,
    private tacheService: TacheService,
    private modalService: NgbModal,
    private toastr: ToastrService,
    public AuthenticationService: AuthenticationService) { }

  ngOnInit(): void {
    this.getProject();
  }

  getProject() {
    this.projetService.getById(this.activateRoute.parent.snapshot.params.idProjet).subscribe((res: any) => {
      this.projet = res.result
      this.getPage(0)
      this.changeStatus = false

    })
  }


  getPage(page: number) {
    this.loading = true;
    this.tacheList = []
    this.tacheService.findByPageAndProject(page, this.pageSize, this.projet.id).pipe(takeUntil(this.destroy$))
      .subscribe((res: any) => {
        if (res.content == 0 ){
          this.ifEmpty = true
        }
        this.loading = false;
        this.tacheList = res.content;
        this.currentPage = page;
        this.lastPage = res.totalPages - 1;
        this.totalElements = res.totalElements;
        this.ifEmpty = false
        for (let t of this.tacheList) {
          this.status = t.status
          this.priority = t.priority
          t.datePr = new Date(t.dateFinPrev).getTime() - new Date(t.dateDebutPrev).getTime()
          t.datePr = t.datePr / (1000 * 3600)
          t.dateR = new Date(t.dateFinReel).getTime() - new Date(t.dateDebutReel).getTime()
          t.dateR = t.dateR / (1000 * 3600)
  
        }
      }, err => {
        console.log(err);
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
    modalRef.componentInstance.idProjet = this.projet.id;
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
    modalRef.componentInstance.idProjet = this.projet.id;
    modalRef.result.then(() => {
      this.getPage(this.currentPage);
    });
  }

  Gamme(tache) {
    const modalRef = this.modalService.open(GammeComponent, this.ngbModalOptions);
    modalRef.componentInstance.product = tache.product;
    modalRef.componentInstance.tacheParent = tache;
    modalRef.componentInstance.idProjet = this.projet.id;
    modalRef.result.then(() => {
      this.getPage(this.currentPage);
    });
  }

  changeS() {
    if (this.changeStatus == false) {
      this.changeStatus = true
    }
    else {
      this.changeStatus = false
    }
  }

  updateS() {
    this.projetService.addProjet(this.projet).subscribe((res: any) => {
      this.changeStatus = false
    });
  }

}
