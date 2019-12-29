package com.onfresh.cloud.api.service.order;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.onfresh.cloud.api.constants.ApiConstants;
import com.onfresh.cloud.api.service.order.vo.JDDJOrder;
import com.onfresh.cloud.api.service.order.vo.OrderPageResponseVo;
import com.onfresh.cloud.api.service.order.vo.OrderQueryVo;
import com.onfresh.cloud.api.service.order.vo.OrderResultResponseVo;
import com.onfresh.cloud.api.util.ApiRequestUtil;
import com.onfresh.cloud.api.vo.BaseResponseVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ApiOnfreshOrderService {
    private static final Logger logger = LoggerFactory.getLogger(ApiOnfreshOrderService.class);

    private Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
        @Override
        public LocalDateTime deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            String datetime = json.getAsJsonPrimitive().getAsString();
            return LocalDateTime.parse(datetime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
    }).registerTypeAdapter(LocalDate.class, new JsonDeserializer<LocalDate>() {
        @Override
        public LocalDate deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            String datetime = json.getAsJsonPrimitive().getAsString();
            return LocalDate.parse(datetime, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
    }).create();

    @Autowired
    private ApiRequestUtil apiRequestUtil;

    public void saveNewOrderFromJDDJ(Long orderId) {
        List<JDDJOrder> orderList = queryOrderListFromJDDJ(OrderQueryVo.builder().orderId(orderId).build());
//        if (CollectionUtils.isNotEmpty(orderList)) {
//            List<OnfreshOrder> collect = orderList.stream().map(x -> {
//                OnfreshOrder onfreshOrder = new OnfreshOrder();
//                BeanUtils.copyProperties(x, onfreshOrder);
//                onfreshOrder.setIsDelete(IsDeleteEnum.NOT_DELETE.getCode());
//                return onfreshOrder;
//            }).collect(Collectors.toList());
//            onfreshOrderService.saveAllOrder(collect);
//        }
    }

    /**
     * 查询到家订单列表接口
     * @param query
     * @return
     */
    public List<JDDJOrder> queryOrderListFromJDDJ(OrderQueryVo query){
        List<JDDJOrder> result = null;
        BaseResponseVo<String> responseStr = apiRequestUtil.parseBaseResponseVo(ApiConstants.ORDER_LIST_QUERY_URL, gson.toJson(query));
        if (responseStr.getCode() == 0) {
            OrderResultResponseVo<String> resultStr = gson.fromJson(responseStr.getData(), new TypeToken<OrderResultResponseVo<String>>() {
            }.getType());
            if (resultStr.getCode() == 0) {
                OrderPageResponseVo<JDDJOrder> orderPage = gson.fromJson(resultStr.getResult(), new TypeToken<OrderPageResponseVo<JDDJOrder>>() {
                }.getType());
                result = orderPage.getResultList();
            } else {
                logger.info("order query from jd error:{}", resultStr.getMsg());
            }
        } else {
            logger.info("order query from jd error:{}", responseStr.getMsg());
        }
        return result;
    }

    /**
     * 商家接单后 在14分钟时如果商家未呼叫骑手 自动呼叫骑手取件
     * <p>
     * 京东配送订单模式下，商家拣货完成时需要发货调用此接口，召唤达达配送员。
     * 呼叫配送员后，订单状态不会改变。需要等到配送员取货后，orderStatus订单状态才会由32000:等待出库变为33040:配送中。
     * 可以利用运单状态消息中的配送状态进行判断。
     * 配送状态10为等待抢单，即表明商家呼叫过配送员。
     *
     * @param billId
     */
    public void orderAutoOutStore(Long billId) {
//        OnfreshOrder find = onfreshOrderService.findOrderByOrderId(billId);
//        if (find != null && find.getOrderAgingType() == OrderAgingTypeEnum.ONE_HOURS.getCode().intValue())
//            orderAutoOutStoreTimer.doTask(billId);
    }
}
