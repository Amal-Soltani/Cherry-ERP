import { Component, OnInit } from '@angular/core';
import { ChangeDetectorRef } from '@angular/core';
import { CalendarOptions, DateSelectArg, EventClickArg, EventApi, EventInput } from '@fullcalendar/core';
import interactionPlugin from '@fullcalendar/interaction';
import dayGridPlugin from '@fullcalendar/daygrid';
import timeGridPlugin from '@fullcalendar/timegrid';
import listPlugin from '@fullcalendar/list';
import bootstrapPlugin from '@fullcalendar/bootstrap';
import { NgbModal, NgbModalOptions } from '@ng-bootstrap/ng-bootstrap';
import { AddOrEditComponent } from 'src/app/routes/project/pilotage/add-or-edit/add-or-edit.component';
import frLocale from '@fullcalendar/core/locales/fr';
import { AuthenticationService } from 'src/app/shared/services/authentication.service';
import { Planning } from 'src/app/shared/models/planning';
import { PlanningService } from 'src/app/shared/services/planning.service';


@Component({
  selector: 'app-planning',
  templateUrl: './planning.component.html',
  styleUrls: ['./planning.component.scss']
})
export class PlanningComponent implements OnInit {

  ngbModalOptions: NgbModalOptions = {
    size: 'xl',
    backdrop: 'static',
    keyboard: false
  };

  plannigList: Planning[] = []
  calendarEvents: any[] = [];


  calendarOptions: CalendarOptions = {
    plugins: [
      interactionPlugin,
      dayGridPlugin,
      timeGridPlugin,
      listPlugin,
      bootstrapPlugin
    ],
    headerToolbar: {
      left: 'prev,next today',
      center: 'title',
      right: 'monthGridYear,dayGridMonth,timeGridWeek,timeGridDay,listWeek'
    },
    initialView: 'dayGridMonth',
    views: {
      monthGridYear: {
        type: 'dayGridMonth', // Base it on the dayGridMonth view
        duration: { years: 1 }, // Show 1 year in total
        buttonText: 'Year', // Text on the button in the toolbar
      }
    },
    themeSystem: 'bootstrap',
    locale: frLocale,
    weekNumbers: true,
    weekends: true,
    editable: this.hasCalendarAccess(), // Set editable based on permissions // Enable drag-and-drop event editing
    selectable: true, // Allow users to select days/times
    selectMirror: true, // Shows a mirrored preview while selecting dates
    dayMaxEvents: true, // Show "more" if too many events
    select: this.handleDateSelect.bind(this),
    eventClick: this.handleEventClick.bind(this),
    eventsSet: this.handleEvents.bind(this),
    // Show pop-up on mouse hover
    eventMouseEnter: this.handleMouseEnter.bind(this),
    eventMouseLeave: this.handleMouseLeave.bind(this),
  
  };

  constructor(private pilotageService: PlanningService,
    private modalService: NgbModal,
    private changeDetector: ChangeDetectorRef,
    public AuthenticationService: AuthenticationService) {
  }

  hasCalendarAccess(): boolean {
    return this.AuthenticationService.hasPermission(['PR_M_W', 'PR_M_A', 'PR_A_W', 'PR_A_A','GL_A','ERP_A']);
  }



  handleMouseEnter(mouseEnterInfo) {
    const employee = mouseEnterInfo.event.extendedProps.employee;
    const of = mouseEnterInfo.event.extendedProps.of;
    const produit = mouseEnterInfo.event.extendedProps.produit;

    const tooltip = document.createElement('div');
    tooltip.className = 'custom-tooltip';  // Custom CSS class for styling
    tooltip.innerHTML = `
      ${employee ? `<div> <i class="fa fa-user mr-2"></i> ${employee}</div>` : ''}
      ${of ? `<div>${of}</div>` : ''} 
      ${produit ? `<div>${produit}</div>` : ''}
    `;

    tooltip.id = `tooltip-${mouseEnterInfo.event.id}`;  // Add unique ID for each tooltip

    document.body.appendChild(tooltip);

    // Position the tooltip relative to the mouse position
    tooltip.style.position = 'absolute';
    tooltip.style.top = `${mouseEnterInfo.jsEvent.pageY + 10}px`;
    tooltip.style.left = `${mouseEnterInfo.jsEvent.pageX + 10}px`;
    tooltip.style.zIndex = '1000';
    tooltip.style.background = '#e0ecf9';
    tooltip.style.padding = '10px';
    tooltip.style.border = '1px solid #ccc';
    tooltip.style.borderRadius = '4px';
    tooltip.style.boxShadow = '0 2px 8px rgba(0, 0, 0, 0.2)';

    // Attach the tooltip ID to the event element so we can reference it later
    mouseEnterInfo.el.setAttribute('data-tooltip-id', tooltip.id);
  }

  handleMouseLeave(mouseLeaveInfo) {
     // Retrieve the tooltip ID from the event element
  const tooltipId = mouseLeaveInfo.el.getAttribute('data-tooltip-id');
  if (tooltipId) {
    const tooltip = document.getElementById(tooltipId);
    if (tooltip) {
      tooltip.remove();  // Remove the tooltip element
    }
  }
  }


  //add event
  handleDateSelect(selectInfo: DateSelectArg) {
    if (this.hasCalendarAccess()) {
      const modalRef = this.modalService.open(AddOrEditComponent, this.ngbModalOptions);
    modalRef.componentInstance.dateDebut = selectInfo.startStr;
    modalRef.componentInstance.dateFin = selectInfo.endStr;
    modalRef.result.then(() => {
      this.getPlannig()
    });
    }
    
  }


  //update event
  handleEventClick(clickInfo: EventClickArg) {
    if (this.hasCalendarAccess()) {
      const modalRef = this.modalService.open(AddOrEditComponent, this.ngbModalOptions);
      modalRef.componentInstance.pilotageId = clickInfo.event.id;
      modalRef.result.then(() => {
        this.getPlannig()
      });
    }
  }


  handleEvents(events: EventApi[]) {
    if (this.hasCalendarAccess()) {  // Capture event changes
      events.forEach(event => {
        const updatedDateSatrt = this.convertToDatetimeLocal((new Date(event.start)).toString())
        const updatedDateEND = this.convertToDatetimeLocal((new Date(event.end)).toString())
        // Now, send the updated event back to the server to update the database
        this.updateEventInDatabase(+event.id, updatedDateSatrt,updatedDateEND);
      });
      this.changeDetector.detectChanges();}
  }

  convertToDatetimeLocal(backendDate: string): string {
    const date = new Date(backendDate);
    const year = date.getFullYear();
    const month = ('0' + (date.getMonth() + 1)).slice(-2);
    const day = ('0' + date.getDate()).slice(-2);
    const hours = ('0' + date.getHours()).slice(-2);
    const minutes = ('0' + date.getMinutes()).slice(-2);
    const second = ('0' + date.getSeconds()).slice(-2);

    // Return formatted date
    return `${year}-${month}-${day}T${hours}:${minutes}:${second}`;
  }

  updateEventInDatabase(eventId: number, startDate: string, endDate: string) {
    this.pilotageService.updateDate(eventId, startDate,endDate).subscribe(
      (response) => {
        console.log('Event updated successfully', response);
      },
      (error) => {
        console.error('Error updating event', error);
      }
    );
  }


  getPlannig() {
    this.pilotageService.getAll().subscribe((res: any) => {
      this.calendarEvents = [];  // Clear events before pushing
      console.log(res.result)
      for (let p of res.result) {
        this.calendarEvents.push({
          id: p.id,
          start: p.dateDebut,
          end: p.dateFin,
          employee:p.employee.employeeNumber,
          of: 'OF : ' + p.tache.reference,
          produit: 'Produit : ' + p.tache.product.libelle
        });
      }
      this.calendarOptions.events = this.calendarEvents;
    });
  }

  ngOnInit(): void {
    this.getPlannig()
  }



}
