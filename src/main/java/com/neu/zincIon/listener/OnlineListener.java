package com.neu.zincIon.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class OnlineListener implements HttpSessionListener {

    private Integer onlineNumber = 0;
    public OnlineListener() {
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        onlineNumber++;
//        //将人数设置到session作用域中
//        se.getSession().setAttribute("onlineNumber",onlineNumber);
        //将人数设置到servletContext中，让所以浏览器都能看见当前实时人数
        se.getSession().getServletContext().setAttribute("onlineNumber",onlineNumber);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        onlineNumber--;
//        se.getSession().setAttribute("onlineNumber",onlineNumber);
        se.getSession().getServletContext().setAttribute("onlineNumber",onlineNumber);
    }

}
