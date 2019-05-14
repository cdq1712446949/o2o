package com.cdq.o2o.service;

import com.cdq.o2o.dao.BaseTest;
import com.cdq.o2o.entity.Area;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AreaServiceTest extends BaseTest {

    @Autowired
    private AreaService areaService;

    @Test
    public void testGetArea(){
        List<Area> areaList=areaService.getAreaList();
        System.out.println(areaList.get(0).getAreaName());
    }

}
