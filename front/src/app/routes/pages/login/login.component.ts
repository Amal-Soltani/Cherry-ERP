import {Component, HostListener, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {first} from 'rxjs/operators';
import {AuthenticationService} from '../../../shared/services/authentication.service';
import {SettingsService} from '../../../core/settings/settings.service';
import {CompanyService} from '../../../shared/services/company.service';;
@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['../pages.component.scss']
})
export class LoginComponent implements OnInit {

  settings: any;
  loginForm: FormGroup;
  submitted = false;
  loading = false;
  returnUrl: string;
  errorMsg: any;
  error = '';
  waitingValidation: any;
  validAccount: any;

  longitude: any;
  latitude: any;

  isVisible: boolean = false;

  constructor(private formBuilder: FormBuilder,
              private authenticationService: AuthenticationService,
              settingsService: SettingsService,
              private companyService: CompanyService,
              private route: ActivatedRoute) {
         this.settings = settingsService.getAppSetting(null);
  }

  ngOnInit() {
    this.loginForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
    // get return url from route parameters or default to '/'
    this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
    this.waitingValidation = this.route.snapshot.queryParams['waitingValidation'];
    this.validAccount = this.route.snapshot.queryParams['validAccount'];
    this.error = this.route.snapshot.queryParams['error'];

    this.getPosition().then(pos =>  {
      console.log(`Positon: ${pos.lng} ${pos.lat}`);
      this.longitude = pos.lng;
      this.latitude = pos.lat;
    });
  }
  // convenience getter for easy access to form fields

  get f() { return this.loginForm.controls; }

  onSubmitLogin() {
    this.submitted = true;
    this.errorMsg = '';
    // stop here if form is invalid
    if (this.loginForm.invalid) {
      return;
    }
    this.loading = true;
    this.authenticationService.login(this.f.username.value, this.f.password.value, this.longitude, this.latitude)
      .pipe(first())
      .subscribe(
        data => {
          this.findCurrentCompany();
          this.epicFunction('LOGIN');
        },
        err => {
          this.loading = false;
          if (err) {
            this.errorMsg = err;
          } else {
            this.errorMsg = 'Error Authentication';
          }
        });
  }

  getPosition(): Promise<any> {
    return new Promise((resolve, reject) => {

      navigator.geolocation.getCurrentPosition(resp => {

          resolve({lng: resp.coords.longitude, lat: resp.coords.latitude});
        },
        err => {
          reject(err);
        });
    });
  }

  epicFunction(action: string) {

  }

  findCurrentCompany() {
    this.companyService.getMyCompany()
      .subscribe(res => {
        let company = res.result;
        localStorage.setItem('company', JSON.stringify(company));
        if (company.language) {
          this.returnUrl = '/' + company.language + this.returnUrl;
        } else {
          this.returnUrl = '/fr' + this.returnUrl;
        }
        console.log(this.returnUrl);
        window.location.assign(this.returnUrl);
        this.loading = false;
      }, err => {
        console.log(err);
      });
  }

}
