import { Component, OnInit } from '@angular/core';
import { NgbModal, NgbModalOptions } from '@ng-bootstrap/ng-bootstrap';
import { ToastrService } from 'ngx-toastr';
import { Subject, takeUntil } from 'rxjs';
import { ArticleService } from 'src/app/shared/services/article.service';
import { AddOrEditArticleComponent } from './add-or-edit-article/add-or-edit-article.component';
import { ConfirmDeleteComponent } from 'src/app/shared/component/confirm-delete/confirm-delete.component';
import { AuthenticationService } from 'src/app/shared/services/authentication.service';

@Component({
  selector: 'app-article',
  templateUrl: './article.component.html',
  styleUrls: ['./article.component.scss']
})
export class ArticleComponent implements OnInit {

    ngbModalOptions: NgbModalOptions = {
      size: 'lg-60',
      backdrop: 'static',
      keyboard: false
    };
    destroy$: Subject<boolean> = new Subject<boolean>();
    loading: boolean;
    totalElements: number;
    pageSize = 10;
    currentPage: number;
    lastPage: number;
    type: string;
    filter: string;
  
    articleList: any[]=[]
    articles: any[]=[]
    info : boolean
    article:any
    roles: any;

  constructor(private modalService: NgbModal,
    private articleService: ArticleService,
    private toastr: ToastrService,
    public AuthenticationService: AuthenticationService) { }

    ngOnInit(): void {
      this.getPage(0);
    }

    getPage(page: number) {
      this.loading = true;
      this.articleService.findByPage(page, this.pageSize).pipe(takeUntil(this.destroy$))
        .subscribe((res: any) => {
          this.loading = false;
          this.articleList = res.content;
          this.articles = res.content;
          this.currentPage = page;
          this.lastPage = res.totalPages - 1;
          this.totalElements = res.totalElements;
        }, err => {
          console.log(err);
        });
    }

    onSearchChange(searchTerm: string) {
      searchTerm = searchTerm.toLowerCase();
      this.articleList = this.articles
      this.articleList = this.articleList.filter(item =>
        (item.reference && item.reference.toLowerCase().includes(searchTerm)) ||
        (item.name && item.name.toLowerCase().includes(searchTerm) )||
        (item.matiere && item.matiere.toLowerCase().includes(searchTerm)) ||
        (item.libelle && item.libelle.toLowerCase().includes(searchTerm)) ||
        (item.uniteMesure && item.uniteMesure.includes(searchTerm))
      )
    }
  
    add() {
      const modalRef = this.modalService.open(AddOrEditArticleComponent, this.ngbModalOptions);
      modalRef.result.then(() => {
        this.getPage(0);
      });
    }
  
    update(article: any) {
      const modalRef = this.modalService.open(AddOrEditArticleComponent, this.ngbModalOptions);
      modalRef.componentInstance.article = article;
      modalRef.result.then(() => {
        this.getPage(this.currentPage);
      });
    }

    
    delete(article) {
      const modalRef = this.modalService.open(ConfirmDeleteComponent);
      modalRef.result.then((res) => {
        if (res === true) {
          this.articleService.delete(article.id).subscribe(() => {
            this.toastr.success($localize`Succès`, $localize`Supprimé avec succès`);
            this.getPage(this.currentPage);
          }, err => {
            console.log(err);
            this.toastr.error($localize`Erreur`, $localize`Une erreur s'est produite. Veuillez essayer de nouveau.`);
          }
          )
        }
      });
    }

    detail(article: any) {
      if (this.info == false) {
        this.info = true
        this.article = article
      } else {
        this.info = false
      }
    }
  
    ngOnDestroy(): void {
      this.destroy$.next(true);
      // Unsubscribe from the subject
      this.destroy$.unsubscribe();
    }

}
