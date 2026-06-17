package com.market.service.impl;

import com.market.entity.Notice;
import com.market.mapper.NoticeMapper;
import com.market.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {
    private final NoticeMapper mapper;
    @Override public Notice getById(Long id) { return mapper.selectById(id); }
    @Override public List<Notice> list(Notice q) { return mapper.selectAll(); }
    @Override public int save(Notice n) { return mapper.insert(n); }
    @Override public int update(Notice n) { return mapper.update(n); }
    @Override public int deleteById(Long id) { return mapper.deleteById(id); }
    @Override public int deleteBatch(List<Long> ids) { return mapper.deleteBatch(ids); }
    @Override public List<Notice> selectList(String keyword, Integer noticeType, Integer status) { return mapper.selectList(keyword, noticeType, status); }
    @Override public List<Notice> selectAll() { return mapper.selectAll(); }
    @Override public int updateStatus(Long id, Integer status) { return mapper.updateStatus(id, status); }
    @Override public int updateTop(Long id, Integer isTop) { return mapper.updateTop(id, isTop); }
    @Override public List<Notice> selectTop(Integer limit) { return mapper.selectTop(limit); }
}
