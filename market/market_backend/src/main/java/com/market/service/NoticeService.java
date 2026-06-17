package com.market.service;

import com.market.entity.Notice;
import java.util.List;

public interface NoticeService extends BaseService<Notice, Long> {
    List<Notice> selectList(String keyword, Integer noticeType, Integer status);
    List<Notice> selectAll();
    int updateStatus(Long id, Integer status);
    int updateTop(Long id, Integer isTop);
    List<Notice> selectTop(Integer limit);
}
