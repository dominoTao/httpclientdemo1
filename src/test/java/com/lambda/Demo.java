package com.lambda;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class Demo {
    @Test
    public void run2(){
        Stream<List<Integer>> inputStream = Stream.of(Arrays.asList(1), Arrays.asList(2, 3), Arrays.asList(4, 5, 6));

        Stream<Integer> outputStream = inputStream.flatMap(t -> t.stream()).peek(System.out::print);
        outputStream.count();
    }
    @Test
    public void run3(){
        Stream.of(1,2,3,4,5,2,3,4,1).distinct().skip(3).peek(System.out::println).count();
        Stream.generate(Math::random).peek(System.out::println).limit(10).count();
        Stream.iterate(1, t->t+1).limit(10).peek(System.out::println).count();

        Stream.of(1,2,3,4,5,2,3,4,1).findFirst();
    }
    @Test
    public void run4(){
        List<Integer> nums = Lists.newArrayList(1,1,null,2,3,4,null,5,6,7,8,9,10);
        int sum = nums.stream().distinct().filter(t -> t != null)
                .skip(2)
                .limit(4)
                .mapToInt(t -> t * 2)
                .peek(System.out::println)
                .sum();
        System.out.println(sum);
    }
    @Test
    public void run5(){
        List<Integer> nums = Lists.newArrayList(1,1,null,2,3,4,null,5,6,7,8,9,10);
        ArrayList<Integer> collect = nums.stream()
                .distinct()
                .filter(t -> t != null)
                .peek(System.out::println)
                .collect(ArrayList::new,
                        (list, item) -> list.add(item),
                        (list1, list2) -> list1.addAll(list2));

    }

    @Test
    public void run6(){
        String[] array = {"a", "b", "c"};
        for(Integer i : Lists.newArrayList(1,2,3)){
            Stream.of(array).map(item -> Strings.padEnd(item, i, '@')).forEach(System.out::println);
        }
    }
@Test
    public void run7(){
//    Optional<Integer> first = Stream.of(1, 2, 3, 4, 5, 2, 3, 4, 1).findFirst();
    Optional<Integer> first = Stream.of(1, 2, 3, 4, 5, 2, 3, 4, 1).findAny();
    Integer integer = first.get();
    System.out.println(integer);
}
}
