import {Observable, fromEvent, Observer, timer, range, ReplaySubject, from} from "rxjs";
import * as React from "react";
import ReactDOM from "react-dom";
import {delay, map, reduce, retryWhen, scan} from 'rxjs/operators';

let observable: Observable<number> = Observable.create((observer: Observer<number>) => {

    [1, 2, 4].forEach((item) => {
        observer.next(item);
    });

});

// function App(props) {
//
//     return <div></div>
// }


interface AppProps {

    store: any;
}

interface ItemProps {

    renderCount: number;
}

class Item extends React.Component<ItemProps, {}> {


    constructor(props: ItemProps, context: any) {
        super(props, context);
    }

    state = {
        count: this.props.renderCount
    };

    render(): React.ReactNode {
        const {renderCount} = this.props;
        const {count} = this.state;
        return <div>renderCount：{renderCount},{count}</div>
    }
}

class App extends React.Component<AppProps, {}> {


    constructor(props: AppProps, context: any) {
        super(props, context);
    }

    state = {
        times: -1,
        num: 0
    };

    componentDidMount(): void {
        timer(1000, 2000).subscribe((times) => {
            this.setState({times});
        });
        range(1, 10).pipe(reduce(
            (pev, current) => pev + current),
            map((num) => num - 3),
            delay(1500))
            .subscribe((num) => {
                this.setState({num});
            })
    }

    render(): React.ReactNode {

        const {times, num} = this.state;
        const {store} = this.props;

        return <React.Fragment>
            <div>times：{times}</div>
            <div>num：{num}</div>
            <div>name：{store.name}</div>
            <Item renderCount={store.count}/>
        </React.Fragment>
    }
}


interface AppStore {

    count: number;

    name: string;

    version: string;

    osName: string
}


export type SetPropMethod<P> = <K extends keyof P>(
    state: Pick<P, K> | P
) => void;

export interface ReducerHolder<P> {

    /**
     * 默认的state
     */
    store: P;


    /**
     * 设置 props 用来更新 store中的state
     * 所有的 reducer更新state都需要通过该方法
     * @param newState
     * @param state
     */
    setProps: SetPropMethod<P>;
}

class StoreStateManager implements ReducerHolder<AppStore> {

    store: AppStore = {
        count: 0,
        name: "1",
        version: "1.0.0",
        osName: "win10",
    };


    setProps = <K extends keyof AppStore>(
        state: Pick<AppStore, K> | AppStore,
    ) => {
        this.store.count = state.count;
        if (state.name) {
            this.store.name = state.name;
        }
    };


}

const stateManager = new StoreStateManager();

stateManager.setProps({count: 2, name: "李四"});

from([1, 2, 4, 5]).pipe(delay(200)).subscribe((item) => {
    p.next({
        name: "count",
        value: item
    });
});


// 1. Store （data source） 用于保存数据 支出全局和局部
// 2. store都是一个Observable 和 observer（即是一个subject）

const p: ReplaySubject<any> = new ReplaySubject<any>();

interface Payload<T> {

    name: string;

    value: T
}

p.subscribe((payload: Payload<any>) => {
    const state = {};
    state[payload.name] = payload.value; 
    stateManager.setProps(state);
});


// timer(1000, 2000).subscribe((times) => {
//
//     p.next({
//         name: "count",
//         value: times
//     });
// });

ReactDOM.render(<App store={stateManager.store}/>, document.getElementById("app"));