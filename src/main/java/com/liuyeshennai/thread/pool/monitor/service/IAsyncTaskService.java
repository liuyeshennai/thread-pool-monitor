package com.liuyeshennai.thread.pool.monitor.service;

import java.util.concurrent.Future;

/**
 * 异步任务.
 */
public interface IAsyncTaskService {

  Future<Long> task();
}
