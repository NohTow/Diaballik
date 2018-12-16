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
  public dataGame: MyData;
  
  constructor(private http: HttpClient, private router: Router, private data: MyData) { 
    this.dataGame = data;
  }

  ngOnInit() {
  }
  public undo(){
    this.http.put("game/undo",{}).subscribe(returnedData => this.dataGame.storage = returnedData);
  }
  public redo(){
    this.http.put("game/redo",{}).subscribe(returnedData => this.dataGame.storage = returnedData);
  }
  public iaPlay(){
    this.http.put("game/mooveIA",{}).subscribe(returnedData => this.dataGame.storage = returnedData);
  }
  public leftClick(x: number, y: number): void {
    if(this.dataGame.storage === ""){
      //this.http.put("game/newGamePvP/5/Antoine/Adrien/Random", {}, {}).subscribe(returnedData => this.dataGame.storage = returnedData);
      this.http.put("game/newGamePvIA/1/Antoine/Standard/Noob",{}, {}).subscribe(returnedData => this.dataGame.storage = returnedData);
    }
    console.log(this.dataGame.storage)
    if(!(this.dataGame.isInList(x,y))){
      this.dataGame.actualCase = JSON.parse('{"x":'+x+',"y":'+y+'}');
     // console.log(this.dataGame.actualCase);
     //this.http.put("game/newGamePvP/5/Antoine/Adrien/Random", {}, {}).subscribe(returnedData => console.log(returnedData));
       this.http.get("game/moovePlayable/"+x+"/"+y+"",{}).subscribe(returnedData =>{
        console.log("game/moovePlayable/"+x+"/"+y+"");
        console.log(returnedData);
        this.dataGame.testlist = returnedData;
        //console.log(this.dataGame.testlist);
    });
  }else{
    this.http.put("game/moove/"+this.dataGame.actualCase.x+"/"+x+"/"+this.dataGame.actualCase.y+"/"+y,{}).subscribe(returnedData =>{
    this.dataGame.storage = returnedData;
    this.dataGame.actualCase = "";
    this.data.testlist = "";
    });
  }
    //this.dataGame.testlist = JSON.parse('{"list":[{"x":'+x+',"y":'+y+'},{"x":3,"y":7}]}');
   // this.http.put("game/newGamePvP/5/Antoine/Asn/Random", {}, {}).subscribe(returnedData => {
     // console.log(returnedData);
     // this.dataGame.storage = returnedData;
     // console.log(this.dataGame.storage.joueur2.name);
    //console.log(this.dataGame.isInList(1,1));
    //console.log(this.dataGame.isInList(7,5));
   // this.http.put("game/newGamePvP/5/Antoine/Adrien/Random", {}, {}).subscribe(returnedData => console.log(returnedData));
      
   // });
  }



}
