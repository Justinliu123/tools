package com.example.demo.util;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolUtil {
  public static ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 50, 2L, TimeUnit.MINUTES, new LinkedBlockingDeque<>());
}
