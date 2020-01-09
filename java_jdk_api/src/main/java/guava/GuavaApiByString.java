package guava;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;

/**
 * guava api测试 字符串工具
 * Created by wuxp on 2017/6/4.
 */
public class GuavaApiByString {

    public static void main(String[] args) {
        Joiner joiner = Joiner.on("; ").skipNulls();
        String s = joiner.join("Harry", null, "Ron", "Hermione");

        System.out.println(s);
        Iterable<String> stringIterable = Splitter.on(',').trimResults()
                .omitEmptyStrings()
                .split("foo,bar,,   qux");

    }
}
