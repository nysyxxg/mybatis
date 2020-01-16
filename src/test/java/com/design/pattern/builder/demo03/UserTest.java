package com.design.pattern.builder.demo03;

public class UserTest {
    public static void main(String[] args) {
        User user =  new User.UserBuilder("王", "小二")
                .age(20)
                .phone("123456789")
                .address("中国")
                .build();
        System.out.println(user);
    }

}
