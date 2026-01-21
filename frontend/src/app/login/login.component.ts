import { Component } from "@angular/core";
import {AuthService} from '../../services/auth.service'
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AuthResponse } from "../../interfaces/AuthResponse";

@Component({
    selector : 'app-login',
    templateUrl : './login.component.html',
    standalone : true,
    imports: [FormsModule, CommonModule],
})

export class LoginComponent{

        credentials = {email:'',password:''}
        rememberMe = false;

        constructor(private authService:AuthService , private router:Router){}

        onSubmit(){
            this.authService.login({...this.credentials,
                rememberMe: this.rememberMe
            }).subscribe({
                next:(response : AuthResponse) =>{
                    this.authService.saveToken(response.token,this.rememberMe);
                    console.log("login success");
                    this.router.navigate(['/userpage']);
                },
                error:(error)=>{
                    console.error("login failed : " , error);
                    alert("invalid email or password");
                }
            })
        }

}
