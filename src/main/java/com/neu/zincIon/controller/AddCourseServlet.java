package com.neu.zincIon.controller;

import com.neu.zincIon.pojo.Course;
import com.neu.zincIon.service.CourseService;
import com.neu.zincIon.service.UserService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet(name = "AddCourseServlet", value = "/ac")
public class AddCourseServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //返回主页面打开对应子页面
        request.getSession().setAttribute("openType","course");

        System.out.println("进入ac.get");
        Map<String,Object> map = UserService.getLtuidAndStuidList();
        List<String> ltuidList = (List<String>) map.get("ltuidList");
        List<String> stuidList = (List<String>) map.get("stuidList");
        request.getSession().setAttribute("ltuidList",ltuidList);
        request.getSession().setAttribute("stuidList",stuidList);
        response.sendRedirect("addCourse.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //返回主页面打开对应子页面
        request.getSession().setAttribute("openType","course");

        System.out.println("进入ac.post");
        String courseName = request.getParameter("courseName");
        String begTime = request.getParameter("begTime");
        String ltuid = request.getParameter("ltuid");
        String stuid = request.getParameter("stuid");

        if(CourseService.addCourse(new Course(courseName,begTime,ltuid,stuid))) {

            //覆写session中的选项，用于审批流管理
            List<String> courseList = CourseService.getCourseListName();
            request.getSession().setAttribute("courseNameList",courseList);

            response.getWriter().print("<script language='javascript'>" +
                    "alert('添加课程成功！');" +
                    "window.location.href='manage.jsp';</script>')");  //注册成功重定向到登录jsp
            System.out.println("添加课程成功");
        } else {
            response.getWriter().print("<script language='javascript'>" +
                    "alert('注册失败，课程名已被占用，请重新输入！');" +
                    "window.location.href='addCourse.jsp';</script>')");  //注册失败重定向到注册jsp
            System.out.println("注册失败，课程名已被占用，请重新输入！");
        }

    }
}
