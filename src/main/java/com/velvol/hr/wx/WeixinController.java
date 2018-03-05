package com.velvol.hr.wx;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

/**
 * Created by Administrator on 2018/2/26.
 */
public class WeixinController {

    //private Logger log =Logger.getLogger(WeixinController.class);
    private Log log = LogFactory.getLog(getClass());

    @RequestMapping(method = RequestMethod.GET)
    public void get(HttpServletRequest request, HttpServletResponse response) {
        log.info("请求进来了...");
        // 微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
        String signature =request.getParameter("signature");
        // 时间戳
        String timestamp =request.getParameter("timestamp");
        // 随机数
        String nonce =request.getParameter("nonce");
        // 随机字符串
        String echostr =request.getParameter("echostr");

        PrintWriter out = null;
        try {
            out = response.getWriter();
            // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，否则接入失败
//            if (SignUtil.checkSignature(signature,timestamp, nonce)) {
//                out.print(echostr);
//            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            out.close();
            out = null;
        }
    }
}

