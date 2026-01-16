import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { environment } from "../environments/environment";
import { TransferRequest } from "../interfaces/TransferRequest";

@Injectable({ providedIn: "root" })

export class Transfer{
  constructor(private http: HttpClient) { }
  
  transfer(transferData : TransferRequest) {
    const token =
      localStorage.getItem("auth_token") ||
      sessionStorage.getItem("auth_token");
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });
    return this.http.post(
      `${environment.apiUrl}/api/v1/transfer/`, transferData, { headers }
    );
    
  }

}