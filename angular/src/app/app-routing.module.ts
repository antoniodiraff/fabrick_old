import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { AccessComponent } from './components/access/access.component';
import { MonitoringComponent } from './components/monitoring/monitoring.component';
import { ErrorPageComponent } from './components/error-page/error-page.component';
import { SecPrivilegeDetailComponent } from './components/security/sec-privilege-detail/sec-privilege-detail.component';
import { SecPrivilegeComponent } from './components/security/sec-privilege/sec-privilege.component';
import { SecRoleDetailComponent } from './components/security/sec-role-detail/sec-role-detail.component';
import { SecRoleComponent } from './components/security/sec-role/sec-role.component';
import { SecUserDetailComponent } from './components/security/sec-user-detail/sec-user-detail.component';
import { SecUserComponent } from './components/security/sec-user/sec-user.component';
import { AccountComponent } from './components/account/account.component';
import { AccountDetailComponent } from './components/account-detail/account-detail.component';
import { TransactionComponent } from './components/transaction/transaction.component';
import { TransactionDetailComponent } from './components/transaction-detail/transaction-detail.component';
import { TypeComponent } from './components/type/type.component';
import { TypeDetailComponent } from './components/type-detail/type-detail.component';
import { AuditRegisterComponent } from './components/audit-register/audit-register.component';
import { AuditRegisterDetailComponent } from './components/audit-register-detail/audit-register-detail.component';


const routes: Routes = [
  { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
  { path: 'error/:code', component: ErrorPageComponent },
  { path: 'error', component: ErrorPageComponent },
  { path: 'dashboard', component: DashboardComponent, 
    data:{breadcrumb: [
      {label: 'Dashboard',routerLink: '',class: 'breadcrumb-item',active: true, icon: 'pi pi-home'}
    ]}
  },
  { path: 'access', component: AccessComponent, 
    data:{breadcrumb: [
      {label: 'Dashboard',routerLink: '/',class: 'breadcrumb-item',active: false,icon: 'pi pi-home'},
      {label: 'Accesso',routerLink: '/access',class: 'breadcrumb-item',active: true, icon: 'pi pi-lock'}
    ]}
  },
  { path: 'monitoring', component: MonitoringComponent,
    data:{breadcrumb: [
      {label: 'Dashboard',routerLink: '/',class: 'breadcrumb-item',active: false,icon: 'pi pi-home'},
      {label: 'Monitoraggio',routerLink: '',class: 'breadcrumb-item',active: true}
    ]}
  },
  { path: 'sec-user', component: SecUserComponent, 
    data:{breadcrumb: [
        {label: 'Dashboard',routerLink: '/',class: 'breadcrumb-item',active: false,icon: 'pi pi-home'},
        {label: 'SecUser',routerLink: '',active: true, class: 'breadcrumb-item active' }
    ]}
  },  { path: 'sec-user-details/:id', component: SecUserDetailComponent,
    data:{breadcrumb: [
      {label: 'Dashboard',routerLink: '/',class: 'breadcrumb-item',active: false,icon: 'pi pi-home'},
      {label: 'SecUser',routerLink: '/sec-user',active: false, class: 'breadcrumb-item' },
      {label: 'SecUser Details',routerLink: '',active: true, class: 'breadcrumb-item active' }
    ]}
  },  { path: 'sec-role', component: SecRoleComponent, 
    data:{breadcrumb: [
        {label: 'Dashboard',routerLink: '/',class: 'breadcrumb-item',active: false,icon: 'pi pi-home'},
        {label: 'Ruoli',routerLink: '',active: true, class: 'breadcrumb-item active' }
    ]}
  },  { path: 'sec-role-details/:id', component: SecRoleDetailComponent,
    data:{breadcrumb: [
      {label: 'Dashboard',routerLink: '/',class: 'breadcrumb-item',active: false,icon: 'pi pi-home'},
      {label: 'Ruoli',routerLink: '/sec-role',active: false, class: 'breadcrumb-item' },
      {label: 'Dettaglio',routerLink: '',active: true, class: 'breadcrumb-item active' }
    ]}
  },  { path: 'sec-privilege', component: SecPrivilegeComponent, 
    data:{breadcrumb: [
        {label: 'Dashboard',routerLink: '/',class: 'breadcrumb-item',active: false,icon: 'pi pi-home'},
        {label: 'Privilegi',routerLink: '',active: true, class: 'breadcrumb-item active' }
    ]}
  },  { path: 'sec-privilege-details/:id', component: SecPrivilegeDetailComponent,
    data:{breadcrumb: [
      {label: 'Dashboard',routerLink: '/',class: 'breadcrumb-item',active: false,icon: 'pi pi-home'},
      {label: 'Privilegi',routerLink: '/sec-privilege',active: false, class: 'breadcrumb-item' },
      {label: 'Dettaglio',routerLink: '',active: true, class: 'breadcrumb-item active' }
    ]}
  },
  { path: 'account', component: AccountComponent, 
    data:{breadcrumb: [
        {label: 'Dashboard',routerLink: '/',class: 'breadcrumb-item',active: false,icon: 'pi pi-home'},
        {label: 'Account',routerLink: '',active: true, class: 'breadcrumb-item active' }
    ]}
  },  { path: 'account-details/:id', component: AccountDetailComponent,
    data:{breadcrumb: [
      {label: 'Dashboard',routerLink: '/',class: 'breadcrumb-item',active: false,icon: 'pi pi-home'},
      {label: 'Account',routerLink: '/account',active: false, class: 'breadcrumb-item' },
      {label: 'Account Details',routerLink: '',active: true, class: 'breadcrumb-item active' }
    ]}
  },  { path: 'transaction', component: TransactionComponent, 
    data:{breadcrumb: [
        {label: 'Dashboard',routerLink: '/',class: 'breadcrumb-item',active: false,icon: 'pi pi-home'},
        {label: 'Transaction',routerLink: '',active: true, class: 'breadcrumb-item active' }
    ]}
  },  { path: 'transaction-details/:id', component: TransactionDetailComponent,
    data:{breadcrumb: [
      {label: 'Dashboard',routerLink: '/',class: 'breadcrumb-item',active: false,icon: 'pi pi-home'},
      {label: 'Transaction',routerLink: '/transaction',active: false, class: 'breadcrumb-item' },
      {label: 'Transaction Details',routerLink: '',active: true, class: 'breadcrumb-item active' }
    ]}
  },  { path: 'type', component: TypeComponent, 
    data:{breadcrumb: [
        {label: 'Dashboard',routerLink: '/',class: 'breadcrumb-item',active: false,icon: 'pi pi-home'},
        {label: 'Type',routerLink: '',active: true, class: 'breadcrumb-item active' }
    ]}
  },  { path: 'type-details/:id', component: TypeDetailComponent,
    data:{breadcrumb: [
      {label: 'Dashboard',routerLink: '/',class: 'breadcrumb-item',active: false,icon: 'pi pi-home'},
      {label: 'Type',routerLink: '/type',active: false, class: 'breadcrumb-item' },
      {label: 'Type Details',routerLink: '',active: true, class: 'breadcrumb-item active' }
    ]}
  },  { path: 'audit-register', component: AuditRegisterComponent, 
    data:{breadcrumb: [
        {label: 'Dashboard',routerLink: '/',class: 'breadcrumb-item',active: false,icon: 'pi pi-home'},
        {label: 'AuditRegister',routerLink: '',active: true, class: 'breadcrumb-item active' }
    ]}
  },  { path: 'audit-register-details/:id', component: AuditRegisterDetailComponent,
    data:{breadcrumb: [
      {label: 'Dashboard',routerLink: '/',class: 'breadcrumb-item',active: false,icon: 'pi pi-home'},
      {label: 'AuditRegister',routerLink: '/audit-register',active: false, class: 'breadcrumb-item' },
      {label: 'AuditRegister Details',routerLink: '',active: true, class: 'breadcrumb-item active' }
    ]}
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
