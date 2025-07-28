package com.mall.classDemo;

public class Student extends Person {

    public String sex;

    public Student(String name, int age, String sex) {
        super(name, age);
        this.sex = sex;
    }

    public String toString() {
        return "Student{" +
                "name=" + name +
                ", age=" + age +
                ", sex=" + sex +
                '}';
    }
}

class classTest {
    public static void main(String[] args) {
        Student student = new Student("tom", 18, "male");
        System.out.println(student);
    }
}