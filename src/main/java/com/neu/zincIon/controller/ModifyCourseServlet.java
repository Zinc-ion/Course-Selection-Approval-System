package com.neu.zincIon.controller;

import com.neu.zincIon.pojo.Course;
import com.neu.zincIon.service.CourseService;
import com.neu.zincIon.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "ModifyCourseServlet", value = "/mc")
public class ModifyCourseServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //返回主页面打开对应子页面
        request.getSession().setAttribute("openType","course");

        System.out.println("进入mc.get");
        //先在get里检查是否找到对应项，再传给修改页面进行修改
        String cid = request.getParameter("modify");
        //检查选项是否未空
        if(cid == null) {
            response.getWriter().print("<script language='javascript'>" +
                    "alert('修改失败，未找到对应课程');" +
                    "window.location.href='manage.jsp';</script>')");
            System.out.println("修改失败，未找到对于课程！");
        }
        System.out.println("要修改的课程名：" + cid);

        //查询该课程名下的所有课程

        Course course = CourseService.getCourseByName(cid);

        if(course == null) {
            //未找到对应课程
            response.getWriter().print("<script language='javascript'>" +
                    "alert('修改失败，未找到对应课程');" +
                    "window.location.href='modifyCourse.jsp';</script>')");  //查询对应项失败重定向到学生页面jsp
            System.out.println("修改失败，未找到对应课程！");
        }

        Map<String,Object> map = new HashMap<String, Object>();

        //课程已提交、课程主讲教师审批中、课程主管教师审批中、审批成功、课程驳回。
        map.put("courseName",course.getCourseName());
        map.put("begTime",course.getbegTime());
        map.put("ltuid",course.getLtuid());
        map.put("stuid",course.getStuid());
        //将map存入session
        request.getSession().setAttribute("modifyCourse",map);

        //将主讲主管存入session用于选项
        Map<String,Object> tMap = UserService.getLtuidAndStuidList();
        List<String> ltuidList = (List<String>) tMap.get("ltuidList");
        List<String> stuidList = (List<String>) tMap.get("stuidList");
        request.getSession().setAttribute("ltuidList",ltuidList);
        request.getSession().setAttribute("stuidList",stuidList);

        response.sendRedirect("modifyCourse.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //返回主页面打开对应子页面
        request.getSession().setAttribute("openType","course");

        System.out.println("进入mu.post");
        Map<String,Object> map = (Map<String, Object>) request.getSession().getAttribute("modifyCourse");
        String courseName = (String) map.get("courseName");
        //获取新内容
        String begTime = request.getParameter("begTime");
        String ltuid = request.getParameter("ltuid");
        String stuid = request.getParameter("stuid");

        if(CourseService.upDateCourse(new Course(courseName,begTime,ltuid,stuid))) {
            response.getWriter().print("<script language='javascript'>" +
                    "alert('修改成功！');" +
                    "window.location.href='manage.jsp';</script>')");
            System.out.println("课程信息修改成功！");
        } else {
            response.getWriter().print("<script language='javascript'>" +
                    "alert('修改失败！');" +
                    "window.location.href='modifyCourse.jsp';</script>')");  //注册失败重定向到注册jsp
            System.out.println("修改课程信息失败！");
        }
    }
}
