import { Component, OnInit, Input, ChangeDetectorRef } from '@angular/core';
import { EmployeeService } from 'src/app/shared/services/employee.service';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';
import { BlockUI, NgBlockUI } from 'ng-block-ui';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ToastrService } from 'ngx-toastr';
import {Employee} from '../../../../shared/models/employee';
import {ValidatorUtils} from '../../../../shared/utils/validator.utils';

@Component({
  selector: 'app-add-or-edit-employee-v0',
  templateUrl: './add-or-edit-employee.component-v0.html',
  styleUrls: ['./add-or-edit-employee.component-v0.scss']
})
// tslint:disable-next-line:component-class-suffix
export class AddOrEditEmployeeComponentV0 implements OnInit {

  @Input() employee: Employee;
  @Input() external = false;

  employeeform: FormGroup;
  submitted: boolean;

  clientSupplier = 'SUPPLIER';
  achatVente: string;
  selectedClient: string;

  @BlockUI() blockUI: NgBlockUI;

  constructor(private cdr: ChangeDetectorRef,
    public activeModal: NgbActiveModal,
    private fb: FormBuilder,
    private toastr: ToastrService,
    private employeeService: EmployeeService) { }

  ngOnInit(): void {
    this.clearForm();
  }

  clearForm() {
    this.employeeform = this.fb.group({
      id: new FormControl(this.employee ? this.employee.id : 0),
      version: new FormControl(this.employee ? this.employee.version : 0),
      employeeNumber: new FormControl(this.employee ? this.employee.employeeNumber : '', [Validators.required, Validators.pattern(ValidatorUtils.alphanumeric_regex)]),
      firstName: new FormControl(this.employee ? this.employee.firstName : ''),
      lastName: new FormControl(this.employee ? this.employee.lastName : ''),
      post: new FormControl(this.employee ? this.employee.post : ''),
      phone: new FormControl(this.employee ? this.employee.phone : ''),
      external: new FormControl(this.employee ? this.employee.external : this.external),
      externalType: new FormControl(this.employee ? this.employee.externalType : null),
      partnerId: new FormControl(this.employee ? this.employee.partnerId : null),
    });
    this.submitted = false;
  }


  onSubmit() {
    if (!this.employeeform.valid) {
      this.toastr.error($localize `Veuillez remplir correctement les champs`, $localize `Erreur`);
      return;
    }
    this.submitted = true;
    this.blockUI.start();
    // if edit mode
    if (this.employee) {
      this.employeeService.update(this.employee.id, this.employeeform.value)
        .subscribe((res: any) => {
          this.blockUI.stop();
          this.toastr.success($localize `Modification faite avec succès`, $localize `Succès`);
          this.employee = null;
          this.activeModal.close(res.result);
        }, err => {
          this.blockUI.stop();
          console.log(err);
          this.toastr.error($localize `Une erreur s'est produite lors de l'enregistrement` + ': ' + err, $localize `Erreur`);
        });
    }
    else {
      this.employeeService.create(this.employeeform.value)
        .subscribe((res: any) => {
          this.toastr.success($localize `Enregistrement fait avec succès`, $localize `Succès`);
          this.activeModal.close(res.result);
          this.blockUI.stop();
        }, err => {
          console.log(err);
          this.blockUI.stop();
          this.toastr.error($localize `Une erreur s'est produite lors de l'enregistrement` + ': ' + err, $localize `Erreur`);
        });
    }
  }

  onTypeChange(event: any) {
    this.achatVente = this.clientSupplier === 'CLIENT' ? 'VENTE' : 'ACHAT';
    this.employeeform.patchValue({ externalType: this.clientSupplier });
    this.cdr.detectChanges();
  }

  onClientSelected(event: any) {
    this.employeeform.patchValue({ partnerId: event.id });
  }
}
