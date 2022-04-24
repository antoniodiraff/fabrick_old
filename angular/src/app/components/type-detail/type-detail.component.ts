import { Component, ElementRef, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ConfirmationService, MessageService } from 'primeng/api';
import {Location} from '@angular/common';
import { Type } from 'src/app/models/type.model';
import { TypeService } from 'src/app/services/type.service';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { FormUtils } from 'src/app/utils/form.utils';
import { SecurityService } from 'src/app/services/security.service';
import { DataUtils } from 'src/app/utils/data.utils';
import { SecUtils } from 'src/app/security/security.utils';



@Component({
  selector: 'app-type-detail',
  templateUrl: './type-detail.component.html',
  styleUrls: ['./type-detail.component.css']
})
export class TypeDetailComponent implements OnInit {

  id: number;
  type: Type;
  loading: boolean = true;
  edit: boolean = false;
  selectedTypes: Type[];
  submitted: boolean;
  private sub: any;
  typeValidation: FormGroup;



//authMap : Map<String,boolean> = new Map;

  constructor(private route: ActivatedRoute,
              private el: ElementRef,
              private messageService: MessageService,
              private confirmationService: ConfirmationService,
              private router: Router,
              private secService : SecurityService,
	            private _location: Location,
              private typeService: TypeService,

               ) { }

  ngOnInit(): void {
    //this.initPrivileges();
    this.type=new Type;
    this.sub = this.route.params.subscribe(params => {
      this.id = +params['id']; 
      //console.log(this.id);

      this.typeService.get(this.id).subscribe({
        next: (res: Type) => {
          this.type = res;
	  /*Per le Date effettuare questa operazione per vedere il calendar
          this.type.dataType = new Date(this.type.dataType);
	  */

          this.loading = false;
          console.log(this.type);
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

    this.loading=false;

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

    if(this.typeValidation.status=="VALID"){
     if (this.type.enumeration) {
      
      this.typeService.update(this.type).subscribe({
        next: (res) => {
          //console.log(res);
          this.messageService.add({ severity: 'success', summary: 'Fatto', detail: 'Type aggiornato', life: 3000 });
        },
        complete: () => { 
          this.edit=false;
          this.submitted = false;
          let myTag = this.el.nativeElement.querySelector("i");
          this.closeIcon();
        },
        error: (e) => {
          console.log("Error: ",e);
          this.messageService.add({ severity: 'error', summary: 'Attenzione', detail: 'Type non modificato', life: 3000 });
        }
      });
    }
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

  deleteElement(type: Type, messageOn: boolean = true) {
    this.confirmationService.confirm({
      message: 'Sei sicuro di voler eliminare l \'elemento selezionato ?',
      header: 'Conferma',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.typeService.delete(type.enumeration).subscribe({
          next: (res) => {
            if(messageOn)
            this.messageService.add({ severity: 'success', summary: 'Fatto', detail: 'Type eliminato', life: 3000 });
            this.router.navigate(['/type']);
          },
          complete: () => { },
          error: (e) => {
            console.log(e);
            this.messageService.add({ severity: 'error', summary: 'Attenzione', detail: 'Type non eliminato', life: 3000 });
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
