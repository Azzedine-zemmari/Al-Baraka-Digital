import {HttpClient} from '@angular/common/http';
import {environment} from '../src/environments/environment';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  constructor(private http: HttpClient) {}

  register(registerData:any){
    return this.http.post<any>(`${environment.apiUrl}/api/v1/auth/register`,registerData);
  }
}
