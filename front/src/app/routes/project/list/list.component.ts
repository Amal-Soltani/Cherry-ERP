import { Component, OnInit } from '@angular/core';
import { ProjetService } from 'src/app/shared/services/projet.service';
import { AddOrEditProjectComponent } from './add-or-edit-project/add-or-edit-project.component';
import { ConfirmDeleteComponent } from 'src/app/shared/component/confirm-delete/confirm-delete.component';
import { Subject, takeUntil } from 'rxjs';
import { NgbModal, NgbModalOptions } from '@ng-bootstrap/ng-bootstrap';
import { ToastrService } from 'ngx-toastr';
import { StatutProjetEnum } from 'src/app/shared/models/projects';
import { TacheService } from 'src/app/shared/services/tache.service';
import { AuthenticationService } from 'src/app/shared/services/authentication.service';

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.scss']
})
export class ListComponent implements OnInit {
  ngbModalOptions: NgbModalOptions = {
    size: 'lg-60',//'xl'
    backdrop: 'static',
    keyboard: false
  };

  projetList: any[] = [];
  reference: any
  isClicked: boolean
  changeStatus: boolean
  equipeList: Array<any> = []
  category = "tous"
  projects: any

  statut = StatutProjetEnum;
  keys = Object.keys

  destroy$: Subject<boolean> = new Subject<boolean>();
  loading: boolean;
  totalElements: number;
  pageSize = 10;
  currentPage: number;
  lastPage: number;
  type: string;
  filter: string;


  constructor(private modalService: NgbModal,
    private toastr: ToastrService,
    private projetService: ProjetService,
    private tacheService: TacheService,
    public AuthenticationService: AuthenticationService) { }


  ngOnInit(): void {
    this.statut.brouillon
    this.getPage(0);
  }

  get(status: any) {
    this.category = status
    this.getPage(0)
  }

  getPage(page: number) {
    this.loading = true;
    this.projetList = []
      this.projects = []
    if (this.category === "tous") {
      this.projetService.findByPage(page, this.pageSize).pipe(takeUntil(this.destroy$))
        .subscribe((res: any) => {
          this.loading = false;
          this.projetList = res.content;
          this.projects = this.projetList
          this.currentPage = page;
          this.lastPage = res.totalPages - 1;
          this.totalElements = res.totalElements;
          for (let project of this.projetList) {
            project.isClicked = false
            project.changeStatus = false
            this.getEquipe(project)
          }
        }, err => {
          console.log(err);
        });
    } else {
      this.projetService.findByPageAndStatus(page, this.pageSize, this.category).pipe(takeUntil(this.destroy$))
        .subscribe((res: any) => {
          this.loading = false;
          this.projetList = res.content;
          this.projects = this.projetList
          this.currentPage = page;
          this.lastPage = res.totalPages - 1;
          this.totalElements = res.totalElements;
          for (let project of this.projetList) {
            project.isClicked = false
            project.changeStatus = false
            this.getEquipe(project)
          }
        }, err => {
          console.log(err);
        });
    }

  }

  onSearchChange(searchTerm: string) {
    searchTerm = searchTerm.toLowerCase();
    this.projetList = this.projects
    this.projetList = this.projetList.filter(item =>
      (item.reference && item.reference.toLowerCase().includes(searchTerm.toLowerCase())) ||
      (item.titre && item.titre.toLowerCase().includes(searchTerm.toLowerCase())) ||
      (item.localisation && item.localisation.toLowerCase().includes(searchTerm.toLowerCase())) ||
      (item.employee.firstName && item.employee.firstName.toLowerCase().includes(searchTerm.toLowerCase())) ||
      (item.employee.lastName && item.employee.lastName.toLowerCase().includes(searchTerm.toLowerCase())) ||
      (item.dateLancement && item.dateLancement.includes(searchTerm)) ||
      (item.datePrevueLivraison && item.datePrevueLivraison.includes(searchTerm))
    );
  }

  getEquipe(projet: any) {
    projet.equipeList = [];
    this.tacheService.getByProject(projet.id).subscribe((res: any) => {
      for (let r of res.result) {
        // Check if the employee already exists in the equipeList based on employee ID
        const employeeExists = projet.equipeList.some(emp => emp.col4 === r.employee.id);
        if (!employeeExists) {
          projet.equipeList.push({
            col1: r.employee.firstName,
            col2: r.employee.lastName,
            col3: projet.id,
            col4: r.employee.id
          });
        }
      }
    })
  }


  public calculateTimeWorked(project: any): number {
    if (project.datePrevueLivraison != null && project.dateLancement != null) {
      const dFin = new Date(project.datePrevueLivraison);
      const dDebut = new Date(project.dateLancement);
      const timeDifference = dFin.getTime() / (1000 * 60) - dDebut.getTime() / (1000 * 60);
      const curDate = new Date()
      const cuTimeDifference = curDate.getTime() / (1000 * 60) - dDebut.getTime() / (1000 * 60);
      if (Math.round(100 * cuTimeDifference / timeDifference) > 100) {
        return 100
      } else if (Math.round(100 * cuTimeDifference / timeDifference) < 0) {
        return 0
      }
      else {
        return Math.round(100 * cuTimeDifference / timeDifference)
      }
    }
  }



  add() {
    const modalRef = this.modalService.open(AddOrEditProjectComponent, this.ngbModalOptions);
    modalRef.result.then(() => {
      this.getPage(0);
    });

  }

  detail(p) {
    if (p.isClicked == false) {
      p.isClicked = true
    } else {
      p.isClicked = false
    }
  }

  update(projet: any) {
    const modalRef = this.modalService.open(AddOrEditProjectComponent, this.ngbModalOptions);
    modalRef.componentInstance.projet = projet;
    modalRef.result.then((res) => {
      this.getPage(0)
      projet.isClicked = false
    });
  }

  delete(projet: any) {
    const modalRef = this.modalService.open(ConfirmDeleteComponent);
    modalRef.result.then((res) => {
      if (res === true) {
        this.projetService.DeleteProjetBYId(projet.id).subscribe(
          () => {
            this.toastr.success($localize`Succès`, $localize`Supprimé avec succès`);
            this.equipeList = null
            projet.isClicked = false
            this.getPage(this.currentPage);
          },
          err => {
            console.log(err);
            this.toastr.error($localize`Erreur`, $localize`Une erreur s'est produite. Veuillez essayer de nouveau.`);
          });
      }
    });
  }

  changeS(p) {
    if (p.changeStatus == false) {
      p.changeStatus = true
    }
    else {
      p.changeStatus = false
    }
  }

  updateS(p) {
    this.projetService.addProjet(p).subscribe((res: any) => {
      p.changeStatus = false
    });
  }

  ngOnDestroy(): void {
    this.destroy$.next(true);
    // Unsubscribe from the subject
    this.destroy$.unsubscribe();
  }

}
