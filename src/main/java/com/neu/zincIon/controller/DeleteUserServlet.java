package com.neu.zincIon.controller;

import com.neu.zincIon.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "DeleteUserServlet", value = "/du")
public class DeleteUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //返回主页面打开对应子页面
        request.getSession().setAttribute("openType","user");
        doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //返回主页面打开对应子页面
        request.getSession().setAttribute("openType","user");

        String uid = request.getParameter("delete");
        //检查选项是否未空
        if(uid == null) {
            response.getWriter().print("<script language='javascript'>" +
                    "alert('删除失败，未找到对应用户');" +
                    "window.location.href='manage.jsp';</script>')");  //查询对应项失败重定向
            System.out.println("删除失败，未找到对应用户！");
        }


        UserService.deleteUserById(uid);

        response.getWriter().print("<script language='javascript'>" +
                "alert('删除成功！');" +
                "window.location.href='manage.jsp';</script>')");
        System.out.println("删除成功！");


    }
}
