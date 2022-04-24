import { Component, OnInit } from '@angular/core';
import { ConfirmationService, LazyLoadEvent, MessageService, SortEvent } from 'primeng/api';
import { Type } from 'src/app/models/type.model';
import { Paginator } from 'src/app/models/paginator.model';
import { TypeService } from 'src/app/services/type.service';
import { Router } from '@angular/router';
import { environment } from 'src/environments/environment';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { FormUtils } from 'src/app/utils/form.utils';
import { SecurityService } from 'src/app/services/security.service';
import { DataUtils } from 'src/app/utils/data.utils';
import { SecUtils } from 'src/app/security/security.utils';


@Component({
  selector: 'app-type',
  templateUrl: './type.component.html',
  styleUrls: ['./type.component.css']
})
export class TypeComponent implements OnInit {

  typeDialog: boolean;
  types: Type[];
  type: Type;
  selectedTypes: Type[];
  submitted: boolean;
  totalRecords: number;
  loading: boolean;
  defaultRows: number = 10;
  errorCode:any;
  typeValidation: FormGroup;

  //authMap : Map<String,boolean> = new Map;

  constructor(private typeService: TypeService, 
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
    this.type = {};
    this.submitted = false;
    this.typeDialog = true;
  }

  deleteSelectedElements() {
    this.confirmationService.confirm({
      message: 'Sei sicuro di voler eliminare gll elementi selezionati?',
      header: 'Conferma',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.typeService.deleteAll(this.selectedTypes).subscribe({
          next: (res) => {
            console.log(res);
            this.types = this.types.filter(val => !this.selectedTypes.includes(val));
            this.selectedTypes = null;
          },
          complete: () => { 
            this.messageService.add({ severity: 'success', summary: 'Fatto', detail: 'Types eliminati', life: 3000 });
          },
          error: (e) => { 
            this.errorCode=e.status;
            console.error(e);
          }
        });
        
      }
    });
  }

  editElement(type: Type) {
    this.type = type;
    /*
    In caso di campi Date va fatta questa operazione
    this.type.datatype = new Date(type.datatype);
    */
    this.typeDialog = true;
  }

  deleteElement(type: Type, messageOn: boolean = true) {
    this.confirmationService.confirm({
      message: 'Sei sicuro di voler eliminare l \'elemento selezionato?',
      header: 'Conferma',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.types = this.types.filter(val => val.enumeration !== type.enumeration);
        this.typeService.delete(type.enumeration).subscribe({
          next: (res) => {
            console.log(res);
            this.type = {};
            if(messageOn)
            this.messageService.add({ severity: 'success', summary: 'Fatto', detail: 'Type eliminato', life: 3000 });
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
    this.typeDialog = false;
    this.submitted = false;
  }

  saveElement() {
    this.submitted = true;
    this.startValidator();

    if(this.typeValidation.status=="VALID"){
      if (this.type.enumeration) {
        //this.types[this.findIndexById(this.type.enumeration)] = this.type;
        this.typeService.update(this.type).subscribe({
          next: (res) => {
            console.log(res);
            this.messageService.add({ severity: 'success', summary: 'Fatto', detail: 'Type aggiornato', life: 3000 });
          },
          complete: () => { },
          error: (e) => { 
            this.errorCode=e.status;
            console.error(e);
          }
        });
      }
      else {
        //console.log(this.type);
        this.typeService.create(this.type).subscribe({
          next: (res) => {
            //console.log(res);
            this.loadList(0, this.defaultRows, 1, "enumeration");
          },
          complete: () => { 
            this.messageService.add({ severity: 'success', summary: 'Fatto', detail: 'Type Creato', life: 3000 });
          },
          error: (e) => { 
	   this.errorCode=e.status;
	   console.error(e);
          }
        });

      }

      this.types = [...this.types];
      this.typeDialog = false;
      this.type = {};
    }
  }

  startValidator(){
    this.typeValidation = new FormGroup({
      enumeration: new FormControl(this.type.enumeration, Validators.compose([
      ])),

      value: new FormControl(this.type.value, Validators.compose([
      ])),


   });
   //console.log(this.typeValidation);
   
  }

  getValidationMessage(typeValidationField){
    return FormUtils.getValidationMessage(typeValidationField);
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
      event.sortField = "enumeration";
    }
    this.loadList(currentPage, event.rows, event.sortOrder, event.sortField);

  }

  loadList(currentPage: number, size: number, sortOrder: number, sortField: string) {
    this.typeService.getPaginated(currentPage, size, sortOrder, sortField).subscribe({
      next: (res: Paginator) => {
        this.types = res.content;
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

  detailElement(type: Type) {
    //console.log(type);
    this.router.navigate(['/type-details', type.enumeration]);
  }

  downloadReport(type: string){
      this.typeService.getReport(type)
        .subscribe(x => {
            var newBlob = new Blob([x], { type: "application/pdf" });
            const data = window.URL.createObjectURL(newBlob);
            
            var link = document.createElement('a');
            link.href = data;
            link.download = "type."+type;
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
    //return this.authMap.get("type"+SecUtils.authMap.get(operation));
    return true;
  }

}
