import { Directive, ElementRef, OnInit , Input, Renderer2, AfterViewInit } from '@angular/core';
import {AuthenticationService} from '../services/authentication.service';

@Directive({
  selector: '[canAcess]'
})
export class CanAcessDirective implements OnInit, AfterViewInit {

  @Input('canAcess') permission: any[]; // Required permission passed in

  constructor(private el: ElementRef, private authenticationService: AuthenticationService, private renderer: Renderer2) { }

  ngAfterViewInit(): void {
  }

  ngOnInit() {
    if (!this.authenticationService.hasPermission(this.permission)) {
      this.renderer.setStyle(this.el.nativeElement, 'display', 'none');
      const childElements = this.el.nativeElement.childNodes;
      for (let child of childElements) {
        this.renderer.removeChild(this.el.nativeElement, child);
      }
    }
  }

}
