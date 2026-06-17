package com.market.mapper;

import com.market.entity.Order;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderMapper {
    Order selectById(Long id);
    List<Order> selectList(@Param("orderNo") String orderNo, @Param("status") Integer status,
                           @Param("startTime") String startTime, @Param("endTime") String endTime);
    List<Order> selectAll();
    int insert(Order order);
    int update(Order order);
    int deleteById(Long id);
    int deleteBatch(@Param("ids") List<Long> ids);
    int updateStatus(@Param("id") Long id, @Param("status") Integer status);
    int updateDelivery(@Param("id") Long id, @Param("deliveryNo") String deliveryNo,
                       @Param("deliveryCompany") String deliveryCompany,
                       @Param("deliveryTime") LocalDateTime deliveryTime,
                       @Param("status") Integer status);
    int countByStatus(@Param("status") Integer status);
    List<Order> selectByMemberId(Long memberId);
}
