import { ActivatedRoute } from '@angular/router';
import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { EmployeeService } from '../../../shared/services/employee.service';
import { ConfirmDeleteComponent } from '../../../shared/component/confirm-delete/confirm-delete.component';
import { Subject } from 'rxjs';
import { NgbModal, NgbModalOptions } from '@ng-bootstrap/ng-bootstrap';
import { takeUntil } from 'rxjs/operators';

@Component({
  selector: 'app-employee',
  templateUrl: './employee.component.html',
  styleUrls: ['./employee.component.scss']
})
export class EmployeeComponent implements OnInit, OnDestroy {

  @Input() external = false;

  ngbModalOptions: NgbModalOptions = {
    size: 'lg',
    backdrop : 'static',
    keyboard : false
  };
  loading: boolean;
  totalElements: number;
  pageSize = 10;
  currentPage: number;
  lastPage: number;
  filter: string;
  employees = [];

  destroy$: Subject<boolean> = new Subject<boolean>();

  constructor(private modalService: NgbModal,
    private toastr: ToastrService,
    private employeeService: EmployeeService,
    private route: ActivatedRoute) { }

  ngOnInit() {
    this.external = this.route.snapshot.routeConfig.path === 'external';
    this.getPage(0);
  }

  getPage(page: number) {
    this.loading = true;
    this.employeeService.findByPage(page, this.pageSize).pipe(takeUntil(this.destroy$))
      .subscribe((res: any) => {
        this.loading = false;
        this.employees = res.content.filter(employee => (!employee.external && !this.external) || (employee.external === this.external));
        this.currentPage = page;
        this.lastPage = res.totalPages - 1;
        this.totalElements = res.totalElements;
      }, err => {
        console.log(err);
      });
  }



  onChangePageSize(pageSize) {
    this.pageSize = pageSize;
    this.getPage(0);
  }

  delete(employee: any) {
    const modalRef = this.modalService.open(ConfirmDeleteComponent);
    modalRef.result.then((res) => {
      if (res === true) {
        this.employeeService.delete(employee.id).subscribe(
          () => {
            this.toastr.success($localize `Succès`, $localize `Supprimé avec succès`);
            this.getPage(this.currentPage);
          },
          err => {
            console.log(err);
            this.toastr.error($localize `Erreur`, $localize`Une erreur s'est produite. Veuillez essayer de nouveau.`);
          });
      }
    });
  }

  ngOnDestroy(): void {
    this.destroy$.next(true);
    this.destroy$.unsubscribe();
  }

}
