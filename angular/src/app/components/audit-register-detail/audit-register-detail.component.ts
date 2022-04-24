import { Component, ElementRef, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ConfirmationService, MessageService } from 'primeng/api';
import {Location} from '@angular/common';
import { AuditRegister } from 'src/app/models/audit-register.model';
import { AuditRegisterService } from 'src/app/services/audit-register.service';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { FormUtils } from 'src/app/utils/form.utils';
import { SecurityService } from 'src/app/services/security.service';
import { DataUtils } from 'src/app/utils/data.utils';
import { SecUtils } from 'src/app/security/security.utils';
import { AccountService } from 'src/app/services/account.service';
import { Account} from 'src/app/models/account.model';



@Component({
  selector: 'app-audit-register-detail',
  templateUrl: './audit-register-detail.component.html',
  styleUrls: ['./audit-register-detail.component.css']
})
export class AuditRegisterDetailComponent implements OnInit {

  id: number;
  auditRegister: AuditRegister;
  loading: boolean = true;
  edit: boolean = false;
  selectedAuditRegisters: AuditRegister[];
  submitted: boolean;
  private sub: any;
  auditRegisterValidation: FormGroup;

  /* Account Relation */
  accounts: Account[];
  selectedAccount: Account;
  accountDeleted: boolean;


//authMap : Map<String,boolean> = new Map;

  constructor(private route: ActivatedRoute,
              private el: ElementRef,
              private messageService: MessageService,
              private confirmationService: ConfirmationService,
              private router: Router,
              private secService : SecurityService,
	            private _location: Location,
              private auditRegisterService: AuditRegisterService,
              private accountService: AccountService,

               ) { }

  ngOnInit(): void {
    //this.initPrivileges();
    this.auditRegister=new AuditRegister;
    this.sub = this.route.params.subscribe(params => {
      this.id = +params['id']; 
      //console.log(this.id);

      this.auditRegisterService.get(this.id).subscribe({
        next: (res: AuditRegister) => {
          this.auditRegister = res;
	  /*Per le Date effettuare questa operazione per vedere il calendar
          this.auditRegister.dataAuditRegister = new Date(this.auditRegister.dataAuditRegister);
	  */

          this.loading = false;
          console.log(this.auditRegister);
        },
        complete: () => { },
        error: () => { }
      });

   });
  }

  enableEdit(){
    this.loading=true;
    this.edit=true;
    this.submitted = false;
    this.openIcon();

    /* Account Enable Preparation */
    this.accountDeleted = false;
    if(!this.auditRegister.account){this.auditRegister.account=new Account();}
    this.selectedAccount = this.auditRegister.account;
    if(!this.accounts){
      this.accountService.getAll().subscribe({
        next: (res: Account[]) => {this.accounts = res;},
        complete: () => {this.loading = false;},
        error: (e) => { console.log(e)}
      });
    }
    else{this.loading = false;}
    this.loading = false;


  }

  disableEdit(){
    this.confirmationService.confirm({
      message: 'Sei sicuro di volere annullare? Tutte le modifiche effettuate andranno perse.',
      header: 'Conferma',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.edit=false;
        this.closeIcon();
        this.ngOnInit();
      }
    });
  }

  save(){
    this.submitted = true;
    this.startValidator();

    if(this.auditRegisterValidation.status=="VALID"){
     if (this.auditRegister.auditId) {
      if(this.selectedAccount!=null && Object.keys(this.selectedAccount).length != 0){
          this.auditRegister.account=this.selectedAccount;
        }
        else if(this.auditRegister.account != null && Object.keys(this.auditRegister.account).length == 0){//Check Oggetto presente ma vuoto
          this.auditRegister.account=null;
        }
      this.auditRegisterService.update(this.auditRegister).subscribe({
        next: (res) => {
          //console.log(res);
          this.messageService.add({ severity: 'success', summary: 'Fatto', detail: 'AuditRegister aggiornato', life: 3000 });
        },
        complete: () => { 
          this.edit=false;
          this.submitted = false;
          let myTag = this.el.nativeElement.querySelector("i");
          this.closeIcon();
        },
        error: (e) => {
          console.log("Error: ",e);
          this.messageService.add({ severity: 'error', summary: 'Attenzione', detail: 'AuditRegister non modificato', life: 3000 });
        }
      });
    }
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

  deleteElement(auditRegister: AuditRegister, messageOn: boolean = true) {
    this.confirmationService.confirm({
      message: 'Sei sicuro di voler eliminare l \'elemento selezionato ?',
      header: 'Conferma',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.auditRegisterService.delete(auditRegister.auditId).subscribe({
          next: (res) => {
            if(messageOn)
            this.messageService.add({ severity: 'success', summary: 'Fatto', detail: 'AuditRegister eliminato', life: 3000 });
            this.router.navigate(['/audit-register']);
          },
          complete: () => { },
          error: (e) => {
            console.log(e);
            this.messageService.add({ severity: 'error', summary: 'Attenzione', detail: 'AuditRegister non eliminato', life: 3000 });
          }
        });
      }
    });
  }


  ngOnDestroy() {
    this.sub.unsubscribe();
  }

  openIcon(){
    let myTag = this.el.nativeElement.querySelector("#status-icon");
    if(myTag.classList.contains('pi-lock')){
        myTag.classList.remove('pi-lock'); 
        myTag.classList.add('pi-lock-open'); 
    }
  }
  closeIcon(){
    let myTag = this.el.nativeElement.querySelector("#status-icon");
          if(myTag.classList.contains('pi-lock-open')){
              myTag.classList.remove('pi-lock-open'); 
              myTag.classList.add('pi-lock'); 
          }
  }

  back() {
    this._location.back();
  }

  /* Account Edit Utils */
  setAccount(){
    this.auditRegister.account = null;
    this.selectedAccount = null;
    this.accountDeleted = true;
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
