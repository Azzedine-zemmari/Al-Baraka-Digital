import { Component } from "@angular/core";
import { AuthService } from "../../services/auth.service";
import { Route, Router, RouterModule } from "@angular/router";

@Component({
    selector:"app-userpage",
    standalone:true,
    templateUrl : "./userpage.component.html",
    imports:[RouterModule]
})


export class UserPageComponent{
    constructor(private authService:AuthService , private route:Router){}
    
    logout(){
        this.authService.logout();
        this.route.navigate(['/login'])
    }
    
}