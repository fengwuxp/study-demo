function test() {
    return {
        name:"张三",
        age:14
    }
}
//test();

var NashronTest = Java.type('com.java8.nashron.NashronTest');
print(NashronTest)
var result = NashronTest.fun1('John Doe');
print(result);
