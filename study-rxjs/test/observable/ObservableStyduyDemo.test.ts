import * as log4js from "log4js";
import {from, Observable} from 'rxjs';

const logger = log4js.getLogger();
logger.level = 'debug';

// https://rxjs.dev/guide/observable
describe("test observable study", () => {

    const sleep = (times) => {
        return new Promise((resolve) => {

            setTimeout(resolve, times);
        })
    };

    test("observable study demo 1", () => {

        const observable = new Observable(subscriber => {
            subscriber.next(1);
            subscriber.next(2);
            subscriber.next(3);
            setTimeout(() => {
                subscriber.next(4);
                subscriber.complete();
            }, 1000);
        });
    });

    test("observable study demo 2", async () => {


        const foo = new Observable(subscriber => {
            console.log('Hello');
            subscriber.next(42);
            subscriber.next(44);
        });

        foo.subscribe(x => {
            console.log(x);
        });
        foo.subscribe(y => {
            console.log(y);
        });
    });

    test("observable study demo 3", async () => {
        const foo = new Observable(subscriber => {
            console.log('Hello');
            subscriber.next(42);
            subscriber.next(100);
            subscriber.next(200);
            setTimeout(() => {
                subscriber.next(300); // happens asynchronously
            }, 1000);
        });

        console.log('before');
        foo.subscribe(x => {
            console.log(x);
        });
        console.log('after');
        await sleep(1200);
        console.log("end")
    }, 2 * 1000);

    test("observable study demo 4", async () => {
        // "Error" and "Complete" notifications may happen only once during the Observable Execution, and there can only be either one of them.
        const observable = new Observable(function subscribe(subscriber) {
            try {
                subscriber.next(1);
                subscriber.next(2);
                subscriber.next(3);
                subscriber.complete();
                throw new Error("error")
            } catch (err) {
                subscriber.error(err); // delivers an error if it caught one
            }
        });
        observable.subscribe((data) => {
            logger.debug(data)
        }, (e) => {
            logger.error(e)
        }, () => {
            logger.info("122")
        });
    });

    test("observable study demo 5", async () => {
        const input = [10, 20, 30];
        const observable = from(input);
        const subscription = observable.subscribe(x => console.log(x));
        // Later:
        subscription.unsubscribe();
    });

    test("observable study demo 6", async () => {
        function subscribe(subscriber) {
            const intervalId = setInterval(() => {
                subscriber.next('hi');
            }, 1000);

            return function unsubscribe() {
                clearInterval(intervalId);
            };
        }

        const unsubscribe = subscribe({next: (x) => console.log(x)});

// Later:
        unsubscribe(); // dispose the resources
    })

});
