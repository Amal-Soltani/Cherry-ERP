import { Component, OnInit } from '@angular/core';
import {AuthGuard} from "../shared/guards/auth.guard";
import {MessagingFirebaseService} from "../shared/services/messaging-firebase.service";

@Component({
  selector: 'app-layout',
  templateUrl: './layout.component.html',
  styleUrls: ['./layout.component.scss']
})
export class LayoutComponent implements OnInit {

  constructor(private authGuard: AuthGuard, private messagingService: MessagingFirebaseService) { }

  ngOnInit() {
    //Listener for push notifications token
    const tokenChannel = new BroadcastChannel('token-channel');
    // tokenChannel.onmessage
    tokenChannel.addEventListener('message', event => {
        setTimeout(() => {
          let pushToken = localStorage.getItem('push-token');
          if (pushToken) {
            this.sendTokenToServer(pushToken);
          }
        }, 100);
    });

    let pushToken = localStorage.getItem('push-token');
    if (pushToken && !this.messagingService.isTokenSended) {
      this.sendTokenToServer(pushToken);
    }
    //Listener for push notifications
    const notifChannel = new BroadcastChannel('push-notif');
    notifChannel.addEventListener('message', event => {
      console.log("new notif", event);
    });
  }

  sendTokenToServer(pushToken) {
    this.messagingService.postTokenToBackend(pushToken).subscribe(
      (data: any) => {
        console.log("token sended successfully");
        this.messagingService.isTokenSended = true;
      },
      err => {
        console.error("error when sending token", err);
      }
    );
  }

}
