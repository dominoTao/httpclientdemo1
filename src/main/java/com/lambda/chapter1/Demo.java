package com.lambda.chapter1;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Demo {
    public void run2(){
        Stream<List<Integer>> inputStream = Stream.of(Arrays.asList(1), Arrays.asList(2, 3), Arrays.asList(4, 5, 6));

        Stream<Integer> outputStream = inputStream.flatMap(t -> t.stream()).peek(System.out::println);
    }

    public void run1(){
        Thread thread = new Thread(() -> System.out.println(Thread.currentThread().getName() + " : task starting..."));
        thread.start();

        List<String> collect = Arrays.asList(new String[]{"hello", "lambda", "!"})
                .stream().map(ele -> ele.toUpperCase())
                .peek(System.out::println)
                .collect(Collectors.toList());


        Demo demo = new Demo();
        demo.whatThis();
/*Stream 静态方法创建Stream*/
        Stream.of(1,2,3,4,5,2,3,4,1).distinct().peek(System.out::println).count();
        Stream.generate(Math::random).peek(System.out::println).limit(10).count();
        Stream.iterate(1, t->t+1).limit(10).peek(System.out::println).count();
/*Collection子类获取Stream*/
        /*Collection接口：
        default Stream<E> parallelStream() {
            return StreamSupport.stream(spliterator(), true);
        }
        default Stream<E> stream() {
            return StreamSupport.stream(spliterator(), false);
        }
        可以生成Steam
        */
    }

    public void whatThis(){
        List<String> execStrs = Arrays.asList(new String[]{"Ni","Hao","Lambda"}).stream()
                .map(t -> {
                    System.out.println(this.getClass().getName());
                    return t.toLowerCase();
                })
//                .peek(System.out::println)
                .collect(Collectors.toList());
    }
}
