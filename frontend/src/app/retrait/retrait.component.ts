import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-retrait',
  imports:[CommonModule,FormsModule],
  templateUrl: './retrait.component.html',
  styleUrl: './retrait.component.css'
})
export class RetraitComponent {
  amount: number = 0;
  
  makeRetrait() {
    // TODO: Implement the logic to make a retrait
  }
}
