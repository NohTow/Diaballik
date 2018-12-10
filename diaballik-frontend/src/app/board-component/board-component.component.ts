import { Component, OnInit } from '@angular/core';
import { MyData } from '../mydata';
@Component({
  selector: 'app-board-component',
  templateUrl: './board-component.component.html',
  styleUrls: ['./board-component.component.css']
})
export class BoardComponentComponent implements OnInit {

  title = 'Game Board :';
  
  constructor(private data: MyData) { 
    
  }

  ngOnInit() {
  }

}
