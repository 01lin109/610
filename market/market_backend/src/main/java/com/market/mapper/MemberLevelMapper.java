package com.market.mapper;

import com.market.entity.MemberLevel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MemberLevelMapper {
    MemberLevel selectById(Long id);
    List<MemberLevel> selectAll();
    int insert(MemberLevel level);
    int update(MemberLevel level);
    int deleteById(Long id);
}
