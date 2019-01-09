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
  public IA: boolean;
  public typeJeu: String;
  public nameGame: String;
  constructor(private http: HttpClient, private router: Router, private data: MyData) { 
    
    
    this.dataGame = data;
    const URL = this.router.parseUrl(this.router.url);
    var ia = (URL.queryParams.ia);
    var nj1 = (URL.queryParams.n1);
    this.nameGame=(URL.queryParams.nameGame);
    this.typeJeu = (URL.queryParams.typeJeu);
    var mode = (URL.queryParams.mode);
    console.log(mode);
    if(this.typeJeu==="newgame"){
      if(ia==='false'){
        var nj2 = (URL.queryParams.n2);
        this.IA = false;
        this.http.put("game/newGamePvP/5/"+nj1+"/"+nj2+"/"+mode, {}, {}).subscribe(returnedData => this.dataGame.storage = returnedData);
      }else{
        this.IA = true;
        var difficulty = (URL.queryParams.diff);
        //console.log("game/newGamePvIA/1"+nj1+"/"+mode+"/"+difficulty);
        this.http.put("game/newGamePvIA/1/"+nj1+"/"+mode+"/"+difficulty,{}, {}).subscribe(returnedData => this.dataGame.storage = returnedData);
    
      }
    }else if(this.typeJeu==='replay'){
      this.http.get("game/replay/"+this.nameGame+'.json',{}).subscribe(returnedData => this.dataGame.storage = returnedData);
    }else{
      this.http.get("game/load/"+this.nameGame+'.json',{}).subscribe(returnedData => this.dataGame.storage = returnedData);
    }
    
    
  }

  ngOnInit() {
    
  }
  public undo(){
    //beug de devoir appuyer 2 fois sur undo
    this.http.put("game/undo",{}).subscribe(returnedData =>{
      this.dataGame.storage = returnedData;
      console.log(this.dataGame.storage.undo);
    });
    this.data.testlist = "";
    this.data.actualCase = "";
  }
  public redo(){
    //rajouter le gagnant en redoant => renvoie un player dans le back
    this.http.put("game/redo",{}).subscribe(returnedData =>{
      this.dataGame.storage = returnedData;
      console.log(this.dataGame.storage.save);
    } );
    this.data.testlist = "";
    this.data.actualCase = "";
  }
  public iaPlay(){
    this.http.put("game/mooveIA",{}).subscribe(returnedData => this.dataGame.storage = returnedData);
  }
  public leftClick(x: number, y: number): void {
    if(this.typeJeu!="replay"){
      if(!(this.dataGame.isInList(x,y))){
        this.dataGame.actualCase = JSON.parse('{"x":'+x+',"y":'+y+'}');
      // console.log(this.dataGame.actualCase);
      //this.http.put("game/newGamePvP/5/Antoine/Adrien/Random", {}, {}).subscribe(returnedData => console.log(returnedData));
        this.http.get("game/moovePlayable/"+x+"/"+y+"",{}).subscribe(returnedData =>{
          console.log("game/moovePlayable/"+x+"/"+y+"");
          console.log(returnedData);
          this.dataGame.testlist = returnedData;
        
          console.log(this.dataGame.storage.type);
      });
    }else{
      this.http.put("game/moove/"+this.dataGame.actualCase.x+"/"+x+"/"+this.dataGame.actualCase.y+"/"+y,{}).subscribe(returnedData =>{
      this.dataGame.storage = returnedData;
      if(this.dataGame.storage.type!=='Game'){
        this.http.put("game/save/finishedgame/"+this.nameGame,{}).subscribe();
      }
    /* else{
        (this.dataGame.storage.hasIA==='true' && this.dataGame.storage.color==='Green'){
          for(var i =0;i<3; i++){
            this.http.put("game/mooveIA",{}).subscribe(returnedData => this.dataGame.storage = returnedData);
            setTimeout(()=>{},9000);
          }
        } 
      }*/
      this.dataGame.actualCase = "";
      this.data.testlist = "";
      });
    }  
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
  public save(): void{
    this.http.put("game/save/savedgame/"+this.nameGame,{}).subscribe();
    
  }

  public download(): void{
    //peut être faire un get GAME avant (au cas où la partie soit fini, ie storage = le joueur gagnant)
    var data = "data:text/json;charset=utf-8," + encodeURIComponent(JSON.stringify(this.data.storage));
    var downloader = document.createElement('a');

    downloader.setAttribute('href', data);
    downloader.setAttribute('download', 'file.json');
    downloader.click();
  }
}
