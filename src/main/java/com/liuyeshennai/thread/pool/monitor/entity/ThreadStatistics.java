package com.liuyeshennai.thread.pool.monitor.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * .
 */
@Data
@AllArgsConstructor
@Accessors(chain = true)
public class ThreadStatistics {

  /**
   * 活跃线程数.
   */
  private Integer activeCount;
  /**
   * 统计到的活跃线程次数.
   */
  private Integer weight;
}
