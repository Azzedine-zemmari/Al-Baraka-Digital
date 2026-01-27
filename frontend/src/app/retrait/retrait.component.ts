import { Component } from "@angular/core";
import { CommonModule } from "@angular/common";
import { FormsModule } from "@angular/forms";
import { RetraitService } from "../../services/retrait.service";
import { HttpErrorResponse } from "@angular/common/http";
import { RouterModule } from "@angular/router";
@Component({
  selector: "app-retrait",
  imports: [CommonModule, FormsModule,RouterModule],
  templateUrl: "./retrait.component.html",
  styleUrl: "./retrait.component.css",
})
export class RetraitComponent {
  amount: number = 0;
  constructor(private retraitService: RetraitService) {}
  makeRetrait() {
    this.retraitService.retrait({ amount: this.amount }).subscribe({
      next: (message: string) => alert(message),
      error: (err: HttpErrorResponse) => {
        const message = err.error.message || "Une erreur est survenue";
        alert(message);
      },
    });
  }
}
