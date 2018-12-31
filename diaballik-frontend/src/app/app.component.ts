import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Diaballik';
  constructor(private http: HttpClient, private router: Router) { 
  }
  public retourMenu(): void{
    this.router.navigate(['menu'],{queryParams: {}});
  }
}
