import {HttpClient} from '@angular/common/http';
import {environment} from '../src/environments/environment';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthResponse } from '../src/interfaces/AuthResponse';
import jwt_decode from "jwt-decode";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  constructor(private http: HttpClient) {}

  getRole(): String | null{
    const token = localStorage.getItem('auth_token') || sessionStorage.getItem('auth_token');
    if(!token) return null;

    const decode: any = jwt_decode(token);
    return decode.role;
  }

  isClient(): boolean{
    return this.getRole() === "ROLE_CLIENT";
  }

  isAuthenticated(): boolean {
  const token = localStorage.getItem('auth_token') || sessionStorage.getItem('auth_token');
  return !!token;
}

  logout(){
    localStorage.removeItem('auth_token');
    sessionStorage.removeItem('auth_token');
  }

  register(registerData:any){
    return this.http.post<any>(`${environment.apiUrl}/api/v1/auth/register`,registerData);
  }
  
  login(loginData:any) : Observable<AuthResponse>{
    return this.http.post<AuthResponse>(`${environment.apiUrl}/api/v1/auth/login`,loginData);
  }

  saveToken(token:string , rememberMe: boolean){
    if(rememberMe){
      localStorage.setItem('auth_token',token);
    }else{
      sessionStorage.setItem('auth_token',token);
    }
  }
}
