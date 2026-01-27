import { Component } from '@angular/core';
import { Transfer } from '../../services/transfter.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-transfer',
  imports: [CommonModule,FormsModule,RouterModule],
  templateUrl: './transfer.component.html',
  styleUrl: './transfer.component.css'
})
export class TransferComponent {
  amount: number = 0;
  account!: number;
  constructor(private transferService : Transfer){}
  makeTransfer() {
    this.transferService.transfer({ amount: this.amount, destinationAccountId: this.account }).subscribe({
      next: (message : string) => alert(message),
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
