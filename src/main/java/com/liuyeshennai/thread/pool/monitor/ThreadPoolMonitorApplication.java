package com.liuyeshennai.thread.pool.monitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ThreadPoolMonitorApplication {

  public static void main(String[] args) {
    SpringApplication.run(ThreadPoolMonitorApplication.class, args);
  }

}
