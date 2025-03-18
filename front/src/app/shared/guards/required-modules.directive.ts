import {AfterViewInit, Directive, ElementRef, Input, OnInit, Renderer2} from '@angular/core';
import {CompanyService} from "../services/company.service";

@Directive({
  selector: '[requiredModules]'
})
export class RequiredModulesDirective implements OnInit, AfterViewInit {

  @Input('requiredModules') modules: any[];

  constructor(private el: ElementRef, private companyService: CompanyService, private renderer: Renderer2) {
  }

  ngAfterViewInit(): void {
  }

  ngOnInit() {
    if (!this.companyService.hasModules(this.modules)) {
      this.renderer.setStyle(this.el.nativeElement, 'display', 'none');
      const childElements = this.el.nativeElement.childNodes;
      for (let child of childElements) {
        this.renderer.removeChild(this.el.nativeElement, child);
      }
    }
  }

}
