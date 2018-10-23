package com.love_peace.delayqueue.consume.service;

import com.love_peace.delayqueue.product.DelayJob;

/**
 * @Author: LiuHao
 * @Descirption: 延迟Job的服务实现
 * @Date: 2018/10/19_16:10
 */
public interface ExecuteJobService {
    void execute(DelayJob job);
}
