import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { BlockUI, NgBlockUI } from 'ng-block-ui';
import { ToastrService } from 'ngx-toastr';
import { Phase } from 'src/app/shared/models/phase';
import { PhaseService } from 'src/app/shared/services/phase.service';

@Component({
  selector: 'app-add-or-edit-phase',
  templateUrl: './add-or-edit-phase.component.html',
  styleUrls: ['./add-or-edit-phase.component.scss']
})
export class AddOrEditPhaseComponent implements OnInit {
  phase: Phase = new Phase();
  phaseform: FormGroup;
  submitted: boolean;
  @BlockUI() blockUI: NgBlockUI;

  constructor(private phaseService: PhaseService,
    public activeModal: NgbActiveModal,
    private toastr: ToastrService,
    private fb: FormBuilder) { }

  ngOnInit(): void {
    this.clearForm();
  }

  clearForm() {
    this.phaseform = this.fb.group({
      name : new FormControl(this.phase ? this.phase.name : '', [Validators.required]),
      description : new FormControl(this.phase ? this.phase.description : '', [Validators.maxLength(500)])
    });
    this.submitted = false;
  }

  onSubmit() {
    if (!this.phaseform.valid) {
      this.toastr.error($localize`Veuillez remplir correctement les champs`, $localize`Erreur`);
      return;
    }
    this.submitted = true;
    this.blockUI.start();
    
      this.phaseService.add(this.phase).subscribe((res: any) => {
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
