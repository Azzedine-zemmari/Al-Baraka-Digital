import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { environment } from "../environments/environment";
import { Operation } from "../interfaces/Operation";
import { Observable } from "rxjs";

@Injectable({ providedIn: "root" })

export class Operations {
    constructor(private http: HttpClient) { }

    showUnjustifiedOps(): Observable<Operation[]> {
        // const token =
        //     localStorage.getItem("auth_token") ||
        //     sessionStorage.getItem("auth_token");
        // const headers = new HttpHeaders({
        //     Authorization: `Bearer ${token}`,
        // });
        return this.http.get<Operation[]>(`${environment.apiUrl}/api/client/operations/unjustified`)
    }
    uploadJustificatif(operationId: number, file: File) {
        // const token =
        //     localStorage.getItem("auth_token") ||
        //     sessionStorage.getItem("auth_token");
        // const headers = new HttpHeaders({
        //     Authorization: `Bearer ${token}`,
        // });

        const formData = new FormData();
        formData.append("justificatif", file);

        return this.http.post(
            `${environment.apiUrl}/api/client/operations/${operationId}/document`, formData
            
        )

    }
}