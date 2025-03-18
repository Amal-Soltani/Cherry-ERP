import { Component, OnInit } from '@angular/core';
import { ConfirmDeleteComponent } from 'src/app/shared/component/confirm-delete/confirm-delete.component';
import { AddOrEditEquipmentComponent } from '../add-or-edit-equipment/add-or-edit-equipment.component';
import { NgbModal, NgbModalOptions } from '@ng-bootstrap/ng-bootstrap';
import { EquipmentService } from 'src/app/shared/services/equipment.service';
import { ToastrService } from 'ngx-toastr';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { AuthenticationService } from 'src/app/shared/services/authentication.service';

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.scss']
})
export class ListComponent implements OnInit {

  ngbModalOptions: NgbModalOptions = {
    size: 'lg',
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

  ifEmpty : boolean;
  message = $localize `Pas de données à afficher`;

  equipmentList: any[] = [];
  equipments: any[] = [];

  constructor(private modalService: NgbModal,
    private equipmentService: EquipmentService,
    private toastr: ToastrService,
    public AuthenticationService: AuthenticationService) { }

  ngOnInit(): void {
    this.getPage(0);
  }

  getPage(page: number) {
    this.loading = true;
    this.equipmentService.findByPage(page, this.pageSize).pipe(takeUntil(this.destroy$))
      .subscribe((res: any) => {
        if (res.content == 0 ){
          this.ifEmpty = true
        }
        this.loading = false;
        this.equipmentList = res.content;
        this.equipments = res.content;
        this.currentPage = page;
        this.lastPage = res.totalPages - 1;
        this.totalElements = res.totalElements;
        this.ifEmpty = false
      }, err => {
        console.log(err);
      });
  }

  onSearchChange(searchTerm: string) {
    searchTerm = searchTerm.toLowerCase();
    this.equipmentList = this.equipments
    this.equipmentList = this.equipmentList.filter(item =>
      (item.reference && item.reference.toLowerCase().includes(searchTerm)) ||
      (item.name && item.name.toLowerCase().includes(searchTerm)) ||
      (item.emplacement && item.emplacement.toLowerCase().includes(searchTerm))
    )
  }

  add() {
    const modalRef = this.modalService.open(AddOrEditEquipmentComponent, this.ngbModalOptions);
    modalRef.result.then(() => {
      this.getPage(0);
    });
  }

  update(equipment: any) {
    const modalRef = this.modalService.open(AddOrEditEquipmentComponent, this.ngbModalOptions);
    modalRef.componentInstance.equipment = equipment;
    modalRef.result.then(() => {
      this.getPage(0);
    });
  }
  delete(equipment) {
    const modalRef = this.modalService.open(ConfirmDeleteComponent);
    modalRef.result.then((res) => {
      if (res === true) {
        this.equipmentService.delete(equipment.id).subscribe(() => {
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

  ngOnDestroy(): void {
    this.destroy$.next(true);
    // Unsubscribe from the subject
    this.destroy$.unsubscribe();
  }
}
