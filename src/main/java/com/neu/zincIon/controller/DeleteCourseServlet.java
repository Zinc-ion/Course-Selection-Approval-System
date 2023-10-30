package com.neu.zincIon.controller;

import com.neu.zincIon.service.CourseService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "DeleteCourseServlet", value = "/dc")
public class DeleteCourseServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //返回主页面打开对应子页面
        request.getSession().setAttribute("openType","course");

        doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //返回主页面打开对应子页面
        request.getSession().setAttribute("openType","course");

        String courseName = request.getParameter("delete");
        //检查选项是否未空
        if(courseName == null) {
            response.getWriter().print("<script language='javascript'>" +
                    "alert('删除失败，课程名为空！');" +
                    "window.location.href='manage.jsp';</script>')");  //查询对应项失败重定向
            System.out.println("删除失败，课程名为空！");
        }


        if(CourseService.deleteCourse(courseName)) {

            //覆写session中的选项，用于审批流管理
            List<String> courseList = CourseService.getCourseListName();
            request.getSession().setAttribute("courseNameList",courseList);

            response.getWriter().print("<script language='javascript'>" +
                    "alert('删除成功！');" +
                    "window.location.href='manage.jsp';</script>')");
            System.out.println("删除成功！");

        } else {
            response.getWriter().print("<script language='javascript'>" +
                    "alert('删除失败，未找到对应课程');" +
                    "window.location.href='manage.jsp';</script>')");  //查询对应项失败重定向
            System.out.println("删除失败，未找到对应课程！");
        }


    }
}
