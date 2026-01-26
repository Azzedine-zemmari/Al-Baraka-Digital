import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../environments/environment';
import { Document } from '../interfaces/Document';

@Injectable({ providedIn: 'root' })
export class DocumentService {

  constructor(private http: HttpClient) {}

  getAllDocuments(): Observable<Document[]> {
    // const token =
    //   localStorage.getItem('auth_token') ||
    //   sessionStorage.getItem('auth_token');

    // const headers = new HttpHeaders({
    //   Authorization: `Bearer ${token}`
    // });

    return this.http.get<Document[]>(
      `${environment.apiUrl}/api/documents`
    );
  }

  getDownloadUrl(operationId: number): string {
    return `${environment.apiUrl}/api/agent/operations/${operationId}/download`;
  }
}
