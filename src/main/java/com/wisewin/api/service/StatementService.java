package com.wisewin.api.service;

import com.wisewin.api.dao.StatementDAO;
import com.wisewin.api.entity.bo.StatementBO;
import com.wisewin.api.entity.bo.StatisticalBO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
@Transactional
public class StatementService {
    @Resource
    StatementDAO statementDAO;

    public List<StatisticalBO> getStatement(Date date){
        //获取今天日期
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);//获取年份
        int month = calendar.get(Calendar.MONTH) + 1;//获取月份
        int day=calendar.get(Calendar.DATE);//获取日

        //获取当年统计信息
        StatementBO statementYear=statementDAO.getRegisteredCountAndActiveCount(year,null,null);
        if(statementYear==null){
            statementYear=new StatementBO();
        }
        statementYear.setCommentSum(statementDAO.getCommentCount(year,null,null));
        statementYear.setAppraisalSum(statementDAO.getAppraisalCount(year,null,null));
        //获取当月统计信息
        StatementBO statementMonth=statementDAO.getRegisteredCountAndActiveCount(year,month,null);
        if(statementMonth==null){
            statementMonth=new StatementBO();
        }
        statementMonth.setCommentSum(statementDAO.getCommentCount(year,month,null));
        statementMonth.setAppraisalSum(statementDAO.getAppraisalCount(year,month,null));
        //获取当日统计信息
        StatementBO statementDay=statementDAO.getRegisteredCountAndActiveCount(year,month,day);
        if(statementDay==null){
            statementDay=new StatementBO();
        }
        statementDay.setCommentSum(statementDAO.getCommentCount(year,month,day));
        statementDay.setAppraisalSum(statementDAO.getAppraisalCount(year,month,day));


        //为了前台方便，转换数据格式
        List<StatisticalBO> statementBOList=new ArrayList<StatisticalBO>();
        //注册数
        StatisticalBO registered=new StatisticalBO();
        registered.setDayCount(statementDay.getRegistrationSum());
        registered.setMonthCount(statementMonth.getRegistrationSum());
        registered.setYearCount(statementYear.getRegistrationSum());
        registered.setTypeName("注册数");
        statementBOList.add(registered);

        //活跃数数
        StatisticalBO active=new StatisticalBO();
        active.setDayCount(statementDay.getActiveSum());
        active.setMonthCount(statementMonth.getActiveSum());
        active.setYearCount(statementYear.getActiveSum());
        active.setTypeName("活跃数");
        statementBOList.add(active);

        //评论数
        StatisticalBO comment=new StatisticalBO();
        comment.setDayCount(statementDay.getCommentSum());
        comment.setMonthCount(statementMonth.getCommentSum());
        comment.setYearCount(statementYear.getCommentSum());
        comment.setTypeName("评论数");
        statementBOList.add(comment);

        //物品数
        StatisticalBO appraisal=new StatisticalBO();
        appraisal.setDayCount(statementDay.getAppraisalSum());
        appraisal.setMonthCount(statementMonth.getAppraisalSum());
        appraisal.setYearCount(statementYear.getAppraisalSum());
        appraisal.setTypeName("发布物品数");
        statementBOList.add(appraisal);
        return statementBOList;
    }

}
