package com.weil.de;

import lombok.Data;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @ClassName StreamTest
 * @Author weil
 * @Description //流测试
 * @Date 2021/8/24 18:50
 * @Version 1.0.0
 **/
public class StreamTest {
    /**
     * 流的创建：集合、数组、静态方法
     */
    @Test
    public void createStream(){
        // 1.集合
        List<Integer> integers = Arrays.asList(1, 2, 3);
        // 顺序流
        Stream<Integer> s1 = integers.stream();
        // 并行流
        Stream<Integer> s1_1 = integers.parallelStream();
        // 2.数组
        int[] arr = new int[]{1,2,3};
        IntStream s2 = Arrays.stream(arr);
        // 3.静态方法
        Stream<Integer> s3 = Stream.of(1, 2, 3);
        // 随机生成3个
        Stream<Integer> s4 = Stream.iterate(0, x -> x + 3).limit(3);
        s4.forEach(System.out::println);
        Stream<Double> s5 = Stream.generate(Math::random).limit(3);
        s5.forEach(System.out::println);
    }
    /**
     * 遍历/匹配(foreach/find/match)
     */
    @Test
    public void t1(){
        List<Integer> list = Arrays.asList(2, 34, 12, 7, 26, 18);
        list.stream().filter(x -> x > 10).forEach(System.out::println);
        Optional<Integer> first = list.stream().filter(x -> x > 10).findFirst();
        System.out.println("匹配第一个："+ first.get());
        Optional<Integer> any = list.parallelStream().filter(x -> x > 10).findAny();
        System.out.println("匹配任意一个："+ any.get());
        boolean b = list.stream().anyMatch(x -> x > 20);
        System.out.println("是否存在大于20的数："+ b);

    }

    /**
     * 过滤filter
     * 筛选员工中工资高于8000的人，并形成新的集合
     */
    @Test
    public void t2(){
        List<Person> list = getList();
        List<String> collect = list.stream().filter(x -> x.getSalary() > 8000).map(Person::getName).collect(Collectors.toList());
        System.out.println(collect);
    }

    /**
     * 聚合（max/min/count）
     */
    @Test
    public void t3(){
        // 字符串最长
        String[] strs = new String[]{"asdf","werw","gra","dadfe","ffddegez"};
        // 默认排序
        Optional<String> max = Arrays.stream(strs).max(Comparator.comparing(String::length));
        System.out.println(max.get());
        // 自定义排序
        Optional<String> max1 = Arrays.stream(strs).max((o1, o2) -> new Integer(o1.length()).compareTo(o2.length()));
        System.out.println(max1.get());

        // 筛选员工工资最高的人
        List<Person> list = getList();
        Optional<Person> max2 = list.stream().max(Comparator.comparing(Person::getSalary));
        System.out.println(max2.get().getName());
    }
    /**
     * 映射map,flatmap
     * 可以将一个流的元素按照一定的映射规则映射到另一个流中
     */
    @Test
    public void t4(){
        // 每个员工工资加200
        List<Person> list = getList();
        System.out.println("原来员工工资："+list.get(0).getName()+","+list.get(0).getSalary());
        List<Person> collect = list.stream().map(p -> {
            p.setSalary(p.getSalary() + 200);
            return p;
        }).collect(Collectors.toList());
        System.out.println("调薪后原来员工工资："+list.get(0).getName()+","+list.get(0).getSalary());
        System.out.println("调薪后现在员工工资："+collect.get(0).getName()+","+collect.get(0).getSalary());
        System.out.println("原来集合和现在集合是否相同："+ (list==collect));
        System.out.println("原来集合中的元素和现在集合是否相同："+ (list.get(0)==collect.get(0)));
        //原来员工工资：Tom,8900
        //调薪后原来员工工资：Tom,9100
        //调薪后现在员工工资：Tom,9100
        //原来集合和现在集合是否相同：false
        //原来集合中的元素和现在集合是否相同：true

        // flatmap 合并流，把流连一起
        String[] strs = {"a,b,c", "d,e,f"};
        List<String> collect1 = Arrays.stream(strs).flatMap(s -> {
            String[] split = s.split(",");
            return Arrays.stream(split);
        }).collect(Collectors.toList());
        System.out.println(collect1);
    }

    /**
     * reduce规约
     * 缩减，把流中的元素缩减成一个，求最值等
     */
    @Test
    public void t5(){
        List<Integer> integers = Arrays.asList(1, 2, 3, 4);
        Integer max = integers.stream().reduce((integer1, integer2) -> integer1>integer2?integer1:integer2).get();
        System.out.println("最大值："+ max);
        Integer min = integers.stream().reduce(Integer::min).get();
        System.out.println("最小值："+ min);
        Integer sum = integers.stream().reduce(0, Integer::sum);
        System.out.println("求和："+ sum);

        // 求员工工资之和(3种)，最高工资
        List<Person> list = getList();
        Integer salary1 = list.stream().map(Person::getSalary).reduce(Integer::sum).get();
        System.out.println("工资和："+salary1);
        Integer salary2 = list.stream().reduce(0, (a, p) -> a += p.getSalary(), (b, c) -> b + c);
        System.out.println("工资和："+salary2);
        Integer salary3 = list.stream().reduce(0, (a, p) -> a += p.getSalary(), Integer::sum);
        System.out.println("工资和："+salary3);
        Integer max1 = list.stream().map(Person::getSalary).reduce(Integer::max).get();
        Integer max2 = list.stream().reduce(0, (m, p) -> m > p.getSalary() ? m : p.getSalary(), Integer::max);
        Integer max3 = list.stream().reduce(0, (m, p) -> m > p.getSalary() ? m : p.getSalary(), (b, c) -> b > c ? b : c);
        System.out.println("最高工资："+max1+","+max2+","+max3);
    }

    /**
     * 收集collect
     * 1.归集 toList,toSet,toMap
     * 2.统计 count/averaging/max/min/sum,统计以上所有
     * 3.分区、分组 partitioningBy/groupingBy
     * 4.joining
     * 5.reducing
     */
    @Test
    public void t6(){
        // 1.归集
        // 随机生成5个
        List<Integer> l = Stream.iterate(0, x -> x + 2).limit(5).collect(Collectors.toList());
        Set<Integer> set = Stream.iterate(0, x -> x + 2).limit(5).collect(Collectors.toSet());
        List<Person> list = getList();
        Map<String, Integer> map = list.stream().collect(Collectors.toMap(Person::getName, Person::getAge));
        System.out.println("list:"+l);
        System.out.println("set:"+set);
        System.out.println("map:"+map);
        // 2.统计
        Long count = list.stream().collect(Collectors.counting());
        Integer max = list.stream().collect(Collectors.maxBy(Comparator.comparing(Person::getSalary))).get().getSalary();
        Integer min = list.stream().collect(Collectors.minBy(Comparator.comparing(Person::getSalary))).get().getSalary();
        Integer sum = list.stream().collect(Collectors.summingInt(Person::getSalary));
        Double avg = list.stream().collect(Collectors.averagingDouble(Person::getSalary));
        DoubleSummaryStatistics statistics = list.stream().collect(Collectors.summarizingDouble(Person::getSalary));
        System.out.println("count:"+count+";max:"+max+";min:"+min+";sum:"+sum+";avg:"+avg);
        System.out.println("statistics:"+statistics);
        // 3.分区、分组
        Map<Boolean, List<Person>> partition = list.stream().collect(Collectors.partitioningBy(p -> p.getSalary() > 8000));
        Map<String, Map<String, List<Person>>> group = list.stream().collect(Collectors.groupingBy(Person::getSex, Collectors.groupingBy(Person::getArea)));
        System.out.println("partition:"+partition);
        System.out.println("group:"+group);
        // 4.joining
        String s = list.stream().map(Person::getName).collect(Collectors.joining(","));
        System.out.println(s);
        // 5.reducing
        Integer sum1 = list.stream().collect(Collectors.reducing(0, Person::getSalary, Integer::sum));
        System.out.println(sum1);
    }

    /**
     * 7.排序sorted
     */
    @Test
    public void t7(){
        List<Person> list = getList();
        // 工资由低到高
        List<Integer> salarys1 = list.stream().sorted(Comparator.comparing(Person::getSalary)).map(Person::getSalary).collect(Collectors.toList());
        // 由高到低
        List<Integer> salarys2 = list.stream().sorted(Comparator.comparing(Person::getSalary).reversed()).map(Person::getSalary).collect(Collectors.toList());
        System.out.println(salarys1);
        System.out.println(salarys2);
        // 先按年龄由低到高；再按薪资
        List<Person> collect = list.stream().sorted(Comparator.comparing(Person::getAge).thenComparing(Person::getSalary)).collect(Collectors.toList());
        System.out.println(collect);
    }

    /**
     * 提取/组合
     * 去重、跳过、限制
     */
    @Test
    public void t8(){
        String[] str1 = {"a","b","c"};
        String[] str2 = {"d","e","a"};
        Stream<String> s1 = Stream.of(str1);
        Stream<String> s2 = Stream.of(str2);
        // 合并去重
        List<String> strs = Stream.concat(s1, s2).distinct().collect(Collectors.toList());
        System.out.println(strs);
        // 限制+跳过 取6个偶数(0,2,4,6,8,10) 去掉第一个
        List<Integer> list = Stream.iterate(0, x -> x + 2).limit(6).skip(1).collect(Collectors.toList());
        System.out.println(list);


    }
    /**
     * 封装集合
     */
    public List<Person> getList(){
        List<Person> personList = new ArrayList<>();
        personList.add(new Person("Tom", 8900, 28, "male", "New York"));
        personList.add(new Person("Jack", 7000, 31, "male", "Washington"));
        personList.add(new Person("Lily", 7800, 27, "female", "Washington"));
        personList.add(new Person("Anni", 8200, 29, "female", "New York"));
        personList.add(new Person("Owen", 9500, 35, "male", "China"));
        personList.add(new Person("Alisa", 7900, 30, "female", "New York"));
        return personList;
    }
    /**
     * 员工类，测试
     */
    @Data
    class Person{
        private String name;  // 姓名
        private int salary; // 薪资
        private int age; // 年龄
        private String sex; //性别
        private String area;  // 地区

        // 构造方法
        public Person(String name, int salary, int age,String sex,String area) {
            this.name = name;
            this.salary = salary;
            this.age = age;
            this.sex = sex;
            this.area = area;
        }
    }
}
