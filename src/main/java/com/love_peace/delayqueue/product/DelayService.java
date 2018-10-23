package com.love_peace.delayqueue.product;

import com.love_peace.delayqueue.consume.timer.JobTimer;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @Author: LiuHao
 * @Descirption: 提交任务，生产方
 * @Date: 2018/10/19_16:05
 */
@Service
public class DelayService {

    @Autowired
    private RedissonClient client;

    public void submitJob(DelayJob job, Long delay, TimeUnit timeUnit) {
        RBlockingQueue<DelayJob> blockingQueue = client.getBlockingQueue(JobTimer.jobsTag);
        RDelayedQueue delayedQueue = client.getDelayedQueue(blockingQueue);
        delayedQueue.offer(job,delay,timeUnit);
    }

}
