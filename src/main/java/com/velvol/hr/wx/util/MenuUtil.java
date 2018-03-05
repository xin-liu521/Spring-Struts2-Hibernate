package com.velvol.hr.wx.util;

import com.velvol.hr.web.action.WeixinAction;
import com.velvol.hr.wx.bean.Button;
import com.velvol.hr.wx.bean.ClickButton;
import com.velvol.hr.wx.bean.Menu;
import com.velvol.hr.wx.bean.ViewButton;
import com.velvol.hr.wx.quartz.AccessTokenTaker;
import net.sf.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @desc 创建菜单
 * Created by Administrator on 2018/2/28.
 */
public class MenuUtil {

    public static Menu initMenu(){

        Menu menu = new Menu();

        ViewButton button11 = new ViewButton();//一级菜单员工通道的二级菜单

        button11.setName("入职进展");

        button11.setType("view");

        button11.setUrl("http://www.weibopark.com/hrsysmove/entry_course.jsp");
        button11.setId("11");

        ViewButton button12 = new ViewButton();//一级菜单员工通道的二级菜单

        button12.setName("员工登录");

        button12.setType("view");

        button12.setUrl("http://www.weibopark.com/hrsysmove/login.jsp");
        button12.setId("12");


        ViewButton button31 = new ViewButton();//一级菜单

        button31.setName("骑手注册");

        button31.setType("view");

        button31.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx9dacd5aa887e237e&redirect_uri=http://www.weibopark.com/hrsysmove/weixinAction_weixinOAuth.action&response_type=code&scope=snsapi_base&state=1#wechat_redirect");

        button31.setId("31");


        ClickButton button22 = new ClickButton();//一级菜单

        button22.setName("公司简介");

        button22.setType("click");

        button22.setKey("key22");


        Button button2 = new Button();//一级菜单

        button2.setName("员工通道"); //将11/12两个button作为二级菜单封装第二个二级菜单

        button2.setSub_button(new Button[]{button11,button12});



        menu.setButton(new Button[]{button22,button2,button31});// 将31Button直接作为一级菜单

        return menu;

    }

    /**
     * 创建自定义菜单(每天限制1000次)
     * */
    public static int createMenu(Menu menu, String access_token){
        String jsonMenu= JSONObject.fromObject(menu).toString();

        int status=0;
//        JSONObject tokenJson = JSONObject.fromObject(new WeixinAction().getAccessToken());
//        String token = tokenJson.getString("access_token");
        System.out.println("菜单："+jsonMenu);
        String path="https://api.weixin.qq.com/cgi-bin/menu/create?access_token="+ access_token;
        try {
            URL url=new URL(path);
            HttpURLConnection http = (HttpURLConnection)url.openConnection();
            http.setDoOutput(true);
            http.setDoInput(true);
            http.setRequestMethod("POST");
            http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            http.connect();
            OutputStream os = http.getOutputStream();
            os.write(jsonMenu.getBytes("UTF-8"));
            os.close();

            InputStream is = http.getInputStream();
            int size = is.available();
            byte[] bt = new byte[size];
            is.read(bt);
            String message=new String(bt,"UTF-8");
            JSONObject jsonMsg = JSONObject.fromObject(message);
            status = Integer.parseInt(jsonMsg.getString("errcode"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return status;
    }

    /**
     * @desc 生成菜单
     * @param args
     */
    public static void main(String[] args){

        // 调用接口获取access_token
        String access_token = new WeixinAction().getAccessToken();
        int status = createMenu(MenuUtil.initMenu(), access_token);
        if(status==0){
            System.out.println("菜单创建成功！");
        }else{
            System.out.println("菜单创建失败！");
        }
    }


}
