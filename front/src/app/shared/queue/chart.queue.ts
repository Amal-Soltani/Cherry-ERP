import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class ChartQueue {

  private subject = new Subject<any>();

  private renderChartIsFinished = new Subject<any>();

  // data = { updateData : false, filter : {}}
  nextChartData(data: any) {
    setTimeout(() => {
      this.subject.next(data);
    }, 500);
  }

  getChartAsObservable(): Observable<any> {
    return this.subject.asObservable();
  }

  // renderChartIsFinished
  renderChartIsFinishedEvent(data: any) {
    setTimeout(() => {
      this.renderChartIsFinished.next(data);
    }, 500);
  }

  renderChartIsFinishedObservable(): Observable<any> {
    return this.renderChartIsFinished.asObservable();
  }
}
