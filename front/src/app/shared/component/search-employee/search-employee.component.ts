import {Component, EventEmitter, Input, OnChanges, OnDestroy, OnInit, Output, SimpleChanges} from '@angular/core';
import {EmployeeService} from '../../services/employee.service';
import {Employee} from '../../models/employee';

import {filter} from 'lodash';

@Component({
  selector: 'app-search-employee',
  templateUrl: './search-employee.component.html',
  styleUrls: ['./search-employee.component.scss']
})
export class SearchEmployeeComponent implements OnInit, OnDestroy, OnChanges {

  @Input() multiple = false;
  @Input() disabled = false;
  @Input() placeholder: string;
  @Input() employee: any;
  @Input() selectedIds: number[];
  @Input() external = false;

  @Output() onSelectEmployee = new EventEmitter<any>();

  employees: Employee[] = [];
  loading = false;

  constructor(private employeeService: EmployeeService) { }

  ngOnInit() {
    this.loadListEmployees();
  }

  ngOnDestroy() { }

  ngOnChanges(changes: SimpleChanges) {}

  onChange(event) {
    this.onSelectEmployee.emit(event);
    this.employee = event;
  }

  loadListEmployees() {
    if (!this.employees || this.employees.length === 0) {
      this.loading = true;
      this.employeeService.findAll()
        .subscribe(res => {
          this.loading = false;
          this.employees = res.result;
          //this.employees = this.employees.filter(employee => (!employee.external && !this.external) || (employee.external === this.external));
          if (this.selectedIds) {
            const arr =  filter(this.employees, (o) => { return this.selectedIds.includes(o.id); });
            this.employee = arr[0];
          }
        }, err => {
          this.loading = false;
          console.log(err);
        });
    }
  }

  customSearchFn(term: string, item: any) {
    term = term.toLowerCase();
    return (item.firstName && item.firstName.toLowerCase().indexOf(term) > -1 ) ||
      (item.lastName && item.lastName.toLowerCase().indexOf(term) > -1 ||
      (item.employeeNumber && item.employeeNumber.toLowerCase().indexOf(term) > -1));
  }
}
