import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { BlockUI, NgBlockUI } from 'ng-block-ui';
import { ToastrService } from 'ngx-toastr';
import { Article, UniteMesureEnum } from 'src/app/shared/models/article';
import { ArticleService } from 'src/app/shared/services/article.service';

@Component({
  selector: 'app-add-or-edit-article',
  templateUrl: './add-or-edit-article.component.html',
  styleUrls: ['./add-or-edit-article.component.scss']
})
export class AddOrEditArticleComponent implements OnInit {

  article: Article = new Article();
  articleform: FormGroup;
  submitted: boolean;
  @BlockUI() blockUI: NgBlockUI;

  UM = UniteMesureEnum
  keysUM = Object.keys

  constructor(private articleService: ArticleService,
    public activeModal: NgbActiveModal,
    private toastr: ToastrService,
    private fb: FormBuilder) { }

    ngOnInit(): void {
      this.generateReference();
      this.clearForm();
    }

    clearForm() {
      this.articleform = this.fb.group({
        reference: new FormControl(this.article ? this.article.reference : '', [Validators.required]),
        designation: new FormControl(this.article ? this.article.designation : '', [Validators.maxLength(500)]),
        uniteMesure: new FormControl(this.article ? this.article.uniteMesure : '', [Validators.required]),
        matiere: new FormControl(this.article ? this.article.matiere : '', [Validators.required]),
        name:new FormControl(this.article ? this.article.name : '', [Validators.required]),
        couleur: new FormControl(this.article ? this.article.couleur : '',[Validators.maxLength(20)]),
        taille:new FormControl(this.article ? this.article.taille : '', [Validators.min(0)]),
        longueur:new FormControl(this.article ? this.article.longueur : '', [Validators.min(0)]),
        largeur:new FormControl(this.article ? this.article.largeur : '', [Validators.min(0)]),
        hauteur:new FormControl(this.article ? this.article.hauteur : '', [Validators.min(0)]),
      });
      this.submitted = false;
    }


    generateReference(){
      if(this.article.reference == null){
        this.articleService.generateReference().subscribe((res:any)=>{
          this.article.reference = res.reference
        })
      }
    }

    onSubmit() {
      this.submitted = true;
      if (!this.articleform.valid) {
        this.toastr.error($localize`Veuillez remplir correctement les champs`, $localize`Erreur`);
        return;
      }
      this.blockUI.start();
      if (this.article.id == null){
        this.articleService.add(this.article).subscribe((res: any) => {
          this.toastr.success($localize`Enregistrement fait avec succès`, $localize`Succès`);
          this.activeModal.close(res.result);
          this.blockUI.stop();
        }, err => {
          console.log(err);
          this.blockUI.stop();
          this.toastr.error($localize`Une erreur s'est produite lors de l'enregistrement` + ': ' + err, $localize`Erreur`);
        });
      }else{
        this.articleService.add(this.article)
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
