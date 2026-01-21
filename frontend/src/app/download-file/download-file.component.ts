import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DocumentService } from '../../services/document.service';
import { Document } from '../../interfaces/Document';
import { SafeResourceUrl , DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'app-download-file',
  imports: [CommonModule],
  templateUrl: './download-file.component.html',
  styleUrl: './download-file.component.css'
})
export class DownloadFileComponent {
  documents: Document[] = [];
  previewUrl!: SafeResourceUrl | null;

  constructor(
    private documentService: DocumentService,
    private sanitizer: DomSanitizer
  ) {}

  ngOnInit(): void {
    this.documentService.getAllDocuments().subscribe(data => {
      this.documents = data;
    });
  }

  preview(operationId: number) {
    const url = this.documentService.getDownloadUrl(operationId);
    this.previewUrl = this.sanitizer.bypassSecurityTrustResourceUrl(url);
  }

  closePreview() {
    this.previewUrl = null;
  }
}
