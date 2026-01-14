import { CommonModule } from "@angular/common";
import { Component, OnInit } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { User } from "../../interfaces/User";
import { ShowAllService } from "../../services/showAllUser.service";

@Component({
  selector: "app-dashboard-admin",
  imports: [FormsModule, CommonModule],
  templateUrl: "./dashboard-admin.component.html",
  styleUrl: "./dashboard-admin.component.css",
})
export class DashboardAdminComponent {
  users: User[] = [];

  constructor(private usersData: ShowAllService) {}

  ngOnInit(): void {
    this.loadData();
  }

  loadData(): void {
    this.usersData.getAll().subscribe({
      next: (data) => {
        this.users = data;
      },
      error: (err) => {
        console.error("Erreur chargement utilisateurs", err);
      },
    });
  }
  toggleUserStatus(id: number) {
    
  }
}
