package com.design.pattern.builder.demo03;


/**
 * 第三种解决方案：利用Builder模式
 * 利用Builder模式，我们可以解决上面的问题，代码如下：
 */
public class User {

    private final String firstName;     // 必传参数
    private final String lastName;      // 必传参数
    private final int age;              // 可选参数
    private final String phone;         // 可选参数
    private final String address;       // 可选参数

    // private 私有的构造方法，不能使用new
    private User(UserBuilder builder) {
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.age = builder.age;
        this.phone = builder.phone;
        this.address = builder.address;
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

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    public static class UserBuilder {
        private final String firstName;
        private final String lastName;
        private int age;
        private String phone;
        private String address;

        public UserBuilder(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public UserBuilder age(int age) {
            this.age = age;
            return this;
        }

        public UserBuilder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public UserBuilder address(String address) {
            this.address = address;
            return this;
        }
//        关于Builder的一点说明
//                线程安全问题

//        由于Builder是非线程安全的，所以如果要在Builder内部类中检查一个参数的合法性，必需要在对象创建完成之后再检查。
        public User build1() {
            User user = new User(this);
            if (user.getAge() > 120) {  // 必需要在对象创建完成之后再检查, 才能保证线程安全
                throw new IllegalStateException("Age out of range");   // 线程安全
            }
            return user;
        }
        // 上面的写法是正确的，而下面的代码是非线程安全的：
        public User build2() {
            if (age > 120) {
                throw new IllegalStateException("Age out of range"); // 非线程安全
            }
            return new User(this);
        }

        public User build () {
            return new User(this);   // 非线程安全
        }
    }
}
/**
 有几个重要的地方需要强调一下：

 User类的构造方法是私有的。也就是说调用者不能直接创建User对象。
 User类的属性都是不可变的。所有的属性都添加了final修饰符，并且在构造方法中设置了值。并且，对外只提供getters方法。
 Builder模式使用了链式调用。可读性更佳。
 Builder的内部类构造方法中只接收必传的参数，并且该必传的参数适用了final修饰符。

 相比于前面两种方法，Builder模式拥有其所有的优点，而没有上述方法中的缺点。
 客户端的代码更容易写，并且更重要的是，可读性非常好。
 唯一可能存在的问题就是会产生多余的Builder对象，消耗内存。
 然而大多数情况下我们的Builder内部类使用的是静态修饰的(static)，所以这个问题也没多大关系。
 现在，让我们看看如何创建一个User对象呢？

 **/