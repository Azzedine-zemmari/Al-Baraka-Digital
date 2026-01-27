import { Injectable } from "@angular/core";
import { ActivatedRouteSnapshot, CanActivate,GuardResult,MaybeAsync,Router, RouterStateSnapshot } from "@angular/router";
import { AuthService } from "../src/services/auth.service";


@Injectable({providedIn:"root"})

export class AdminGuard implements CanActivate {
    constructor(private authService:AuthService , private router:Router){}

   canActivate(): boolean{
    if(this.authService.isAuthenticated() && this.authService.isAdmin()){
        return true;
    }
    this.router.navigate(['/login']);
    return false;
   }
}