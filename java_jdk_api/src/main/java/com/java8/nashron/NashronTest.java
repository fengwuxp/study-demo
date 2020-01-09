package com.java8.nashron;

import jdk.nashorn.api.scripting.ScriptObjectMirror;
import jdk.nashorn.internal.objects.Global;
import jdk.nashorn.internal.runtime.ScriptObject;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.FileReader;
import java.net.URL;
import java.util.Map;
import java.util.Set;

public class NashronTest {

    public static void main(String[] args) throws Exception {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName(
                "nashorn");
        URL resource = NashronTest.class.getResource("/index.js");
        System.out.println(resource);
        Object o = engine.eval(new FileReader(resource.getPath()));
//        System.out.println(o);
        Invocable invocable = (Invocable) engine;

        // ScriptObjectMirror scriptObjectMirror=new ScriptObjectMirror(new ScriptObject(),new Global());
        JsInvocable jsInvocable = invocable.getInterface(JsInvocable.class);
        ScriptObjectMirror result = (ScriptObjectMirror) jsInvocable.test();
        // Object result = invocable.invokeFunction("test", "Peter Parker");

//        String[] ownKeys = result.getOwnKeys(true);
//        Object member = result.getMember("age");
//        System.out.println(member);
        Set<Map.Entry<String, Object>> entries = result.entrySet();

        for (Map.Entry<String, Object> entry : entries) {
            System.out.println(entry.getKey());
            Object value = entry.getValue();
            System.out.println(value);
            System.out.println(value.getClass().getSimpleName());
        }

//        Member to = result.to(Member.class);
//        System.out.println(to);

//        System.out.println(result);
//        System.out.println(result.getClass());

    }

    public static String fun1(String name) {
        System.out.format("Hi there from Java, %s", name);
        return "greetings from java";
    }

}
