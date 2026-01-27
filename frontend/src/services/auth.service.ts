import {HttpClient} from '@angular/common/http';
import {environment} from '../environments/environment';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthResponse } from '../interfaces/AuthResponse';
import { jwtDecode } from 'jwt-decode';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  constructor(private http: HttpClient, private router:Router) {}

  getRole(): String | null{
    const token = localStorage.getItem('auth_token') || sessionStorage.getItem('auth_token');
    if(!token) return null;

    const decode: any = jwtDecode(token);
    return decode.role;
  }

  isClient(): boolean{
    return this.getRole() === "ROLE_CLIENT";
  }

  isAgent(): boolean{
    return this.getRole() === "ROLE_AGENT_BANCAIRE";
  }
  isAdmin(): boolean{
    return this.getRole() === "ROLE_ADMIN";
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
    console.log("login init...")
    return this.http.post<AuthResponse>(`${environment.apiUrl}/api/v1/auth/login`,loginData);
  }

  saveToken(token:string , rememberMe: boolean){
    if(rememberMe){
      localStorage.setItem('auth_token',token);
    }else{
      sessionStorage.setItem('auth_token',token);
    }
  }
  redirectByRole() {
    const role = this.getRole();

    switch (role) {
      case 'ROLE_ADMIN':
        this.router.navigate(['/dashbard-admin']);
        break;

      case 'ROLE_AGENT_BANCAIRE':
        this.router.navigate(['/documents']);
        break;

      case 'ROLE_CLIENT':
        this.router.navigate(['/userpage']);
        break;

      default:
        this.router.navigate(['/login']);
    }
  }
}
