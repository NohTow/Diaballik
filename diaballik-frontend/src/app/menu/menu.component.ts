import { Component, OnInit } from '@angular/core';
import { MyData } from '../mydata';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';


@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {
  public ia: boolean;
  public n1: String;
  public n2: String;
  public mode: String;
  public diff: String;
  public dataGame: MyData;
  
  constructor(private http: HttpClient, private router: Router, private data: MyData){
    this.dataGame = data;
    this.ia = false;
    this.n1=""
    this.n2=""
    this.diff="Noob";
    this.mode="Standard";
  }

  ngOnInit() {
    
  }
  onClick(){
    this.router.navigate(['board-component']);
  }
  onVali(){
     
      
     // this.http.put("game/newGamePvP/5/Antoine/Adrien/Random", {}, {}).subscribe(returnedData => this.dataGame.storage = returnedData);
      this.router.navigate(['board-component'],{queryParams: {ia:this.ia, n1:this.n1, n2:this.n2, mode:this.mode, diff: this.diff}});
      //Mettre les valeurs necessaires à la création de la game dans les queryParams pour créer le board component dans onInit avec ces params (pour les f5)
      //On pourrait créer la game ici, mais les f5 la casserait
  }
    


}
