import { Component } from '@angular/core';
import { FormsModule } from "@angular/forms";
import { Router } from "@angular/router";

@Component({
  selector: 'app-form',
  standalone: true,
  imports: [
    FormsModule
  ],
  templateUrl: './form.html',
  styleUrl: './form.scss'
})
export class Form {
  userDetails = {
    name: ''
  }

  constructor(private router: Router) {
  }

  submitForm(form: any): void {
    if (form.valid) {
      this.router.navigate(['/player/' + this.userDetails.name]);
    }
  }
}
