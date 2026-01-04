import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { DepositeRequest } from "../src/interfaces/DepositeRequest";
import { Observable } from "rxjs";
import { environment } from "../src/environments/environment";

@Injectable({providedIn:"root"})

export class DepositService{
    constructor(private http: HttpClient){}

    deposit(depositData: DepositeRequest) : Observable<any>{
        const token = localStorage.getItem("auth_token") || sessionStorage.getItem("auth_token");
        const headers = {Authorization : `Bearer ${token}`}
        return this.http.post(`${environment.apiUrl}/v1/deposite/`,depositData,{headers})
    }
}