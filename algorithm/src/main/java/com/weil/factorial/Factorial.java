package com.weil.factorial;

/**
 * @Name: Factorial
 * @Description: 求n的阶乘，使用递归实现
 * @Author: weil
 * @Date: 2023-03-29 09:59
 * @Version: 1.0
 */
public class Factorial {

    /**
     * 计算，递归算法：f(n)=n*f(n-1), n是大于0的整数
     */
    public Integer calc(Integer n){
        System.out.println("n="+n+"前");
        if(n==1){
            return 1;
        }
        int i = n * calc(n - 1);
        System.out.println("n="+n+"后");
        return i;
    }
    public static void main(String[] args) {
        Factorial factorial = new Factorial();

        System.out.println(factorial.calc(3));
    }
}
