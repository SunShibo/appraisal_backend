package com.wisewin.api.service;

import com.wisewin.api.common.Const;
import com.wisewin.api.dao.AppraisalDAO;
import com.wisewin.api.dao.CommentDAO;
import com.wisewin.api.entity.bo.*;
import com.wisewin.api.entity.dto.AcceptLogDTO;
import com.wisewin.api.entity.dto.CommentDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
public class CommentService {
    @Resource
    private CommentDAO commentDAO;

    @Resource
    private AppraisalDAO appraisalDAO;

    static final Logger log = LoggerFactory.getLogger(CommentService.class);

    public ResponseBO selectSum(Date startTime, Date endTime) throws ParseException {
        ResponseBO responseBO = new ResponseBO();
        FormUtilBO commentSum = new FormUtilBO();
        FormUtilBO publishSum = new FormUtilBO();
        //查询当天
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        int comment = this.getCommentSum(map);
        commentSum.setDay(comment);
        int appraisal = this.getAppraisalSum(map);
        publishSum.setDay(appraisal);

        //查询本月
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //获取前月的第一天
        Calendar cal_1 = Calendar.getInstance();
        //获取当前日期
        cal_1.setTime(startTime);
        cal_1.add(Calendar.MONTH, 0);
        cal_1.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        String firstDay = format.format(cal_1.getTime());
        String str = firstDay + " 00:00:00";
        Date startTime1 = dd.parse(str);

        Calendar ct = Calendar.getInstance();
        ct.setTime(endTime);
        String firstDay1 = format.format(ct.getTime());
        String endTime1 = firstDay1 + " 23:59:59";
        Date endTime111 = dd.parse(endTime1);

        Map<String, Object> map1 = new HashMap<String, Object>();
        map1.put("startTime", startTime1);
        map1.put("endTime", endTime111);
        int commentMouth = this.getCommentSum(map1);
        commentSum.setMonth(commentMouth);
        int appraisalMouth = this.getAppraisalSum(map1);
        publishSum.setMonth(appraisalMouth);

        //查询本年

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startTime);
        int s = calendar.get(calendar.YEAR);

        Calendar calendar1 = Calendar.getInstance();
        calendar1.clear();
        calendar1.set(Calendar.YEAR, s);
        String firstDay2 = format.format(calendar1.getTime());
        String endTime2 = firstDay2 + " 00:00:00";
        Date s1 = dd.parse(endTime2);

        Map<String, Object> map2 = new HashMap<String, Object>();
        map2.put("startTime", s1);
        map2.put("endTime", endTime);
        int commentYear = this.getCommentSum(map1);
        commentSum.setYear(commentYear);
        int appraisalYear = this.getAppraisalSum(map1);
        publishSum.setYear(appraisalYear);


        responseBO.setCommentSum(commentSum);
        responseBO.setPublishSum(publishSum);

        return responseBO;
    }

    //查询总评论数
    public int getCommentSum(Map<String, Object> map) {
        return commentDAO.getCommentSum(map);
    }

    //商品发布数
    public int getAppraisalSum(Map<String, Object> map) {
        return commentDAO.getAppraisalSum(map);
    }


    public Map<String, Object> getCommentByAppraisalId(Integer id, Integer pageOffset, Integer pageSize) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<CommentDTO> list = new ArrayList<CommentDTO>();
        map.put("pageOffset", pageOffset);
        map.put("pageSize", pageSize);
        map.put("appraisalId", id);
        log.info("pageOffset:{}",pageOffset);
        log.info("pageSize:{}",pageSize);
        log.info("id:{}",id);

        List<GradeBO> gradeBOS = commentDAO.queryGradeList();

        List<ComtBO> commentBOS = commentDAO.queryComment(map);
        Integer count  = commentDAO.queryCommentCount(map);
        log.info("commentbos:{}",commentBOS);
        if (!CollectionUtils.isEmpty(commentBOS)) {
            for (ComtBO commentBO : commentBOS) {
                CommentDTO dto = new CommentDTO();
                dto.setId(commentBO.getId());
                dto.setCnComment(commentBO.getCnComment());
                dto.setCommentId(commentBO.getId());
                dto.setGradeName(isGrade(gradeBOS,commentBO.getIntegral()));
                dto.setHeadUrl(commentBO.getHeadUrl());
                dto.setJudge(commentBO.getJudge());
                dto.setUserId(commentBO.getUserId());
                dto.setName(commentBO.getName());
                dto.setGoodsState(commentBO.getGoodsState());
                dto.setCreateTime(commentBO.getCreateTime().substring(0,19));
                if(dto.getGoodsState().equals("yes")){
                    list.add(0,dto);
                } else {
                    list.add(dto);
                }

            }
        }
        map.put("commentbos",list);
        map.put("count",count);
        return map;
    }


    /**
     * 获取用户等级
     * @param integral
     * @return
     */
    private String isGrade(List<GradeBO> gradeBOS,Integer integral){
        /*List<GradeBO> gradeBOS = commentDAO.queryGradeList();
        try {
            for(int i = 0; i < gradeBOS.size(); i++ ){
                if(gradeBOS.get(i).getEmpirical() <= integral && gradeBOS.get(i).getEmpiricalEnd() >= integral){
                    return gradeBOS.get(i).getGradeName();
                }
            }
        } catch (Exception e){

        } finally {
            return "新手";
        }*/
        for (GradeBO gradeBO : gradeBOS) {
            if(integral <= gradeBO.getEmpirical()){
                return gradeBO.getGradeName();
            }
        }
        return "未知等级";
    }

    public int deleteCommentById(Integer adminId,Integer id){
        CommentBO commentBO = commentDAO.selectCommentStatusById(id);
        int i = commentDAO.deleteComment(id);
        this.addLog(adminId,commentBO.getUserId(),Const.DEL,commentBO.getCnComment());
        return i ;
    }


    public int selectCommentGoods(Integer commentId){
       return  commentDAO.selectCommentGoods(commentId) ;
    }


    public  int updateCommentGoods(Integer adminId,String goodsState,Integer id){
        int i = 0;
        AppraisalBO appraisalBO = new AppraisalBO();
        //判断该评论状态为真假
        CommentBO commentBO = commentDAO.selectCommentStatusById(id);
        appraisalBO.setId(commentBO.getAppraisalId());

        if("yes".equals(goodsState)){
            //将评论列为采纳
             i = commentDAO.updateCommentGoods(goodsState, id);

            if(commentBO.getJudge().equals("genuine")){
                //真
                //将鉴定列为真
                appraisalBO.setAppraisalState("adopt");
            } else {
                appraisalBO.setAppraisalState("failedpass");
            }
            i = appraisalDAO.updateAppraisal(appraisalBO);


            this.addLog(adminId,commentBO.getUserId(), Const.ACCEPT,commentBO.getCnComment());
        } else {
            //取消评论的采纳
            i = commentDAO.updateCommentGoods(goodsState, id);
            //将鉴定置为待鉴定
            appraisalBO.setAppraisalState("undetermined");
            i = appraisalDAO.updateAppraisal(appraisalBO);
            this.addLog(adminId,commentBO.getUserId(), Const.CANCEL,commentBO.getCnComment());
            //将纠错状态列为无纠错内容
          //  appraisalDAO.updateAppraisalreisedState("0",commentBO.getAppraisalId());
        }
        return i;

    }

    public void addComment(Integer appraisalId, Integer userId, String cnComment, String judge) {
        appraisalDAO.addComment(appraisalId,userId,cnComment,judge);
    }


    public  void addLog(Integer adminId,Integer userId,String type,String content){
        appraisalDAO.addLog(new AcceptLogDTO(adminId,userId,type,content));
    }

    public Map<String,Object> queryLog(Date startTime,Date endTime,String userName,Integer pageOffset,Integer pageSize){
        Map<String,Object> paramMap=new HashMap<String, Object>();
        paramMap.put("startTime",startTime);
        paramMap.put("endTime",endTime);
        paramMap.put("uName",userName);
        paramMap.put("pageOffset",pageOffset);
        paramMap.put("pageSize",pageSize);
        Map<String,Object> resultMap=new HashMap<String, Object>();
        resultMap.put("list",appraisalDAO.queryLog(paramMap));
        resultMap.put("count",appraisalDAO.queryLogCount(paramMap));
        return resultMap;
    }
}


