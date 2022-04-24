import { Component, OnInit } from '@angular/core';
import { ConfirmationService, LazyLoadEvent, MessageService, SortEvent } from 'primeng/api';
import { AuditRegister } from 'src/app/models/audit-register.model';
import { Paginator } from 'src/app/models/paginator.model';
import { AuditRegisterService } from 'src/app/services/audit-register.service';
import { Router } from '@angular/router';
import { environment } from 'src/environments/environment';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { FormUtils } from 'src/app/utils/form.utils';
import { SecurityService } from 'src/app/services/security.service';
import { DataUtils } from 'src/app/utils/data.utils';
import { SecUtils } from 'src/app/security/security.utils';


@Component({
  selector: 'app-audit-register',
  templateUrl: './audit-register.component.html',
  styleUrls: ['./audit-register.component.css']
})
export class AuditRegisterComponent implements OnInit {

  auditRegisterDialog: boolean;
  auditRegisters: AuditRegister[];
  auditRegister: AuditRegister;
  selectedAuditRegisters: AuditRegister[];
  submitted: boolean;
  totalRecords: number;
  loading: boolean;
  defaultRows: number = 10;
  errorCode:any;
  auditRegisterValidation: FormGroup;

  //authMap : Map<String,boolean> = new Map;

  constructor(private auditRegisterService: AuditRegisterService, 
              private messageService: MessageService, 
              private confirmationService: ConfirmationService,
              private secService : SecurityService,
              private router: Router
              ) { }

  ngOnInit() {
    //console.log("Init");
    //this.initPrivileges();
  }

  openNew() {
    this.auditRegister = {};
    this.submitted = false;
    this.auditRegisterDialog = true;
  }

  deleteSelectedElements() {
    this.confirmationService.confirm({
      message: 'Sei sicuro di voler eliminare gll elementi selezionati?',
      header: 'Conferma',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.auditRegisterService.deleteAll(this.selectedAuditRegisters).subscribe({
          next: (res) => {
            console.log(res);
            this.auditRegisters = this.auditRegisters.filter(val => !this.selectedAuditRegisters.includes(val));
            this.selectedAuditRegisters = null;
          },
          complete: () => { 
            this.messageService.add({ severity: 'success', summary: 'Fatto', detail: 'AuditRegisters eliminati', life: 3000 });
          },
          error: (e) => { 
            this.errorCode=e.status;
            console.error(e);
          }
        });
        
      }
    });
  }

  editElement(auditRegister: AuditRegister) {
    this.auditRegister = auditRegister;
    /*
    In caso di campi Date va fatta questa operazione
    this.auditRegister.dataauditRegister = new Date(auditRegister.dataauditRegister);
    */
    this.auditRegisterDialog = true;
  }

  deleteElement(auditRegister: AuditRegister, messageOn: boolean = true) {
    this.confirmationService.confirm({
      message: 'Sei sicuro di voler eliminare l \'elemento selezionato?',
      header: 'Conferma',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.auditRegisters = this.auditRegisters.filter(val => val.auditId !== auditRegister.auditId);
        this.auditRegisterService.delete(auditRegister.auditId).subscribe({
          next: (res) => {
            console.log(res);
            this.auditRegister = {};
            if(messageOn)
            this.messageService.add({ severity: 'success', summary: 'Fatto', detail: 'AuditRegister eliminato', life: 3000 });
          },
          complete: () => { },
          error: (e) => { 
            this.errorCode=e.status;
            console.error(e);
          }
        });
      }
    });
  }

  hideDialog() {
    this.auditRegisterDialog = false;
    this.submitted = false;
  }

  saveElement() {
    this.submitted = true;
    this.startValidator();

    if(this.auditRegisterValidation.status=="VALID"){
      if (this.auditRegister.auditId) {
        //this.auditRegisters[this.findIndexById(this.auditRegister.auditId)] = this.auditRegister;
        this.auditRegisterService.update(this.auditRegister).subscribe({
          next: (res) => {
            console.log(res);
            this.messageService.add({ severity: 'success', summary: 'Fatto', detail: 'AuditRegister aggiornato', life: 3000 });
          },
          complete: () => { },
          error: (e) => { 
            this.errorCode=e.status;
            console.error(e);
          }
        });
      }
      else {
        //console.log(this.auditRegister);
        this.auditRegisterService.create(this.auditRegister).subscribe({
          next: (res) => {
            //console.log(res);
            this.loadList(0, this.defaultRows, 1, "auditId");
          },
          complete: () => { 
            this.messageService.add({ severity: 'success', summary: 'Fatto', detail: 'AuditRegister Creato', life: 3000 });
          },
          error: (e) => { 
	   this.errorCode=e.status;
	   console.error(e);
          }
        });

      }

      this.auditRegisters = [...this.auditRegisters];
      this.auditRegisterDialog = false;
      this.auditRegister = {};
    }
  }

  startValidator(){
    this.auditRegisterValidation = new FormGroup({
      auditId: new FormControl(this.auditRegister.auditId, Validators.compose([
      ])),

      requestDate: new FormControl(this.auditRegister.requestDate, Validators.compose([
      ])),

      operation: new FormControl(this.auditRegister.operation, Validators.compose([
      ])),


   });
   //console.log(this.auditRegisterValidation);
   
  }

  getValidationMessage(auditRegisterValidationField){
    return FormUtils.getValidationMessage(auditRegisterValidationField);
  }

  loadListEvent(event: LazyLoadEvent) {
    this.loading = true;

    console.log(event);
    //in a real application, make a remote request to load data using state metadata from event
    //event.first = First row offset
    //event.rows = Number of rows per page
    //event.sortField = Field name to sort with
    //event.sortOrder = Sort order as number, 1 for asc and -1 for dec
    //filters: FilterMetadata object having field as key and filter value, filter matchMode as value

    let currentPage = event.first / event.rows;
    if (event.sortField == null || event.sortField == undefined) {
      event.sortField = "auditId";
    }
    this.loadList(currentPage, event.rows, event.sortOrder, event.sortField);

  }

  loadList(currentPage: number, size: number, sortOrder: number, sortField: string) {
    this.auditRegisterService.getPaginated(currentPage, size, sortOrder, sortField).subscribe({
      next: (res: Paginator) => {
        this.auditRegisters = res.content;
        this.totalRecords = res.totalElements;
        this.loading = false;
        //console.log(res);
      },
      complete: () => { },
      error: (e) => { 
        this.errorCode=e.status;
        console.error(e);
      }
    });
  }

  detailElement(auditRegister: AuditRegister) {
    //console.log(auditRegister);
    this.router.navigate(['/audit-register-details', auditRegister.auditId]);
  }

  downloadReport(type: string){
      this.auditRegisterService.getReport(type)
        .subscribe(x => {
            var newBlob = new Blob([x], { type: "application/pdf" });
            const data = window.URL.createObjectURL(newBlob);
            
            var link = document.createElement('a');
            link.href = data;
            link.download = "auditRegister."+type;
            // this is necessary as link.click() does not work on the latest firefox
            link.dispatchEvent(new MouseEvent('click', { bubbles: true, cancelable: true, view: window }));
            
            setTimeout(function () {
                // For Firefox it is necessary to delay revoking the ObjectURL
                window.URL.revokeObjectURL(data);
                link.remove();
            }, 100);
        });
  }

  /*
  initPrivileges(){
    this.secService.getPrivileges().subscribe({
      next: (res) => {
        this.authMap = DataUtils.authArrayToHashmap(res);
        //console.log("Sec: ",this.authMap);
      },
      complete: () => { },
      error: (e) => {console.error(e); }
    });
  }
  */
  
  secCheck(operation){
    //console.log("OP:",operation);
    //return this.authMap.get("auditregister"+SecUtils.authMap.get(operation));
    return true;
  }

}
