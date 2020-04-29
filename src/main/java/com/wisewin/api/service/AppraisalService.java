package com.wisewin.api.service;

import com.wisewin.api.dao.AppraisalDAO;
import com.wisewin.api.dao.ReisedDAO;
import com.wisewin.api.entity.bo.AppraisalBO;
import com.wisewin.api.entity.bo.ReisedUserBO;
import com.wisewin.api.entity.dto.AppraisalDTO;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: Wang bin
 * @date: Created in 11:16 2019/9/5
 */
@Service
public class AppraisalService {

    @Autowired
    private AppraisalDAO appraisalDAO;

    @Autowired
    private ReisedDAO reisedDAO;

    static final Logger log = LoggerFactory.getLogger(AppraisalService.class);

    /**
     * 获取鉴定列表
     *
     * @param map
     * @return
     */
    public Map<String, Object> listAppraisal(Map<String, Object> map) {
        List<AppraisalDTO> list = new ArrayList<AppraisalDTO>();
        List<AppraisalBO> appraisalDAOS = appraisalDAO.queryAppraisal(map);
        //log.info("appraisalDAOs:{}",appraisalDAOS);
        Integer integer = appraisalDAO.queryAppraisalConunt(map);
        Map<String, Object> mp = new HashMap<String, Object>();
        for (AppraisalBO dao : appraisalDAOS) {
            AppraisalDTO appraisalDTO = new AppraisalDTO();
            appraisalDTO.setTitle(dao.getTitle());
            appraisalDTO.setAppraisalState(dao.getAppraisalState());
            appraisalDTO.setApCase(dao.getApCase());
            appraisalDTO.setApImages(Arrays.asList(dao.getApImages().split(",")).get(0));
            appraisalDTO.setAppraisalTypeName(dao.getAppraisalTypeName());
            appraisalDTO.setDescribc(dao.getDescribc());
            appraisalDTO.setId(dao.getId());
            appraisalDTO.setUserId(dao.getUserId());
            appraisalDTO.setAppraisalTypeId(dao.getAppraisalTypeId());
            appraisalDTO.setUserName(dao.getUserName());
            appraisalDTO.setState(dao.getStatus());
            appraisalDTO.setCreateTime(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(dao.getCreateTime()));
            appraisalDTO.setUpdateTime(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(dao.getUpdateTime()));
            appraisalDTO.setReisedState(dao.getReisedState());
            list.add(appraisalDTO);
        }
        mp.put("list", list);
        mp.put("count", integer);
        return mp;
    }


    /**
     * 设置为鉴定案例
     *
     * @param
     */
    public void updateAppraisalCase(Integer id) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("case", "yes");
        map.put("id", id);
        appraisalDAO.updateAppraisalCase(map);
    }


    /**
     * 获取鉴定详情
     *
     * @param appraisalId
     * @return 鉴定对象
     */
    public Map<String, Object> appraisalDetails(Integer appraisalId) {
        AppraisalBO dao = appraisalDAO.queryappraisalById(appraisalId);
        AppraisalDTO appraisalDTO = new AppraisalDTO();
        Map<String, Object> map = new HashMap<String, Object>();
        if (dao != null) {
            appraisalDTO.setTitle(dao.getTitle());
            appraisalDTO.setAppraisalState(dao.getAppraisalState());
            appraisalDTO.setApCase(dao.getApCase());
            appraisalDTO.setAppraisalTypeName(dao.getAppraisalTypeName());
            appraisalDTO.setDescribc(dao.getDescribc());
            appraisalDTO.setId(dao.getId());
            appraisalDTO.setUserId(dao.getUserId());
            appraisalDTO.setAppraisalTypeId(dao.getAppraisalTypeId());
            appraisalDTO.setUserName(dao.getUserName());
            appraisalDTO.setCreateTime(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(dao.getCreateTime()));
            appraisalDTO.setUpdateTime(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(dao.getUpdateTime()));
            map.put("appraisal", appraisalDTO);
            map.put("img", Arrays.asList(dao.getApImages().split(",")));
        }
        return map;
    }


    /**
     * 获取纠错评论
     * @param commentId
     * @param pageNo
     * @param pageSize
     * @return 返回被采纳评论下的纠错列表
     */
    public List<ReisedUserBO> queryReisedList(Integer commentId, Integer pageNo, Integer pageSize){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("commentId", commentId);
        map.put("pageOffset", pageNo);
        map.put("pageSize", pageSize);
        List<ReisedUserBO> reisedUserBOS = reisedDAO.queryReised(map);
        return reisedUserBOS;
    }

    /**
     * 修改鉴定
     * @param appraisal
     * @return
     */
    public int updateAppraisal(AppraisalBO appraisal){
        return appraisalDAO.updateAppraisal(appraisal);
    }


    public int  updateAppraisalStateById(@Param("state")String state, @Param("id")Integer id){

        return appraisalDAO.updateAppraisalStateById(state,id) ;
    }


   public  int updateAppraisalreisedState(String reisedState, Integer id){
        return appraisalDAO.updateAppraisalreisedState(reisedState, id);
   }


}
