import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {User} from '../models/user';
import {Observable} from 'rxjs';
import {Page} from '../models/page';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};
const apiUrl = '/cherry-hse/users';

const apiSpidashUrl = '/cherry-hse/spidash';

@Injectable()
export class UserService {

  constructor(private http: HttpClient) { }

  findByPage(p: number, size: number): Observable<any> {
    const params = new HttpParams()
      .set('page', String(p)).set('pageSize', String(size));
    const url = `${apiUrl}`;
    return this.http.get<Page>(url, {params});
  }

  findBasicByPage(): Observable<any> {
    const params = new HttpParams();
    const url = `${apiUrl}/basic`;
    return this.http.get<Page>(url, {params});
  }

  search(keyword: string): Observable<any> {
    const params = new HttpParams();
    const url = `${apiUrl}/search/` + keyword;
    return this.http.get<any>(url,{params});
  }

  setEnable(id, activate) {
    const url = `${apiUrl}/enable/${id}?` + 'enable=' + activate;
    return this.http.post(url, httpOptions);
  }

  getById(id: number) {
    const url = `${apiUrl}/${id}`;
    return this.http.get<any>(url);
  }

  create(user: User) {
    return this.http.post<User>(apiUrl, user, httpOptions);
  }

  update(id, user: User) {
    const url = `${apiUrl}/${id}`;
    return this.http.put(url, user, httpOptions);
  }

  delete(id: number) {
    const url = `${apiUrl}/${id}`;
    return this.http.delete<User>(url, httpOptions);
  }

  invite(invitationRequest: any) {
    const url = `${apiUrl}/invite`;
    return this.http.post<any>(url, invitationRequest, httpOptions);
  }

  acceptInvitation(acceptInvitationRequest: any) {
    const url = `${apiUrl}/accept-invitation`;
    return this.http.post<any>(url, acceptInvitationRequest, httpOptions);
  }

  findAllByCompany(companyId: any, p: number, size: number) {
    const url = `${apiUrl}/all-by-company/${companyId}`;
    const params = new HttpParams()
      .set('page', String(p)).set('pageSize', String(size));
    return this.http.get<any>(url, {params});
  }

  findByIds(ids: number[]): Observable<any> {
    const params = new HttpParams()
      .set('ids', String(ids));
    const url = `${apiUrl}/findByIds`;
    return this.http.get<any>(url,{params});
  }


  findAllBasic(): Observable<any> {
    const params = new HttpParams();
    const url = `${apiUrl}/all-basic`;
    return this.http.get<any>(url,{params});
  }


}
