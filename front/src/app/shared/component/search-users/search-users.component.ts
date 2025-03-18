import {Component, EventEmitter, Input, OnChanges, OnDestroy, OnInit, Output, SimpleChanges} from '@angular/core';
import {UserService} from '../../services/user.service';
import {findIndex} from 'lodash';

@Component({
  selector: 'app-search-users',
  templateUrl: './search-users.component.html',
  styleUrls: ['./search-users.component.scss']
})
export class SearchUsersComponent implements OnInit, OnDestroy, OnChanges {

  @Input() multiple = false;
  @Input() disabled = false;
  @Input() placeholder: string;
  @Input() user: any;
  @Input() selectedIds: number[];

  @Output() onSelectUser = new EventEmitter<any>();

  currentCompany: any;
  users: any[] = [];
  loading = false;

  constructor(private userService: UserService) { }

  ngOnInit() {
    this.currentCompany = JSON.parse(localStorage.getItem('company'));
    if (this.selectedIds) {
      this.loadListUsers();
    }
  }

  ngOnDestroy() { }

  ngOnChanges(changes: SimpleChanges) {}

  onChange(event) {
    this.onSelectUser.emit(event);
    this.user = event;
  }

  loadListUsers() {
    if (!this.users || this.users.length === 0) {
      this.loading = true;
      this.userService.findAllBasic()
        .subscribe(res => {
          this.loading = false;
          this.users = res;
          if (this.selectedIds && !this.multiple) {
            let i = findIndex(this.users, (u) => { return  this.selectedIds.includes(u.id); });
            this.user = this.users[i];
          }
        }, err => {
          this.loading = false;
          console.log(err);
        });
    }
  }

  customSearchFn(term: string, item: any) {
    term = term.toLowerCase();
    const fullName = item.employee.lastName + ' ' + item.employee.firstName;
    const lastName = item.employee.lastName;
    const firstName = item.employee.firstName;
    return (firstName && firstName.toLowerCase().indexOf(term) > -1 ) ||
      (lastName && lastName.toLowerCase().indexOf(term) > -1) ||
      (fullName && fullName.toLowerCase().indexOf(term) > -1);
  }
}
