import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { DepositeRequest } from "../interfaces/DepositeRequest";
import { Observable } from "rxjs";
import { environment } from "../environments/environment";
import {UserAuthenticatedResponse} from '../interfaces/UserAuthenticatedResponse';

@Injectable(
  {
    providedIn:"root"
  }
)

export class UserPageService{
  constructor(private http:HttpClient) {}

  loadData(): Observable<UserAuthenticatedResponse> {
    // const token =
    //   localStorage.getItem("auth_token") ||
    //   sessionStorage.getItem("auth_token");

    // const headers = new HttpHeaders({
    //   Authorization: `Bearer ${token}`
    // });

    return this.http.get<UserAuthenticatedResponse>(
      `${environment.apiUrl}/api/v1/auth/userInfo`);
  }

}
