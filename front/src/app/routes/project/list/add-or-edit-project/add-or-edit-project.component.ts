import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { BlockUI, NgBlockUI } from 'ng-block-ui';
import { ToastrService } from 'ngx-toastr';
import { Projects, StatutProjetEnum } from 'src/app/shared/models/projects';
import { EmployeeService } from 'src/app/shared/services/employee.service';
import { ProjetService } from 'src/app/shared/services/projet.service';
import { ValidatorUtils } from 'src/app/shared/utils/validator.utils';

@Component({
  selector: 'app-add-or-edit-project',
  templateUrl: './add-or-edit-project.component.html',
  styleUrls: ['./add-or-edit-project.component.scss']
})
export class AddOrEditProjectComponent implements OnInit {

  projet: Projects = new Projects();
  projetform: FormGroup;
  submitted: boolean;
  @BlockUI() blockUI: NgBlockUI;

  status = StatutProjetEnum;
  keys = Object.keys

  clientList: any[] = [];
  client: any

  constructor(public activeModal: NgbActiveModal,
    private toastr: ToastrService,
    private fb: FormBuilder,
    private projetService: ProjetService,
    private employeeService: EmployeeService) { }

  ngOnInit(): void {
    console.log(this.projet.datePrevueLancement)
    this.generateReference();
    this.clearForm();
    this.getclientList();
  }

  clearForm() {
    this.projetform = this.fb.group({
      reference: new FormControl(this.projet ? this.projet.reference : '', [Validators.required, Validators.pattern(ValidatorUtils.alphanumeric_regex)]),
      titre: new FormControl(this.projet ? this.projet.titre : '',[Validators.maxLength(500)]),
      client: new FormControl(''),
      statut: new FormControl(this.projet ? this.projet.statut : ''),
      localisation: new FormControl(this.projet ? this.projet.localisation : '',[Validators.maxLength(500)]),
      designationProjet: new FormControl(this.projet ? this.projet.designationProjet : '', [Validators.maxLength(500)]),
      DatePrevueLancement: new FormControl(this.projet ? this.projet.datePrevueLancement : ''),
      DateLancement: new FormControl(this.projet ? this.projet.dateLancement : ''),
      DatePrevueLivraison: new FormControl(this.projet ? this.projet.datePrevueLivraison : ''),
      DateLivraison: new FormControl(this.projet ? this.projet.dateLivraison : ''),
      DateCloture: new FormControl(this.projet ? this.projet.dateCloture : ''),
      checked: new FormControl(this.projet ? this.projet.production : '')
    });
    this.submitted = false;
  }

  generateReference(){
    if(this.projet.reference == null){
      this.projetService.generateReference().subscribe((res:any)=>{
        this.projet.reference = res.reference
      })
    }
  }

  getclientList() {
    this.employeeService.getClient().subscribe((res: any) => {
      this.clientList = res.result
    })
    this.client = "["+this.projet.employee.employeeNumber+"]"+" : "+this.projet.employee.firstName +" "+ this.projet.employee.lastName
  }

  selectedClient(clientchange) {
    if (this.projet.employee == null || this.projet.employee != clientchange) {
      this.projet.employee = clientchange
    }
  }


  onSubmit() {
    this.submitted = true;
    if (!this.projetform.valid) {
      this.toastr.error($localize`Veuillez remplir correctement les champs`, $localize`Erreur`);
      return;
    }
    this.blockUI.start();
    this.projetService.addProjet(this.projet)
      .subscribe((res: any) => {
        this.toastr.success($localize`Enregistrement fait avec succès`, $localize`Succès`);
        this.activeModal.close(res.result);
        this.blockUI.stop();
      }, err => {
        console.log(err);
        this.blockUI.stop();
        this.toastr.error($localize`Une erreur s'est produite lors de l'enregistrement` + ': ' + err, $localize`Erreur`);
      });
  }
}
