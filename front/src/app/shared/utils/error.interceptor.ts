import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {catchError} from 'rxjs/operators';
import { ErrorDialogService } from '../services/error-dialog.service';
import { Injectable } from '@angular/core';
import { RestMsgPipe } from '../pipes/rest-msg.pipe';

import $ from 'jquery';
import {ToastrService} from 'ngx-toastr';

@Injectable()
export class HttpErrorInterceptor implements HttpInterceptor {

  errorMessage = 'unknown-error';

  constructor(private errorDialogService: ErrorDialogService, private restMsgPipe: RestMsgPipe, private toastr: ToastrService) { }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(request).pipe(catchError(err => {
      if (err.status === 401) {
        // auto logout if 401 response returned from api
        //this.authenticationService.logout();
        //location.reload(true);
        localStorage.removeItem('token');
        localStorage.removeItem('currentUser');
        localStorage.removeItem('tokenExp');
        localStorage.removeItem('ip');
      }

      if (err.error instanceof ErrorEvent) {
          // client-side error
          this.errorMessage = err.error.message;
      } else {
          // server-side error
          this.errorMessage = (err.error && err.error.error) || (err.error && err.error.errors) || (err.error && err.error.message) || err.statusText || '';
          //window.alert(`Error Code: ${err.status}\nMessage: ${this.errorMessage}`);
      }
      console.log(this.errorMessage);
      $('#maskLoading').css('display', 'none');
      const data = this.restMsgPipe.transform(this.errorMessage);
      if (data) {
        //this.toastr.error($localize `Erreur`, data);
      }
      return throwError(this.restMsgPipe.transform(this.errorMessage));
    }));
  }

  /*intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>>{
    return next.handle(request)
      .pipe(
        catchError( (error: HttpErrorResponse) => {
          let errMsg = '';
          // Client Side Error
          if (error.error instanceof ErrorEvent) {
            errMsg = `Error: ${error.error.message}`;
          }
          else {  // Server Side Error
            errMsg = `Error Code: ${error.status},  Message: ${error.message}`;
          }
          return throwError(errMsg);
        })
      );
  }*/
}
