package com.neu.zincIon.controller;

import com.neu.zincIon.pojo.Approval;
import com.neu.zincIon.pojo.Course;
import com.neu.zincIon.service.ApprovalService;
import com.neu.zincIon.service.CourseService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "RjApprovalServlet", value = "/rja")
public class RjApprovalServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入rja");
        String lteauid = java.net.URLDecoder.decode((String) request.getSession().getAttribute("user"),"UTF-8");
        String ltuid = lteauid; //将session中存的当前用户id取出后解码
        String role = (String) request.getSession().getAttribute("role");


        //取名字
        String suidCourseName = request.getParameter("reject");
        //检查选项是否未空
        if(suidCourseName == null) {
                response.getWriter().print("<script language='javascript'>" +
                        "alert('驳回失败，未找到对应申请');" +
                        "window.location.href='teacherFile.jsp';</script>')");  //查询对应项失败重定向到主讲教师页面jsp
                System.out.println("驳回败，未找到对应申请！");

        }
        String[] ss = suidCourseName.split(" ");
        System.out.println("suidCourseName:"+suidCourseName);
        String suid = ss[0];
        String courseName = ss[1];

        String rjr = request.getParameter("rejectReason");
        //根据拒绝原因判断是那个教师拒绝的
        StringBuffer sb = new StringBuffer();

        //取教师级别

        if(role.equals("主讲教师")) {

            sb.append(role + " :");
            sb.append(rjr);
            rjr = sb.toString();
            //申请已提交、课程主讲教师审批中、课程主管教师审批中、审批成功、申请驳回。
            Approval ap = ApprovalService.getApproval(suid,courseName);

            //动态审批情况
            Course courseByName = CourseService.getCourseByName(ap.getCourseName());
            if(courseByName.getLtuid().equals("DynamicApproval")) {
                //进入动态审批
                //将rjlt设置为自己的order
                ap.setRjlt(ap.getLt());
            }
            //更新
            //普通情况
            ap.setState("申请驳回");
            ap.setRejection_reason(rjr);
            ApprovalService.updateApproval(ap);


//                            //重新查询所有该主讲教师名下待审批的项目，用于同意和驳回中的选项，包含approval主键suid，courseName
//            List<String> leadTApprovalNameList = ApprovalService.getLTApprovalNameList(ltuid);
//            request.getSession().setAttribute("leadTApprovalNameList",leadTApprovalNameList);

            response.getWriter().print("<script language='javascript'>" +
                    "alert('驳回成功！');" +
                    "window.location.href='teacherFile.jsp';</script>')");  //驳回成功跳转教师页面
            System.out.println("lt驳回成功！");

//            response.sendRedirect("teacherFile.jsp");

        } else if (role.equals("主管教师")) {

            sb.append(role + " :");
            sb.append(rjr);
            rjr = sb.toString();

            //申请已提交、课程主讲教师审批中、课程主管教师审批中、审批成功、申请驳回。
            Approval ap = ApprovalService.getApproval(suid,courseName);

            //动态审批情况
            Course courseByName = CourseService.getCourseByName(ap.getCourseName());
            if(courseByName.getStuid().equals("DynamicApproval")) {
                //进入动态审批
                //将rjst设置为自己的order
                ap.setRjst(ap.getSt());
            }
            //更新
            ap.setState("申请驳回");
            ap.setRejection_reason(rjr);
            ApprovalService.updateApproval(ap);




//                            //重新查询所有该主讲教师名下待审批的项目，用于同意和驳回中的选项，包含approval主键suid，courseName
//            List<String> sTApprovalNameList = ApprovalService.getSTApprovalNameList(ltuid);
//            request.getSession().setAttribute("sTApprovalNameList",sTApprovalNameList);

            response.getWriter().print("<script language='javascript'>" +
                    "alert('驳回成功！');" +
                    "window.location.href='teacherFile.jsp';</script>')");  //查询对应项失败重定向到主管教师页面
            System.out.println("st驳回成功！");


        }



    }
}
