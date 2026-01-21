import { ComponentFixture, TestBed } from '@angular/core/testing';
import { UploadFileComponent } from './upload-file.component';

import { HttpClientTestingModule } from '@angular/common/http/testing';
import { Operations } from '../../services/Operations.service';


describe('UploadFileComponent', () => {

  let component: UploadFileComponent;
  let fixture: ComponentFixture<UploadFileComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        UploadFileComponent,
        HttpClientTestingModule 
      ],
      providers:[Operations]
    }).compileComponents();

    fixture = TestBed.createComponent(UploadFileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
