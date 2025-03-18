import { Component, OnInit } from '@angular/core';
import { NgbModal, NgbModalOptions } from '@ng-bootstrap/ng-bootstrap';
import { ToastrService } from 'ngx-toastr';
import { AddOrEditComponent } from './add-or-edit/add-or-edit.component';
import { ActivatedRoute } from '@angular/router';
import { ConfirmDeleteComponent } from 'src/app/shared/component/confirm-delete/confirm-delete.component';
import * as XLSX from 'xlsx'
import { AuthenticationService } from 'src/app/shared/services/authentication.service';
import { takeUntil } from 'rxjs/operators';
import { Subject } from 'rxjs';
import { PlanningService } from 'src/app/shared/services/planning.service';
import jsPDF from 'jspdf';
import 'jspdf-autotable';
import { head } from 'lodash';


@Component({
  selector: 'app-pilotage',
  templateUrl: './pilotage.component.html',
  styleUrls: ['./pilotage.component.scss']
})
export class PilotageComponent implements OnInit {

  ngbModalOptions: NgbModalOptions = {
    size: 'xl',
    backdrop: 'static',
    keyboard: false
  };

  idProjet: any
  pilotageList: any[] = []
  pilotages: any[] = []
  quality: any
  performance: any
  diponibility: any
  TRS: any

  destroy$: Subject<boolean> = new Subject<boolean>();
  loading: boolean;
  totalElements: number;
  pageSize = 10;
  currentPage: number;
  lastPage: number;
  type: string;
  filter: string;

  ifEmpty: boolean;
  message = $localize`Pas de données à afficher`;


  constructor(private modalService: NgbModal,
    private pilotageService: PlanningService,
    private activateRoute: ActivatedRoute,
    private toastr: ToastrService,
    public AuthenticationService: AuthenticationService) { }

  ngOnInit(): void {
    this.getPage(0);
  }

  getPage(page: number) {
    this.idProjet = this.activateRoute.parent.snapshot.params.idProjet
    this.loading = true;
    if (this.idProjet != null) {
      this.pilotageService.findByPageAndProject(this.idProjet, page, this.pageSize).subscribe((res: any) => {
        if (res.content == 0) {
          this.ifEmpty = true
        }
        this.loading = false;
        this.currentPage = page;
        this.lastPage = res.totalPages - 1;
        this.totalElements = res.totalElements;
        this.pilotageList = res.content
        this.pilotages = res.content
        this.ifEmpty = false
      })
      this.pilotageService.TRSByProject(this.idProjet).subscribe((res: any) => {
        this.quality = res.result.map((d: any) => d.tauxQualite);
        this.performance = res.result.map((d: any) => d.tauxPerformance);
        this.diponibility = res.result.map((d: any) => d.tauxDisponibilite);
        this.TRS = res.result.map((d: any) => d.trs);
      })
    } else {
      this.pilotageService.findByPage(page, this.pageSize).pipe(takeUntil(this.destroy$))
        .subscribe((res: any) => {
          this.loading = false;
          this.currentPage = page;
          this.lastPage = res.totalPages - 1;
          this.totalElements = res.totalElements;
          this.pilotageList = res.content
          this.pilotages = res.content
          this.pilotageService.TRSByAllProjects().subscribe((res: any) => {
            this.quality = res.result.map((d: any) => d.tauxQualite);
            this.performance = res.result.map((d: any) => d.tauxPerformance);
            this.diponibility = res.result.map((d: any) => d.tauxDisponibilite);
            this.TRS = res.result.map((d: any) => d.trs);
          })
        }, err => {
          console.log(err);
        });
    }
  }



  onSearchChange(searchTerm: string) {
    searchTerm = searchTerm.toLowerCase();
    this.pilotageList = this.pilotages
    this.pilotageList = this.pilotageList.filter(item =>
      (item.equipment && item.equipment.toLowerCase().includes(searchTerm)) ||
      (item.phase && item.phase.toLowerCase().includes(searchTerm)) ||
      (item.tache.reference && item.tache.reference.toLowerCase().includes(searchTerm)) ||
      (item.tache.titre && item.tache.titre.toLowerCase().includes(searchTerm)) ||
      (item.tache.ofexterne && item.tache.ofexterne.toLowerCase().includes(searchTerm)) ||
      (item.projects.reference && item.projects.reference.toLowerCase().includes(searchTerm.toLowerCase())) ||
      (item.projects.titre && item.projects.titre.toLowerCase().includes(searchTerm.toLowerCase())) ||
      (item.tache.product.reference && item.tache.product.reference.toLowerCase().includes(searchTerm.toLowerCase())) ||
      (item.tache.product.libelle && item.tache.product.libelle.toLowerCase().includes(searchTerm.toLowerCase())) ||
      (item.employee.employeeNumber && item.employee.employeeNumber.toLowerCase().includes(searchTerm.toLowerCase())) ||
      (item.employee.firstName && item.employee.firstName.toLowerCase().includes(searchTerm.toLowerCase())) ||
      (item.employee.lastName && item.employee.lastName.toLowerCase().includes(searchTerm.toLowerCase())) ||
      (item.productionLigne.reference && item.productionLigne.reference.toLowerCase().includes(searchTerm.toLowerCase())) ||
      (item.productionLigne.name && item.productionLigne.name.toLowerCase().includes(searchTerm.toLowerCase()))
    )
  }

  public calculateTimeWorked(pilotage): number {
    const dateFin = new Date(pilotage.dateFin);
    const dateDebut = new Date(pilotage.dateDebut);
    const timeDifferenceMs = dateFin.getTime() - dateDebut.getTime();
    return timeDifferenceMs / (1000 * 60); // Convert to minutes
  }


  add() {
    const modalRef = this.modalService.open(AddOrEditComponent, this.ngbModalOptions);
    modalRef.componentInstance.idProjet = this.idProjet;
    modalRef.result.then(() => {
      this.getPage(0);
    });
  }

  update(pilotage) {
    const modalRef = this.modalService.open(AddOrEditComponent, this.ngbModalOptions);
    modalRef.componentInstance.pilotage = pilotage;
    modalRef.componentInstance.idProjet = this.idProjet;
    modalRef.result.then(() => {
      this.getPage(0);
    });
  }


  delete(pilotage: any) {
    const modalRef = this.modalService.open(ConfirmDeleteComponent);
    modalRef.result.then((res) => {
      if (res === true) {
        this.pilotageService.delete(pilotage.id, pilotage.employee.id).subscribe(
          () => {
            this.toastr.success($localize`Succès`, $localize`Supprimé avec succès`);
            this.pilotageList = null
            this.getPage(this.currentPage);
          },
          err => {
            console.log(err);
            this.toastr.error($localize`Erreur`, $localize`Une erreur s'est produite. Veuillez essayer de nouveau.`);
          });
      }
    });
  }

  Exporter() {

    const table1 = document.getElementById('table1-data') as HTMLTableElement;
    const table2 = document.getElementById('table2-data') as HTMLTableElement;

    // Initialiser jsPDF
    const doc = new jsPDF('p', 'mm', 'a4'); // Portrait, millimètres, A4

    const downloadDate = new Date().toLocaleDateString('en-GB', {
      day: '2-digit',
      month: '2-digit',
      year: 'numeric',
    });

    // Convertir l'en-tête HTML en données pour le PDF
    const headerRows = [
      [
        { content: 'Pointage de production', colSpan: 5,styles: { halign: 'left'} },
        { content: `${downloadDate}`, colSpan: 5, styles: { halign: 'right', fontSize: 10 } },
      ],
    ];

    (doc as any).autoTable({
      head: headerRows,
      startY: 10, // Start position for the header
      theme: 'plain', // Plain theme for a clean layout
      styles: {
        fontSize: 14,
        cellPadding: 2,
      },
      headStyles: {
        fillColor: [255, 255, 255], // White background
        textColor: [0, 0, 0], // Black text
        fontStyle: 'bold',
      },
      bodyStyles: {
        halign: 'center', // Center align the text
      },
    });


    // Ajouter un espace entre les tables
    const nextTableY1 = (doc as any).lastAutoTable.finalY + 10;

     // Récupérer les données de la table en excluant la première colonne
     const rows = Array.from(table1.rows).map(row =>
      Array.from(row.cells).slice(1).map(cell => cell.textContent || '') // Exclure la première colonne
    );

    
    // Ajouter la table au PDF avec des styles personnalisés
      (doc as any).autoTable({
      body: rows,
      startY: nextTableY1,  // Position de départ sur l'axe Y
      theme: 'grid', // Thème: 'striped', 'grid', 'plain'
      bodyStyles: { textColor: [0, 0, 0] }, // Couleur du texte des cellules
      alternateRowStyles: { fillColor: [240, 240, 240] }, // Couleur alternée des lignes
      margin: { top: 20 },
      styles: { fontSize: 9}, // Taille de police par défaut
    });


    // Ajouter un espace entre les tables
    const nextTableY = (doc as any).lastAutoTable.finalY + 10;


    // Ajouter la table au PDF avec des styles personnalisés
    (doc as any).autoTable({
      html: table2,
      startY: nextTableY,  // Position de départ sur l'axe Y
      theme: 'grid', // Thème: 'striped', 'grid', 'plain'
      headStyles: { fillColor: [22, 160, 133], fontSize: 9 }, // Couleur d'en-tête (vert)
      bodyStyles: { textColor: [0, 0, 0], fontSize: 9 }, // Couleur du texte des cellules
      alternateRowStyles: { fillColor: [240, 240, 240] }, // Couleur alternée des lignes
      margin: { top: 20 },
    });


    // Télécharger le fichier PDF
    doc.save('TableStyled.pdf');

  }
}
