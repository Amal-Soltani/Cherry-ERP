import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { BlockUI, NgBlockUI } from 'ng-block-ui';
import { ToastrService } from 'ngx-toastr';
import { Employee } from 'src/app/shared/models/employee';
import { ProductionLigne } from 'src/app/shared/models/productionLigne';
import { EmployeeService } from 'src/app/shared/services/employee.service';
import { ProductionLigneService } from 'src/app/shared/services/production-ligne.service';

@Component({
  selector: 'app-add-or-edit-production-line',
  templateUrl: './add-or-edit-production-line.component.html',
  styleUrls: ['./add-or-edit-production-line.component.scss']
})
export class AddOrEditProductionLineComponent implements OnInit {
  ligneProduction: ProductionLigne = new ProductionLigne();
  ligneform: FormGroup;
  submitted: boolean;
  @BlockUI() blockUI: NgBlockUI;

  employeelist: Employee[] =[]
  employee: any

  constructor(private ligneProductionService: ProductionLigneService,
    public activeModal: NgbActiveModal,
    private toastr: ToastrService,
    private employeeService: EmployeeService,
    private fb: FormBuilder) { }
  
    ngOnInit(): void {
      this.clearForm();
      this.generateReference()
      this.getResponsablesList()
    }
  
    clearForm() {
      this.ligneform = this.fb.group({
        reference : new FormControl(this.ligneProduction ? this.ligneProduction.reference : '', [Validators.required]),
        name : new FormControl(this.ligneProduction ? this.ligneProduction.name : '', [Validators.required]),
        employe : new FormControl('')
      });
      this.submitted = false;
    }

    generateReference(){
      if(this.ligneProduction.reference == null){
        this.ligneProductionService.generateReference().subscribe((res:any)=>{
          this.ligneProduction.reference = res.reference
        })
      }
    }

    getResponsablesList() {
      this.employeeService.findAll().subscribe((res: any) => {
        this.employeelist = res.result;
      })
      this.employee = "["+this.ligneProduction.employee.employeeNumber+"]"+" : "+this.ligneProduction.employee.firstName +" "+ this.ligneProduction.employee.lastName

    }

    selectedResponsible(responsableChange) {
      if (this.ligneProduction.employee == null || this.ligneProduction.employee != responsableChange) {
        this.ligneProduction.employee = responsableChange
      }
    }
  
    onSubmit() {
      if (!this.ligneform.valid) {
        this.toastr.error($localize`Veuillez remplir correctement les champs`, $localize`Erreur`);
        return;
      }
      this.submitted = true;
      this.blockUI.start();
      
        this.ligneProductionService.add(this.ligneProduction).subscribe((res: any) => {
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
