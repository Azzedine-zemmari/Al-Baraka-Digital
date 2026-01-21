import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RetraitComponent } from './retrait.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RetraitService } from '../../services/retrait.service';


describe('RetraitComponent', () => {
  let component: RetraitComponent;
  let fixture: ComponentFixture<RetraitComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RetraitComponent,HttpClientTestingModule],
      providers:[RetraitService]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RetraitComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
