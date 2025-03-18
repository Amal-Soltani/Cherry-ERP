import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Page } from '../models/page';


const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})

export class PlanningService {

  readonly apiUrl = '/cherry-hse/planning';

  constructor(private http: HttpClient) { }

  findByPage(p: number, size: number): Observable<any> {
    const params = new HttpParams()
      .set('page', String(p)).set('pageSize', String(size));
    return this.http.get<Page>(`${this.apiUrl}/all`, { params });
  }

  findByPageAndProject(projectId: number,p: number, size: number): Observable<any> {
    const params = new HttpParams()
      .set('projectId', String(projectId)).set('page', String(p)).set('pageSize', String(size));
    return this.http.get<Page>(`${this.apiUrl}/allByProject`, { params });
  }

  
  findByPageAndTache(tacheId: number,p: number, size: number): Observable<any> {
    const params = new HttpParams()
      .set('tacheId', String(tacheId)).set('page', String(p)).set('pageSize', String(size));
    return this.http.get<Page>(`${this.apiUrl}/allByTache`, { params });
  }

  add(pilotage: any) {
    return this.http.post(`${this.apiUrl}`, pilotage, httpOptions)
  }

  affecter(pilotage: any, projectId: any) {
    return this.http.put(`${this.apiUrl}/${projectId}`, pilotage, httpOptions)
  }

  updateDate(id: any, dateStart: any, dateEnd: any) {
    return this.http.put(`${this.apiUrl}/${id}/${dateStart}/${dateEnd}`, httpOptions)
  }

  getAll() {
    return this.http.get(`${this.apiUrl}/findAll`, httpOptions);
  }

  getByProject(projectId: any) {
    return this.http.get(`${this.apiUrl}/${projectId}`, httpOptions);
  }

  getById(id: any) {
    return this.http.get(`${this.apiUrl}/byId/${id}`, httpOptions);
  }

  getByIdAndTache(id, refOF) {
    return this.http.get(`${this.apiUrl}/byTache/${id}/${refOF}`, httpOptions);
  }

  getByTache(of: any) {
    return this.http.get(`${this.apiUrl}/byOF/${of}`, httpOptions);
  }


  delete(id: any, employeeId: any) {
    return this.http.delete(`${this.apiUrl}/${id}/${employeeId}`, httpOptions);
  }

  StaticOF(id: number): Observable<any> {
    const params = new HttpParams().set('id', id.toString());
    return this.http.get(`${this.apiUrl}/StaticOF/${id}`, { params });
  }

  QteProduiteGammeEmp(id: number): Observable<any> {
    const params = new HttpParams().set('id', id.toString());
    return this.http.get(`${this.apiUrl}/QteProduiteGammeEmp/${id}`, { params });
  }


  //Par phase

  TRSByPhaseProject(id: number): Observable<any> {
    const params = new HttpParams().set('id', id.toString());
    return this.http.get(`${this.apiUrl}/TRSByPhaseProject/${id}`, { params });
  }

  TRSByPhase(): Observable<any> {
    return this.http.get(`${this.apiUrl}/TRSByPhase`);
  }


  //Par employé

  TRSByEmpProject(id: number): Observable<any> {
    const params = new HttpParams().set('id', id.toString());
    return this.http.get(`${this.apiUrl}/TRSByEmpProject/${id}`, { params });
  }

  TRSByEmp(): Observable<any> {
    return this.http.get(`${this.apiUrl}/TRSByEmp`);
  }

  //Par equipement

  TRSByEquipProject(id: number): Observable<any> {
    const params = new HttpParams().set('id', id.toString());
    return this.http.get(`${this.apiUrl}/TRSByEquipProject/${id}`, { params });
  }

  TRSByEquip(): Observable<any> {
    return this.http.get(`${this.apiUrl}/TRSByEquip`);
  }

  //Par ligne de production

  TRSByLigneProject(id: number): Observable<any> {
    const params = new HttpParams().set('id', id.toString());
    return this.http.get(`${this.apiUrl}/TRSByLigneProject/${id}`, { params });
  }

  TRSByLigne(): Observable<any> {
    return this.http.get(`${this.apiUrl}/TRSByLigne`);
  }


  TRSByProject(id: number): Observable<any> {
    const params = new HttpParams().set('id', id.toString());
    return this.http.get(`${this.apiUrl}/TRSByProject/${id}`, { params });
  }

  TRSByAllProjects(): Observable<any> {
    return this.http.get(`${this.apiUrl}/TRSByAllProjects`);
  }









}