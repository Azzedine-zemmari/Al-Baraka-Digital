import { Component } from "@angular/core";
import { CommonModule } from "@angular/common";
import { FormsModule } from "@angular/forms";
import { RetraitService } from "../../services/retrait.service";
import { HttpErrorResponse } from "@angular/common/http";
@Component({
  selector: "app-retrait",
  imports: [CommonModule, FormsModule],
  templateUrl: "./retrait.component.html",
  styleUrl: "./retrait.component.css",
})
export class RetraitComponent {
  amount: number = 0;
  constructor(private retraitService: RetraitService) {}
  makeRetrait() {
    this.retraitService.retrait({ amount: this.amount }).subscribe({
      next: (res: any) => alert("retrait avec success"),
      error: (err: HttpErrorResponse) => {
        const message = err.error.message || "Une erreur est survenue";
        alert(message);
      },
    });
  }
}
