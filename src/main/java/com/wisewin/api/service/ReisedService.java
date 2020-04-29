package com.wisewin.api.service;

import com.sun.corba.se.spi.ior.ObjectKey;
import com.wisewin.api.common.Const;
import com.wisewin.api.dao.AppraisalDAO;
import com.wisewin.api.dao.CommentDAO;
import com.wisewin.api.dao.ReisedDAO;
import com.wisewin.api.entity.bo.AppraisalBO;
import com.wisewin.api.entity.bo.CommentBO;
import com.wisewin.api.entity.bo.ReisedBO;
import com.wisewin.api.entity.dto.CommentDTO;
import com.wisewin.api.entity.dto.ReisedDTO;
import com.wisewin.api.entity.dto.ResultDTO;
import com.wisewin.api.entity.dto.ResultDTOBuilder;
import org.glassfish.hk2.api.messaging.SubscribeTo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: Wang bin
 * @date: Created in 11:09 2019/9/9
 */
@Service
public class ReisedService {
    
    @Autowired
    private ReisedDAO reisedDAO;

    @Autowired
    private CommentDAO commentDAO;

    @Autowired
    private CommentService commentService;

    @Autowired
    private AppraisalDAO appraisalDAO;

    private static final Logger log = LoggerFactory.getLogger(ReisedService.class);


    /**
     * 获取纠错列表
     * @param map
     * @return
     */
    public List<ReisedDTO>  listReised(Map<String, Object> map){
        List<ReisedBO> reisedBOS = reisedDAO.queryReisedList(map);
        List<ReisedDTO> list = new ArrayList<ReisedDTO>();
        if(CollectionUtils.isEmpty(reisedBOS)){
            for (ReisedBO reisedBO : reisedBOS) {
                ReisedDTO reisedDTO = new ReisedDTO();
                reisedDTO.setAppraisalId(reisedDTO.getAppraisalId());
                reisedDTO.setApRead(reisedDTO.getApRead());
                reisedDTO.setApShow(reisedDTO.getApShow());
                reisedDTO.setCommentId(reisedDTO.getCommentId());
                reisedDTO.setCreateTime(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(reisedBO.getCreateTime()) );
                reisedDTO.setUpdateTime(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(reisedBO.getUpdateTime()));
                reisedDTO.setContent(reisedDTO.getContent());
                reisedDTO.setId(reisedBO.getId());
                reisedDTO.setStatus(reisedDTO.getStatus());
                reisedDTO.setUserId(reisedDTO.getUserId());
                list.add(reisedDTO);
            }
        }

        return list;
    }

    /**
     * 采纳纠错信息
     * @param reisedId
     * @return
     */
    public ResultDTO adoptReised(Integer adminId,Integer reisedId){
        if(reisedId == null){
            return ResultDTOBuilder.failure("0000001");
        }
        ReisedBO reisedBO = reisedDAO.selectReisedById(reisedId);
        if(reisedBO == null){
            return ResultDTOBuilder.failure("0000002");
        }
        //将原本鉴定下的评论都置为未采纳
        int i = reisedDAO.updateCommentByAppraisalId(reisedBO.getAppraisalId());
        if(i > 0){
           // reisedBO.setStatus(Const.REISED_ACCEPT);
            i = reisedDAO.updateReisedStatus(reisedId);

        }
        //将纠错插入评论表成为新采纳评论
        if(i > 0){
            CommentBO comment = new CommentBO();
            comment.setAppraisalId(reisedBO.getAppraisalId());
            comment.setCnComment(reisedBO.getContent());
            comment.setUserId(reisedBO.getUserId());
            comment.setGoodsState("yes");
            comment.setJudge(reisedBO.getJudge());
            log.info("comment:{}",comment);
            commentDAO.insertComment(comment);

            //修改鉴定案例状态
            AppraisalBO appraisalBO = new AppraisalBO();
            appraisalBO.setId(comment.getAppraisalId());
            if(comment.getJudge().equals(Const.JIDGE_GENUINE)){
                appraisalBO.setAppraisalState(Const.APPRAISAL_STATE_ADOPT);
                appraisalDAO.updateAppraisal(appraisalBO);
            }
            if(comment.getJudge().equals(Const.COMMENT_COUNTERFEIT)){
                appraisalBO.setAppraisalState(Const.APPRAISAL_STATE_FAILEDPASS);
                appraisalDAO.updateAppraisal(appraisalBO);
            }

            commentService.addLog(adminId,comment.getUserId(),Const.ERROR,comment.getCnComment());
            return ResultDTOBuilder.success("0000000");
        }
        return ResultDTOBuilder.failure("0000002");
    }
}
