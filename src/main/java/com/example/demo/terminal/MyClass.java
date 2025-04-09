package com.example.demo.terminal;

import java.io.Closeable;
import java.io.IOException;

public class MyClass implements Closeable {
    @Override
    public void close() throws IOException {
        System.out.println(this.getClass().getName() + " close");
    }

    public void print() {
        System.out.println("MyClass print");
    }
}
