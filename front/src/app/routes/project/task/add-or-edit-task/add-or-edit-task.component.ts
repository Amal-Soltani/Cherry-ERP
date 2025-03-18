import { Component, OnInit } from '@angular/core';
import { AbstractControl, ValidatorFn, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal, NgbModal, NgbModalOptions } from '@ng-bootstrap/ng-bootstrap';
import { BlockUI, NgBlockUI } from 'ng-block-ui';
import { ToastrService } from 'ngx-toastr';
import { AddOrEditProductComponent } from 'src/app/routes/production/product/list/add-or-edit-product/add-or-edit-product.component';
import { Employee } from 'src/app/shared/models/employee';
import { Product } from 'src/app/shared/models/product';
import { Projects } from 'src/app/shared/models/projects';
import { PriorityTacheEnum, StatutTacheEnum, Tache } from 'src/app/shared/models/tache';
import { EmployeeService } from 'src/app/shared/services/employee.service';
import { ProductService } from 'src/app/shared/services/product.service';
import { ProjetService } from 'src/app/shared/services/projet.service';
import { TacheService } from 'src/app/shared/services/tache.service';

@Component({
  selector: 'app-add-or-edit-task',
  templateUrl: './add-or-edit-task.component.html',
  styleUrls: ['./add-or-edit-task.component.scss']
})
export class AddOrEditTaskComponent implements OnInit {
  ngbModalOptions: NgbModalOptions = {
    size: 'lg',
    backdrop: 'static',
    keyboard: false
  };

  tache: Tache = new Tache;
  tacheform: FormGroup;
  submitted: boolean;

  @BlockUI() blockUI: NgBlockUI;
  idProjet: any

  tacheParent: any
  parentReference: any
  parentTitle: any

  projectList: Projects[] = []
  productlist: Product[] = [];
  employeelist: Employee[] = []
  product: any;
  reference: any
  employee: any
  project: any


  status = StatutTacheEnum
  keyS = Object.keys

  priority = PriorityTacheEnum
  keyP = Object.keys

  constructor(public activeModal: NgbActiveModal,
    private fb: FormBuilder,
    private toastr: ToastrService,
    private tacheService: TacheService,
    private productService: ProductService,
    private employeeService: EmployeeService,
    private projetServivce: ProjetService,
    private modalService: NgbModal) {

  }

  ngOnInit(): void {
    this.generateReference();
    this.getProductList()
    this.getTache()
    this.getProjectsList();
    this.getResponsiblesList()
    this.clearForm()
    if(this.tache.product !== null){
      this.product = this.tache.product.libelle
    }
    if(this.tache.projet !== null){
      this.project = "[" + this.tache.projet.reference + "]" + " : " + this.tache.projet.titre
    }
    if(this.tache.employee !== null){
      this.employee = "[" + this.tache.employee.employeeNumber + "]" + " : " + this.tache.employee.firstName + " " + this.tache.employee.lastName
    }
  }

  clearForm() {
    this.tacheform = this.fb.group({
      BNC: new FormControl(this.tache ? this.tache.bonCmd : '', [Validators.required]),
      priority: new FormControl(this.tache ? this.tache.priority : '', [Validators.required]),
      status: new FormControl(this.tache ? this.tache.status : '', [Validators.required]),
      titre: new FormControl(this.tache ? this.tache.titre : '', [Validators.required]),
      product: new FormControl('', [Validators.required]),
      OF: new FormControl(this.tache ? this.tache.ofexterne : '', [Validators.maxLength(500)]),
      quantite: new FormControl(this.tache ? this.tache.quantite : '', [Validators.min(0)]),
      designation: new FormControl(this.tache ? this.tache.designation : '', [Validators.maxLength(1000)]),
      dateDebutPrev: new FormControl(this.tache ? this.tache.dateDebutPrev : ''),
      dateDebutReel: new FormControl(this.tache ? this.tache.dateDebutReel : ''),
      dateFinPrev: new FormControl(this.tache ? this.tache.dateFinPrev : ''),
      dateFinReel: new FormControl(this.tache ? this.tache.dateFinReel : ''),
      responsible: new FormControl(''),
      projet: new FormControl('')
    }, {
      validators: [this.dateRangeValidatorPrev(), this.dateRangeValidatorReel()]
    });

    this.submitted = false;

  }

  generateReference() {
    if (this.tache.reference == null) {
      this.tacheService.generateReference().subscribe((res: any) => {
        this.tache.reference = res.reference
      })
    }
  }

  dateRangeValidatorPrev(): ValidatorFn {
    return (formGroup: AbstractControl): { [key: string]: any } | null => {
      const dateDebutPrev = formGroup.get('dateDebutPrev').value;
      const dateFinPrev = formGroup.get('dateFinPrev').value;

      if (dateDebutPrev && dateFinPrev && dateDebutPrev > dateFinPrev) {
        return { 'dateRangeValidatorPrev': true };
      } else {
        return null;
      }
    };
  }
  dateRangeValidatorReel(): ValidatorFn {
    return (formGroup: AbstractControl): { [key: string]: any } | null => {

      const dateDebutReel = formGroup.get('dateDebutReel').value;
      const dateFinReel = formGroup.get('dateFinReel').value;

      if (dateDebutReel && dateFinReel && dateDebutReel > dateFinReel) {
        return { 'dateRangeValidatorReel': true };
      } else {
        return null;
      }
    };
  }

  getTache() {
    if (this.tacheParent != null) {
      this.tache.parent = this.tacheParent.id
      this.parentTitle = this.tacheParent.titre
      this.parentReference = this.tacheParent.reference
      this.tache.bonCmd = this.tacheParent.bonCmd
    }
    if (this.tache != null) {
      if (this.tache.parent != null) {
        this.tacheService.getById(this.tache.parent).subscribe((res: any) => {
          this.parentTitle = res.result.titre
          this.parentReference = res.result.reference
        })
      }
    }
  }

  getProjectsList() {
    this.projetServivce.FindAllProjets().subscribe((res: any) => {
      this.projectList = res.result
    })
  }

  selectedProject(projectchange) {
    if (this.tache.projet == null || this.tache.projet != projectchange) {
      this.tache.projet = projectchange
    }
  }


  getProductList() {
    this.productService.getAll().subscribe((res: any) => {
      this.productlist = res.result;
    })
  }

  selectedProduct(productchange) {
    if (this.tache.projet == null || this.tache.product !== productchange) {
      this.tache.product = productchange
    }
  }

  addProduct(){
    const modalRef = this.modalService.open(AddOrEditProductComponent, this.ngbModalOptions);
    modalRef.result.then(() => {
      this.getProductList();
    });
  }

  getResponsiblesList() {
    this.employeeService.findAll().subscribe((res: any) => {
      this.employeelist = res.result;
    })
  }

  selectedRes(reschange) {
    if (this.tache.employee == null || this.tache.employee != reschange) {
      this.tache.employee = reschange
    }
  }

  onSubmit() {  
    this.submitted = true;
    if (!this.tacheform.valid) {
      this.toastr.error($localize`Veuillez remplir correctement les champs`, $localize`Erreur`);
      return;
    }
    if (this.idProjet !== null) {
      this.projetServivce.getById(this.idProjet).subscribe((res: any) => {
        this.tache.projet = res.result
        this.tacheService.add(this.tache).subscribe((res: any) => {
          this.toastr.success($localize`Enregistrement fait avec succès`, $localize`Succès`);
          this.activeModal.close(res.result);
          this.blockUI.stop();
        }, err => {
          console.log(err);
          this.blockUI.stop();
          this.toastr.error($localize`Une erreur s'est produite lors de l'enregistrement` + ': ' + err, $localize`Erreur`);
        });
      })
    }
    if(this.idProjet == null){
      this.tacheService.add(this.tache).subscribe((res: any) => {
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


}
