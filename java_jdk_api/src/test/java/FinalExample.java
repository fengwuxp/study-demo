

public class FinalExample{
    final int i;
    static FinalExample obj;

    //由于（1），（2）可能被重排序，当线程1开始执行，被构造的对象的引用会在构造函数内溢出，
    // 然后线程2开始执行就访问到了还未赋值的final 变量 i, 最后线程1才在构造函数内部给 i 赋值。
    // 这就错误的查看了。
    public FinalExample(){
        i=1;//(1)
        obj=this;//(2)
        //(1),(2)可能被重排序
    }
    //线程1
    public static void writer(){
        new FinalExample();
    }
    //线程2
    public static void reader(){
        if(obj !=null){
            int temp =obj.i;
        }
    }
    static boolean out(char c){
        System.out.println(c);
        return true;
    }
    public static void main(String[] args) {

        int i = 0;
        for(out('A');out('B') && (i<2);out('C')){
            i++;
            out('D');
        }
    }
}