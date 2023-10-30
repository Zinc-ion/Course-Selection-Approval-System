package com.neu.zincIon.controller;

import com.neu.zincIon.pojo.User;
import com.neu.zincIon.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "ModifyUserServlet", value = "/mu")
public class ModifyUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //返回主页面打开对应子页面
        request.getSession().setAttribute("openType","user");

        System.out.println("进入mu.get");
        //先在get里检查是否找到对应项，再传给修改页面进行修改
        String uid = request.getParameter("modify");
        //检查选项是否未空
        if(uid == null) {
            response.getWriter().print("<script language='javascript'>" +
                    "alert('修改失败，未找到对应用户');" +
                    "window.location.href='manage.jsp';</script>')");
            System.out.println("修改失败，未找到对于用户！");
        }
        System.out.println("要修改的用户名：" + uid);

        //查询该用户名下的所有用户

        User user = UserService.seletUserById(uid);

        if(user == null) {
            //未找到对应用户
            response.getWriter().print("<script language='javascript'>" +
                    "alert('修改失败，未找到对应用户');" +
                    "window.location.href='modifyUser.jsp';</script>')");  //查询对应项失败重定向到学生页面jsp
            System.out.println("修改失败，未找到对应用户！");
        }

        Map<String,Object> map = new HashMap<String, Object>();

        //用户已提交、课程主讲教师审批中、课程主管教师审批中、审批成功、用户驳回。
        map.put("userId",user.getUserId());
        map.put("userPwd",user.getUserPwd());
        map.put("userMail",user.getUserMail());
        map.put("userJob",user.getUserJob());
        //将map存入session

        request.getSession().setAttribute("modifyUser",map);
        response.sendRedirect("modifyUser.jsp");

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //返回主页面打开对应子页面
        request.getSession().setAttribute("openType","user");

        System.out.println("进入mu.post");
        Map<String,Object> map = (Map<String, Object>) request.getSession().getAttribute("modifyUser");
        String userId = (String) map.get("userId");
        //获取新内容
        String userPwd = request.getParameter("pwd");
        String userMail = request.getParameter("mail");
        String userJob = request.getParameter("job");

        if(UserService.modifyUser(new User(userId,userPwd,userMail,userJob))) {
            response.getWriter().print("<script language='javascript'>" +
                    "alert('修改成功！');" +
                    "window.location.href='manage.jsp';</script>')");
            System.out.println("用户信息修改成功！");
        } else {
            response.getWriter().print("<script language='javascript'>" +
                    "alert('修改失败！');" +
                    "window.location.href='modifyUser.jsp';</script>')");  //注册失败重定向到注册jsp
            System.out.println("修改失败！");
        }
    }
}
