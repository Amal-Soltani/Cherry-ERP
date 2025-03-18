import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { BlockUI, NgBlockUI } from 'ng-block-ui';
import { ToastrService } from 'ngx-toastr';
import { FNC } from 'src/app/shared/models/FNC';
import { DecsionEnum, FNCAnomalies } from 'src/app/shared/models/FNCAnomalies';
import { AnomaliesService } from 'src/app/shared/services/anomalies.service';
import { EmployeeService } from 'src/app/shared/services/employee.service';
import { EquipmentService } from 'src/app/shared/services/equipment.service';
import { FNCService } from 'src/app/shared/services/fnc.service';
import { FNCAnomaliesService } from 'src/app/shared/services/fncanomalies.service';
import { ProjetService } from 'src/app/shared/services/projet.service';
import { TacheService } from 'src/app/shared/services/tache.service';

@Component({
  selector: 'app-add-or-edit-fnc',
  templateUrl: './add-or-edit-fnc.component.html',
  styleUrls: ['./add-or-edit-fnc.component.scss']
})
export class AddOrEditFNCComponent implements OnInit {
  fiche: FNC = new FNC();
  FNCform: FormGroup;
  submitted: boolean;
  @BlockUI() blockUI: NgBlockUI;

  employees: any[] = []
  projects: any[] = []
  taches: any[] = []
  equipments: any[] = []
  anomalies: any[] = []

  decision = DecsionEnum;
  decisionKeys = Object.keys

  employee: any
  tache: any
  product: any
  projet: any
  projetREF: any
  client: any
  BC: any
  OFclient: any
  qte: any
  status: any
  priority: any
  FNCAnomalies: FNCAnomalies = new FNCAnomalies();

  Rows: Array<any> = []
  FNCAnomaliesList: Array<any> = []

  constructor(private FNCService: FNCService,
    private employeeService: EmployeeService,
    private tacheService: TacheService,
    private projectService: ProjetService,
    private equipmentService: EquipmentService,
    private anomaliesService: AnomaliesService,
    private FNCAnomaliesService: FNCAnomaliesService,
    public activeModal: NgbActiveModal,
    private toastr: ToastrService,
    private fb: FormBuilder) {

  }

  ngOnInit(): void {
    this.generateReference();
    this.clearForm();
    this.getEmployees();
    this.getProjectsList();
    this.getTacheList(this.fiche.projet)
    this.getEquipmentList();
    this.getAnomaliesList();
    this.getFNC();
    this.getDetails(this.fiche.tache);
    this.employee = "[" + this.fiche.employee.employeeNumber + "]" + " : " + this.fiche.employee.firstName + " " + this.fiche.employee.lastName
    this.tache = "[" + this.fiche.tache.reference + "]" + " : " + this.fiche.tache.titre 

  }

  clearForm() {
    this.FNCform = this.fb.group({
      reference: new FormControl(this.fiche ? this.fiche.reference : ''),
      projet: new FormControl(this.fiche ? this.fiche.projet : '',[Validators.required]),
      projetREF: new FormControl(''),
      employee: new FormControl(''),
      reportDate: new FormControl(this.fiche ? this.fiche.date : '',[Validators.required]),
      tache: new FormControl(''),
      equipment: new FormControl(this.fiche ? this.fiche.equipment : ''),
      col1: new FormControl(''),
      col2: new FormControl('', [Validators.min(0)]),
      col3: new FormControl('', [Validators.min(0)]),
      col4: new FormControl('', [Validators.min(0)]),
      col5: new FormControl(''),
      col6: new FormControl('', [Validators.maxLength(500)]),
      col7: new FormControl('', [Validators.maxLength(500)]),
    });
    this.submitted = false;
    
  }

  getFNC() {
    if (this.fiche !== null) {
      this.FNCAnomaliesService.findAll(this.fiche.id).subscribe((res: any) => {
        this.FNCAnomaliesList = res.result
        this.getAnomalies(this.FNCAnomaliesList)
      })
    }
  }

  getAnomalies(FNCAnomaliesLis){
    for (let r of this.FNCAnomaliesList) {
      this.Rows.push({
        col0: r.id, col1: r.anomalies.type, col2: r.nc, col3: r.rebut,
        col4: r.cout, col5: r.decision, col6: r.description, col7: r.cause
      })
      for (let p of this.Rows) {
        p.isSaved = true
      }
    }
  }

  generateReference(){
    if(this.fiche.reference == null){
      this.FNCService.generateReference().subscribe((res:any)=>{
        this.fiche.reference = res.reference
      })
    }
  }

  getEmployees() {
    this.employeeService.findAll().subscribe((res: any) => {
      this.employees = res.result
    })
  }

  selectedEmp(empchange) {
    if (this.fiche.employee == null || this.fiche.employee != empchange) {
      this.fiche.employee = empchange
    }
  }

  selectedTache(tachechange) {
    if (this.fiche.tache == null || this.fiche.tache != tachechange) {
      this.fiche.tache = tachechange
    }
  }

  getProjectsList() {
    this.projectService.FindAllProjets().subscribe((res: any) => {
      this.projects = res.result
    })
  }

  getTacheList(projet) {
    this.projectService.getById(projet).subscribe((res:any)=>{
      this.projetREF = res.result.reference
    })
    this.tacheService.getByProject(projet).subscribe((res: any) => {
      this.taches = res.result
    })
  }

  getEquipmentList() {
    this.equipmentService.findAll().subscribe((res: any) => {
      this.equipments = res.result
    })
  }

  getDetails(tache) {
    this.BC = tache.bonCmd
    this.product = tache.product.libelle
    this.projet = "[" + tache.projet.reference + "]" + " " + tache.projet.titre
    this.client = "[" + tache.projet.employee.employeeNumber + "]" + " " + tache.projet.employee.firstName + " " + tache.projet.employee.lastName
    this.OFclient = tache.ofexterne
    this.qte = tache.quantite
    this.status = tache.status
    this.priority = tache.priority
  }

  getAnomaliesList() {
    this.anomaliesService.findAll().subscribe((res: any) => {
      this.anomalies = res.result
    })
  }

  addRow() {
    this.Rows.push({ col0: "R-" + this.Rows.length, col1: '', col2: '', col3: '', col4: '', col5: '', col6: '', col7: '' });
  }

  add(row) {
    row.isSaved = true
    console.log(this.Rows)

  }
  update(row) {
    row.isSaved = false
    console.log(this.Rows)
  }

  Annuler(row) {
    row.isSaved = true
    this.getAnomalies(this.FNCAnomaliesList)
  }

  delete(row) {
    this.Rows = this.Rows.filter(el => el.col0 !== row.col0);
    console.log(this.Rows)
  }

  onSubmit() {
    this.submitted = true;
    if (!this.FNCform.valid) {
      this.toastr.error($localize`Veuillez remplir correctement les champs`, $localize`Erreur`);
      return;
    }
    this.blockUI.start();
    //Add and update
    this.FNCService.add(this.fiche).subscribe((res: any) => {
      for (let r of this.Rows) {
        if (r.col0.toString().includes("R-")) {
          //add
          this.anomaliesService.findByType(r.col1).subscribe((anomalie: any) => {
            this.FNCAnomalies.anomalies = anomalie.result
            this.FNCAnomalies.id = null
            this.FNCAnomalies.nc = r.col2
            this.FNCAnomalies.rebut = r.col3
            this.FNCAnomalies.cout = r.col4
            if (r.col5 == "derogation") {
              this.FNCAnomalies.decision = r.col5;
            }
            if (r.col5 == "destruction") {
              this.FNCAnomalies.decision = r.col5
            }
            if (r.col5 == "recuperation") {
              this.FNCAnomalies.decision = r.col5
            }
            this.FNCAnomalies.description = r.col6
            this.FNCAnomalies.cause = r.col7
            console.log(this.FNCAnomalies)
            this.FNCAnomaliesService.add(this.FNCAnomalies, res.result.id).subscribe((res: any) => {
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
          // update
          for (let f of this.FNCAnomaliesList) {
            if (r.col0 == f.id) {
              this.anomaliesService.findByType(r.col1).subscribe((anomalie: any) => {
                this.FNCAnomalies.anomalies = anomalie.result
                this.FNCAnomalies.id = f.id
                this.FNCAnomalies.nc = r.col2
                this.FNCAnomalies.rebut = r.col3
                this.FNCAnomalies.cout = r.col4
                if (r.col5 == "derogation") {
                  this.FNCAnomalies.decision = r.col5;
                }
                if (r.col5 == "destruction") {
                  this.FNCAnomalies.decision = r.col5
                }
                if (r.col5 == "recuperation") {
                  this.FNCAnomalies.decision = r.col5
                }
                this.FNCAnomalies.description = r.col6
                this.FNCAnomalies.cause = r.col7
                this.FNCAnomaliesService.add(this.FNCAnomalies, res.result.id).subscribe((res: any) => {
                  this.toastr.success($localize`Enregistrement fait avec succès`, $localize`Succès`);
                  this.activeModal.close(res.result);
                  this.blockUI.stop();
                }, err => {
                  console.log(err);
                  this.blockUI.stop();
                  this.toastr.error($localize`Une erreur s'est produite lors de l'enregistrement` + ': ' + err, $localize`Erreur`);
                })
              })
            }
          }
        }
      }
    })

    //delete Non conformités
      const difference = this.FNCAnomaliesList.filter((item1) => !this.Rows.some((item2) => item1.id === item2.col0));
      if (difference !== null) {
        for (let d of difference) {
          this.FNCAnomaliesService.delete(this.fiche.id, d.id).subscribe(
            () => {
              this.toastr.success($localize`Succès`, $localize`Supprimé avec succès`);
            },
            err => {
              console.log(err);
              this.toastr.error($localize`Erreur`, $localize`Une erreur s'est produite. Veuillez essayer de nouveau.`);
            });
        }
      }
  }
}
