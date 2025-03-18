import { HttpClient, HttpEvent, HttpEventType } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { BlockUI, NgBlockUI } from 'ng-block-ui';
import { ToastrService } from 'ngx-toastr';
import { Document } from 'src/app/shared/models/document';
import { DocumentService } from 'src/app/shared/services/document.service';



@Component({
  selector: 'app-add-or-edit-file',
  templateUrl: './add-or-edit-file.component.html',
  styleUrls: ['./add-or-edit-file.component.scss']
})
export class AddOrEditFileComponent implements OnInit {

  fileform: FormGroup;
  submitted: boolean;
  @BlockUI() blockUI: NgBlockUI;

  file: File;

  filenames: string[] = []
  fileStatus = { status: '', requestType: '', percent: 0 }

  documentList: any[] = []
  document: Document = new Document

  numProduct: any
  idProjet: any
  doc: any

  //indice :any

  constructor(public activeModal: NgbActiveModal,
    private fb: FormBuilder,
    private documentService: DocumentService,
    private toastr: ToastrService,) { }

  ngOnInit(): void {
    this.clearForm();
    if (this.doc != null) {
      this.documentList.push(this.doc)
    }
  }

  clearForm() {
    this.fileform = this.fb.group({
      indice: new FormControl(this.document ? this.document.indice : '', [Validators.maxLength(500)]),
      name: new FormControl(this.document ? this.document.name : '', [Validators.maxLength(500)]),
      size: new FormControl(this.document ? this.document.size : '', [Validators.maxLength(500)])
    });
  }

  onFileSelected(event) {
    for (this.file of event.target.files) {
      this.document.name = this.file.name
      this.document.size = this.file.size
      this.document.type = this.file.type
      this.document.data = this.file
      this.document.indice = null
      this.documentList.push(this.document)
    }
  }

  save(document) {
    if (!this.fileform.valid) {
      this.toastr.error($localize`Veuillez remplir correctement les champs`, $localize`Erreur`);
      return;
    }
    this.submitted = true;
    this.documentService.save(document.id, document.indice).subscribe((res: any) => {
      this.toastr.success($localize`Enregistrement fait avec succès`, $localize`Succès`);
      this.activeModal.close(res.result);
      this.blockUI.stop();
    }, err => {
      console.log(err);
      this.blockUI.stop();
      this.toastr.error($localize`Une erreur s'est produite lors de l'enregistrement` + ': ' + err, $localize`Erreur`);
    })
  }

  uploadFile(document) {
    this.document = document
    const formData = new FormData();
    formData.append('file', this.file);
    this.documentService.upload(this.numProduct, document.indice, formData).subscribe(res => {
      this.resportProgress(res)
    });
  }

  resportProgress(event: HttpEvent<string[]>): void {
    switch (event.type) {
      case HttpEventType.UploadProgress:
        this.updateStatus(event.loaded, event.total)
        break
      case HttpEventType.ResponseHeader:
        console.log('return : ', event)
        break
      case HttpEventType.Response:
        console.log('return : ', event)
        if (event.body instanceof Array) {
          for (const filename of event.body) {
            this.filenames.unshift(filename)
          }
        }
        break
      default:
        console.log(event)
    }
  }

  updateStatus(loaded: number, total: number) {
    this.fileStatus.status = 'progress'
    this.fileStatus.percent = Math.round(100 * loaded / total)
  }
}
