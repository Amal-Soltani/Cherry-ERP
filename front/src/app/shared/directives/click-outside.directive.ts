import {Directive, ElementRef, EventEmitter, HostListener, Output} from '@angular/core';

@Directive({
  selector: '[spiClickOutside]'
})
export class ClickOutsideDirective {
@Output()
public spiClickOutside = new EventEmitter();
private isClickedOutside = false;
  constructor(private elementRef: ElementRef) { }
  @HostListener('document:click', ['$event.target'])
  public onClick(targetElement) {
    const event = this.elementRef.nativeElement.contains(targetElement);
    if (targetElement && !event) {
      if (this.isClickedOutside) {
        this.spiClickOutside.emit(event);
      } else {
        this.isClickedOutside = true;
      }
    } else {
      this.isClickedOutside = false;
    }
  }
}
