import { Injectable } from '@angular/core';
import { ConfigService } from 'src/app/core/services/config.service';

@Injectable({
  providedIn: 'root'
})
export class RoleService {

  constructor() { }

  getRoles(component: string | undefined): any {
    if (component) {
      const rolesByComponent: Record<string, any> = ConfigService.config.rolesByComponent;
      return rolesByComponent[component];
    }
    return null;
  }

}
