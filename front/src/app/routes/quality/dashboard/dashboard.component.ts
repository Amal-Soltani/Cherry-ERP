import { Component, OnInit } from '@angular/core';
import * as Highcharts from 'highcharts';
import { FNCService } from 'src/app/shared/services/fnc.service';
import { FNCAnomaliesService } from 'src/app/shared/services/fncanomalies.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

  years: number[] = [];
  selectedYear: number = new Date().getFullYear();
  transactionType: string = 'month';

  Highcharts: typeof Highcharts = Highcharts;
  chartsData: { options: Highcharts.Options }[] = [];
  chartFNC: { options: Highcharts.Options } = { options: null };
  chartNC_rebut: { options: Highcharts.Options } = { options: null };
  chartCout: { options: Highcharts.Options } = { options: null };
  chartAnomaliesCateg: { options: Highcharts.Options } = { options: null };

  constructor(private FNCService: FNCService,
    private FNCAnomaliesService: FNCAnomaliesService) { }

  ngOnInit(): void {
    this.generateYearList()
    this.onYearChange(this.selectedYear)
    this.getChartGranularity(this.transactionType)
  }


  generateYearList() {
    const currentYear = new Date().getFullYear();
    this.FNCService.findTheFirstDate().subscribe((res: any) => {
      if (res.result.date) {
        const convertedDate = new Date(res.result.date).getFullYear();
        this.years = [];

        for (let year = currentYear; year >= convertedDate; year--) {
          this.years.push(year);
        }
      }
    })
  }


  onYearChange(year: number) {
    this.selectedYear = year
    this.getChartGranularity(this.transactionType)
    this.getChartAnomaliesCateg(this.selectedYear)
  }


  getChartGranularity(e) {
    if (e == 'week') {
      this.FNCAnomaliesService.GetFNCCountByYearAndWeek(this.selectedYear).subscribe((res: any) => {
        const weeks = res.result.map((d: any) => d.week);
        const fncCounts = res.result.map((d: any) => d.fncCount);
        const nc = res.result.map((d: any) => d.nc);
        const rebut = res.result.map((d: any) => d.rebut);
        const cout = res.result.map((d: any) => d.cout);
        let weekList: String[] = [];
        weekList = weeks.map((week: number) => `Week ${week}`)

        console.log (weeks)
        this.getChartFNC(weekList, fncCounts)
        this.getChartNC_rebut(weekList, nc, rebut)
        this.getChartCout(weekList, cout)

      })

    } else if (e == 'month') {
      this.FNCAnomaliesService.GetFNCCountByYearAndMonth(this.selectedYear).subscribe((res: any) => {
        const months = res.result.map((d: any) => d.month);
        const fncCounts = res.result.map((d: any) => d.fncCount);
        const nc = res.result.map((d: any) => d.nc);
        const rebut = res.result.map((d: any) => d.rebut);
        const cout = res.result.map((d: any) => d.cout);
        let monthList: String[] = [];
        monthList = months.map((value: number) => this.getMonthName(value))

        this.getChartFNC(monthList, fncCounts)
        this.getChartNC_rebut(monthList, nc, rebut)
        this.getChartCout(monthList, cout)
      });
    }
  }

  getMonthName(month: number): string {
    const monthNames = ["Jan", "Feb", "Mar", "Apr", "May", "Jun",
      "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];
    return monthNames[month - 1];
  }

  getChartFNC(data: any[], fncCounts: any[]) {
    this.chartFNC.options = {
      chart: {
        type: 'line',
        zooming: {
          type: 'xy'
        }
      },
      title: {
        text: ` Nombre de FNC en ${this.selectedYear}`,
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
        categories: data
      },
      yAxis: {
        title: {
          text: 'FNC'
        }
      },
      series: [
        {
          name: 'Nombre FNC',
          data: fncCounts,
        } as any
      ],
      navigation: {
        buttonOptions: {
          enabled: true,
        }
      }
    }
  }


  getChartNC_rebut(data: any[], nc: any[], rebut: any[]) {
    this.chartNC_rebut.options = {
      chart: {
        type: 'column',
        zooming: {
          type: 'xy'
        }
      },
      title: {
        text: `Quantité NC/Rébut en ${this.selectedYear} `,
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
        categories: data
      },
      yAxis: {
        title: {
          text: 'NC / Rébut'
        }
      },
      series: [
        {
          name: 'QT.NC',
          data: nc
        },
        {
          name: 'QT.Rébut',
          data: rebut
        } as any
      ],
      navigation: {
        buttonOptions: {
          enabled: true,
        }
      }
    }
  }

  getChartCout(data: any[], cout: any[]) {
    this.chartCout.options = {
      chart: {
        type: 'column',
        zooming: {
          type: 'xy'
        }
      },
      title: {
        text: `Coût de NQ en ${this.selectedYear}`,
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
        categories: data
      },
      yAxis: {
        title: {
          text: 'Coût NC'
        }
      },
      series: [
        {
          name: 'Coût NC',
          data: cout
        } as any
      ],
      navigation: {
        buttonOptions: {
          enabled: true,
        }
      }
    }
  }

  getChartAnomaliesCateg(year: number) {

    this.FNCService.findFNCPercentageByYear(year).subscribe((res: any) => {
      const chartData = res.result.map((d: any) => ({
        name: d.categorie,
        y: d.percentage,
        count: d.fncCount,
        totalCount: d.totalFNC
      }));

      this.chartAnomaliesCateg.options = {
        chart: {
          type: 'pie'
        },
        title: {
          text: `% FNC par catégorie en ${this.selectedYear}`,
          style: {
            color: '#23b7e5',
            fontSize: '13',
            fontWeight: 'normal'
          },
          align: 'left'
        },
        tooltip: {
          pointFormat: 'Nombre: <b>{point.count} / {point.totalCount}</b>',
          shared: true,
        },
        credits: {
          enabled: false
        },
        plotOptions: {
          pie: {
            allowPointSelect: true,
            cursor: 'pointer',
            dataLabels: {
              enabled: true,
              style: {
                fontSize: '1.2em',
                textOutline: 'none',
                opacity: 0.7
              },
              distance: 20, // Valid distance for pie chart
              format: '{point.percentage:.1f}%',
              filter: {
                operator: '>',
                property: 'percentage',
                value: 10
              }
            }
          }
        },
        series: [
          {
            name: 'Percentage',
            colorByPoint: true,
            data: chartData
          }
        ] as any

      }
    })

  }


}




