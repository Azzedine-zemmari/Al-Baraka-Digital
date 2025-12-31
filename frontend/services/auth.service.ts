import {HttpClient} from '@angular/common/http';
import {environment} from '../environments/environment';
export class AuthService {
  constructor(private http: HttpClient) {}

  register(registerData:any){
    return this.http.post<any>(`${environment.apiUrl}/api/v1/auth/register`,registerData);
  }
}
