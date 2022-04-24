import { Component, OnInit } from '@angular/core';
import { ConfirmationService, LazyLoadEvent, MessageService, SortEvent } from 'primeng/api';
import { Account } from 'src/app/models/account.model';
import { Paginator } from 'src/app/models/paginator.model';
import { AccountService } from 'src/app/services/account.service';
import { Router } from '@angular/router';
import { environment } from 'src/environments/environment';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { FormUtils } from 'src/app/utils/form.utils';
import { SecurityService } from 'src/app/services/security.service';
import { DataUtils } from 'src/app/utils/data.utils';
import { SecUtils } from 'src/app/security/security.utils';


@Component({
  selector: 'app-account',
  templateUrl: './account.component.html',
  styleUrls: ['./account.component.css']
})
export class AccountComponent implements OnInit {

  accountDialog: boolean;
  accounts: Account[];
  account: Account;
  selectedAccounts: Account[];
  submitted: boolean;
  totalRecords: number;
  loading: boolean;
  defaultRows: number = 10;
  errorCode:any;
  accountValidation: FormGroup;

  //authMap : Map<String,boolean> = new Map;

  constructor(private accountService: AccountService, 
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
    this.account = {};
    this.submitted = false;
    this.accountDialog = true;
  }

  deleteSelectedElements() {
    this.confirmationService.confirm({
      message: 'Sei sicuro di voler eliminare gll elementi selezionati?',
      header: 'Conferma',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.accountService.deleteAll(this.selectedAccounts).subscribe({
          next: (res) => {
            console.log(res);
            this.accounts = this.accounts.filter(val => !this.selectedAccounts.includes(val));
            this.selectedAccounts = null;
          },
          complete: () => { 
            this.messageService.add({ severity: 'success', summary: 'Fatto', detail: 'Accounts eliminati', life: 3000 });
          },
          error: (e) => { 
            this.errorCode=e.status;
            console.error(e);
          }
        });
        
      }
    });
  }

  editElement(account: Account) {
    this.account = account;
    /*
    In caso di campi Date va fatta questa operazione
    this.account.dataaccount = new Date(account.dataaccount);
    */
    this.accountDialog = true;
  }

  deleteElement(account: Account, messageOn: boolean = true) {
    this.confirmationService.confirm({
      message: 'Sei sicuro di voler eliminare l \'elemento selezionato?',
      header: 'Conferma',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.accounts = this.accounts.filter(val => val.accountId !== account.accountId);
        this.accountService.delete(account.accountId).subscribe({
          next: (res) => {
            console.log(res);
            this.account = {};
            if(messageOn)
            this.messageService.add({ severity: 'success', summary: 'Fatto', detail: 'Account eliminato', life: 3000 });
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
    this.accountDialog = false;
    this.submitted = false;
  }

  saveElement() {
    this.submitted = true;
    this.startValidator();

    if(this.accountValidation.status=="VALID"){
      if (this.account.accountId) {
        //this.accounts[this.findIndexById(this.account.accountId)] = this.account;
        this.accountService.update(this.account).subscribe({
          next: (res) => {
            console.log(res);
            this.messageService.add({ severity: 'success', summary: 'Fatto', detail: 'Account aggiornato', life: 3000 });
          },
          complete: () => { },
          error: (e) => { 
            this.errorCode=e.status;
            console.error(e);
          }
        });
      }
      else {
        //console.log(this.account);
        this.accountService.create(this.account).subscribe({
          next: (res) => {
            //console.log(res);
            this.loadList(0, this.defaultRows, 1, "accountId");
          },
          complete: () => { 
            this.messageService.add({ severity: 'success', summary: 'Fatto', detail: 'Account Creato', life: 3000 });
          },
          error: (e) => { 
	   this.errorCode=e.status;
	   console.error(e);
          }
        });

      }

      this.accounts = [...this.accounts];
      this.accountDialog = false;
      this.account = {};
    }
  }

  startValidator(){
    this.accountValidation = new FormGroup({
      accountId: new FormControl(this.account.accountId, Validators.compose([
      ])),

      iban: new FormControl(this.account.iban, Validators.compose([
      ])),

      abiCode: new FormControl(this.account.abiCode, Validators.compose([
      ])),

      cabCode: new FormControl(this.account.cabCode, Validators.compose([
      ])),

      countryCode: new FormControl(this.account.countryCode, Validators.compose([
      ])),

      internationalCin: new FormControl(this.account.internationalCin, Validators.compose([
      ])),

      nationalCin: new FormControl(this.account.nationalCin, Validators.compose([
      ])),

      account: new FormControl(this.account.account, Validators.compose([
      ])),

      alias: new FormControl(this.account.alias, Validators.compose([
      ])),

      productName: new FormControl(this.account.productName, Validators.compose([
      ])),

      holderName: new FormControl(this.account.holderName, Validators.compose([
      ])),

      activatedDate: new FormControl(this.account.activatedDate, Validators.compose([
      ])),

      currency: new FormControl(this.account.currency, Validators.compose([
      ])),


   });
   //console.log(this.accountValidation);
   
  }

  getValidationMessage(accountValidationField){
    return FormUtils.getValidationMessage(accountValidationField);
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
      event.sortField = "accountId";
    }
    this.loadList(currentPage, event.rows, event.sortOrder, event.sortField);

  }

  loadList(currentPage: number, size: number, sortOrder: number, sortField: string) {
    this.accountService.getPaginated(currentPage, size, sortOrder, sortField).subscribe({
      next: (res: Paginator) => {
        this.accounts = res.content;
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

  detailElement(account: Account) {
    //console.log(account);
    this.router.navigate(['/account-details', account.accountId]);
  }

  downloadReport(type: string){
      this.accountService.getReport(type)
        .subscribe(x => {
            var newBlob = new Blob([x], { type: "application/pdf" });
            const data = window.URL.createObjectURL(newBlob);
            
            var link = document.createElement('a');
            link.href = data;
            link.download = "account."+type;
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
    //return this.authMap.get("account"+SecUtils.authMap.get(operation));
    return true;
  }

}
