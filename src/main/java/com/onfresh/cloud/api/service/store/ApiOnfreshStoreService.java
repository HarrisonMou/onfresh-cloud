package com.onfresh.cloud.api.service.store;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 京东到家门店类服务
 */
@Service
public class ApiOnfreshStoreService {
    private static Logger logger = LoggerFactory.getLogger(ApiOnfreshStoreService.class);
//
//    @Autowired
//    private AdminOnfreshStoreService adminOnfreshStoreService;
//
//    /**
//     * 进行门店创建修改监听接口 处理
//     * @param subscribeVenderCreateData
//     */
//    public void doVenderCreateOrUpdateSubcribe(JDDJSubscribeVenderCreateData subscribeVenderCreateData) {
//        logger.info("处理京东到家门店监听事件,data:{}, 消息类型:{}", subscribeVenderCreateData, StoreSubscribeStatusEnum.getMsgByStatusId(subscribeVenderCreateData.getStatusId()));
//        switch (StoreSubscribeStatusEnum.getEnumByStatusId(subscribeVenderCreateData.getStatusId())){
//            case CREATE_VENDER: adminOnfreshStoreService.syncStoreFromJd(subscribeVenderCreateData.getBillId()); break; //创建门店事件
//            case UPDATE_VENDER: adminOnfreshStoreService.updateStoreFromJd(subscribeVenderCreateData.getBillId()); break; //更新门店事件
//            case DELETE_VENDER: adminOnfreshStoreService.deleteStoreFromJd(subscribeVenderCreateData.getBillId()); break; //删除门店事件
//        }
//    }
//
//    /**
//     * 获取门店编码列表
//     * @return
//     */
//    public List<String> getStoreStationNos() {
//        logger.info("获取门店编码列表");
//        return adminOnfreshStoreService.getStoreStationNos();
//    }
}
