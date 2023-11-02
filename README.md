# thread-pool-monitor
# 引言
在java项目中，我们经常会创建线程池来减少频繁创建线程带来的额外的系统开销，不过目前针对线程池的核心线程数的设置，一直没有一个让我很认同的观点，更多的是根据cpu核心数并参考IO密集型，CPU密集型，以及响应时间等参数来估算。

# 我的方案
我的想法简单粗暴，就是根据测试来设置合理的线程池参数。思路比较明确，定时监控线程池的活跃线程数，队列积压，根据压测并发量综合评估最合适的线程数，甚至可以做到动态设置。

# 项目介绍
项目核心代码只有一个类ThreadMonitorService，有两个方案write()即read(),分别负责记录及读取线程。其他类都是用于模拟实际并发。运行项目可以看到后台日志。
![image](https://github.com/liuyeshennai/thread-pool-monitor/assets/34265990/2e55a42f-8685-4378-a9ed-8d99d6c73f6b)
可以看到模拟52个并发，那么监控到最多38个积压，排行最大的活跃线程数是10个，以此类推。

根据这个监控结果，就可以根据实际项目的高峰并发量来设置CorePoolSize的数量，因为实际项目会比这个场景复杂的多，不仅仅是用户多，而且接口也不是只有一个，因此可以现根据初步的经验来设置一个足够的线程数，再根据监控结果每个迭代去优化线程池参数。
