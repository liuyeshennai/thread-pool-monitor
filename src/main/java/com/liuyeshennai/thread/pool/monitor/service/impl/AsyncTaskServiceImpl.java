package com.liuyeshennai.thread.pool.monitor.service.impl;


import com.liuyeshennai.thread.pool.monitor.service.IAsyncTaskService;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

/**
 * 异步任务.
 */
@Slf4j
@Service
public class AsyncTaskServiceImpl implements IAsyncTaskService {

  @Async
  @Override
  public Future<Long> task() {
    StopWatch sw = new StopWatch();
    sw.start();
    try {
      int random = ThreadLocalRandom.current().nextInt(1, 10);
      Thread.sleep(random * 1000);
    } catch (InterruptedException e) {
      log.error("InterruptedException", e);
    }
    return new AsyncResult<>(sw.getTotalTimeMillis());
  }
}
