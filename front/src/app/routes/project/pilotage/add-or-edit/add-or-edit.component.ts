import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { BlockUI, NgBlockUI } from 'ng-block-ui';
import { ToastrService } from 'ngx-toastr';
import { Projects } from 'src/app/shared/models/projects';
import { Employee } from 'src/app/shared/models/employee';
import { Phase } from 'src/app/shared/models/phase';
import { Tache } from 'src/app/shared/models/tache';
import { EmployeeService } from 'src/app/shared/services/employee.service';
import { GmPhaseService } from 'src/app/shared/services/gmPhase.service';
import { PhaseService } from 'src/app/shared/services/phase.service';
import { ProjetService } from 'src/app/shared/services/projet.service';
import { TacheService } from 'src/app/shared/services/tache.service';
import { ProductionLigne } from 'src/app/shared/models/productionLigne';
import { ProductionLigneService } from 'src/app/shared/services/production-ligne.service';
import { Planning } from 'src/app/shared/models/planning';
import { PlanningService } from 'src/app/shared/services/planning.service';

@Component({
  selector: 'app-add-or-edit',
  templateUrl: './add-or-edit.component.html',
  styleUrls: ['./add-or-edit.component.scss']
})
export class AddOrEditComponent implements OnInit {

  pilotage: Planning = new Planning();
  pilotageform: FormGroup;
  submitted: boolean;
  @BlockUI() blockUI: NgBlockUI;

  tacheList: Tache[] = []
  projectList: Projects[] = []
  phaseList: Phase[] = []
  ligneList: ProductionLigne[] = []
  employeeList: Employee[] = []
  idProjet: any
  OF: any
  pilotageId: any
  dateDebut: any
  dateFin: any
  tempsUni: any
  tache: any
  employee: any
  project: any
  ligne: any
  phase: any

  constructor(private tacheService: TacheService,
    private toastr: ToastrService,
    public activeModal: NgbActiveModal,
    private phaseService: PhaseService,
    private ligneService: ProductionLigneService,
    private employeeService: EmployeeService,
    private pilotageService: PlanningService,
    private projetServivce: ProjetService,
    private gmPhaseService: GmPhaseService,
    private fb: FormBuilder) { }

  ngOnInit(): void {
    this.clearForm();
    this.getProjectsList();
    this.getTacheList();
    this.getLigneList();
    this.getEmployeeList();
    this.getPhaseList();
    this.getPilotage();
  }

  clearForm() {
    //const isProjectRequired = this.idProjet == null;
    this.pilotageform = this.fb.group({
      tache: new FormControl('', [Validators.required]),
      phase: new FormControl(this.pilotage ? this.pilotage.phase : ''),
      lineId: new FormControl(''),
      sDate: new FormControl(this.pilotage ? this.pilotage.dateDebut : ''),
      eDate: new FormControl(this.pilotage ? this.pilotage.dateFin : ''),
      tempsArrets: new FormControl(this.pilotage ? this.pilotage.tempsArret : '', [Validators.required]),
      project: new FormControl('',// isProjectRequired ? [Validators.required] : []
        ),
      stopReason: new FormControl(this.pilotage ? this.pilotage.raisonArret : '', [Validators.maxLength(500)]),
      estimatedDuration: new FormControl(this.pilotage ? this.pilotage.tempsMachine : '', [Validators.min(0)]),
      qntite: new FormControl(this.pilotage ? this.pilotage.qteTotale : '', [Validators.required]),
      qntiteNc: new FormControl(this.pilotage ? this.pilotage.qteNC : '', [Validators.required]),
      qntiteRebut: new FormControl(this.pilotage ? this.pilotage.qteRebut : '', [Validators.required]),
      note: new FormControl(this.pilotage ? this.pilotage.note : '', [Validators.maxLength(500)]),
      equipm: new FormControl(this.pilotage ? this.pilotage.equipment : ''),
      tempsuni: new FormControl(''),
      empl: new FormControl('', [Validators.required])
    });
    this.submitted = false;
  }


  getPilotage() {
    if (this.pilotageId == null && this.dateDebut == null && this.dateFin == null) {
      this.pilotage.dateDebut = this.convertToDatetimeLocal(this.pilotage.dateDebut.toString())
      this.pilotage.dateFin = this.convertToDatetimeLocal(this.pilotage.dateFin.toString())

    } else if (this.pilotageId != null) {
      this.pilotageService.getById(this.pilotageId).subscribe((res: any) => {
        this.pilotage = res.result

        this.pilotage.dateDebut = this.convertToDatetimeLocal(this.pilotage.dateDebut.toString())
        this.pilotage.dateFin = this.convertToDatetimeLocal(this.pilotage.dateFin.toString())

        if (this.pilotage.employee !== null) {
          this.employee = "[" + this.pilotage.employee.employeeNumber + "]" + " : " + this.pilotage.employee.firstName + " " + this.pilotage.employee.lastName
        }
        if (this.pilotage.projects !== null) {
          this.project = "[" + this.pilotage.projects.reference + "]" + " : " + this.pilotage.projects.titre
        }
        if (this.pilotage.productionLigne !== null) {
          this.ligne = this.pilotage.productionLigne.name
        }
        if (this.pilotage.phase !== null) {
          this.phase = this.pilotage.phase
        }
        if (this.pilotage.tache !== null) {
          this.tache = "[" + this.pilotage.tache.reference + "]" + " : " + this.pilotage.tache.titre
        }
      })
    } else if (this.dateDebut != null && this.dateFin != null) {
      this.pilotage.dateDebut = this.convertToDatetimeLocal(this.dateDebut.toString())
      this.pilotage.dateFin = this.convertToDatetimeLocal(this.dateFin.toString())
    }
  }

  convertToDatetimeLocal(backendDate: string): string {
    const date = new Date(backendDate);
    const year = date.getFullYear();
    const month = ('0' + (date.getMonth() + 1)).slice(-2); // Add leading zero if needed
    const day = ('0' + date.getDate()).slice(-2);
    const hours = ('0' + date.getHours()).slice(-2);
    const minutes = ('0' + date.getMinutes()).slice(-2);

    // Return formatted date
    return `${year}-${month}-${day}T${hours}:${minutes}`;
  }

  getTacheList() {
    if (this.pilotage.employee != null || this.pilotage.projects != null) {
      this.employee = "[" + this.pilotage.employee.employeeNumber + "]" + " : " + this.pilotage.employee.firstName + " " + this.pilotage.employee.lastName
      this.project = "[" + this.pilotage.projects.reference + "]" + " : " + this.pilotage.projects.titre
      this.ligne = this.pilotage.productionLigne.name
      this.phase = this.pilotage.phase
      this.tache = this.pilotage.tache.titre
    }
    if (this.idProjet != null) {
      this.tacheService.getByProject(this.idProjet).subscribe((res: any) => {
        this.tacheList = res.result
        console.log(this.tacheList)
      })
    } else {
      this.tacheService.getAll().subscribe((res: any) => {
        this.tacheList =  res.result
      })
    }
  }

  selectedTache(tachechange) {
    if (this.pilotage.tache == null || this.pilotage.tache != tachechange) {
      this.pilotage.tache = tachechange
      this.phase = null
      this.pilotage.equipment = null
      this.tempsUni = null
    }
  }

  getPhaseList() {
    this.phaseService.findAll().subscribe((res: any) => {
      this.phaseList = res.result
    })
  }

  selectedPhase(phasechange) {
    if (this.pilotage.phase == null || this.pilotage.phase != phasechange) {
      this.pilotage.phase = phasechange.name
      this.getEquipment(phasechange)
    }
  }

  getEquipment(phase) {
    this.pilotage.equipment = null
    this.tempsUni = null
    this.gmPhaseService.getOne(this.pilotage.tache.product.gamme.id, phase.id).subscribe((res: any) => {
      this.pilotage.equipment = res.result.equipment
      this.tempsUni = res.result.temps
    })
  }

  getLigneList() {
    this.ligneService.findAll().subscribe((res: any) => {
      this.ligneList = res.result
    })
  }

  selectedLigne(lignechange) {
    if (this.pilotage.productionLigne == null || this.pilotage.productionLigne != lignechange) {
      this.pilotage.productionLigne = lignechange
    }
  }

  getEmployeeList() {
    this.employeeService.findAll().subscribe((res: any) => {
      this.employeeList = res.result
    })
  }

  selectedEmployee(empchange) {
    if (this.pilotage.employee == null || this.pilotage.employee != empchange) {
      this.pilotage.employee = empchange
    }
  }

  getProjectsList() {
    this.projetServivce.FindAllProjets().subscribe((res: any) => {
      this.projectList = res.result
    })
  }

  selectedProject(projectchange) {
    if (this.pilotage.projects == null || this.pilotage.projects != projectchange) {
      this.pilotage.projects = projectchange
    }
  }


  public calculateTimeWorked(): number {
    if (this.pilotage.dateDebut != null && this.pilotage.dateFin != null) {
      const dateFin = new Date(this.pilotage.dateFin);
      const dateDebut = new Date(this.pilotage.dateDebut);
      const timeDifferenceMs = dateFin.getTime() - dateDebut.getTime();
      return timeDifferenceMs / (1000 * 60); // Convert to minutes
    } else {
      return 0
    }

  }


  onSubmit() {
    this.submitted = true;
    if (!this.pilotageform.valid) {
      this.toastr.error($localize`Veuillez remplir correctement les champs`, $localize`Erreur`);
      return;
    }
    this.blockUI.start();
    if (this.idProjet != null && this.pilotage.projects == null) {
      this.projetServivce.getById(this.idProjet).subscribe((res: any) => {
        this.pilotage.projects = res.result
        this.pilotageService.add(this.pilotage).subscribe((res: any) => {
          this.toastr.success($localize`Enregistrement fait avec succès`, $localize`Succès`);
          this.activeModal.close(res.result);
          this.blockUI.stop();
        }, err => {
          console.log(err);
          this.blockUI.stop();
          this.toastr.error($localize`Une erreur s'est produite lors de l'enregistrement` + ': ' + err, $localize`Erreur`);
        })
      })
    } else {
      this.pilotageService.add(this.pilotage).subscribe((res: any) => {
        this.toastr.success($localize`Enregistrement fait avec succès`, $localize`Succès`);
        this.activeModal.close(res.result);
        this.blockUI.stop();
      }, err => {
        console.log(err);
        this.blockUI.stop();
        this.toastr.error($localize`Une erreur s'est produite lors de l'enregistrement` + ': ' + err, $localize`Erreur`);
      })
    }

    this.idProjet = null
  }
}
