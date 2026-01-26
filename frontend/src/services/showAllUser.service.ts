import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { environment } from "../environments/environment";
import { User } from "../interfaces/User";

@Injectable({ providedIn: "root" })
export class ShowAllService {
  constructor(private httpClient: HttpClient) {}

  getAll(): Observable<User[]> {
    // const token =
    //   localStorage.getItem("auth_token") ||
    //   sessionStorage.getItem("auth_token");
    // const headers = new HttpHeaders({
    //   Authorization: `Bearer ${token}`,
    // });
    return this.httpClient.get<User[]>(
      `${environment.apiUrl}/api/admin/users/showAll`,
    );
  }
}
