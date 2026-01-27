import { CommonModule } from "@angular/common";
import { Component } from "@angular/core";
import { RouterModule } from "@angular/router";
import { DepositService } from "../../services/deposite.service";
import { FormsModule } from "@angular/forms";
import { HttpErrorResponse } from '@angular/common/http';


@Component({
    selector:'app-deposit',
    templateUrl:'./deposite.component.html',
    standalone:true,
    imports:[CommonModule,FormsModule,RouterModule]
})

export class DepositComponent{
    amount = 0;

    constructor(private depositeService:DepositService){}

    makeDeposit(){
        this.depositeService.deposit({amount:this.amount}).subscribe({
            next: (message : string)=> alert(message),
            error: (err: HttpErrorResponse) => {
            const errorMessage =
            'Deposit failed. Please try again.';

            alert(errorMessage);
        }
        });
    }
}
