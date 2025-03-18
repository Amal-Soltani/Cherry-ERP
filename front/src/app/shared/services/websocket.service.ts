import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import { Subject, Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { ConfigService } from 'src/app/core/services/config.service';

@Injectable()
export class WebSocketService {
    webSocketEndPoint: string = ConfigService.config.urlWebsocket;
    topic: string = "/topic/new-notification";
    stompClient: any;
    message = new Subject<any>();

    constructor() { }

    _connect() {
        console.log("Initialize WebSocket Connection");
        let ws = new SockJS(this.webSocketEndPoint);
        this.stompClient = Stomp.over(ws);
        this.stompClient.connect({}, (frame) => {
            this.stompClient.subscribe(this.topic, (sdkEvent) => {
                this.onMessageReceived(sdkEvent);
            });
            //this.stompClient.reconnect_delay = 2000;
        }, this.errorCallBack);
    };

    _disconnect() {
        if (this.stompClient !== null) {
            this.stompClient.disconnect();
        }
        console.log("Disconnected");
    }

    // on error, schedule a reconnection attempt
    errorCallBack(error) {
        console.log("errorCallBack -> " + error)
        setTimeout(() => {
            this._connect();
        }, 5000);
    }

    /**
     * Send message to sever via web socket
     * @param {*} message
     */
    _send(message) {
        console.log("Sending message via web socket");
        this.stompClient.send("/app/create-notification", {}, JSON.stringify(message));
    }

    onMessageReceived(message) {
        // solutin provisiore jusqu'à la mise en place d'une solution coté backend pour envoyer les messages par usager et par role
        let currentUser = JSON.parse(localStorage.getItem('currentUser'));
        let messageBody = JSON.parse(message.body);
        if ((messageBody.userId == null &&  messageBody.role == null) ||
            (messageBody.userId && messageBody.userId == currentUser.id) ||
            (messageBody.role && currentUser.roles.includes(messageBody.role))) {
            this.message.next(message);
        }
    }

    getNewMessage(): Observable<boolean> {
        return this.message.asObservable();
    }
}
