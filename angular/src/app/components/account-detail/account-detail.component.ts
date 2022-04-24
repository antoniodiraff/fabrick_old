import { Component, ElementRef, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ConfirmationService, MessageService } from 'primeng/api';
import {Location} from '@angular/common';
import { Account } from 'src/app/models/account.model';
import { AccountService } from 'src/app/services/account.service';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { FormUtils } from 'src/app/utils/form.utils';
import { SecurityService } from 'src/app/services/security.service';
import { DataUtils } from 'src/app/utils/data.utils';
import { SecUtils } from 'src/app/security/security.utils';
import { TransactionService } from 'src/app/services/transaction.service';
import { Transaction} from 'src/app/models/transaction.model';



@Component({
  selector: 'app-account-detail',
  templateUrl: './account-detail.component.html',
  styleUrls: ['./account-detail.component.css']
})
export class AccountDetailComponent implements OnInit {

  id: number;
  account: Account;
  loading: boolean = true;
  edit: boolean = false;
  selectedAccounts: Account[];
  submitted: boolean;
  private sub: any;
  accountValidation: FormGroup;

  /* Transaction Relation */
  transactions: Transaction[];
  selectedTransactions: Transaction[];
  transactionDialog: boolean;
  totalRecordsTransaction: number;


//authMap : Map<String,boolean> = new Map;

  constructor(private route: ActivatedRoute,
              private el: ElementRef,
              private messageService: MessageService,
              private confirmationService: ConfirmationService,
              private router: Router,
              private secService : SecurityService,
	            private _location: Location,
              private accountService: AccountService,
              private transactionService: TransactionService,

               ) { }

  ngOnInit(): void {
    //this.initPrivileges();
    this.account=new Account;
    this.sub = this.route.params.subscribe(params => {
      this.id = +params['id']; 
      //console.log(this.id);

      this.accountService.get(this.id).subscribe({
        next: (res: Account) => {
          this.account = res;
	  /*Per le Date effettuare questa operazione per vedere il calendar
          this.account.dataAccount = new Date(this.account.dataAccount);
	  */

          this.loading = false;
          console.log(this.account);
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

    /* Transaction Enable Preparation */
    if(!this.transactions){
      this.transactionService.getAll().subscribe({
        next: (res: Transaction[]) => {
          /*Rimuovo elementi già associati ad Account */
          this.totalRecordsTransaction = res.length;
          this.transactions = DataUtils.removeSameCommonElements(res,this.account.transactions);        },
        complete: () => {this.loading = false;},
        error: (e) => { console.log(e)}
      });
    }
    else{this.loading = false;}


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

    if(this.accountValidation.status=="VALID"){
     if (this.account.accountId) {
      
      this.accountService.update(this.account).subscribe({
        next: (res) => {
          //console.log(res);
          this.messageService.add({ severity: 'success', summary: 'Fatto', detail: 'Account aggiornato', life: 3000 });
        },
        complete: () => { 
          this.edit=false;
          this.submitted = false;
          let myTag = this.el.nativeElement.querySelector("i");
          this.closeIcon();
        },
        error: (e) => {
          console.log("Error: ",e);
          this.messageService.add({ severity: 'error', summary: 'Attenzione', detail: 'Account non modificato', life: 3000 });
        }
      });
    }
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

  deleteElement(account: Account, messageOn: boolean = true) {
    this.confirmationService.confirm({
      message: 'Sei sicuro di voler eliminare l \'elemento selezionato ?',
      header: 'Conferma',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.accountService.delete(account.accountId).subscribe({
          next: (res) => {
            if(messageOn)
            this.messageService.add({ severity: 'success', summary: 'Fatto', detail: 'Account eliminato', life: 3000 });
            this.router.navigate(['/account']);
          },
          complete: () => { },
          error: (e) => {
            console.log(e);
            this.messageService.add({ severity: 'error', summary: 'Attenzione', detail: 'Account non eliminato', life: 3000 });
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

  /* Transaction Edit Utils */
  removeTransaction(transaction:Transaction){
    this.transactions.push(transaction);
    var result = new Map(this.account.transactions.map(i => [i.transactionId, i]));
    result.delete(transaction.transactionId);
    this.account.transactions=Array.from( result.values() );
  }
  setTransactions(){
    this.transactionDialog = true;
  }
  addTransactionsToAccount(){
    for(var i = 0; i < this.selectedTransactions.length ; i++){
      this.account.transactions.push(this.selectedTransactions[i]);
      this.transactions = DataUtils.removeSameCommonElements(this.transactions,this.account.transactions);
    }
    this.selectedTransactions=[];
    this.transactionDialog = false;
  }
  hideDialogTransaction() {
    this.transactionDialog = false;
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
