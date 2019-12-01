package com.sutdy.flink;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;

public class WordCount {

    public static void main(String[] args) throws Exception {

        //获取运行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        //从文件中读取
        String path = WordCount.class.getResource("/demo.txt").getPath();
        DataStreamSource<String> text = env.readTextFile(path);


        DataStream windowCount = text.flatMap(new FlatMapFunction<String, WordWithCount>() {
            @Override
            public void flatMap(String value, Collector<WordWithCount> collector) throws Exception {
                String[] splits = value.split("\\s");

                for (String word : splits) {
                    collector.collect(new WordWithCount(word, 1L));
                }
            }
        }).keyBy("word")
//                .reduce(new ReduceFunction<WordWithCount>() {
//                    @Override
//                    public WordWithCount reduce(WordWithCount wordWithCount, WordWithCount t1) throws Exception {
//                        wordWithCount.count += t1.count;
//                        return wordWithCount;
//                    }
//                });
                .timeWindow(Time.seconds(2), Time.seconds(1))
                .sum("count");

        //把数据打印到控制台
        windowCount.print();//.setParallelism(1);//使用一个并行度
        //注意：因为flink是懒加载的，所以必须调用execute方法，上面的代码才会执行
        env.execute("streaming word count");

    }

    /**
     * 主要为了存储单词以及单词出现的次数
     */

    public static class WordWithCount {

        public String word;

        public long count;

        public WordWithCount() {

        }

        public WordWithCount(String word, long count) {

            this.word = word;

            this.count = count;

        }

        @Override

        public String toString() {

            return "WordWithCount{" +

                    "word='" + word + '\'' +

                    ", count=" + count +

                    '}';

        }

    }


}
