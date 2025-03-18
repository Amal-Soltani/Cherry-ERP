import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {Page} from '../models/page';
import { ConfigService } from 'src/app/core/services/config.service';


@Injectable()
export class AppService {

  apiUrl = ConfigService.config.APIEndpoint + '/build-properties';

  constructor(private http: HttpClient) {}

  version(): Observable<any> {
    const url = `${this.apiUrl}/version`;
    return this.http.get<any>(url);
  }

}
