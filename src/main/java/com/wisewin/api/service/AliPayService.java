package com.wisewin.api.service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayFundTransToaccountTransferRequest;
import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;
import com.wisewin.api.entity.bo.WithdrawBO;
import com.wisewin.api.util.AlipayConfig;
import com.wisewin.api.util.OrderUtil;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AliPayService {
    public boolean aliPay(WithdrawBO withdrawBO) throws AlipayApiException {
        //支付宝
        AlipayClient  alipayClient = new DefaultAlipayClient(AlipayConfig.URL, AlipayConfig.APP_ID,
                AlipayConfig.APP_PRIVATE_KEY, AlipayConfig.FORMAT, AlipayConfig.CHARSET,
                AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.SIGN_TYPE);
        AlipayFundTransToaccountTransferRequest transferRequest = new AlipayFundTransToaccountTransferRequest();
        transferRequest.setBizContent("{" +
                "    \"out_biz_no\":\""+withdrawBO.getOrderNumber()+"\"," +
                "    \"payee_type\":\"ALIPAY_USERID\"," +
                "    \"payee_account\":\""+withdrawBO.getAccount()+"\"," +
                "    \"amount\":\""+withdrawBO.getMoney().doubleValue()+"\"," +
                "  }");
        AlipayFundTransToaccountTransferResponse transferResponse = alipayClient.execute(transferRequest);
        if(transferResponse.isSuccess()){
            withdrawBO.setType("yes");
            withdrawBO.setApiMsg("转账成功");
            withdrawBO.setApiType(transferResponse.getMsg());
            return true;
        } else {
            withdrawBO.setType("failure");
            withdrawBO.setApiMsg("转账失败");
            withdrawBO.setApiType(transferResponse.getMsg());
            return false;
        }
    }


}
