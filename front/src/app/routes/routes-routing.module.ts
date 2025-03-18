import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LayoutComponent } from '../layout/layout.component';
import { AuthGuard } from '../shared/guards/auth.guard';


const routes: Routes = [
  {
    path: '',
    component: LayoutComponent,canActivateChild: [AuthGuard],
    children: [
      { path: '', redirectTo: '/home', pathMatch: 'full'},
      { path: 'home', loadChildren: () => import('./home/home.module').then(m => m.HomeModule)},
      { path: 'settings-security', loadChildren: () => import('./settings-security/settings-security.module').then(m => m.SettingsSecurityModule),},
      { path: 'project', loadChildren: () => import('./project/project.module').then(m => m.ProjectModule)},
      { path: 'mes', loadChildren: () => import('./production/production.module').then(m => m.ProductionModule)},
      { path: 'quality', loadChildren: () => import('./quality/quality.module').then(m => m.QualityModule)
      },
      { path: 'equipment', loadChildren: () => import('./gmao/gmao.module').then(m => m.GmaoModule)},
      { path: 'stock', loadChildren: () => import('./stock/stock.module').then(m => m.StockModule)},
      { path: 'documentary', loadChildren: () => import('./document/document.module').then(m => m.DocumentModule)},
    ]
  },

  { path: 'page', loadChildren: () => import('./pages/pages.module').then(m => m.PagesModule) },
  // Not found
  { path: '**', redirectTo: '/page/404' }

];

@NgModule({
  imports: [
    RouterModule.forRoot(routes, { relativeLinkResolution: 'legacy' })
  ],
  exports: [RouterModule],
  providers: [AuthGuard]
})
export class RoutesRoutingModule { }
