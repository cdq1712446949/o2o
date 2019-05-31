package com.cdq.o2o.dao;

import com.cdq.o2o.entity.HeadLine;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class HeadLineDaoTest extends BaseTest {
    @Autowired
    private HeadLineDao headLineDao;

    @Test
    public void testQueryHeadLineList(){
        HeadLine headLine=new HeadLine();
        List<HeadLine> lines=headLineDao.queryHeadLineList(headLine);
        System.out.println("查询数量："+lines.size());
    }

}
