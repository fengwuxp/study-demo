


/**
 * State
 * 职责：
 *    1. 保存应用的数据
 *    2. 订阅来之各个状态处理器的事件
 *    3. 将数据广播到页面
 *
 * 数据的流向：
 *    用户输入或接口请求 -> State -> 页面
 *    sate的数据可以来自其他的sate
 *
 *    例如：ViewState的数据可以来自ApplicationState，SubPage的数据可以来ViewState，像水流一样从高到底
 *    当然也可以反过来，像水水流一样可以从低处到高处
 *
 *
 * State订阅状态处理器（状态处理器负责处理用户输入和接口请求）
 *
 * 页面或者组件订阅State的事件
 *
 * 结构：
 *    抛弃全局的sate，每一个页面都允许存在一个state，state可以随时被创建，或者移除，但是一个页面或组件实例只能有一个state
 *
 */
interface State<T> {

    /**
     * 获取当前保存的状态
     */
    getState: () => T;


    // subscribe:()=>void;
}

/**
 * Abstract State
 */
class AbstractState<T> implements State<T> {

    private state: T;

    getState = () => this.state;


}

/**
 * rxjs State
 */
export default class RxjsState<T> extends AbstractState<T> {

}