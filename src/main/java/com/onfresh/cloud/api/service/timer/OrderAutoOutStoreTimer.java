package com.onfresh.cloud.api.service.timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Timer;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class OrderAutoOutStoreTimer {
    private static final Logger logger = LoggerFactory.getLogger(OrderAutoOutStoreTimer.class);
    private static final Long taskTime = 14 * 60 * 1000L;
    public static Map<Long, Timer> timerMap = new ConcurrentHashMap<Long, Timer>();

    public void doTask(Long taskOrderId){
        logger.info("开始执行订单自动出库任务！, orderId:{}", taskOrderId);
        if(taskOrderId != null){
            Timer taskTimer = new Timer("Order Auto Out Store Timer");
            timerMap.put(taskOrderId, taskTimer);
            taskTimer.schedule(new OrderAutoOutStoreTimerTask(taskOrderId), taskTime);
        }
    }
}
