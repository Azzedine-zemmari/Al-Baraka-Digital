import { CommonModule } from "@angular/common";
import { Component } from "@angular/core";
import { RouterModule } from "@angular/router";
import { DepositService } from "../../services/deposite.service";
import { FormsModule } from "@angular/forms";

@Component({
    selector:'app-deposit',
    templateUrl:'./deposite.component.html',
    standalone:true,
    imports:[CommonModule,FormsModule]
})

export class DepositComponent{
    amount : number = 0;
    
    constructor(private depositeService:DepositService){}

    makeDeposit(){
        this.depositeService.deposit({amount:this.amount}).subscribe({
            next: (res)=> alert("deposit successfully"),
            error: (err) => alert("deposit failed : " + err)
        });
    }
}