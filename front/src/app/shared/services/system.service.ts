import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import { ConfigService } from 'src/app/core/services/config.service';

@Injectable()
export class SystemService {

  apiUrl = ConfigService.config.APIEndpoint + '/systeminfo';

  constructor(private http: HttpClient) { }

  findSystemInfo(): Observable<any> {
    return this.http.get<any>(this.apiUrl);
  }

}
