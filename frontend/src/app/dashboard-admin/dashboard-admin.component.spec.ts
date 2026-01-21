import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DashboardAdminComponent } from './dashboard-admin.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ShowAllService } from '../../services/showAllUser.service';


describe('DashboardAdminComponent', () => {
  let component: DashboardAdminComponent;
  let fixture: ComponentFixture<DashboardAdminComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DashboardAdminComponent,HttpClientTestingModule],
      providers:[ShowAllService]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DashboardAdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
