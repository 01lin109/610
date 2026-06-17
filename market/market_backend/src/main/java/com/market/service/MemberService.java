package com.market.service;

import com.market.entity.Member;
import java.math.BigDecimal;
import java.util.List;

public interface MemberService extends BaseService<Member, Long> {
    List<Member> selectList(String keyword, Long levelId, Integer status);
    List<Member> selectAll();
    int updateStatus(Long id, Integer status);
    int updatePoints(Long id, Integer points);
    int updateBalance(Long id, BigDecimal balance);
    Long selectTotalCount();
}
