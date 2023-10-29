package com.neu.zincIon.controller;

import com.neu.zincIon.service.ApprovalService;
import com.neu.zincIon.service.CourseService;
import com.neu.zincIon.service.LoginCheck;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "Login", value = "/login")
public class Login extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println(456);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //编写了字符过滤器后就不需要在每个servlet里定义req和resp的编码格式了
        String uid = request.getParameter("uid");
        String upwd = request.getParameter("upwd");

        System.out.println("用户ID："+uid + " 密码：" + upwd);

        int flag = LoginCheck.login(uid, upwd);
        //根据role不同定向到不同页面 标示旗flag，0：管理员 1：学生 2：主讲教师 3：主管教师
        if(flag >= 0){
            //要向Cookie或Session里存中文需要先编码后解码
            String uidUTF8 = java.net.URLEncoder.encode(uid,"UTF-8");
            //登录成功就存一个session，用于通过AccessFilter
            System.out.println("登录成功");
            request.getSession().setAttribute("user",uidUTF8); //这里Attribute的name要和filter里的对应才能登陆后放行
            if(flag == 2) {
                request.getSession().setAttribute("role","主讲教师");   //区分不同职业
                response.sendRedirect("teacherFile.jsp");
            } else if (flag == 1) {
                request.getSession().setAttribute("role","学生");   //区分不同职业
                //查询可用课程列表
                List<String> courseAvailableNameList = getAvailableList(uid);
                request.getSession().setAttribute("courseNameList",courseAvailableNameList);
                //查询该学生下所有申请中状态为驳回或审批成功的申请，用于申请确认选项
                List<String> completeACNameList = ApprovalService.getCompleteACNameList(uid);
                request.getSession().setAttribute("completeACNameList" ,completeACNameList);
                response.sendRedirect("studentFile.jsp");
            } else if (flag == 0) {
                List<String> courseList = CourseService.getCourseListName();
                request.getSession().setAttribute("courseNameList",courseList);
                request.getSession().setAttribute("role","管理员");   //区分不同职业
                response.sendRedirect("manage.jsp");
            } else if (flag == 3) {
                request.getSession().setAttribute("role","主管教师");   //区分不同职业
                response.sendRedirect("teacherFile.jsp");
            }
        }else if(flag == -1){
            response.getWriter().print("<script language='javascript'>" +
                    "alert('您输入的账号或密码错误，请重新输入！');" +
                    "window.location.href='login.jsp';</script>')");  //登录失败重定向到登录jsp
            System.out.println("登录失败，重定向至登录jsp");
        }
    }
    public List<String> getAvailableList(String suid) {
        //查询当前用户还可以选那些课
        List<String> selectedList = ApprovalService.getApprovalNameList(suid);
        List<String> courseNATList = CourseService.getCourseListNameAndTime();
        List<String> availableList = new ArrayList<String>(); //包含名字和时间
        for(int i = 0; i<courseNATList.size();i++) {
            boolean flag = true;
            String[] c = courseNATList.get(i).split(" "); //只取主键
            String courseName = c[0];

            for(int j = 0; j<selectedList.size();j++) {
                if(selectedList.get(j).equals(courseName)) {
                    flag = false;
                }
            }
            if(flag) {
                availableList.add(courseNATList.get(i));
            }
        }
        return availableList;
    }

}
