package com.liuyeshennai.thread.pool.monitor.service.impl;

import com.liuyeshennai.thread.pool.monitor.service.IAsyncTaskService;
import com.liuyeshennai.thread.pool.monitor.service.IThreadTestService;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 多线程测试.
 */
@Service
@Slf4j
public class ThreadTestServiceImpl implements IThreadTestService {

  private final IAsyncTaskService asyncTaskService;

  @Autowired
  public ThreadTestServiceImpl(
      IAsyncTaskService asyncTaskService) {
    this.asyncTaskService = asyncTaskService;
  }

  @PostConstruct
  @Override
  public void test() {
    log.info("thread test starting");
    while (true) {
      int random = ThreadLocalRandom.current().nextInt(1, 100);
      log.info("test thread count:{}" , random);
      List<Future<Long>> list = new ArrayList<>(random);
      for (int i = 0; i < random; i++) {
        Future<Long> f = asyncTaskService.task();
        list.add(f);
      }
      list.forEach(e -> {
        try {
          e.get();
        } catch (InterruptedException interruptedException) {
          interruptedException.printStackTrace();
        } catch (ExecutionException executionException) {
          executionException.printStackTrace();
        }
      });
      try {
        Thread.sleep(3 * 1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
