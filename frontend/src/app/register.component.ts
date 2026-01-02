import {Component} from '@angular/core';
import {AuthService} from '../../services/auth.service'
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';


@Component({
    selector:'app-register',
    templateUrl:'./register.component.html',
    standalone: true,
    imports: [FormsModule, CommonModule],
})

export class RegisterComponent{
    credentials = {email:'',password:'',fullname:''}

    constructor(private authService:AuthService , private router:Router){}

    onSubmit(){
        this.authService.register(this.credentials).subscribe({
            next: (response) =>{
                localStorage.setItem('token',response.token)
                this.router.navigate(['/dashboard'])
            },
            error: (err) => alert('register Failed !')
        });
    }
}