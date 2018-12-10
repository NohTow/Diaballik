import { Injectable } from '@angular/core';

// This class is data that will be used through the different components
// by dependency injection.
// See mycomponent.component.ts for a usage of this class.
// See app.module.ts to see how this injection is configured
@Injectable()
export class MyData {
    // The data to store
    // any: https://www.typescriptlang.org/docs/handbook/basic-types.html
    public storage: any;

    public constructor() {
        // An example of how storage can be used to store json data.
        // The JSON class can both parse JSON from a string, a produce a string from a JSON object (stringify)
        this.storage = JSON.parse('{"type":"Game","@id":1,"idGame":5,"board":{"type":"Board","@id":2,"plateau":[[{"type":"Pawn","@id":3,"x":0,"y":0,"hasBall":false,"color":"Yellow"},{"type":"Pawn","@id":4,"x":0,"y":1,"hasBall":false,"color":"Yellow"},{"type":"Pawn","@id":5,"x":0,"y":2,"hasBall":false,"color":"Yellow"},{"type":"Pawn","@id":6,"x":0,"y":3,"hasBall":true,"color":"Yellow"},{"type":"Pawn","@id":7,"x":0,"y":4,"hasBall":false,"color":"Yellow"},{"type":"Pawn","@id":8,"x":0,"y":5,"hasBall":false,"color":"Yellow"},{"type":"Pawn","@id":9,"x":0,"y":6,"hasBall":false,"color":"Yellow"}],[null,null,null,null,null,null,null],[null,null,null,null,null,null,null],[null,null,null,null,null,null,null],[null,null,null,null,null,null,null],[null,null,null,null,null,null,null],[{"type":"Pawn","@id":10,"x":6,"y":0,"hasBall":false,"color":"Green"},{"type":"Pawn","@id":11,"x":6,"y":1,"hasBall":false,"color":"Green"},{"type":"Pawn","@id":12,"x":6,"y":2,"hasBall":false,"color":"Green"},{"type":"Pawn","@id":13,"x":6,"y":3,"hasBall":true,"color":"Green"},{"type":"Pawn","@id":14,"x":6,"y":4,"hasBall":false,"color":"Green"},{"type":"Pawn","@id":15,"x":6,"y":5,"hasBall":false,"color":"Green"},{"type":"Pawn","@id":16,"x":6,"y":6,"hasBall":false,"color":"Green"}]],"list":[3,4,5,6,7,8,9,10,11,12,13,14,15,16]},"nbAction":0,"numTurn":1,"color":"Yellow","hasIA":false,"save":[],"undo":[],"gameBoard":2,"joueur1":{"type":"Humain","@id":17,"name":"Antoine","color":"Yellow"},"joueur2":{"type":"Humain","@id":18,"name":"Adrien","color":"Green"}}');
    }
}
