import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AuditRegisterComponent } from './audit-register.component';

describe('AuditRegisterComponent', () => {
  let component: AuditRegisterComponent;
  let fixture: ComponentFixture<AuditRegisterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AuditRegisterComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AuditRegisterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
