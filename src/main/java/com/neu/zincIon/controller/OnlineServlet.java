package com.neu.zincIon.controller;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "OnlineServlet", value = "/online")
public class OnlineServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        String key = request.getParameter("key");
        //网页上用网址确认重进Online并传参，判断是否传参来销毁session
        if(key != null && "logout".equals(key)) {
            session.invalidate();
            return;
        }

        //得到在线人数
        Integer onLineNumber = (Integer) session.getServletContext().getAttribute("onlineNumber");
        //设置响应类型
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write("<h2>当前在线人数：" + onLineNumber + "</h2> <a href='online?key=logout'>退出</a>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
