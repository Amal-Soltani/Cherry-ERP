import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgbModal, NgbModalOptions } from '@ng-bootstrap/ng-bootstrap';
import { Projects } from 'src/app/shared/models/projects';
import { ProjetService } from 'src/app/shared/services/projet.service';
import { AddOrEditProjectComponent } from '../add-or-edit-project/add-or-edit-project.component';
import { TacheService } from 'src/app/shared/services/tache.service';
import { AuthenticationService } from 'src/app/shared/services/authentication.service';

@Component({
  selector: 'app-show-project',
  templateUrl: './show-project.component.html',
  styleUrls: ['./show-project.component.scss']
})
export class ShowProjectComponent implements OnInit {
  ngbModalOptions: NgbModalOptions = {
    size: 'xl',
    backdrop: 'static',
    keyboard: false
  };

  activeTab: string = 'general-info';

  projet: Projects
  info: boolean
  equipeList: Array<any> = []

  constructor(private projetService: ProjetService,
    private activateRoute: ActivatedRoute,
    private modalService: NgbModal,
    private tacheService: TacheService,
    public AuthenticationService: AuthenticationService) { }

  ngOnInit(): void {
    this.getProject();
  }

  getProject() {
    this.projetService.getById(this.activateRoute.snapshot.params.idProjet).subscribe((res: any) => {
      this.projet = res.result
      this.getEquipe()
    })
  }

  public calculateTimeWorked(): number {
    if (this.projet.datePrevueLivraison != null && this.projet.dateLancement != null) {
      const dFin = new Date(this.projet.datePrevueLivraison);
      const dDebut = new Date(this.projet.dateLancement);
      const timeDifference = dFin.getTime() / (1000 * 60) - dDebut.getTime() / (1000 * 60);
      const curDate = new Date()
      const cuTimeDifference = curDate.getTime() / (1000 * 60) - dDebut.getTime() / (1000 * 60);
      if (Math.round(100 * cuTimeDifference / timeDifference) > 100) {
        return 100
      } else {
        return Math.round(100 * cuTimeDifference / timeDifference)
      }
    }
  }

  getEquipe() {
    this.tacheService.getByProject(this.projet.id).subscribe((res: any) => {
      for (let r of res.result) {
        // Check if the employee already exists in the equipeList based on employee ID
        const employeeExists = this.equipeList.some(emp => emp.col3 === r.employee.id);
        if (!employeeExists) {
          this.equipeList.push({
            col1: r.employee.firstName,
            col2: r.employee.lastName,
            col3: r.employee.id
          });
        }
      }
    })
  }


  open() {
    if (this.info == false) {
      this.info = true
    } else {
      this.info = false
    }
  }

  update() {
    const modalRef = this.modalService.open(AddOrEditProjectComponent, this.ngbModalOptions);
    modalRef.componentInstance.projet = this.projet
    modalRef.result.then(() => {
      this.info = false
      this.getProject();
    });
  }

  setActiveTab(tab: string): void {
    this.activeTab = tab;
  }

}


