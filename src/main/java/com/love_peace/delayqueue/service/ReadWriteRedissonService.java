package com.love_peace.delayqueue.service;

import com.love_peace.delayqueue.utils.RedissonUtil;
import org.redisson.api.RKeys;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Author: LiuHao
 * @Descirption:
 * @Date: 2018/10/19_13:50
 */
@Service
public class ReadWriteRedissonService {

    private static final Logger logger = LoggerFactory.getLogger(ReadWriteRedissonService.class);

    private static final RedissonUtil redissonUtil = new RedissonUtil();

    @Autowired
    private RedissonClient redissonClient;

    /**
     * 选数据库批量写入
     * @param db
     * @param list
     */
    public void batchWriteObjectToRedis(int db, List<Map<String,Object>> list){
        redissonClient = redissonUtil.changeDB(redissonClient,db);
        int i=0;
        while (i<list.size()){
            Map<String,Object> map = list.get(i);
            for(Map.Entry<String,Object> entry: map.entrySet()){
                redissonUtil.objWrite(redissonClient, entry.getKey(), entry.getValue());
                logger.info("写入了一条数据，{}",entry.getKey());
            }
            ++i;
        }
    }

    public List logRedisData(int db){
        redissonClient = redissonUtil.changeDB(redissonClient,db);
        RKeys rKeys = redissonClient.getKeys();
        Iterable<String> iterable = rKeys.getKeys();
        Iterator iterator = iterable.iterator();
        List list = new ArrayList();
        while (iterator.hasNext()){
            Object key = iterator.next();
            Object value = redissonUtil.objRead(redissonClient, (String) key);
            logger.info("读取数据：key：{}，value:{}",key,value);
            Map map = new HashMap();
            map.put(key,value);
            list.add(map);
        }
        return list;
    }
}
