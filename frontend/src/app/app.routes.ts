import { Routes } from "@angular/router";
import { RegisterComponent } from "./register.component";
import { LoginComponent } from "./login.component";
import { UserPageComponent } from "./userpage.component";
import { DepositComponent } from "./deposit.component";
import { ClientGuard } from "../../guards/ClientGuard";
import { DashboardAdminComponent } from "./dashboard-admin/dashboard-admin.component";
import { RetraitComponent } from "./retrait/retrait.component";
import { TransferComponent } from "./transfer/transfer.component";
import { UploadFileComponent } from "./upload-file/upload-file.component";
import { DownloadFileComponent } from "./download-file/download-file.component";

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
  },
  {
    path: "deposit",
    component: DepositComponent,
    canActivate: [ClientGuard],
  },
  {
    path: "dashbard-admin",
    component: DashboardAdminComponent,
  },
  {
    path: "retrait",
    component: RetraitComponent,
  },
  {
    path: "transfer",
    component: TransferComponent
  },
  {
    path: "uploadjustif",
    component: UploadFileComponent,
    canActivate: [ClientGuard]
  },
  {
    path:"documents",
    component: DownloadFileComponent
  },
  {
    path: "",
    redirectTo: "/register",
    pathMatch: "full",
  },  
];
