package com.cdq.o2o.dao;
import static org.junit.Assert.assertEquals;
import com.cdq.o2o.entity.Area;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class TestAreaDao extends BaseTest{

    @Autowired
    private AreaDao areaDao;

    @Test
    public void testQueryArea(){
        List<Area> areasList=areaDao.queryArea();
        System.out.println(String.valueOf(areasList.size()));
    }


}
