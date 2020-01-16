package com.design.pattern.builder.demo02;

/**
 * 第二种解决办法：采用JavaBean的形式。
 * 我们可以根据JavaBean的习惯，设置一个空参数的构造方法，
 * 然后为每一个属性设置setters和getters方法。就像下面一样：
 *
 */
public class User {

    private String firstName;     // 必传参数
    private String lastName;      // 必传参数
    private int age;              // 可选参数
    private String phone;         // 可选参数
    private String address;       // 可选参数

    public User() {
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }
}
/**
 这种方法看起来可读性不错，而且易于维护。作为调用者，创建一个空的对象，然后只需传入我感兴趣的参数。那么缺点呢？也有两点：

 1：对象会产生不一致的状态。当你想要传入5个参数的时候，你必需将所有的setXX方法调用完成之后才行。
 然而一部分的调用者看到了这个对象后，以为这个对象已经创建完毕，就直接使用了，其实User对象并没有创建完成。
 2： User类是可变的了，不可变类所有好处都不复存在。
 **/