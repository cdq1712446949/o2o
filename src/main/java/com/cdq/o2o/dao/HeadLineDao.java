package com.cdq.o2o.dao;

import com.cdq.o2o.entity.HeadLine;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HeadLineDao {

    /**
     * 查询头条列表
     * @param headLineCondition
     * @return
     */
    List<HeadLine> queryHeadLineList(@Param("headLineCondition") HeadLine headLineCondition);

}
