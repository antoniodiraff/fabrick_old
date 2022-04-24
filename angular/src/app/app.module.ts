import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {InputTextModule} from 'primeng/inputtext';
import {ButtonModule} from 'primeng/button';
import { MenubarModule } from 'primeng/menubar';
import { NavbarComponent } from './components/navbar/navbar.component';
import { SidebarModule } from 'primeng/sidebar';
import { SidebarComponent } from './components/sidebar/sidebar.component';
import { PanelMenuModule } from 'primeng/panelmenu';
import { OverlayPanelModule } from 'primeng/overlaypanel';
import {AvatarModule} from 'primeng/avatar';
import {ListboxModule} from 'primeng/listbox';
import { NotificationPanelComponent } from './components/notification-panel/notification-panel.component';
import { UserprofilePanelComponent } from './components/userprofile-panel/userprofile-panel.component';
import {MenuModule} from 'primeng/menu';
import {CardModule} from 'primeng/card';
import {ChartModule} from 'primeng/chart';
import {TableModule} from 'primeng/table';
import {ToastModule} from 'primeng/toast';
import {CalendarModule} from 'primeng/calendar';
import {SliderModule} from 'primeng/slider';
import {MultiSelectModule} from 'primeng/multiselect';
import {ContextMenuModule} from 'primeng/contextmenu';
import {DialogModule} from 'primeng/dialog';
import {DropdownModule} from 'primeng/dropdown';
import {ProgressBarModule} from 'primeng/progressbar';
import {FileUploadModule} from 'primeng/fileupload';
import {ToolbarModule} from 'primeng/toolbar';
import {RatingModule} from 'primeng/rating';
import {RadioButtonModule} from 'primeng/radiobutton';
import {InputNumberModule} from 'primeng/inputnumber';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { ConfirmationService } from 'primeng/api';
import { MessageService } from 'primeng/api';
import { ActuatorService } from './services/actuator.service';
import { InputTextareaModule } from 'primeng/inputtextarea';
import {SkeletonModule} from 'primeng/skeleton';
import {BadgeModule} from 'primeng/badge';
import { TagModule } from 'primeng/tag';
import {KnobModule} from 'primeng/knob';
import {TreeModule} from 'primeng/tree';
import {AccordionModule} from 'primeng/accordion';
import {PasswordModule} from 'primeng/password';
import {InputSwitchModule} from 'primeng/inputswitch';
import {TabViewModule} from 'primeng/tabview';
import {TooltipModule} from 'primeng/tooltip';
import {BreadcrumbModule} from 'primeng/breadcrumb';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { MonitoringComponent } from './components/monitoring/monitoring.component';
import { BreadcrumbComponent } from './components/breadcrumb/breadcrumb.component';
import { ErrorPageComponent } from './components/error-page/error-page.component';
import { FooterComponent } from './components/footer/footer.component';
import { SecUserComponent } from './components/security/sec-user/sec-user.component';
import { SecUserDetailComponent } from './components/security/sec-user-detail/sec-user-detail.component';
import { SecRoleComponent } from './components/security/sec-role/sec-role.component';
import { SecRoleDetailComponent } from './components/security/sec-role-detail/sec-role-detail.component';
import { SecPrivilegeComponent } from './components/security/sec-privilege/sec-privilege.component';
import { SecPrivilegeDetailComponent } from './components/security/sec-privilege-detail/sec-privilege-detail.component';
import { authInterceptorProviders } from './security/auth.interceptor';
import { AccessComponent } from './components/access/access.component';
import { AccountComponent } from './components/account/account.component';
import { AccountDetailComponent } from './components/account-detail/account-detail.component';
import { TransactionComponent } from './components/transaction/transaction.component';
import { TransactionDetailComponent } from './components/transaction-detail/transaction-detail.component';
import { TypeComponent } from './components/type/type.component';
import { TypeDetailComponent } from './components/type-detail/type-detail.component';
import { AuditRegisterComponent } from './components/audit-register/audit-register.component';
import { AuditRegisterDetailComponent } from './components/audit-register-detail/audit-register-detail.component';

import { AccountService } from './services/account.service';
import { TransactionService } from './services/transaction.service';
import { TypeService } from './services/type.service';
import { AuditRegisterService } from './services/audit-register.service';



@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    SidebarComponent,
    FooterComponent,
    AccessComponent,
    NotificationPanelComponent,
    UserprofilePanelComponent,
    DashboardComponent,
    MonitoringComponent,
    ErrorPageComponent,
    BreadcrumbComponent,
    SecUserComponent,
    SecUserDetailComponent,
    SecRoleComponent,
    SecRoleDetailComponent,
    SecPrivilegeComponent,
    SecPrivilegeDetailComponent,
    AccountComponent,
    AccountDetailComponent,
    TransactionComponent,
    TransactionDetailComponent,
    TypeComponent,
    TypeDetailComponent,
    AuditRegisterComponent,
    AuditRegisterDetailComponent,

  ],
  imports: [
    HttpClientModule,
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    FormsModule,
    InputTextModule,
    MenubarModule,
    ButtonModule,
    SidebarModule,
    PanelMenuModule,
    OverlayPanelModule,
    AvatarModule,
    ListboxModule,
    MenuModule,
    CardModule,
    ChartModule,
    TableModule,
    CalendarModule,
    SliderModule,
    DialogModule,
    MultiSelectModule,
    ContextMenuModule,
    DropdownModule,
    ButtonModule,
    ToastModule,
    InputTextModule,
    ProgressBarModule,
    HttpClientModule,
    FileUploadModule,
    ToolbarModule,
    RatingModule,
    FormsModule,
    RadioButtonModule,
    InputNumberModule,
    ConfirmDialogModule,
    InputTextareaModule,
    SkeletonModule,
    BadgeModule,
    TagModule,
    KnobModule,
    TreeModule,
    AccordionModule,
    PasswordModule,
    InputSwitchModule,
    TabViewModule,
    TooltipModule,
    BreadcrumbModule,

  ],
  providers: [
    MessageService, 
    ConfirmationService,
    ActuatorService,
    AccountService,
    TransactionService,
    TypeService,
    AuditRegisterService,

    //authInterceptorProviders,
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
