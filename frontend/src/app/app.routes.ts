import { Routes } from '@angular/router';
import { RegisterComponent } from './register.component';
import { LoginComponent } from './login.component';
import { UserPageComponent } from './userpage.component';

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
        path:'', redirectTo:"/register",pathMatch:'full'
    }
];
