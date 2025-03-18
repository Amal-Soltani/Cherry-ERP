import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
const CryptoJS = require('crypto-js');

@Injectable({
  providedIn: 'root'
})
export class ConfigService {

  static config: any = {
    rolesByComponent: {}
  };

  constructor(private http: HttpClient) { }

  loadConfig() {
    const configFile = `./assets/config/config`;
    return new Promise<void>((resolve, reject) => {
      this.http.get(configFile, {responseType: 'text'}).toPromise().then((response: any) => {
        const deConfigs= CryptoJS.AES.decrypt(decodeURIComponent(response), '4f0a4183-574a-4540-8055-9a7a7490de70');
        ConfigService.config = JSON.parse(deConfigs.toString(CryptoJS.enc.Utf8));
        console.log(ConfigService.config);
        resolve();
      }).catch(err => {
        reject(err);
      });
    });
  }
}
