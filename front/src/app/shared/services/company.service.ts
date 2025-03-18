import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Company} from '../models/company';
import {Observable} from "rxjs";
import {Page} from "../models/page";
import {User} from "../models";
import { ConfigService } from 'src/app/core/services/config.service';


const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable()
export class CompanyService {

  apiUrl = ConfigService.config.APIEndpoint + '/companies';

  constructor(private http: HttpClient) {}

  ///current-company
  getMyCompany() {
    const url = `${this.apiUrl}/current-company`;
    return this.http.get<any>(url);
  }

  getById(id: number) {
    const url = `${this.apiUrl}/${id}`;
    return this.http.get<any>(url);
  }

  update(company: Company) {
    const url = `${this.apiUrl}`;
    return this.http.put(url, company, httpOptions);
  }

  delete(id: number) {
    const url = `${this.apiUrl}/${id}`;
    return this.http.delete<Company>(url, httpOptions);
  }

  create(company: any) {
    return this.http.post<any>(this.apiUrl, company, httpOptions);
  }

  findByPage(p: number, size: number): Observable<any> {
    const params = new HttpParams()
      .set('page', String(p)).set('pageSize', String(size));
    return this.http.get<Page>(this.apiUrl, {params});
  }

  loginAsAdminCompany(id: number): Observable<any> {
    const params = new HttpParams()
      .set('companyId', String(id));
    return this.http.get<any>(this.apiUrl + '/loginAsAdminCompany', {params});
  }

  register(registerCompanyDto: any) {
    const url = `${this.apiUrl}/register`;
    return this.http.post<any>(url, registerCompanyDto, httpOptions);
  }

  updateLicense(licenseId: any, license: any) {
    const url = `${this.apiUrl}/${licenseId}/update-license`;
    return this.http.post<any>(url, license, httpOptions);
  }

  hasModules(modules: any[]) {
    const token = localStorage.getItem('token');
    if (token) {
      const currentCompany: Company = JSON.parse(localStorage.getItem('company'));
      for (const j in modules) {
        if (currentCompany.license && currentCompany.license.modules.includes(modules[j]) || modules[j] === '*') {
          return true;
        }
      }
    }
    return false;
  }
}
