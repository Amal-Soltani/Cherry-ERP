import {Component, EventEmitter, Input, OnChanges, OnDestroy, OnInit, Output, SimpleChanges} from '@angular/core';
import {findIndex} from 'lodash';
import {EquipmentService} from '../../services/equipment.service';

@Component({
  selector: 'app-search-equipment',
  templateUrl: './search-equipment.component.html',
  styleUrls: ['./search-equipment.component.scss']
})
export class SearchEquipmentComponent implements OnInit, OnDestroy, OnChanges {

  @Input() loadListOnInit = false;
  @Input() multiple = false;
  @Input() disabled = false;
  @Input() placeholder: string;
  @Input() equipment: any;
  @Input() selectedIds: number[];

  @Output() onSelectEquipment = new EventEmitter<any>();
  @Output() onFinishLoadList = new EventEmitter<any>();

  equipments: any[] = [];
  loading = false;

  constructor(private equipmentService: EquipmentService) { }

  ngOnInit() {
    if (this.loadListOnInit) {
      this.loadListEquipments();
    }
  }

  ngOnDestroy() { }

  onChange(event) {
    this.onSelectEquipment.emit(event);
    this.equipment = event;
  }

  loadListEquipments() {
    if (!this.equipments || this.equipments.length === 0) {
      this.loading = true;
      this.equipmentService.findAll()
        .subscribe(res => {
          this.loading = false;
          this.equipments = res.result;
          this.selectDefaultValue();
          this.onFinishLoadList.emit(this.equipments);
        }, err => {
          this.loading = false;
          console.log(err);
        });
    }
  }

  selectDefaultValue() {
    if (this.selectedIds) {
      const i = findIndex(this.equipments, (u) => { return  this.selectedIds.includes(u.id); });
      this.equipment = this.equipments[i];
    }
  }

  customSearchFn(term: string, item: any) {
    term = term.toLowerCase();
    return (item.reference && item.reference.toLowerCase().indexOf(term) > -1 ) ||
      (item.name && item.name.toLowerCase().indexOf(term) > -1 );
  }


  ngOnChanges(changes: SimpleChanges) {
    if (changes['selectedIds']) {
      this.selectDefaultValue();
    }
  }
}
