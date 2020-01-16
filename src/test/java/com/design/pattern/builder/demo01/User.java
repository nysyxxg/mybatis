package com.design.pattern.builder.demo01;

/**
 * 第一种解决方案：利用构造方法。
 * 思路是：第一个构造方法只包含两个必需的参数，
 * 第二个构造方法中，增加一个可选参数，
 * 第三个构造方法中再增加一个可选参数，依次类推，
 * 直到构造方法中包含了所有的参数。如下所示：
 *
 */
public class User {

    private final String firstName;     // 必传参数
    private final String lastName;      // 必传参数
    private final int age;              // 可选参数
    private final String phone;         // 可选参数
    private final String address;       // 可选参数

    public User(String firstName, String lastName) {
        this(firstName, lastName, 0);
    }

    public User(String firstName, String lastName, int age) {
        this(firstName, lastName, age, "");
    }

    public User(String firstName, String lastName, int age, String phone) {
        this(firstName, lastName, age, phone, "");
    }

    public User(String firstName, String lastName, int age, String phone, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.phone = phone;
        this.address = address;
    }

}

/**
 这样做的好处只有一个：可以成功运行。但是弊端很明显：
 参数较少的时候问题还不大，一旦参数多了，代码可读性就很差，并且难以维护。
 对调用者来说也很麻烦。如果我只想多传一个address参数，还必需给age、phone设置默认值。
 而且调用者还会有这样的困惑：我怎么知道第四个String类型的参数该传address还是phone？
 **/