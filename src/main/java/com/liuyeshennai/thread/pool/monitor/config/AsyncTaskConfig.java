package com.liuyeshennai.thread.pool.monitor.config;

import java.util.concurrent.Executor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * Description: thread pool config.
 **/
@Configuration
@EnableAsync
public class AsyncTaskConfig implements AsyncConfigurer {

  @Override
  @Bean
  public Executor getAsyncExecutor() {
    ThreadPoolTaskExecutor threadPool = new ThreadPoolTaskExecutor();
    threadPool.setCorePoolSize(10);
    threadPool.setWaitForTasksToCompleteOnShutdown(true);
    threadPool.setAwaitTerminationSeconds(60 * 30); // 30 * 60 s
    threadPool.setThreadNamePrefix("Thread-Async-");
    threadPool.initialize();
    return threadPool;
  }
}