import { ActivatedRouteSnapshot, CanActivate , GuardResult, MaybeAsync, Router, RouterStateSnapshot } from "@angular/router";
import { AuthService } from "../src/services/auth.service";
import { Injectable } from "@angular/core";


@Injectable({providedIn:"root"})

export class AgentGuard implements CanActivate{
    constructor(private authService:AuthService , private router : Router){}

    canActivate(): boolean{
        if(this.authService.isAuthenticated() && this.authService.isAgent() ){
            return true;
        }
        this.router.navigate(['/login'])
        return false;
    }
}