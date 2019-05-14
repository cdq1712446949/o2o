package com.cdq.o2o.web.superadmin;

import com.cdq.o2o.entity.Area;
import com.cdq.o2o.service.AreaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/superadmin")
public class AreaController {

    Logger logger= LoggerFactory.getLogger(AreaController.class);

    @Autowired
    private AreaService areaService;

    @RequestMapping(value = "/listarea",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> listArea(){
        logger.info("====start====");
        long startTime=System.currentTimeMillis();
        Map<String,Object> map=new HashMap<>();
        List<Area> areaList=new ArrayList<>();
        try{
            areaList=areaService.getAreaList();
            map.put("rows",areaList);
            map.put("total",areaList.size());
        }catch (Exception e){
            map.put("success",false);
            map.put("error",e.toString());
        }
        logger.error("test error!");
        long endTime=System.currentTimeMillis();
        logger.debug("costTime:[{}ms]",endTime-startTime);
        logger.info("====end====");
        return map;
    }

}
