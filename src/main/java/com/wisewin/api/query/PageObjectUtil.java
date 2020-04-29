package com.wisewin.api.query;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/3/14.
 */
public class PageObjectUtil<T> {

    @SuppressWarnings("unchecked")
    public PageObject savePageObject(int count, List<T> list, QueryInfo queryInfo) {
        PageObject page = new PageObject();
        int totalPage = 0;
        int pageSize = queryInfo.getPageSize();
        if (count % pageSize == 0) {
            totalPage = count / pageSize;
        } else {
            totalPage = count / pageSize + 1;
        }
        page.setDatas(list);
        page.setOffset(queryInfo.getPageOffset());
        page.setTotalPage(totalPage);

        page.setTotalRecord(count);
        page.setPageSize(pageSize);

        return page;
    }
    public static Map<String,String> getvalues(HttpServletRequest request){
        Map<String, String> param = new HashMap<String, String>();  //FIXME 未测试  支付宝返回的所有参数
        Map requestMaps = request.getParameterMap();
        for (Iterator iterator = requestMaps.keySet().iterator(); iterator.hasNext(); ) {
            String key = (String) requestMaps.get(iterator.next());
            String[] values = (String[]) requestMaps.get(key);
            String valus = "";
            for (int i = 0; i < values.length; i++) {
                valus = (i == values.length - 1) ? values + values[i] : values + values[i] + ",";
            }
            param.put(key, valus);
        }
        return  param;

    }
}
