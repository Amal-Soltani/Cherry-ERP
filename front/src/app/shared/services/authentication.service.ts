
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { User } from '../models/user';
import { map } from 'rxjs/operators';
import * as JWT from 'jwt-decode';
import { Observable } from 'rxjs';
import { ConfigService } from 'src/app/core/services/config.service';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable()
export class AuthenticationService {

  apiUrl = ConfigService.config.APIEndpoint + '/users';
  loginUrl = ConfigService.config.APIEndpoint + '/login/authenticate';

  constructor(private http: HttpClient) { }

  login(username: string, password: string, longitude: number, latitude: number) {
    localStorage.removeItem('token');
    localStorage.removeItem('currentUser');
    localStorage.removeItem('loggedAs');
    localStorage.removeItem('tokenExp');
    localStorage.removeItem('members');
    return this.http.post<any>(this.loginUrl,
      { username, password, longitude, latitude })
      .pipe(
        map(res => {
          // login successful if there's a jwt token in the response
          // use JWT() for decode. Not jwt-decode() !!
          let t: any;
          t = JWT(res.token);
          localStorage.setItem('token', res.token);
          localStorage.setItem('currentUser', JSON.stringify(res.user));
          localStorage.setItem('loggedAs', res.loggedAs);
          localStorage.setItem('tokenExp', t.exp);

          return t;
        })
      );
  }

  forgotpassword(email: string): Observable<any> {
    const params = new HttpParams()
      .set('email', String(email));
    const url = `${this.apiUrl}/forgotpassword`;
    return this.http.get<any>(url, { params });
  }

  resetpassword(token: string, password: string): Observable<any> {
    const body = { token: String(token), password: String(password) };
    const url = `${this.apiUrl}/resetpassword`;
    return this.http.post<any>(url, body);
  }

  logout() {
    const currentUser = JSON.parse(localStorage.getItem('currentUser'));
    ///users/logout/id
    if (currentUser) {
      const params = new HttpParams();
      const url = `${this.apiUrl}/logout`;
      this.http.get<any>(url, { params }).subscribe(data => {
        console.log('Logout saved');
      }, err => {
      });
    }

    // remove user from local storage to log user out
    localStorage.removeItem('token');
    localStorage.removeItem('currentUser');
    localStorage.removeItem('loggedAs');
    localStorage.removeItem('tokenExp');
    localStorage.removeItem('company');
    localStorage.removeItem('members');
    // reset theme to default
    //this.themesService.setTheme(this.themesService.defaultTheme);
  }

  hasPermission(authArr: string[]) {
    const currentUser: User = JSON.parse(localStorage.getItem('currentUser'));
    if (currentUser.roles.includes('EXT')) {
      return authArr.includes('EXT');
    } else {
      for (const j in authArr) {
        if (currentUser.roles.includes(authArr[j]) || authArr[j] === '*') {
          return true;
        }
      }
    }
    return false;
  }

  isAthenticated() {
    const token = localStorage.getItem('token');
    const tokenExp = localStorage.getItem('tokenExp');
    if (token && new Date().getTime() < Number(tokenExp + '000')) {
      return true;
    }
    return false;
  }

}
