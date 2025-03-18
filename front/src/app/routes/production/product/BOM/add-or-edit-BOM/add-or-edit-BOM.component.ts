import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal, NgbModal, NgbModalOptions } from '@ng-bootstrap/ng-bootstrap';
import { BlockUI, NgBlockUI } from 'ng-block-ui';
import { ToastrService } from 'ngx-toastr';
import { BOM, ManufacturingTypeEnum } from 'src/app/shared/models/b-o-m';
import { Product } from 'src/app/shared/models/product';
import { RawMaterial } from 'src/app/shared/models/raw-material';
import { ArticleService } from 'src/app/shared/services/article.service';
import { DocumentService } from 'src/app/shared/services/document.service';
import { ProductService } from 'src/app/shared/services/product.service';
import { QuantityProductService } from 'src/app/shared/services/quantity-product.service';
import { RawMaterialService } from 'src/app/shared/services/raw-material.service';
import { BOMService } from 'src/app/shared/services/b-o-m.service';
import { AddOrEditFileComponent } from 'src/app/routes/document/add-or-edit-file/add-or-edit-file.component';

@Component({
  selector: 'app-add-or-edit-BOM',
  templateUrl: './add-or-edit-BOM.component.html',
  styleUrls: ['./add-or-edit-BOM.component.scss']
})
export class AddOrEditBOMComponent implements OnInit {

  ngbModalOptions: NgbModalOptions = {
    size: 'lg',
    backdrop: 'static',
    keyboard: false
  };

  nomenclature: BOM = new BOM();
  nomenclatureform: FormGroup;
  submitted: boolean;
  @BlockUI() blockUI: NgBlockUI;

  matierePremiere: RawMaterial = new RawMaterial();
  product: Product;

  documents: any[] = []
  document: any

  numProduct: any
  idProjet: any

  productlist: Product[] = [];
  productlist2: any[] = []
  matierePremiereList: any[] = [];

  Rows: Array<any> = []
  newRow: {}

  indice:any
  libelle: any;
  typeProduct: any;

  typeF = ManufacturingTypeEnum;
  keysF = Object.keys

  manufacturingProcesses = ["DEBITAGE",
    "FRAISAGE CONV",
    "TOURNAGE CONV",
    "FRAISAGE CNC",
    "TOURNAGE CNC",
    "DECOUPE LASER",
    "DECOUPE JET EAU",
    "ROBOFIL ROBOFORM",
    "PLIAGE",
    "SOUDAGE",
    "AJUSTAGE",
    "MOULAGE",
    "SABLAGE",
    "PEINTURE",
    "ANODISATION",
    "TRAITEMENT",
    "ASSEMBLAGE"]


  constructor(public activeModal: NgbActiveModal,
    private fb: FormBuilder,
    private toastr: ToastrService,
    private nomenclatureService: BOMService,
    private productService: ProductService,
    private articleService: ArticleService,
    private matierePremiereService: RawMaterialService,
    private quantityProductService: QuantityProductService,
    private documentService: DocumentService,
    private modalService: NgbModal) {

  }

  ngOnInit(): void {
    this.getProductlist()
    this.generateReference();
    this.getParent();
    this.getSousProduit();
    this.clearForm();
    this.getDocList();
  }

  clearForm() {
    this.nomenclatureform = this.fb.group({
      libelle: new FormControl(this.product ? this.product.libelle : '', [Validators.required]),
      designation: new FormControl(this.nomenclature ? this.nomenclature.designation : '', [Validators.maxLength(500)]),
      typeFabrication: new FormControl(this.nomenclature ? this.nomenclature.manufacturingType : '', [Validators.required]),
      procedesDeFabrication: new FormControl(this.nomenclature ? this.nomenclature.manufacturingProcesses : '', [Validators.required]),
      typeProduct: new FormControl(this.product ? this.product.typeProduct : ''),
      indice: new FormControl(''),
      col1: new FormControl(''),
      col2: new FormControl('', [Validators.maxLength(500)]),
      col3: new FormControl('', [Validators.maxLength(500)]),
      col4: new FormControl('', [Validators.maxLength(500)]),
      col5: new FormControl('', [Validators.min(0)])
    });
    this.submitted = false;
  }

  generateReference() {
    if (this.nomenclature.reference == null) {
      this.nomenclatureService.generateReference().subscribe((res: any) => {
        this.nomenclature.reference = res.reference
      })
    }
  }

  getParent() {
    if(this.idProjet == null){
      this.productService.getById(this.numProduct).subscribe((res: any) => {
        this.libelle = res.result.libelle;
        this.typeProduct = res.result.typeProduct
        if (this.typeProduct == "CONSTRUIRE" || this.typeProduct == "MATERIEL") {
          this.articleService.FindAllExecptUsed(this.numProduct).subscribe((res: any) => {
            this.productlist2 = res.result;
          })
        }
      })
    }
    if(this.nomenclature != null && this.idProjet != null){
      this.productService.getById(this.numProduct).subscribe((res:any)=>{
        this.libelle = res.result.libelle;
        this.typeProduct = res.result.typeProduct
      })
    }
  }

  getParentEvent(event){
    if(this.idProjet != null){
      this.productService.getByLibelle(this.libelle).subscribe((res:any)=>{
        this.numProduct = res.result.id
        this.libelle = res.result.libelle;
        this.typeProduct = res.result.typeProduct
        if (this.typeProduct == "CONSTRUIRE" || this.typeProduct == "MATERIEL") {
          this.articleService.FindAllExecptUsed(this.numProduct).subscribe((res: any) => {
            this.productlist2 = res.result;
          })
        }
      })
    }
  }

  getProductlist(){
    console.log("*////////////////")
    if(this.idProjet != null){
      
      this.productService.getAvailableByProject(this.idProjet).subscribe((res:any)=>{
        this.productlist = res.result
        console.log("*//////777777777//////////")
        console.log(this.productlist)
      })
    }
  }

  getProductList2(event) {
    if (event.value == "produit") {
      this.productService.FindAllExceptParent(this.numProduct).subscribe((res: any) => {
        this.productlist2 = res.result;
      })
    } else {
      this.articleService.FindAllExecptUsed(this.numProduct).subscribe((res: any) => {
        this.productlist2 = res.result;
      })
    }
  }

  getTheType2(newobej, row) {
    if (row.col1 == "produit") {
      this.productService.getByLibelle(newobej).subscribe((res: any) => {
        row.col3 = res.result.typeProduct
      })
    }
  }

  addRow() {
    this.Rows.push({ col0: '', col1: '', col2: '', col3: '', col4: '', col5: '' });
  }

  addUpdateProduct(row) {
    //add and update list of by-products
    this.productService.getByLibelle(row.col2).subscribe((res: any) => {
      var sousProduct = res.result
      if (sousProduct != null) {
        this.quantityProductService.getAll(this.numProduct).subscribe((res: any) => {
          //add
          if (res.result.length == null) {
            this.quantityProductService.add(this.numProduct, sousProduct.id, row.col5).subscribe()
            row.isSaved = true
          }
          //update
          else {
            const rowUpdated = res.result.find((d: any) => d.id == row.col0);
            if (rowUpdated != null) {
              this.quantityProductService.update(rowUpdated.id, sousProduct.id, row.col5).subscribe(() => {
                row.isSaved = true
              })
            } else {
              this.quantityProductService.add(this.numProduct, sousProduct.id, row.col5).subscribe()
              row.isSaved = true
            }
          }
        })
      }
    })

    //add and update list of raw material 
    this.articleService.getByLibelle(row.col2).subscribe((res: any) => {
      var article = res.result
      this.matierePremiereService.getAll(this.numProduct).subscribe((res: any) => {
        //add
        if (res.result.length == 0) {
          this.matierePremiereService.add(this.numProduct, article.id, row.col4, row.col5).subscribe(() => {
            row.isSaved = true
          })
        }
        //update
        else {
          const rowUpdated = res.result.find((d: any) => d.id == row.col0);
          if (rowUpdated != null) {
            this.matierePremiereService.update(this.numProduct, rowUpdated.id, row.col4, row.col5).subscribe(() => {
              row.isSaved = true
            })
          }//add
          else {
            this.matierePremiereService.add(this.numProduct, article.id, row.col4, row.col5).subscribe(() => {
              row.isSaved = true
            })
          }
        }
      })
    })

  }

  getSousProduit() {
    this.quantityProductService.getAll(this.numProduct).subscribe((res: any) => {
      for (let s of res.result) {
        this.Rows.push({ col0: s.id, col2: s.child.libelle, col3: s.child.typeProduct, col5: s.qteParAssemblage })
        for (let p of this.Rows) {
          p.isSaved = true
        }
      }
    })
    this.matierePremiereService.getAll(this.numProduct).subscribe((res: any) => {
      console.log(res.result)
      for (let s of res.result) {
        this.Rows.push({ col0: s.id, col2: s.article.libelle, col4: s.rawDimension, col5: s.grossQuantity })
        for (let p of this.Rows) {
          p.isSaved = true
        }
      }
    })
  }

  updateProduct(row) {
    row.isSaved = false
  }

  Annuler() {
    this.Rows.length = 0
    this.getSousProduit()
  }

  deleteProduct(row) {
    this.productService.getByLibelle(row.col2).subscribe((res: any) => {
      if (res.result != null) {
        this.quantityProductService.delete(this.numProduct, res.result.id).subscribe(() => {
          this.Rows.length = 0
          this.getSousProduit()
        })
      } else {
        this.articleService.getByLibelle(row.col2).subscribe((res: any) => {
          this.matierePremiereService.delete(this.numProduct, res.result.id).subscribe(() => {
            this.Rows.length = 0
            this.getSousProduit()
          })
        })
      }
    })
  }


  getDocList() {
    console.log("***********************")
    console.log(this.productlist)
    for(let p of this.productlist){
      this.documentService.findByProduct(p.id).subscribe((res: any) => {
        this.documents = res.result
        console.log(this.documents)
      })
    }
    if(this.nomenclature.indice != null){
      this.documentService.getOne(this.nomenclature.indice).subscribe((res:any)=>{
        this.indice = res.result.reference
        this.document =   `
        <strong> [${res.result.reference}] : ${res.result.name}</strong>
        <p class="mb-1">
        <i>Indice: ${res.result.indice}</i></p> `
      })
    }
  }

  addDoc(){
    const modalRef = this.modalService.open(AddOrEditFileComponent, this.ngbModalOptions);
    modalRef.componentInstance.numProduct = this.numProduct;
    modalRef.componentInstance.idProjet = this.idProjet;
    modalRef.result.then(() => {
      this.getDocList();
    });
  }

  getIndice(event){
    if (event == null){
      this.indice = null
    }
    else{
      this.indice = event.reference
    }    
  }

  onSubmit() {
    this.nomenclature.indice = this.indice
    this.submitted = true;
    if (!this.nomenclatureform.valid) {
      this.toastr.error($localize`Veuillez remplir correctement les champs`, $localize`Erreur`);
      return;
    }
    this.blockUI.start();
    this.nomenclatureService.add(this.numProduct, this.nomenclature)
      .subscribe((res: any) => {
        this.blockUI.stop();
        this.toastr.success($localize`Modification faite avec succès`, $localize`Succès`);
        this.nomenclature = null;
        this.activeModal.close(res.result);
      }, err => {
        this.blockUI.stop();
        console.log(err);
        this.toastr.error($localize`Une erreur s'est produite lors de l'enregistrement` + ': ' + err, $localize`Erreur`);
      });
  }
}
