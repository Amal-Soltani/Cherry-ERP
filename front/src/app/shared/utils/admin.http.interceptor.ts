import { Injectable } from '@angular/core';
import {HttpRequest, HttpHandler, HttpEvent, HttpInterceptor, HttpResponse} from '@angular/common/http';
import { Observable } from 'rxjs';
import {map} from 'rxjs/operators';
import {BlockUI, NgBlockUI} from 'ng-block-ui';

import $ from 'jquery';

@Injectable()
export class AdminHttpInterceptor implements HttpInterceptor {

  // Decorator wires up blockUI instance
  @BlockUI() blockUI: NgBlockUI;

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

      let token = localStorage.getItem('token');
      if (token) {
        request = request.clone({
          setHeaders: {
            Authorization: `Bearer ${token}`
          }
        });
      }
      if (request.url && request.url.indexOf('findMyLastNotifications') === -1) {
        $('#maskLoading').css('display', 'block');
      }
      return next.handle(request).pipe(
        map((event: HttpEvent<any>) => {
          if (event instanceof HttpResponse) {
            if (request.url && request.url.indexOf('findMyLastNotifications') === -1) {
              $('#maskLoading').css('display', 'none');
            }
          }
          return event;
        }));
    }
}
