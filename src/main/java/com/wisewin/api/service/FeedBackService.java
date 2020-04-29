package com.wisewin.api.service;

import com.wisewin.api.dao.FeedBackDAO;
import org.apache.ibatis.annotations.Param;
import org.omg.CORBA.OBJ_ADAPTER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Wang bin
 * @date: Created in 13:54 2019/10/10
 */
@Service
public class FeedBackService {

    @Autowired
    private FeedBackDAO feedBackDAO;

    public Map<String, Object> queryFeedBack(String describc,
                                            String apStatus,
                                            Integer pageNo, Integer pageSize){
        List<FeedBackDAO> feedBackDAOS = feedBackDAO.feedBackList(describc, apStatus, pageNo, pageSize);
        int i = feedBackDAO.feedBackCount(describc, apStatus);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("feedBackDAOS",feedBackDAOS);
        map.put("count",i);
        return map;

    }


    public int updateFeed(String status, Integer id){
        int i = 0;
        if(status.equals("0")){
            i = feedBackDAO.updateFeedBack("0",id) ;
        } else {
            i = feedBackDAO.updateFeedBack("1",id) ;
        }
        return i;
    }
}
