package com.market.mapper;

import com.market.entity.Member;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MemberMapper {
    Member selectById(Long id);
    Member selectByUsername(String username);
    Member selectByPhone(String phone);
    List<Member> selectList(@Param("keyword") String keyword, @Param("levelId") Long levelId, @Param("status") Integer status);
    List<Member> selectAll();
    int insert(Member member);
    int update(Member member);
    int deleteById(Long id);
    int deleteBatch(@Param("ids") List<Long> ids);
    int updateStatus(@Param("id") Long id, @Param("status") Integer status);
    int updatePoints(@Param("id") Long id, @Param("points") Integer points);
    int updateBalance(@Param("id") Long id, @Param("balance") java.math.BigDecimal balance);
    Long selectTotalCount();
}
