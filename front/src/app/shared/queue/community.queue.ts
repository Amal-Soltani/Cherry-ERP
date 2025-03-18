import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class CommunityQueue {

  private subjectCreateCommunityRequest = new Subject<any>();
  private subjectCommunityIsCreated = new Subject<any>();

  // request create community
  createOrEditCommunity(data: any) {
    setTimeout(() => {
      this.subjectCreateCommunityRequest.next(data);
    }, 500);
  }
  createOrEditCommunityAsObservable(): Observable<any> {
    return this.subjectCreateCommunityRequest.asObservable();
  }
  // community is created
  communityIsCreated(data: any) {
    setTimeout(() => {
      this.subjectCommunityIsCreated.next(data);
    }, 500);
  }
  communityIsCreatedAsObservable(): Observable<any> {
    return this.subjectCommunityIsCreated.asObservable();
  }

}
