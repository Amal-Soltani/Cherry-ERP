import { Component, Input, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ToastrService } from 'ngx-toastr';


@Component({
  selector: 'app-confirm-delete',
  templateUrl: './confirm-delete.component.html',
  styleUrls: ['./confirm-delete.component.scss']
})
export class ConfirmDeleteComponent implements OnInit {

  @Input() securedmode: boolean;

  securedModeResponse: string;

  constructor(public activeModal: NgbActiveModal,
              private toastr: ToastrService) { }

  ngOnInit(): void {

  }

  confirmDelete(): void {
    if (this.securedmode) {
      if (this.securedModeResponse === 'YES') {
        this.securedModeResponse = null;
        this.activeModal.close(true);
      }
      else {
        this.toastr.error($localize `Erreur`, $localize `Vous devez saisir YES pour valider la suppression`);
      }
    }
    else {
      this.securedModeResponse = null;
      this.activeModal.close(true);
    }
  }

}
