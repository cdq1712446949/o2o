package com.cdq.o2o.dao;

import com.cdq.o2o.entity.Area;

import java.util.List;

public interface AreaDao{

    /**
     * 查询区域列表
     * @return areaList
     */
    List<Area> queryArea();

}
