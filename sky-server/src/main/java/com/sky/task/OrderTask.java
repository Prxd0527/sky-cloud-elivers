package com.sky.task;

import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j
public class OrderTask {

    @Autowired
    private OrderMapper orderMapper;

    /**
     * 处理超时订单
     */

    @Scheduled(cron = "0 * * * * ?")    //每分钟触发一次
    public void processTimeoutOrder(){
        log.info("定时处理超时订单:{}", LocalDateTime.now());

        LocalDateTime Time = LocalDateTime.now().plusMinutes(-15);

        List<Orders> orderList = orderMapper.getByStatusAudOrderTimeLt(Orders.PENDING_PAYMENT, Time);

        if (orderList != null && orderList.size() > 0) {
            for (Orders orders : orderList) {
               orders.setStatus(Orders.CANCELLED);
               orders.setCancelReason("订单超时");
               orders.setCancelTime(LocalDateTime.now());
               orderMapper.update(orders);

            }
        }


    }

    /**
     * 处理一直处于派送中订单
     */
    @Scheduled(cron = "0 0 1 * * ?")     //每天触发一次
    public void processDeliveryOrder() {
        log.info("处理一直处于派送中订单:{}", LocalDateTime.now());

        LocalDateTime Time = LocalDateTime.now().plusHours(-1);

        List<Orders> orderList = orderMapper.getByStatusAudOrderTimeLt(Orders.DELIVERY_IN_PROGRESS, Time);

        if (orderList != null && orderList.size() > 0) {
            for (Orders orders : orderList) {
                orders.setStatus(Orders.COMPLETED);
                orderMapper.update(orders);

            }
        }
    }
}
