package com.love_peace.delayqueue.consume.timer;

import com.love_peace.delayqueue.product.DelayJob;
import com.love_peace.delayqueue.consume.service.ExecuteJobService;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Author: LiuHao
 * @Descirption: 轮询消费
 * @Date: 2018/10/19_16:07
 */
@Component
public class JobTimer {

    Logger logger = LoggerFactory.getLogger(getClass());

    public static final String jobsTag = "xxx_job_timer";

    @Autowired
    private RedissonClient client;

    @Autowired
    private ApplicationContext context;

    ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);

    @PostConstruct
    public void startJobTimer() {
        RBlockingQueue<DelayJob> blockingQueue = client.getBlockingQueue(jobsTag);
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        DelayJob job = blockingQueue.take();
                        executorService.execute(new ExecutorTask(context, job));
                    } catch (Exception e) {
                        logger.error(e.getMessage(),e);
                        try {
                            TimeUnit.SECONDS.sleep(60);
                        } catch (Exception ex) {
                        }
                    }
                }
            }
        }.start();
    }

    class ExecutorTask implements Runnable {

        private ApplicationContext context;

        private DelayJob delayJob;

        public ExecutorTask(ApplicationContext context, DelayJob delayJob) {
            this.context = context;
            this.delayJob = delayJob;
        }

        @Override
        public void run() {
            ExecuteJobService service = (ExecuteJobService) context.getBean(delayJob.getaClass());
            service.execute(delayJob);//执行
        }
    }
}
