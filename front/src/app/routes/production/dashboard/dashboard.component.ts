import { Component, OnInit } from '@angular/core';
import { PlanningService } from 'src/app/shared/services/planning.service';
import { ProjetService } from 'src/app/shared/services/projet.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

  projects: any[] = []
  projet: any
  brouillon: number = 0
  enCours: number = 0
  termine: number = 0
  quality: number = 0
  diponibility: number = 0
  performance: number = 0
  TRS: number = 0
  phases: any[] = []
  employees: any[] = []
  equipments: any[] = []
  lignes: any[] = []

  constructor(private projetService: ProjetService,
    private pilotageService: PlanningService) { }

  ngOnInit(): void {
    this.getProjects()
    this.selectedValue(this.projet)
  }

  getProjects() {
    this.brouillon = 0
    this.enCours = 0
    this.termine = 0
    this.projet = null
    this.projetService.FindAllProjets().subscribe((res: any) => {
      this.projects = res.result
      for (let p of this.projects) {
        if (p.statut == "brouillon") {
          this.brouillon++
        } else if (p.statut === "en_cours") {
          this.enCours++
        } else if (p.statut === "cloture") {
          this.termine++
        }
      }
    })

  }


  selectedValue(selectedProject: any) {
    this.quality = 0
    this.diponibility = 0
    this.performance = 0
    this.TRS = 0

    if (selectedProject == null) {
      this.pilotageService.TRSByAllProjects().subscribe((res: any) => {
        this.quality = res.result.map((d: any) => d.tauxQualite);
        this.performance = res.result.map((d: any) => d.tauxPerformance);
        this.diponibility = res.result.map((d: any) => d.tauxDisponibilite);
        this.TRS = res.result.map((d: any) => d.trs);
      })

      this.pilotageService.TRSByPhase().subscribe((res: any) => {
        this.phases = res.result
      })

      this.pilotageService.TRSByEmp().subscribe((res: any) => {
        this.employees = res.result
      })
      this.pilotageService.TRSByEquip().subscribe((res: any) => {
        this.equipments = res.result
      })
      this.pilotageService.TRSByLigne().subscribe((res: any) => {
        this.lignes = res.result
      })
    } else {
      this.pilotageService.TRSByProject(selectedProject.id).subscribe((res: any) => {
        this.quality = res.result.map((d: any) => d.tauxQualite);
        this.performance = res.result.map((d: any) => d.tauxPerformance);
        this.diponibility = res.result.map((d: any) => d.tauxDisponibilite);
        this.TRS = res.result.map((d: any) => d.trs);
      })
      this.pilotageService.TRSByPhaseProject(selectedProject.id).subscribe((res: any) => {
        this.phases = res.result
      })

      this.pilotageService.TRSByEmpProject(selectedProject.id).subscribe((res: any) => {
        this.employees = res.result
      })
      this.pilotageService.TRSByEquipProject(selectedProject.id).subscribe((res: any) => {
        this.equipments = res.result
      })
      this.pilotageService.TRSByLigneProject(selectedProject.id).subscribe((res: any) => {
        this.lignes = res.result
      })
    }
  }

}
