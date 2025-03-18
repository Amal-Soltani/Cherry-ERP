import { Component, OnInit } from '@angular/core';
import { CanAcessDirective } from 'src/app/shared/guards/can-access.Directive';

@Component({
  selector: 'app-quality',
  templateUrl: './quality.component.html',
  styleUrls: ['./quality.component.scss']
})
export class QualityComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

}
