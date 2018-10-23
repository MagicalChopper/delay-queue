package com.love_peace.delayqueue.redisson;

import com.love_peace.delayqueue.DelayQueueApplicationTests;
import com.love_peace.delayqueue.service.ReadWriteRedissonService;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * @Author: LiuHao
 * @Descirption:
 * @Date: 2018/10/19_14:09
 */
public class TestRedisson extends DelayQueueApplicationTests {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ReadWriteRedissonService readWriteRedissonService;

    @Test
    public void testBatchWrite(){
        List<Map<String,Object>> list = new ArrayList();
        int i = 0;
        while (i<5000){
            Map map = new HashMap<>();
            map.put("key"+i,"value"+i);
            list.add(map);
            logger.info("loading data...{}",i);
            ++i;
        }
        readWriteRedissonService.batchWriteObjectToRedis(1,list);
    }

    @Test
    public void testRead(){
        List list = readWriteRedissonService.logRedisData(1);
    }


}
