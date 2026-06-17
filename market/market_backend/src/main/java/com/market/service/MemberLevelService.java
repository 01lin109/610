package com.market.service;

import com.market.entity.MemberLevel;
import java.util.List;

public interface MemberLevelService extends BaseService<MemberLevel, Long> {
    List<MemberLevel> selectAll();
}
