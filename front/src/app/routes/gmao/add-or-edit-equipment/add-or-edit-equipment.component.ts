import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { BlockUI, NgBlockUI } from 'ng-block-ui';
import { ToastrService } from 'ngx-toastr';
import { CategoryEnum, Equipment, StateEnum } from 'src/app/shared/models/equipment';
import { EquipmentService } from 'src/app/shared/services/equipment.service';

@Component({
  selector: 'app-add-or-edit-equipment',
  templateUrl: './add-or-edit-equipment.component.html',
  styleUrls: ['./add-or-edit-equipment.component.scss']
})
export class AddOrEditEquipmentComponent implements OnInit {

  equipment: Equipment = new Equipment();
  equipmentform: FormGroup;
  submitted: boolean;
  @BlockUI() blockUI: NgBlockUI;

  etatEnum = StateEnum
  keysEtat = Object.keys

  catEnum = CategoryEnum
  keysCat = Object.keys

  constructor(private equipmentService: EquipmentService,
    public activeModal: NgbActiveModal,
    private toastr: ToastrService,
    private fb: FormBuilder) { }

  ngOnInit(): void {
    this.generateReference();
    this.clearForm();
  }

  clearForm() {
    this.equipmentform = this.fb.group({
      reference: new FormControl(this.equipment ? this.equipment.reference : '', [Validators.required]),
      category: new FormControl(this.equipment ? this.equipment.categoryEnum : '', [Validators.required]),
      eta: new FormControl(this.equipment ? this.equipment.stateEnum : '', [Validators.maxLength(200)]),
      position: new FormControl(this.equipment ? this.equipment.emplacement : '', [Validators.maxLength(500)]),
      name:new FormControl(this.equipment ? this.equipment.name : '', [Validators.maxLength(500)]),
      dateAchat: new FormControl(this.equipment ? this.equipment.purchaseDate : '',[Validators.maxLength(200)]),
      dateSortie: new FormControl(this.equipment ? this.equipment.releaseDate : '',[Validators.maxLength(200)]),
      priceAchat:new FormControl(this.equipment ? this.equipment.purchasePrice : '', ),
      descrip:new FormControl(this.equipment ? this.equipment.description : '', [Validators.maxLength(500)])
    });
    this.submitted = false;
  }

  generateReference(){
    if(this.equipment.reference == null){
      this.equipmentService.generateReference().subscribe((res:any)=>{
        this.equipment.reference = res.reference
      })
    }
  }

  onSubmit() {
    this.submitted = true
    if (!this.equipmentform.valid) {
      this.toastr.error($localize`Veuillez remplir correctement les champs`, $localize`Erreur`);
      return;
    }
    this.blockUI.start();
    
      this.equipmentService.create(this.equipment).subscribe((res: any) => {
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
