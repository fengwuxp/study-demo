package test.com.wxp.study.dt

//def k = {
//    v -> print(v)
//}
//
//def m(m,h=2) {
//    print(m)
//}
//
//k.print("1234");
//m(456)

def gg = {
    v -> print(v)
}
gg {
//    "1"
//    "2"
//    "3"
}
def list = [1,2,3]  //定义一个List

//调用它的each，这段代码的格式看不懂了吧？each是个函数，圆括号去哪了?

list.each ({
    println(it)
})


class Robot{
    def type,height,width
    def access(location, weight, fragile){
        println "Received fragile? $fragile,weight: $weight,loc:$location"
    }

}


robot = new Robot(type:'arm',width:10,height:40)
println "$robot.type,$robot.height,$robot.width"

robot.access "x":"30","y":20,"z":10,50,true
/*可以修改参数顺序 */
robot.access(50,true,x:30,y:20,z:10)
//可以修改参数顺序
robot.access(true,x:30,y:20,z:10,50)
