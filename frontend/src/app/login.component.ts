import { Component } from "@angular/core";
import {AuthService} from '../../services/auth.service'
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AuthResponse } from "../interfaces/AuthResponse";

@Component({
    selector : 'app-login',
    templateUrl : './login.component.html',
    standalone : true,
    imports: [FormsModule, CommonModule],
})

export class LoginComponent{

        credentials = {email:'',password:''}

        constructor(private authService:AuthService , private router:Router){}

        onSubmit(){
            this.authService.login(this.credentials).subscribe({
                next:(response : AuthResponse) =>{
                    this.authService.saveToken(response.token);
                    console.log("login success");
                },
                error:(error)=>{
                    console.error("login failed : " , error);
                    alert("invalid email or password");
                }
            })
        }

}