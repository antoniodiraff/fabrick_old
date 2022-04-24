import { Component, OnInit } from '@angular/core';
import { ConfirmationService, LazyLoadEvent, MessageService, SortEvent } from 'primeng/api';
import { Transaction } from 'src/app/models/transaction.model';
import { Paginator } from 'src/app/models/paginator.model';
import { TransactionService } from 'src/app/services/transaction.service';
import { Router } from '@angular/router';
import { environment } from 'src/environments/environment';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { FormUtils } from 'src/app/utils/form.utils';
import { SecurityService } from 'src/app/services/security.service';
import { DataUtils } from 'src/app/utils/data.utils';
import { SecUtils } from 'src/app/security/security.utils';


@Component({
  selector: 'app-transaction',
  templateUrl: './transaction.component.html',
  styleUrls: ['./transaction.component.css']
})
export class TransactionComponent implements OnInit {

  transactionDialog: boolean;
  transactions: Transaction[];
  transaction: Transaction;
  selectedTransactions: Transaction[];
  submitted: boolean;
  totalRecords: number;
  loading: boolean;
  defaultRows: number = 10;
  errorCode:any;
  transactionValidation: FormGroup;

  //authMap : Map<String,boolean> = new Map;

  constructor(private transactionService: TransactionService, 
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
    this.transaction = {};
    this.submitted = false;
    this.transactionDialog = true;
  }

  deleteSelectedElements() {
    this.confirmationService.confirm({
      message: 'Sei sicuro di voler eliminare gll elementi selezionati?',
      header: 'Conferma',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.transactionService.deleteAll(this.selectedTransactions).subscribe({
          next: (res) => {
            console.log(res);
            this.transactions = this.transactions.filter(val => !this.selectedTransactions.includes(val));
            this.selectedTransactions = null;
          },
          complete: () => { 
            this.messageService.add({ severity: 'success', summary: 'Fatto', detail: 'Transactions eliminati', life: 3000 });
          },
          error: (e) => { 
            this.errorCode=e.status;
            console.error(e);
          }
        });
        
      }
    });
  }

  editElement(transaction: Transaction) {
    this.transaction = transaction;
    /*
    In caso di campi Date va fatta questa operazione
    this.transaction.datatransaction = new Date(transaction.datatransaction);
    */
    this.transactionDialog = true;
  }

  deleteElement(transaction: Transaction, messageOn: boolean = true) {
    this.confirmationService.confirm({
      message: 'Sei sicuro di voler eliminare l \'elemento selezionato?',
      header: 'Conferma',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.transactions = this.transactions.filter(val => val.transactionId !== transaction.transactionId);
        this.transactionService.delete(transaction.transactionId).subscribe({
          next: (res) => {
            console.log(res);
            this.transaction = {};
            if(messageOn)
            this.messageService.add({ severity: 'success', summary: 'Fatto', detail: 'Transaction eliminato', life: 3000 });
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
    this.transactionDialog = false;
    this.submitted = false;
  }

  saveElement() {
    this.submitted = true;
    this.startValidator();

    if(this.transactionValidation.status=="VALID"){
      if (this.transaction.transactionId) {
        //this.transactions[this.findIndexById(this.transaction.transactionId)] = this.transaction;
        this.transactionService.update(this.transaction).subscribe({
          next: (res) => {
            console.log(res);
            this.messageService.add({ severity: 'success', summary: 'Fatto', detail: 'Transaction aggiornato', life: 3000 });
          },
          complete: () => { },
          error: (e) => { 
            this.errorCode=e.status;
            console.error(e);
          }
        });
      }
      else {
        //console.log(this.transaction);
        this.transactionService.create(this.transaction).subscribe({
          next: (res) => {
            //console.log(res);
            this.loadList(0, this.defaultRows, 1, "transactionId");
          },
          complete: () => { 
            this.messageService.add({ severity: 'success', summary: 'Fatto', detail: 'Transaction Creato', life: 3000 });
          },
          error: (e) => { 
	   this.errorCode=e.status;
	   console.error(e);
          }
        });

      }

      this.transactions = [...this.transactions];
      this.transactionDialog = false;
      this.transaction = {};
    }
  }

  startValidator(){
    this.transactionValidation = new FormGroup({
      transactionId: new FormControl(this.transaction.transactionId, Validators.compose([
      ])),

      operationId: new FormControl(this.transaction.operationId, Validators.compose([
      ])),

      accountingDate: new FormControl(this.transaction.accountingDate, Validators.compose([
      ])),

      valueDate: new FormControl(this.transaction.valueDate, Validators.compose([
      ])),

      amount: new FormControl(this.transaction.amount, Validators.compose([
      ])),

      currency: new FormControl(this.transaction.currency, Validators.compose([
      ])),

      description: new FormControl(this.transaction.description, Validators.compose([
      ])),


   });
   //console.log(this.transactionValidation);
   
  }

  getValidationMessage(transactionValidationField){
    return FormUtils.getValidationMessage(transactionValidationField);
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
      event.sortField = "transactionId";
    }
    this.loadList(currentPage, event.rows, event.sortOrder, event.sortField);

  }

  loadList(currentPage: number, size: number, sortOrder: number, sortField: string) {
    this.transactionService.getPaginated(currentPage, size, sortOrder, sortField).subscribe({
      next: (res: Paginator) => {
        this.transactions = res.content;
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

  detailElement(transaction: Transaction) {
    //console.log(transaction);
    this.router.navigate(['/transaction-details', transaction.transactionId]);
  }

  downloadReport(type: string){
      this.transactionService.getReport(type)
        .subscribe(x => {
            var newBlob = new Blob([x], { type: "application/pdf" });
            const data = window.URL.createObjectURL(newBlob);
            
            var link = document.createElement('a');
            link.href = data;
            link.download = "transaction."+type;
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
    //return this.authMap.get("transaction"+SecUtils.authMap.get(operation));
    return true;
  }

}
