import { Injectable } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ErrorDialogComponent } from '../component/error-dialog/error-dialog.component';
import { ToastrService } from 'ngx-toastr';

@Injectable()
export class ErrorDialogService {

    constructor(private modalService: NgbModal, private toastr: ToastrService) { }

    openDialog(data): void {
      //const modalRef = this.modalService.open(ErrorDialogComponent);
      //modalRef.componentInstance.errorMessage = data;
      this.toastr.error($localize `Erreur`, data);
    }

}
