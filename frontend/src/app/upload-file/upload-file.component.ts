import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Operations } from '../../services/Operations.service';
import { Operation } from '../../interfaces/Operation';

@Component({
  selector: 'app-upload-file',
  imports: [CommonModule,FormsModule],
  templateUrl: './upload-file.component.html',
  styleUrl: './upload-file.component.css'
})
export class UploadFileComponent {
  selectedFile!: File;
  operation! : number;
  Operations : Operation[] = [];

  constructor(private operationService: Operations){}

  onFileSelected(event: Event) {
  const input = event.target as HTMLInputElement;
  if (input.files && input.files.length > 0) {
    this.selectedFile = input.files[0];
  }
}

  ngOnInit() : void{
    this.getUnjustifiedOps();
  }

  getUnjustifiedOps() : void{
    this.operationService.showUnjustifiedOps().subscribe({
      next : (data) => 
        this.Operations = data,
      error : () => {
        const errorMessage = 'Deposit failed. Please try again.';
        alert(errorMessage);
      }
    })
  }

  submitJustification(){
     if (!this.operation || !this.selectedFile) {
      alert("Please select an operation and a file");
      return;
    }
    this.operationService
      .uploadJustificatif(this.operation, this.selectedFile)
      .subscribe({
        next: () => {
          alert("File uploaded successfully");
        },
        error: () => {
          alert("Upload failed");
        }
      });
  }

}
