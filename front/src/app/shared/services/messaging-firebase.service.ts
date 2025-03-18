import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AngularFireMessaging } from '@angular/fire/compat/messaging';
import { environment } from 'src/environments/environment';

const apiUrl = '/cherry-hse/push-notification-token';

@Injectable()
export class MessagingFirebaseService {

  public isTokenSended = false;

  constructor(private http: HttpClient, private angularFireMessaging: AngularFireMessaging) {
  }

  requestPermission() {
    console.log('request permession');
    this.angularFireMessaging.requestToken.subscribe(
      (token) => {
        localStorage.setItem('push-token', token);
        this.isTokenSended = false;
        console.log("token",token);
      });
    this.angularFireMessaging.tokenChanges.subscribe(
      (tokenChanged) => {
        localStorage.setItem('push-token', tokenChanged);
        this.isTokenSended = false;
        const tokenChannel = new BroadcastChannel('token-channel');
        tokenChannel.postMessage("new token");
      }
    );
  }

  postTokenToBackend(pushToken) {
    return this.http.post(apiUrl, pushToken);
  }

  receiveMessage() {
    this.angularFireMessaging.messages.subscribe(
      (msg: any) => {
        const notifChannel = new BroadcastChannel('push-notif');
        notifChannel.postMessage(msg);
        console.log(msg);
        navigator.vibrate([1000, 1000, 500]); console.log("vibrate");
      });
  }
}
