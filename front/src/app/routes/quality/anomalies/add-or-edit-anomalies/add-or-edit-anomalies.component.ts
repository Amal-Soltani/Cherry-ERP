import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup,Validators} from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { BlockUI, NgBlockUI } from 'ng-block-ui';
import { ToastrService } from 'ngx-toastr';
import { Anomalies } from 'src/app/shared/models/anomalies';
import { AnomaliesService } from 'src/app/shared/services/anomalies.service';

@Component({
  selector: 'app-add-or-edit-anomalies',
  templateUrl: './add-or-edit-anomalies.component.html',
  styleUrls: ['./add-or-edit-anomalies.component.scss']
})
export class AddOrEditAnomaliesComponent implements OnInit {

  anomalie: Anomalies = new Anomalies();
  anomalieform: FormGroup;
  submitted: boolean;
  @BlockUI() blockUI: NgBlockUI;

  constructor(private anomaliesService: AnomaliesService,
    public activeModal: NgbActiveModal,
    private toastr: ToastrService,
    private fb: FormBuilder) { }

  ngOnInit(): void {
    this.clearForm();
  }

  clearForm() {
    this.anomalieform = this.fb.group({
      category : new FormControl(this.anomalie ? this.anomalie.categorie : '', [Validators.required]),
      type : new FormControl(this.anomalie ? this.anomalie.type : '', [Validators.required])
    });
    this.submitted = false;
  }

  onSubmit() {
    if (!this.anomalieform.valid) {
      this.toastr.error($localize`Veuillez remplir correctement les champs`, $localize`Erreur`);
      return;
    }
    this.submitted = true;
    this.blockUI.start();
    
      this.anomaliesService.add(this.anomalie).subscribe((res: any) => {
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
