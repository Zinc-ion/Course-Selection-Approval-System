package com.neu.zincIon.controller;

import com.neu.zincIon.service.RegRecord;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Register", value = "/reg")
public class Register extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uid = request.getParameter("uid");
        String upwd = request.getParameter("upwd");
        String uMail = request.getParameter("mail");
        String uJob = request.getParameter("uJob");

        System.out.println(uid + " " + upwd + " " + uMail + " " + uJob + " ");

        if(RegRecord.reg(uid,upwd,uMail,uJob)) {
            String role = (String) request.getSession().getAttribute("role");
            if(role.equals("管理员")) {
                //返回主页面打开对应子页面
                request.getSession().setAttribute("openType","user");

                response.getWriter().print("<script language='javascript'>" +
                        "alert('注册成功！');" +
                        "window.location.href='manage.jsp';</script>')");  //管理员注册成功重定向到管理页面
                System.out.println("注册成功");
            } else {
                response.getWriter().print("<script language='javascript'>" +
                        "alert('注册成功！');" +
                        "window.location.href='login.jsp';</script>')");  //注册成功重定向到登录jsp
                System.out.println("注册成功");
            }
        } else {
            response.getWriter().print("<script language='javascript'>" +
                    "alert('注册失败，uid已被占用，请重新输入！');" +
                    "window.location.href='register.jsp';</script>')");  //注册失败重定向到注册jsp
            System.out.println("注册失败，uid已被占用，请重新输入！");
        }

    }
}