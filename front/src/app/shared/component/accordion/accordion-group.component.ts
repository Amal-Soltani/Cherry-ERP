import { ChangeDetectionStrategy, Component, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'spi-group-accordion',
  template: `
      <div class="mypanel">
          <div class="title" (click)="toggleFn()">
              {{title}}
          </div>
          <div class="body" [ngClass]="{'hidden': !opened}">
              <ng-content></ng-content>
          </div>
          <div>
  `,
  styleUrls: ['accordion.component.css'],
  // changeDetection: ChangeDetectionStrategy.OnPush
})
export class AccordionGroupComponent {

  /**
   * If the panel is opened or closed
   */
  @Input() opened = false;

  /**
   * Text to display in the group title bar
   */
  @Input() title: string;

  /**
   * Emitted when user clicks on group titlebar
   * @type {EventEmitter<any>}
   */
  @Output() toggle: EventEmitter<any> = new EventEmitter<any>();

  toggleFn() {
    console.log(this.opened);
    this.toggle.emit();
  }
}
