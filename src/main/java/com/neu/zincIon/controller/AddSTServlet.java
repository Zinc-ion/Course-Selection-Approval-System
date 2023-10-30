package com.neu.zincIon.controller;

import com.neu.zincIon.pojo.Course;
import com.neu.zincIon.service.CourseService;
import com.neu.zincIon.service.DynamicApprovalService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "AddSTServlet", value = "/ast")
public class AddSTServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //返回主页面打开对应子页面
        request.getSession().setAttribute("openType","approval");

        System.out.println("进入dnap.post");
        String courseName = request.getParameter("course");
        System.out.println("course:" + courseName);
        String addLT = request.getParameter("addLT");
        System.out.println("addLT:" + addLT);
        String addST = request.getParameter("addST");
        System.out.println("addST:" + addST);
        String deleteLT = request.getParameter("deleteLT");
        System.out.println("deleteLT:" + deleteLT);
        String deleteST = request.getParameter("deleteST");
        System.out.println("deleteST:" + deleteST);

        Course course = CourseService.getCourseByName(courseName);


            System.out.println("进行addST");
            DynamicApprovalService.addSTuid(courseName,addST);
            CourseService.upDateCourse(new Course(courseName,course.getbegTime(),"DynamicApproval","DynamicApproval"));
        response.getWriter().print("<script language='javascript'>" +
                "alert('增加主管教师成功！');" +
                "window.location.href='manage.jsp';</script>')");
        System.out.println("增加主管教师成功！");
    }
}
