
import { appInjector } from 'src/app/shared/spi.shared.module';
import { RoleService } from 'src/app/shared/services/role.service';
import { BaseSubscriptionHandler } from './base-subscription-handler';

export abstract class BaseComponent extends BaseSubscriptionHandler {


  roleService: RoleService;
  roles: any;

  constructor() {
    super();
    if (appInjector) {

      this.roleService = appInjector.get(RoleService);
      this.getRoles();
    }
  }

  getRoles() {
    this.roles = this.roleService.getRoles(this.constructor.name);
  }

  exportExcel() {

  }

}
