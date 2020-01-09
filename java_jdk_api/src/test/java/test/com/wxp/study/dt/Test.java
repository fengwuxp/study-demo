package test.com.wxp.study.dt;

public class Test {

    public static void main(String[] args) {
        A a1 = new A();
        A a2 = new B();

        B b = new B();
        C c = new C();
        D d = new D();


//        System.out.println("1--" + a1.show(b));
//        System.out.println("2--" + a1.show(c));
//        System.out.println("3--" + a1.show(d));
        //4--test.com.wxp.study.dt.B and test.com.wxp.study.dt.A .首先a2是A引用，B实例，调用show（test.com.wxp.study.dt.B b）方法，此方法在父类A中没有定义，所以B中方法show(test.com.wxp.study.dt.B b)不会调用（多态必须父类中已定义该方法），
        // 再按优先级为：this.show(O)、super.show(O)、this.show((super)O)、super.show((super)O)，即先查this对象的父类，
        // 没有重头再查参数的父类。查找super.show((super)O)时，B中没有，再向上，找到A中show(test.com.wxp.study.dt.A a),因此执行。
        System.out.println("4--" + a2.show(b));

//        System.out.println("5--" + a2.show(c));  //同上
//        System.out.println("6--" + a2.show(d));  //test.com.wxp.study.dt.A and test.com.wxp.study.dt.D .查找B中没有show(test.com.wxp.study.dt.D d)方法，再查A中，有，执行。
//        System.out.println("7--" + b.show(b));
//        System.out.println("8--" + b.show(c));  //test.com.wxp.study.dt.B and test.com.wxp.study.dt.B .
//        System.out.println("9--" + b.show(d));

    }

}
