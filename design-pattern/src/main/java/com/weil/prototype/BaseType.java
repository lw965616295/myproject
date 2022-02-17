package com.weil.prototype;

/**
 * @ClassName BaseType
 * @Author weil
 * @Description //基础类型，原型模式：对象的拷贝，浅拷贝和深拷贝
 * @Date 2021/8/17 9:45
 * @Version 1.0.0
 **/
public class BaseType implements Cloneable{
    private Dog dog;
    public BaseType(){
        System.out.println("创建了对象！");
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        System.out.println("复制了对象！");
        return super.clone();
    }

    public Dog getDog() {
        return dog;
    }

    public void setDog(Dog dog) {
        this.dog = dog;
    }
}
/**
 * 测试对象，验证clone是否为浅拷贝:引用指向的地址相同
 */
class Dog{
}
class Test{
    public static void main(String[] args) throws CloneNotSupportedException {
        /**
         * 创建了对象！
         * 复制了对象！
         * baseType == clone?false
         * baseType.dog == clone.dog?true
         */
        BaseType baseType = new BaseType();
        baseType.setDog(new Dog());
        BaseType clone = (BaseType) baseType.clone();
        System.out.println("baseType == clone?"+(baseType==clone));
        System.out.println("baseType.dog == clone.dog?"+(baseType.getDog()==clone.getDog()));
    }
}
