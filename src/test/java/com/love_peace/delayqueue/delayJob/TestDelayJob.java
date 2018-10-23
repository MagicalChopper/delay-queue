package com.love_peace.delayqueue.delayJob;

import com.love_peace.delayqueue.DelayQueueApplicationTests;
import com.love_peace.delayqueue.consume.service.XXXJobServiceImpl;
import com.love_peace.delayqueue.product.DelayJob;
import com.love_peace.delayqueue.product.DelayService;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


/**
 * @Author: LiuHao
 * @Descirption:
 * @Date: 2018/10/19_16:18
 */
public class TestDelayJob extends DelayQueueApplicationTests {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private DelayService delayService;

    @Test
    public void testDelayJob() throws IOException {
        DelayJob delayJob = new DelayJob();
        delayJob.setaClass(XXXJobServiceImpl.class);
        long delay = 30L;
        Map map = new HashMap<>();
        map.put("value","默默传个参数");
        delayJob.setJobParams(map);
        delayService.submitJob(delayJob,delay,TimeUnit.SECONDS);
        Thread a = new  Thread(()->{
            int i = 0;
            while(i<delay){
                logger.info(Thread.currentThread().getName() + ":"+ (i++));
                try {
                    TimeUnit.SECONDS.sleep(1L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        executorService.submit(a);
        executorService.submit(a);
        executorService.submit(a);
        executorService.submit(a);
        executorService.submit(a);
        executorService.submit(a);
        executorService.submit(a);
        executorService.submit(a);
        executorService.submit(a);
        executorService.submit(a);

        System.in.read();//避免主线程退出
    }
}
