package com.market.service.impl;

import com.market.entity.Member;
import com.market.mapper.MemberMapper;
import com.market.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberMapper memberMapper;
    @Override public Member getById(Long id) { return memberMapper.selectById(id); }
    @Override public List<Member> list(Member query) { return memberMapper.selectAll(); }
    @Override public int save(Member member) { return memberMapper.insert(member); }
    @Override public int update(Member member) { return memberMapper.update(member); }
    @Override public int deleteById(Long id) { return memberMapper.deleteById(id); }
    @Override public int deleteBatch(List<Long> ids) { return memberMapper.deleteBatch(ids); }
    @Override public List<Member> selectList(String keyword, Long levelId, Integer status) { return memberMapper.selectList(keyword, levelId, status); }
    @Override public List<Member> selectAll() { return memberMapper.selectAll(); }
    @Override public int updateStatus(Long id, Integer status) { return memberMapper.updateStatus(id, status); }
    @Override public int updatePoints(Long id, Integer points) { return memberMapper.updatePoints(id, points); }
    @Override public int updateBalance(Long id, BigDecimal balance) { return memberMapper.updateBalance(id, balance); }
    @Override public Long selectTotalCount() { return memberMapper.selectTotalCount(); }
}
