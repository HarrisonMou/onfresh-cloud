package com.onfresh.cloud.api.service.timer;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.onfresh.cloud.api.constants.ApiConstants;
import com.onfresh.cloud.api.util.ApiRequestUtil;
import com.onfresh.cloud.api.vo.BaseResponseVo;
import com.onfresh.cloud.api.vo.DetailResponseVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;

public class OrderAutoOutStoreTimerTask extends TimerTask {
    private static final Logger logger = LoggerFactory.getLogger(OrderAutoOutStoreTimerTask.class);
    private Long orderId;

    @Autowired
    private ApiRequestUtil apiRequestUtil;

    private Gson gson = new Gson();

    public OrderAutoOutStoreTimerTask(Long orderId) {
        this.orderId = orderId;
    }

    @Override
    public void run() {
        logger.info("run order auto out store timer");
        Map<String, String> params = new HashMap<>();
        params.put("orderId", new String().valueOf(this.orderId));
        params.put("operator", "章鱼买菜管理员");
        BaseResponseVo<String> responseVo = apiRequestUtil.parseBaseResponseVo(ApiConstants.ORDER_JDZB_DELIVERY_URL, gson.toJson(params));
        try {
            if(responseVo.getCode() == 0){
                DetailResponseVo<String> detail = gson.fromJson(responseVo.getData(), new TypeToken<DetailResponseVo<String>>() {
                }.getType());
                logger.info("调用拣货完成且众包配送接口成功！, msg:{}", detail.getMsg());
            }
        } catch (Exception e){
            logger.info("调用拣货完成且众包配送接口成功， 解析json字符串异常！");
        }

        if(OrderAutoOutStoreTimer.timerMap.get(this.orderId) != null){
            OrderAutoOutStoreTimer.timerMap.get(this.orderId).cancel();
            OrderAutoOutStoreTimer.timerMap.remove(this.orderId);
            logger.info("订单自动出库完成！ orderId:{}, 剩余任务数目：{}", this.orderId, OrderAutoOutStoreTimer.timerMap.size());
        }
        logger.info("order auto out store timer success");
    }
}
