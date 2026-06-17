package com.market.service.impl;

import com.market.entity.MemberLevel;
import com.market.mapper.MemberLevelMapper;
import com.market.service.MemberLevelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberLevelServiceImpl implements MemberLevelService {
    private final MemberLevelMapper mapper;
    @Override public MemberLevel getById(Long id) { return mapper.selectById(id); }
    @Override public List<MemberLevel> list(MemberLevel q) { return mapper.selectAll(); }
    @Override public int save(MemberLevel level) { return mapper.insert(level); }
    @Override public int update(MemberLevel level) { return mapper.update(level); }
    @Override public int deleteById(Long id) { return mapper.deleteById(id); }
    @Override public int deleteBatch(List<Long> ids) { return 0; }
    @Override public List<MemberLevel> selectAll() { return mapper.selectAll(); }
}
