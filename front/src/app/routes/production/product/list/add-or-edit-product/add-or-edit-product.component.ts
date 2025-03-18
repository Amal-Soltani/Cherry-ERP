import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { BlockUI, NgBlockUI } from 'ng-block-ui';
import { ToastrService } from 'ngx-toastr';
import { Product, TypeProductEnum, UnitMeasurementEnum } from 'src/app/shared/models/product';
import { ProductService } from 'src/app/shared/services/product.service';

@Component({
  selector: 'app-add-or-edit-product',
  templateUrl: './add-or-edit-product.component.html',
  styleUrls: ['./add-or-edit-product.component.scss']
})
export class AddOrEditProductComponent implements OnInit {

  product: Product = new Product();
  productform: FormGroup;
  submitted: boolean;
  @BlockUI() blockUI: NgBlockUI;

  typeP = TypeProductEnum
  keysP = Object.keys

  UM = UnitMeasurementEnum
  keysUM = Object.keys

  constructor(private productService: ProductService,
    public activeModal: NgbActiveModal,
    private toastr: ToastrService,
    private fb: FormBuilder) { }

  ngOnInit(): void {
    this.generateReference()
    this.clearForm();
  }

  clearForm() {
    this.productform = this.fb.group({
      reference: new FormControl(this.product ? this.product.reference : '', [Validators.required]),
      designation: new FormControl(this.product ? this.product.designation : '', [Validators.maxLength(500)]),
      typeProduct: new FormControl(this.product ? this.product.typeProduct : '', [Validators.required]),
      uniteMesure: new FormControl(this.product ? this.product.unitMeasurement : '', [Validators.required]),
      matiere: new FormControl(this.product ? this.product.matiere : '', [Validators.required]),
      name:new FormControl(this.product ? this.product.name : '', [Validators.required]),
    });
    this.submitted = false;
  }

  generateReference(){
    if(this.product.reference == null){
      this.productService.generateReference().subscribe((res:any)=>{
        this.product.reference = res.reference
      })
    }
  }

  onSubmit() {
    this.submitted = true;
    if (!this.productform.valid) {
      this.toastr.error($localize`Veuillez remplir correctement les champs`, $localize`Erreur`);
      return;
    }
    this.blockUI.start();
    if (this.product.id == null){
      this.productService.AddProduct(this.product).subscribe((res: any) => {
        this.toastr.success($localize`Enregistrement fait avec succès`, $localize`Succès`);
        this.activeModal.close(res.result);
        this.blockUI.stop();
      }, err => {
        console.log(err);
        this.blockUI.stop();
        this.toastr.error($localize`Une erreur s'est produite lors de l'enregistrement` + ': ' + err, $localize`Erreur`);
      });
    }else{
      this.productService.AddProduct(this.product)
        .subscribe((res: any) => {
          this.blockUI.stop();
          this.toastr.success($localize`Modification faite avec succès`, $localize`Succès`);
          this.activeModal.close(res.result);
        }, err => {
          this.blockUI.stop();
          console.log(err);
          this.toastr.error($localize`Une erreur s'est produite lors de l'enregistrement` + ': ' + err, $localize`Erreur`);
        });
    }
  }
}
