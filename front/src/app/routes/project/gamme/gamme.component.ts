import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { BlockUI, NgBlockUI } from 'ng-block-ui';
import { Article } from 'src/app/shared/models/article';
import { Phase } from 'src/app/shared/models/phase';
import { GammeService } from 'src/app/shared/services/gamme.service';
import { EquipmentService } from 'src/app/shared/services/equipment.service';
import { ArticleService } from 'src/app/shared/services/article.service';
import { PhaseService } from 'src/app/shared/services/phase.service';
import { Gamme } from 'src/app/shared/models/gamme';
import { Equipment } from 'src/app/shared/models/equipment';
import { ToastrService } from 'ngx-toastr';
import { GmPhaseService } from 'src/app/shared/services/gmPhase.service';
import { GmPhase } from 'src/app/shared/models/GmPhase';
import { Product } from 'src/app/shared/models/product';
import { TacheService } from 'src/app/shared/services/tache.service';
import { Tache } from 'src/app/shared/models/tache';
import { QuantityProductService } from 'src/app/shared/services/quantity-product.service';
import { AuthenticationService } from 'src/app/shared/services/authentication.service';

@Component({
  selector: 'app-gamme',
  templateUrl: './gamme.component.html',
  styleUrls: ['./gamme.component.scss']
})
export class GammeComponent implements OnInit {
  gammeform: FormGroup;
  submitted: boolean;
  @BlockUI() blockUI: NgBlockUI;

  Rows: Array<any> = []

  equipmentlist: Equipment[] = []
  gmPhaseList: GmPhase[] = []
  articlelist: Article[] = []
  phaselist: Phase[] = []
  produitlist: Product[] = []
  sousTacheList: Tache[]=[]

  tacheParent:any
  product: any
  idProjet:any
  gmPhase: GmPhase = new GmPhase()
  gamme: Gamme = new Gamme

  constructor(public activeModal: NgbActiveModal,
    private fb: FormBuilder,
    private toastr: ToastrService,
    private equipmentService: EquipmentService,
    private articleService: ArticleService,
    private phaseService: PhaseService,
    private gammeService: GammeService,
    private gmPhaseService: GmPhaseService,
    private quantityProductService: QuantityProductService,
    private tacheService: TacheService,
    public AuthenticationService: AuthenticationService) { }

  ngOnInit(): void {
    this.clearForm()
    this.getGamme()
    this.getSousTache()
    this.getPhaseList()
    this.getArticleList()
    this.getEquipmentList()
    this.getProduitlist()
    
  }

  clearForm() {
    this.gammeform = this.fb.group({
      phase: new FormControl('', [Validators.required]),
      temps: new FormControl(''),
      article: new FormControl(''),
      machine: new FormControl(''),
      responsables: new FormControl(''),
      productType: new FormControl(''),
      productName: new FormControl(''),
      produits: new FormControl(''),

    });
    this.submitted = false
  }


  getGamme() {
    this.gammeService.getByProduct(this.product.id).subscribe((res: any) => {
      if (res.result != null) {
        this.gamme = res.result
        this.getGmPhase()
      } else {
        this.gammeService.add(this.product.id).subscribe((res: any) => {
          this.gamme = res.result
        })
      }
    })
  }

  addRow() {
    this.Rows.push({ col1: Number, col2: '', col3: Number, col4: null, col5: null, col6: '' });
  }

  getGmPhase() {
    this.gmPhaseService.getAll(this.gamme.id).subscribe((res: any) => {
      for (let g of res.result) {
        this.Rows.push({ col1: g.id, col2: g.phase.name, col3: g.temps, col4: g.article, col5: g.produits, col6: g.equipment })
      }
      for (let row of this.Rows) {
        row.added = true
      }
    })
  }

  Annuler() {
    this.Rows.length = 0
    this.getGmPhase()
  }

  update(row) {
    row.added = false
  }

  delete(row) {
    console.log(row)
    this.gmPhaseService.delete(row.col1,this.gamme.id).subscribe(() => {
      this.Rows.length = 0
      this.getGmPhase();
    })

  }

  getEquipmentList() {
    this.equipmentService.findAll().subscribe((res: any) => {
      for (let e of res.result) {
        if (e.categoryEnum == "machine") {
          this.equipmentlist.push(e)
          console.log(this.equipmentlist)
        }
      }
    })
  }

  getArticleList() {
    this.articleService.getByProduct(this.product.id).subscribe((res: any) => {
      this.articlelist = res.result
    })
  }

  getProduitlist() {
    this.quantityProductService.getAll(this.product.id).subscribe((res: any) => {
      this.produitlist = res.result
    })
  }

  getSousTache(){
      this.tacheService.getSousTache(this.tacheParent.id,this.idProjet).subscribe((res:any)=>{
        this.sousTacheList = res.result
      })
  }

  getPhaseList() {
    this.phaseService.findAll().subscribe((res: any) => {
      this.phaselist = res.result
    })
  }

  Ajouter(row) {
    if (!this.gammeform.valid) {
      this.toastr.error($localize`Veuillez remplir correctement les champs`, $localize`Erreur`);
      return;
    }
    this.submitted = true;
    this.phaseService.getByName(row.col2).subscribe((res: any) => {
        this.gmPhase.id = row.col1
        this.gmPhase.phase = res.result
        this.gmPhase.temps = row.col3
        this.gmPhase.article = row.col4 
        this.gmPhase.produits = row.col5
        this.gmPhase.equipment = row.col6
      this.gmPhaseService.add(this.gmPhase, this.gamme.id).subscribe((res: any) => {
        row.added = true
      })
    })
  }

}
