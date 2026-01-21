import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DownloadFileComponent } from './download-file.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { DocumentService } from '../../services/document.service';

describe('DownloadFileComponent', () => {
  let component: DownloadFileComponent;
  let fixture: ComponentFixture<DownloadFileComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DownloadFileComponent,HttpClientTestingModule ],
      providers:[DocumentService]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DownloadFileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
