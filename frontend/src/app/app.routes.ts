import { Routes } from '@angular/router';
import { RegisterComponent } from './register.component';
import { LoginComponent } from './login.component';
import { UserPageComponent } from './userpage.component';
import { DepositComponent } from './deposit.component';
import { ClientGuard } from '../../guards/ClientGuard';
import { DashboardAdminComponent } from './dashboard-admin/dashboard-admin.component';

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
        path:"dashbard-admin",component:DashboardAdminComponent
    },
    {
        path:'', redirectTo:"/register",pathMatch:'full'
    }
];
