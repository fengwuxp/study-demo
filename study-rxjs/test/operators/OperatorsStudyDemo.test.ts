import * as log4js from "log4js";
import {asyncScheduler, generate, interval, of, VirtualTimeScheduler} from 'rxjs';
import {first, map} from 'rxjs/operators';
import {AsyncScheduler} from "rxjs/internal/scheduler/AsyncScheduler";

const logger = log4js.getLogger();
logger.level = 'debug';

// https://rxjs.dev/guide/operators
describe("test operators study", () => {

    const sleep = (times) => {
        return new Promise((resolve) => {

            setTimeout(resolve, times);
        })
    };

    test("operators map study ", () => {


        map((x: number, index) => x * x)(of(1, 2, 3))
            .subscribe((v) => console.log(`value: ${v}`));

        // Logs:
        // value: 1
        // value: 4
        // value: 9
    });

    test("operators first study ", () => {

        first()(of(1, 2, 3)).subscribe((v) => console.log(`value: ${v}`));
        // Logs:
        // value: 1
    });

    test("operators interval study ", async () => {

        const observable = interval(1000 /* number of milliseconds */);

        observable.subscribe((x) => {
            console.log(x)
        });

        await sleep(5 * 1000);
    });


    test("operators generate study ", async () => {

        const observable = generate(0, (state) => state % 2 == 0, (state) => {
            return state + 1;
        }, asyncScheduler);

        observable.subscribe((x) => {
            console.log(x)
        });

        await sleep(5 * 1000);
    });

});
