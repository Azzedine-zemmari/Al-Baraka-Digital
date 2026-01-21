import { ComponentFixture, TestBed } from '@angular/core/testing';
import { TransferComponent } from './transfer.component';
import { Transfer } from '../../services/transfter.service';
import { of, throwError } from 'rxjs';
import { HttpErrorResponse } from '@angular/common/http';

describe('TransferComponent', () => {
  let component: TransferComponent;
  let fixture: ComponentFixture<TransferComponent>;
  let transferServiceSpy: jasmine.SpyObj<Transfer>;

  beforeEach(async () => {
    transferServiceSpy = jasmine.createSpyObj('Transfer', ['transfer']);

    // use the fake service 
    await TestBed.configureTestingModule({
      imports: [TransferComponent],
      providers: [
        { provide: Transfer, useValue: transferServiceSpy }
      ]
    }).compileComponents();

    // create the component
    fixture = TestBed.createComponent(TransferComponent);
    // get the typscirpt class  
    component = fixture.componentInstance;
    // make ngOnInit
    fixture.detectChanges();
  });

  it('should create', () => {
    // test the component if it exists 
    expect(component).toBeTruthy();
  });

  it('should call transfer service and show success alert', () => {
    transferServiceSpy.transfer.and.returnValue(of({}));
    spyOn(window, 'alert');

    component.amount = 100;
    component.account = 1;

    component.makeTransfer();

    expect(transferServiceSpy.transfer).toHaveBeenCalledWith({
      amount: 100,
      destinationAccountId: 1
    });
    expect(window.alert).toHaveBeenCalledWith('transfer avec success');
  });
});
