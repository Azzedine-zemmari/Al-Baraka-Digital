import { Component } from '@angular/core';
import { Transfer } from '../../services/transfter.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-transfer',
  imports: [CommonModule,FormsModule],
  templateUrl: './transfer.component.html',
  styleUrl: './transfer.component.css'
})
export class TransferComponent {
  amount: number = 0;
  account!: number;
  constructor(private transferService : Transfer){}
  makeTransfer() {
    this.transferService.transfer({ amount: this.amount, destinationAccountId: this.account }).subscribe({
      next: () => alert("transfer avec success"),
      error: (err: HttpErrorResponse) => {
        const errorMessage =
          err.error?.message ||
          err.error ||
          err.message ||
          'Deposit failed. Please try again.';

        alert(errorMessage);
      }
      });
}
}
