import { Component, OnInit } from '@angular/core';
import { GmPhaseService } from 'src/app/shared/services/gmPhase.service';
import { TacheService } from 'src/app/shared/services/tache.service';
import * as Highcharts from 'highcharts';
import { Subject, takeUntil } from 'rxjs';
import { PlanningService } from 'src/app/shared/services/planning.service';
import { EmployeeService } from 'src/app/shared/services/employee.service';


@Component({
  selector: 'app-of',
  templateUrl: './of.component.html',
  styleUrls: ['./of.component.scss']
})
export class OFComponent implements OnInit {
  clients: any[] = []
  client: any
  cl: any
  responsible: any

  taches: any[] = []
  tache: any

  pointages: any[] = []

  listePhase: any[] = []
  total: any[] = []
  liste: any[] = []

  gmphase: any[] = []
  pilotages: any[] = []
  Highcharts: typeof Highcharts = Highcharts;
  chartsData: { options: Highcharts.Options }[] = [];
  barChart1: { options: Highcharts.Options } = { options: null };
  barChart2: { options: Highcharts.Options } = { options: null };

  destroy$: Subject<boolean> = new Subject<boolean>();
  loading: boolean;
  totalElements: number;
  pageSize = 10;
  currentPage: number;
  lastPage: number;
  type: string;
  filter: string;


  constructor(
    private clientService: EmployeeService,
    private tacheService: TacheService,
    private pilotageService: PlanningService,
    private gmPhaseService: GmPhaseService
  ) { }

  ngOnInit(): void {
    this.getClientsList()
    this.getTachesList(this.client)
  }

  getClientsList() {
    this.clientService.getClient().subscribe((res: any) => {
      this.clients = res.result
    })
  }

  getTachesList(client) {
    this.clientService.findAll().subscribe((res:any)=>{
      const resp = res.result.find((r: any) => r.id == client.managerId);
      if(resp != null){
        this.responsible = resp
      }
    })
    this.cl = client
    if (client != null || client != this.client) {
      this.tacheService.getByClient(client.id).subscribe((res: any) => {
        this.taches = res.result
        this.tache = null

      })
    } else {
      this.tacheService.getAll().subscribe((res: any) => {
        this.taches = res.result
        this.getClient(this.tache)
      })
    }
    this.getPointagesList(this.tache)
  }

  getPage(page: number) {
    this.loading = true;
    this.pilotageService.findByPageAndTache(this.tache.id, page, this.pageSize).pipe(takeUntil(this.destroy$))
      .subscribe((res: any) => {
        this.loading = false;
        this.pointages = res.content;
        this.currentPage = page;
        this.lastPage = res.totalPages - 1;
        this.totalElements = res.totalElements;
      }, err => {
        console.log(err);
      });
  }

  getPointagesList(tache) {
    this.tache = tache
    this.getPage(0)
  }

  getClient(tache) {
    this.clientService.getClientByTache(tache.id).subscribe((res: any) => {
      this.client = "[" + res.result.employeeNumber + "]" + " : " + res.result.firstName + " " + res.result.lastName
      this.cl = res.result
    })
  }

  getListePhase(tache) {
    this.pilotageService.StaticOF(tache.id).subscribe((res: any) => {
      this.listePhase = res.result.map((d: any) => d.phase);
      const qteDemande = res.result.map((d: any) => d.quantite);
      const qtetotal = res.result.map((d: any) => d.qteTotaleSum);
      const qteNC = res.result.map((d: any) => d.qtencSum);
      const qteRebut = res.result.map((d: any) => d.qteRebutSum);
      const TReel = res.result.map((d: any) => d.tempsReel);
      const TEstime = res.result.map((d: any) => d.tempsEstime);

      this.barChart1.options = {
        chart: {
          type: 'column',
          zooming: {
            type: 'xy'
          }
        },
        title: {
          text: `Quantité par gamme`,
          style: {
            color: '#23b7e5',
            fontSize: '13',
            fontWeight: 'normal'
          },
          align: 'left'
        },
        tooltip: {
          shared: true,
        },
        credits: {
          enabled: false
        },
        xAxis: {
          categories: this.listePhase
        }
        ,
        yAxis: {
          title: {
            text: 'QT'
          }
        },
        series: [
          {
            name: 'QT.Produite',
            data: qtetotal
          },
          {
            name: 'QT.NC',
            data: qteNC
          },
          {
            name: 'QT.Rébut',
            data: qteRebut
          },
          {
            name: 'QT.Demandée',
            type: 'line',
            dashStyle: 'shortdash',
            data: qteDemande,
          } as any
        ],
        navigation: {
          buttonOptions: {
            enabled: true,
          }
        }
      }
      this.barChart2.options = {
        chart: {
          type: 'column',
          zooming: {
            type: 'xy'
          }
        },
        title: {
          text: `Temps de production par gamme (Heure)`,
          style: {
            color: '#23b7e5',
            fontSize: '13',
            fontWeight: 'normal'
          },
          align: 'left'
        },
        tooltip: {
          shared: true,
        },
        credits: {
          enabled: false
        },
        xAxis: {
          categories: this.listePhase
        }
        ,
        yAxis: {
          title: {
            text: 'Temps (H)'
          }
        },
        series: [
          {
            name: 'T.Estimé',
            data: TReel,
            color: '#00e272',
          },
          {
            name: 'T.Réel',
            data: TEstime,
            color: '#544fc5',
          } as any
        ],
        navigation: {
          buttonOptions: {
            enabled: true,
          }
        }
      }
    })

    this.pilotageService.QteProduiteGammeEmp(tache.id).subscribe((res: any) => {

      this.liste = res.result.reduce((acc, curr) => {
        // If the phase doesn't exist, create an array for it
        if (!acc[curr.phase]) {
          acc[curr.phase] = [];
        }
        // Push the current employee into the correct phase array
        acc[curr.phase].push(curr);
        return acc;
      }, {} as { [key: string]: any[] });
    })
  }

  public calculateTimeWorked(pilotage): number {
    const dateFin = new Date(pilotage.dateFin);
    const dateDebut = new Date(pilotage.dateDebut);
    const timeDifferenceMs = dateFin.getTime() - dateDebut.getTime();
    return timeDifferenceMs / (1000 * 60); // Convert to minutes
  }

  public getTheorique(pilotage): number {
    var plt = this.pilotages.find(plt => plt.id === pilotage.id)
    if (plt === undefined) {
      this.pilotages.push(pilotage)
      this.gmPhaseService.getAll(pilotage.tache.product.gamme.id).subscribe((res: any) => {
        for (let r of res.result) {
          var gamme = this.gmphase.find(g => g.id === r.id)
          if (gamme === undefined) {
            this.gmphase.push(r)
          }
        }
      })
    }
    for (let gm of this.gmphase) {
      while (gm.phase.name === pilotage.phase && gm.gamme.id === pilotage.tache.product.gamme.id) {
        return gm.temps
      }
    }
  }

}
