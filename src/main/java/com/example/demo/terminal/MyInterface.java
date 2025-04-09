package com.example.demo.terminal;

public interface MyInterface {
    default void print() {
        System.out.println("MyInterface");
    }
}
