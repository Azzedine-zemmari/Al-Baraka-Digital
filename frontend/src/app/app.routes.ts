import { Routes } from '@angular/router';
import { RegisterComponent } from './register.component';
import { LoginComponent } from './login.component';
import { UserPageComponent } from './userpage.component';
import { DepositComponent } from './deposit.component';
import { ClientGuard } from '../../guards/ClientGuard';

export const routes: Routes = [
    {
        path:"register",component:RegisterComponent
    },
    {
        path:"login",component:LoginComponent
    },
    {
        path:"userpage" ,component:UserPageComponent
    },
    {
        path:"deposit",component:DepositComponent,canActivate:[ClientGuard]
    },
    {
        path:'', redirectTo:"/register",pathMatch:'full'
    }
];
