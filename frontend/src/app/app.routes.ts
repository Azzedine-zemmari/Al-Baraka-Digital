import { Routes } from "@angular/router";
import { RegisterComponent } from "./register/register.component";
import { LoginComponent } from "./login/login.component";
import { UserPageComponent } from "./userpage/userpage.component";
import { DepositComponent } from "./deposite/deposit.component";
import { ClientGuard } from "../../guards/ClientGuard";
import { DashboardAdminComponent } from "./dashboard-admin/dashboard-admin.component";
import { RetraitComponent } from "./retrait/retrait.component";
import { TransferComponent } from "./transfer/transfer.component";
import { UploadFileComponent } from "./upload-file/upload-file.component";
import { DownloadFileComponent } from "./download-file/download-file.component";
import { AgentGuard } from "../../guards/AgentGuard";
import { AdminGuard } from "../../guards/AdminGuard";

export const routes: Routes = [
  {
    path: "register",
    component: RegisterComponent,
  },
  {
    path: "login",
    component: LoginComponent,
  },
  {
    path: "userpage",
    component: UserPageComponent,
    canActivate:[ClientGuard]
  },
  {
    path: "deposit",
    component: DepositComponent,
    canActivate: [ClientGuard],
  },
  {
    path: "dashbard-admin",
    component: DashboardAdminComponent,
    canActivate:[AdminGuard]
  },
  {
    path: "retrait",
    component: RetraitComponent,
    canActivate:[ClientGuard]
  },
  {
    path: "transfer",
    component: TransferComponent,
    canActivate:[ClientGuard]
  },
  {
    path: "uploadjustif",
    component: UploadFileComponent,
    canActivate: [ClientGuard]
  },
  {
    path:"documents",
    component: DownloadFileComponent,
    canActivate:[AgentGuard]
  },
  {
    path: "",
    redirectTo: "/register",
    pathMatch: "full",
  },  
];
