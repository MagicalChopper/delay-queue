package com.love_peace.delayqueue.consume.service;

import com.love_peace.delayqueue.product.DelayJob;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Author: LiuHao
 * @Descirption:
 * @Date: 2018/10/19_16:14
 */
@Service
public class XXXJobServiceImpl implements ExecuteJobService {

    @Override
    public void execute(DelayJob job) {
        Map map = job.getJobParams();
        String value = (String) map.get("value");
        System.out.println("我被延时执行了，哈哈哈哈哈,时间到了，我被触发了");
        System.out.println(value);
    }

}
