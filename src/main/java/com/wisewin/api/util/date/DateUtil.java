package com.wisewin.api.util.date;


import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 转换时间公用类
 */
public class DateUtil {

    /**
     * String转换为date类型
     * yyyy-MM-dd HH:mm:ss
     */
    public static Date gainDate(String date){
        Date  thisDate=null;
        try {
            //设置要获取到什么样的时间
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //获取String类型的时间
            thisDate = sdf.parse(date);
        }catch (Exception e){
            e.printStackTrace();
            return  null;
        }
        return thisDate;
    }


    /**
     * date转换为String类型
     * yyyy-MM-dd
     */
    public  static Date getDate(String date){
        Date thisDate=null;
        try {
            //设置要获取到什么样的时间
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            //获取String类型的时间
            thisDate = sdf.parse(date);
        }catch (Exception e){
            e.printStackTrace();
            return  null;
        }
      return  thisDate;

    }
    /**
     * date转换为String类型
     * yyyy-M-d
     */
    public  static String getStrDate(String date){
        Date thisDate=null;
        //设置要获取到什么样的时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
        try {
            //获取Date类型的时间
            thisDate = sdf.parse(date);

        }catch (Exception e){
            e.printStackTrace();
            return  null;
        }
        String str=sdf.format(thisDate);
        return str;

    }
    /**
     * date转换为String类型
     * yyyy-MM-dd
     */
    public  static String getStr(String date) {
        if(date==null || "".equals(date)){
            return null;
        }
        Date thisDate=null;
        //设置要获取到什么样的时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            //获取Date类型的时间
            thisDate = sdf.parse(date);

        }catch (Exception e){
            e.printStackTrace();
            return  null;
        }
        String str=sdf.format(thisDate);
        return str;

    }

    public  static String getDateStr(Date date) {
        if(date==null){
            return null;
        }
        String thisDate="";
        //设置要获取到什么样的时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            //获取Date类型的时间
            thisDate = sdf.format(date);
        }catch (Exception e){
            e.printStackTrace();
            return  null;
        }
        return thisDate;
    }

    /**
     * date转换为String类型
     * yyyy-MM-dd
     */
    public  static String getGainStr(String date) {
        Date thisDate=null;
        //设置要获取到什么样的时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            //获取Date类型的时间
            thisDate = sdf.parse(date);

        }catch (Exception e){
            e.printStackTrace();
            return  null;
        }
        String str=sdf.format(thisDate);
        return str;

    }

    /**
     * 获取过去7天内的日期数组
     * @param intervals      intervals天内
     * @return              日期数组
     */
    public List<Date> getDays(int intervals) {
        List<Date> pastDaysList = new ArrayList<Date>();
        for (int i = intervals -1; i >= 0; i--) {
            pastDaysList.add(getPastDate(i));
        }
        return pastDaysList;
    }
    /**
     * 获取过去第几天的日期
     * @param past
     * @return
     */
    public Date getPastDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String result = format.format(today);
        return  DateUtil.getDate(result);
    }
    /**
          * 判断时间是否在时间段内
          * @param nowTime
          * @param beginTime
          * @param endTime
          * @return
          */
public static boolean belongCalendar(Date nowTime, Date beginTime, Date endTime) {
//设置当前时间
Calendar date = Calendar.getInstance();
date.setTime(nowTime);
//设置开始时间
Calendar begin = Calendar.getInstance();
begin.setTime(beginTime);
//设置结束时间
Calendar end = Calendar.getInstance();
end.setTime(endTime);
//处于开始时间之后，和结束时间之前的判断
if (date.after(begin) && date.before(end)) {
 return true;
} else {
 return false;
 }
}
    /**
     * 获取昨天的日期
     * @return
     */
    public static String getYseterday(){
        Date date=new Date();//取时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE,-1);//把日期往前减少一天，若想把日期向后推一天则将负数改为正数
        date=calendar.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(date);
        return  dateString;
    }

    /**
     * 根据当前日期获得所在周的日期区间（周一日期）
     * @param date
     * @return
     */
    public static String getWeekStart(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        // System.out.println("要计算日期为:" + sdf.format(cal.getTime())); // 输出要计算日期
        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        // 获得当前日期是一个星期的第几天
        int day = cal.get(Calendar.DAY_OF_WEEK);
        // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
        String imptimeBegin = sdf.format(cal.getTime());
        // System.out.println("所在周星期一的日期：" + imptimeBegin);
        cal.add(Calendar.DATE, 6);
        String imptimeEnd = sdf.format(cal.getTime());
        // System.out.println("所在周星期日的日期：" + imptimeEnd);
        return imptimeBegin ;//+ "," + imptimeEnd;
    }

    /**
     * 根据当前日期获得所在周的日期区间（周日日期）
     * @param date
     * @return
     */
    public static String getWeekEnd(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        // System.out.println("要计算日期为:" + sdf.format(cal.getTime())); // 输出要计算日期
        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        // 获得当前日期是一个星期的第几天
        int day = cal.get(Calendar.DAY_OF_WEEK);
        // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
        String imptimeBegin = sdf.format(cal.getTime());
        // System.out.println("所在周星期一的日期：" + imptimeBegin);
        cal.add(Calendar.DATE, 6);
        String imptimeEnd = sdf.format(cal.getTime());
        // System.out.println("所在周星期日的日期：" + imptimeEnd);
        return imptimeEnd;
    }



    public static void main(String[] args) {
        DateUtil dateUtil =new DateUtil();
        Date start =DateUtil.getDate("2019-04-25 13:15:32");
        Date end =DateUtil.getDate("2019-05-25 13:15:32");
        Boolean str = belongCalendar(new Date(),start,end);
        System.out.println(str);



    }

}
