package com.market.mapper;

import com.market.entity.Refund;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RefundMapper {
    Refund selectById(Long id);
    List<Refund> selectList(@Param("refundNo") String refundNo, @Param("status") Integer status,
                            @Param("startTime") String startTime, @Param("endTime") String endTime);
    List<Refund> selectAll();
    int insert(Refund refund);
    int update(Refund refund);
    int deleteById(Long id);
    int updateStatus(@Param("id") Long id, @Param("status") Integer status);
    int handleRefund(@Param("id") Long id, @Param("status") Integer status,
                     @Param("handleNote") String handleNote, @Param("handlerId") Long handlerId);
    int countByStatus(@Param("status") Integer status);
}
