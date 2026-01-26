import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import RetraitRequest from "../interfaces/RetraitRequest";
import { Observable } from "rxjs";
import { environment } from "../environments/environment";

@Injectable({ providedIn: "root" })
export class RetraitService {
  constructor(private http: HttpClient) {}

  retrait(retraitData: RetraitRequest): Observable<any> {
    // const token =
    //   localStorage.getItem("auth_token") ||
    //   sessionStorage.getItem("auth_token");
    // const headers = new HttpHeaders({
    //   Authorization: `Bearer ${token}`,
    // });
    return this.http.post(
      `${environment.apiUrl}/api/v1/retrait/`,
      retraitData,
    );
  }
}
