import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { NgbModal, NgbModalOptions } from '@ng-bootstrap/ng-bootstrap';
import { AddUserComponent } from './add-user/add-user.component';
import { takeUntil } from 'rxjs/operators';
import { Subject } from 'rxjs';
import { ConfirmDeleteComponent } from '../../../shared/component/confirm-delete/confirm-delete.component';
import {UserService} from '../../../shared/services/user.service';
import {User} from '../../../shared/models';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.scss']
})
export class UsersComponent implements OnInit, OnDestroy {

  ngbModalOptions: NgbModalOptions = {
    size: 'lg-80',
    backdrop : 'static',
    keyboard : false
  };
  @Input() users: User[] = [];
  submitted: boolean;

  loading: boolean;
  totalElements: number;
  pageSize = 10;
  currentPage: number;
  lastPage: number;
  filter: string;
  destroy$: Subject<boolean> = new Subject<boolean>();

  constructor(private modalService: NgbModal,
    private toastr: ToastrService,
    private userService: UserService) { }

  ngOnInit() {
    this.getPage(0);
  }

  getPage(page: number) {
    this.loading = true;
    this.userService.findByPage(page, this.pageSize).pipe(takeUntil(this.destroy$))
      .subscribe((res: any) => {
        this.loading = false;
        this.users = res.content;
        this.currentPage = page;
        this.lastPage = res.totalPages - 1;
        this.totalElements = res.totalElements;
      }, err => {
        console.log(err);
      });
  }

  invite(external: boolean) {

  }

  setEnable(event, user: User) {
    this.userService.setEnable(user.id, user.enabled).pipe(takeUntil(this.destroy$))
      .subscribe(res => {
        this.toastr.success($localize `Modification faite avec succès`, $localize `Succès`);
      }, err => {
        console.log(err);
        user.enabled = !user.enabled;
        this.toastr.error( $localize `Erreur`, $localize `Une erreur s'est produite. Veuillez essayer de nouveau.` + ': ' + err);
      });
  }

  add() {
    const modalRef = this.modalService.open(AddUserComponent, this.ngbModalOptions);
    modalRef.result.then((res) => {
      if (res) {
        this.getPage(0);
      }
    });
  }

  update(user: any) {
    const modalRef = this.modalService.open(AddUserComponent, this.ngbModalOptions);
    modalRef.componentInstance.user = user;
    modalRef.result.then((res) => {
      this.getPage(0);
    });
  }

  onChangePageSize(pageSize) {
    this.pageSize = pageSize;
    this.getPage(0);
  }

  delete(user: any) {
    const modalRef = this.modalService.open(ConfirmDeleteComponent);
    modalRef.result.then((res) => {
      if (res === true) {
        this.userService.delete(user.id).pipe(takeUntil(this.destroy$)).subscribe(
          () => {
            this.toastr.success($localize `Succès`, $localize `Supprimé avec succès`);
            this.getPage(this.currentPage);
          },
          err => {
            console.log(err);
            this.toastr.error($localize `Erreur`, $localize `Une erreur s'est produite. Veuillez essayer de nouveau.`);
          });
      }
    });
  }

  ngOnDestroy(): void {
    this.destroy$.next(true);
    this.destroy$.unsubscribe();
  }

}
