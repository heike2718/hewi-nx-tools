import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'auth-api',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './api.component.html',
  styleUrl: './api.component.scss',
})
export class ApiComponent {}
