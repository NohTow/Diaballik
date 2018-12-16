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
    public testlist: any;
    public actualCase: any;

    public constructor() {
        // An example of how storage can be used to store json data.
        // The JSON class can both parse JSON from a string, a produce a string from a JSON object (stringify)
        this.storage = ""
        this.testlist = "";
        this.actualCase = "";
    }

    public isInList(x: number, y: number): boolean{
        let res: boolean = false;
        for(let elem of this.testlist){
            if(elem.newX === x && elem.newY === y){
                res = true;
            }
        }
        return res;
    }
       
}
