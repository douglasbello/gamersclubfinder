import { Component } from '@angular/core';
import { FormsModule } from "@angular/forms";
import { Router } from "@angular/router";

@Component({
  selector: 'app-form-tests',
  standalone: true,
  imports: [
    FormsModule
  ],
  templateUrl: './form-tests.component.html',
  styleUrl: './form-tests.component.scss'
})
export class FormTestsComponent {
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
