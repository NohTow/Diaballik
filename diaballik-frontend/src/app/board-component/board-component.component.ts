import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { MyData } from '../mydata';

@Component({
  selector: 'app-board-component',
  templateUrl: './board-component.component.html',
  styleUrls: ['./board-component.component.css']
})
export class BoardComponentComponent implements OnInit {

  title = 'Game Board :';
  
  constructor(private http: HttpClient, private router: Router, private data: MyData) { 
    
  }

  ngOnInit() {
  }

  public leftClick(event: MouseEvent): void {
    
    this.http.put("game/newGamePvP/5/Antoine/Adrien/Random", {}, {}).subscribe(returnedData => console.log(returnedData));
      
  }



}
