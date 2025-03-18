import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class StoryQueue {

  private subjectParticipants = new Subject<any>();
  private subjectCommunities = new Subject<any>();
  private subjectPost = new Subject<any>();
  private subjectNotifications = new Subject<any>();

  // participants
  updateListParticipants(data: any) {
    setTimeout(() => {
      this.subjectParticipants.next(data);
    }, 500);
  }
  listParticipantsAsObservable(): Observable<any> {
    return this.subjectParticipants.asObservable();
  }

  // communities
  updateListCommunities(data: any) {
    setTimeout(() => {
      this.subjectCommunities.next(data);
    }, 500);
  }
  listCommunitiesAsObservable(): Observable<any> {
    return this.subjectCommunities.asObservable();
  }
  // post
  updateListPosts(data: any) {
    setTimeout(() => {
      this.subjectPost.next(data);
    }, 500);
  }
  listPostsAsObservable(): Observable<any> {
    return this.subjectPost.asObservable();
  }
  // notifications
  updateListNotifications(data: any) {
    setTimeout(() => {
      this.subjectNotifications.next(data);
    }, 500);
  }
  listNotificationsAsObservable(): Observable<any> {
    return this.subjectNotifications.asObservable();
  }
}
