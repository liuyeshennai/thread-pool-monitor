package com.liuyeshennai.thread.pool.monitor.service;

/**
 * IThreadMonitor.
 */
public interface IThreadPoolMonitorService {

  /**
   * 记录活跃线程.
   */
  void write();

  /**
   * 打印活跃线程.
   */
  void read();
}
