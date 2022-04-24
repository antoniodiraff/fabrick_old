import { Component, ElementRef, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ConfirmationService, MessageService } from 'primeng/api';
import {Location} from '@angular/common';
import { Transaction } from 'src/app/models/transaction.model';
import { TransactionService } from 'src/app/services/transaction.service';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { FormUtils } from 'src/app/utils/form.utils';
import { SecurityService } from 'src/app/services/security.service';
import { DataUtils } from 'src/app/utils/data.utils';
import { SecUtils } from 'src/app/security/security.utils';
import { TypeService } from 'src/app/services/type.service';
import { Type} from 'src/app/models/type.model';



@Component({
  selector: 'app-transaction-detail',
  templateUrl: './transaction-detail.component.html',
  styleUrls: ['./transaction-detail.component.css']
})
export class TransactionDetailComponent implements OnInit {

  id: number;
  transaction: Transaction;
  loading: boolean = true;
  edit: boolean = false;
  selectedTransactions: Transaction[];
  submitted: boolean;
  private sub: any;
  transactionValidation: FormGroup;

  /* Type Relation */
  types: Type[];
  selectedType: Type;
  typeDeleted: boolean;


//authMap : Map<String,boolean> = new Map;

  constructor(private route: ActivatedRoute,
              private el: ElementRef,
              private messageService: MessageService,
              private confirmationService: ConfirmationService,
              private router: Router,
              private secService : SecurityService,
	            private _location: Location,
              private transactionService: TransactionService,
              private typeService: TypeService,

               ) { }

  ngOnInit(): void {
    //this.initPrivileges();
    this.transaction=new Transaction;
    this.sub = this.route.params.subscribe(params => {
      this.id = +params['id']; 
      //console.log(this.id);

      this.transactionService.get(this.id).subscribe({
        next: (res: Transaction) => {
          this.transaction = res;
	  /*Per le Date effettuare questa operazione per vedere il calendar
          this.transaction.dataTransaction = new Date(this.transaction.dataTransaction);
	  */

          this.loading = false;
          console.log(this.transaction);
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

    /* Type Enable Preparation */
    this.typeDeleted = false;
    if(!this.transaction.type){this.transaction.type=new Type();}
    this.selectedType = this.transaction.type;
    if(!this.types){
      this.typeService.getAll().subscribe({
        next: (res: Type[]) => {this.types = res;},
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

    if(this.transactionValidation.status=="VALID"){
     if (this.transaction.transactionId) {
      if(this.selectedType!=null && Object.keys(this.selectedType).length != 0){
          this.transaction.type=this.selectedType;
        }
        else if(this.transaction.type != null && Object.keys(this.transaction.type).length == 0){//Check Oggetto presente ma vuoto
          this.transaction.type=null;
        }
      this.transactionService.update(this.transaction).subscribe({
        next: (res) => {
          //console.log(res);
          this.messageService.add({ severity: 'success', summary: 'Fatto', detail: 'Transaction aggiornato', life: 3000 });
        },
        complete: () => { 
          this.edit=false;
          this.submitted = false;
          let myTag = this.el.nativeElement.querySelector("i");
          this.closeIcon();
        },
        error: (e) => {
          console.log("Error: ",e);
          this.messageService.add({ severity: 'error', summary: 'Attenzione', detail: 'Transaction non modificato', life: 3000 });
        }
      });
    }
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

  deleteElement(transaction: Transaction, messageOn: boolean = true) {
    this.confirmationService.confirm({
      message: 'Sei sicuro di voler eliminare l \'elemento selezionato ?',
      header: 'Conferma',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.transactionService.delete(transaction.transactionId).subscribe({
          next: (res) => {
            if(messageOn)
            this.messageService.add({ severity: 'success', summary: 'Fatto', detail: 'Transaction eliminato', life: 3000 });
            this.router.navigate(['/transaction']);
          },
          complete: () => { },
          error: (e) => {
            console.log(e);
            this.messageService.add({ severity: 'error', summary: 'Attenzione', detail: 'Transaction non eliminato', life: 3000 });
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

  /* Type Edit Utils */
  setType(){
    this.transaction.type = null;
    this.selectedType = null;
    this.typeDeleted = true;
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
