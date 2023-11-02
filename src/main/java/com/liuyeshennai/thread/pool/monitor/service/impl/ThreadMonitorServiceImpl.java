package com.liuyeshennai.thread.pool.monitor.service.impl;

import com.liuyeshennai.thread.pool.monitor.config.AsyncTaskConfig;
import com.liuyeshennai.thread.pool.monitor.entity.ThreadStatistics;
import com.liuyeshennai.thread.pool.monitor.service.IThreadPoolMonitorService;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

/**
 * ThreadMonitorService.
 */
@Slf4j
@Service
public class ThreadMonitorServiceImpl implements IThreadPoolMonitorService {

  private final AsyncTaskConfig asyncTaskConfig;
  private final Map<Integer, Integer> threadCntMap = new HashMap<>();

  @Autowired
  public ThreadMonitorServiceImpl(
      AsyncTaskConfig asyncTaskConfig) {
    this.asyncTaskConfig = asyncTaskConfig;
  }

  /**
   * 记录活跃线程. 周期性查询活跃线程数，存入Map. 注意使用守护线程，不影响应用关闭.
   */
  @PostConstruct
  @Override
  public void write() {
    ThreadPoolTaskExecutor executor = (ThreadPoolTaskExecutor) asyncTaskConfig.getAsyncExecutor();
    if (executor == null) {
      log.error("ThreadPoolTaskExecutor is null! ");
      return;
    }
    log.info("thread monitor write service started ");
    Thread thread = new Thread(() -> {
      while (true) {
        int activeCount = executor.getActiveCount();
        if (threadCntMap.size() == 0 || threadCntMap.get(activeCount) == null) {
          threadCntMap.put(activeCount, 1);
        } else {
          Integer val = threadCntMap.get(activeCount) + 1;
          threadCntMap.put(activeCount, val);
        }
        try {
          // 1s统计记一次
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          log.error("InterruptedException", e);
        }
      }
    });
    thread.setDaemon(true);
    thread.start();
  }

  /**
   * 打印活跃线程. 注意使用守护线程，不影响应用关闭.
   */
  @PostConstruct
  @Override
  public void read() {
    log.info("thread monitor read service started ");
    ThreadPoolTaskExecutor executor = (ThreadPoolTaskExecutor) asyncTaskConfig.getAsyncExecutor();
    Thread thread = new Thread(() -> {
      while (true) {
        Set<Integer> sets = threadCntMap.keySet();
        List<ThreadStatistics> list = new ArrayList<>();
        for (Integer i : sets) {
          list.add(new ThreadStatistics(i, threadCntMap.get(i)));
        }
        list = list.stream().sorted(Comparator.comparing(ThreadStatistics::getWeight).reversed())
            .limit(3)
            .collect(Collectors.toList());
        int queueSize = executor.getQueueSize();
        StringBuilder str = new StringBuilder();
        for (ThreadStatistics item : list) {
          str.append(",").append(item.getActiveCount()).append("/").append(item.getWeight());
        }
        log.info("thread monitor :【queueSize:{},activeCount Ranking:{}】",
            queueSize, str.substring(1));
        try {
          // 5s打印一次统计结果
          Thread.sleep(5000);
        } catch (InterruptedException e) {
          log.error("InterruptedException", e);
        }
      }
    });
    thread.setDaemon(true);
    thread.start();
  }
}
