package com.cdq.o2o.service;

import com.cdq.o2o.entity.HeadLine;

import java.util.List;

public interface HeadLineService {

    /**
     * 根据传入的条件查询头条列表
     * @param headLineCondition
     * @return
     */
    List<HeadLine> getHeadLineList(HeadLine headLineCondition);

}
