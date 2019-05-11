import {Observable,fromEvent} from "rxjs";


/**
 * rxjs demo
 * @author wxup
 * @create 2018-06-25 13:33
 **/

let button = document.querySelector('button');

// Observable.create(()=>{
//
// })
//
fromEvent(button,"click")
    .subscribe(() => console.log('Clicked!'));
