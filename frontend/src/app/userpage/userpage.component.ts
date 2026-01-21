import { Component, OnInit } from "@angular/core";
import { CommonModule } from "@angular/common";
import { Router, RouterModule } from "@angular/router";
import { AuthService } from "../../services/auth.service";
import { UserPageService } from "../../services/userpage.service";
import {UserAuthenticatedResponse} from '../../interfaces/UserAuthenticatedResponse';

@Component({
  selector: "app-userpage",
  standalone: true,
  templateUrl: "./userpage.component.html",
  imports: [RouterModule, CommonModule]
})
export class UserPageComponent implements OnInit {

  user!: UserAuthenticatedResponse;
  isLoading = true;

  constructor(
    private userPageService: UserPageService,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadData();
  }

  loadData(): void {
    this.userPageService.loadData().subscribe({
      next: (data) => {
        this.user = data;
        this.isLoading = false;
      },
      error: (err) => {
        console.error(err);
        this.isLoading = false;
      }
    });
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}
