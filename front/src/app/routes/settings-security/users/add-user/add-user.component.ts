import { Component, OnInit, Input } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { FormGroup, FormControl, Validators, FormBuilder } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { NgBlockUI, BlockUI } from 'ng-block-ui';
import { UserService } from '../../../../shared/services/user.service';
import { Employee } from 'src/app/shared/models/employee';

@Component({
  selector: 'app-add-user',
  templateUrl: './add-user.component.html',
  styleUrls: ['./add-user.component.scss']
})
export class AddUserComponent implements OnInit {

  @Input() user: any;
  userform: FormGroup;
  submitted: boolean;
  currentUser: any;
  employee: Employee;
  loading: boolean;

  @BlockUI() blockUI: NgBlockUI;

  constructor(public activeModal: NgbActiveModal,
    private fb: FormBuilder,
    private toastr: ToastrService,
    private userService: UserService) { }

  ngOnInit(): void {
    this.clearForm();
    if (this.user) {
      this.userform.patchValue(this.user);
      this.userform.controls['employee'].setValue(this.user.employee);
      this.userform.controls['email'].disable();
    }
    else {
      this.userform.controls['password'].setValidators(Validators.required);
    }

  }

  rolesUpdated(roles: any) {
    this.userform.patchValue({ roles: roles });
  }

  clearForm() {
    this.userform = this.fb.group({
      employee: new FormControl('', Validators.required),
      email: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', [Validators.required, Validators.minLength(6)]),
      roles: new FormControl(),
      enabled: new FormControl(true)
    });
    this.submitted = false;
  }

  get f() { return this.userform.controls; }

  findAllStore() {
    this.loading = true;
  }

  onSubmit() {
    if (!this.user && !this.userform.valid) {
      this.toastr.error($localize `Veuillez remplir correctement les champs`, $localize `Erreur`);
      return;
    }
    this.submitted = true;
    this.blockUI.start();
    // if edit mode
    if (this.user) {
      this.userService.update(this.user.id, this.userform.value)
        .subscribe((res: any) => {
          this.blockUI.stop();
          this.toastr.success($localize `Modification faite avec succès`, $localize `Succès`);
          this.activeModal.close(res.result);
        }, err => {
          this.blockUI.stop();
          console.log(err);
          this.toastr.error($localize `Une erreur s'est produite lors de l'enregistrement` + ': ' + err, $localize `Erreur`);
        });
    } else {
      this.userService.create(this.userform.value)
        .subscribe((res: any) => {
          this.toastr.success($localize `Enregistrement fait avec succès`, $localize `Succès`);
          this.blockUI.stop();
          this.activeModal.close(res);
        }, err => {
          console.log(err);
          this.blockUI.stop();
          this.toastr.error($localize `Une erreur s'est produite lors de l'enregistrement` + ': ' + err, $localize `Erreur`);
        });
    }
  }

  isRoleActive(roles: string, role) {
    return roles.includes(role);
  }

  onEmployeeSelection($evt) {
    this.employee = $evt;
    this.userform.patchValue({employee: this.employee});
  }

}
