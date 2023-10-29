package com.neu.zincIon.controller;

import com.neu.zincIon.pojo.Approval;
import com.neu.zincIon.pojo.Course;
import com.neu.zincIon.pojo.DA;
import com.neu.zincIon.service.ApprovalService;
import com.neu.zincIon.service.CourseService;
import com.neu.zincIon.service.DynamicApprovalService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "AgApprovalServlet", value = "/aga")
public class AgApprovalServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入aga");
        String lteauid = java.net.URLDecoder.decode((String) request.getSession().getAttribute("user"),"UTF-8");
        String ltuid = lteauid; //将session中存的当前用户id取出后解码
        String role = (String) request.getSession().getAttribute("role");

        //取名字
        String suidCourseName = request.getParameter("agree");
        //检查选项是否未空
        if(suidCourseName == null) {
            response.getWriter().print("<script language='javascript'>" +
                    "alert('同意失败，未找到对应申请');" +
                    "window.location.href='teacherFile.jsp';</script>')");  //查询对应项失败重定向到教师页面jsp
            System.out.println("同意失败，未找到对应申请！");
        }
        String[] ss = suidCourseName.split(" ");
        System.out.println("suidCourseName:"+suidCourseName);
        String suid = ss[0];
        String courseName = ss[1];

        //修改状态 申请已提交、课程主讲教师审批中、课程主管教师审批中、审批成功、申请驳回。
        //不同教师页面传来的分开处理
        Approval ap = ApprovalService.getApproval(suid,courseName);

        if(role.equals("主讲教师")) {


            //动态审批情况
            Course courseByName = CourseService.getCourseByName(ap.getCourseName());
            if(courseByName.getLtuid().equals("DynamicApproval")) {
                //进入动态审批
                //看ap的order是否是lt的最后一个
                List<DA> daltListByCourseName = DynamicApprovalService.getDALTListByCourseName(ap.getCourseName());
                int len = daltListByCourseName.size();
                if(ap.getLt() == daltListByCourseName.get(len-1).getOrder()) {
                    System.out.println("主讲最后一个");
                    //是最后一个则state变为主管

                    //主管为空则直接成功
                    //看ap的order是否是st的最后一个
                    List<DA> dastListByCourseName = DynamicApprovalService.getDASTListByCourseName(ap.getCourseName());
                    int stSize = dastListByCourseName.size();
                    if(stSize == 0) {
                        ap.setState("审批成功");
                    } else {
                        //st有人则接着审批
                        ap.setState("课程主管教师审批中");
                    }
                } else {
                    System.out.println("主讲非最后一个");
                    //不是最后一个则变ap中lt为dalt中下一个order
                    int p = 0;
                    for (int i = 0;i<len;i++) {
                        if(ap.getLt()==daltListByCourseName.get(i).getOrder()) {
                            p = i;
                            break;
                        }
                    }
                    ap.setLt(daltListByCourseName.get(p+1).getOrder());
                }
            } else {
                //普通情况
                ap.setState("课程主管教师审批中");
            }
            //更新
            ApprovalService.updateApproval(ap);


//            //查询所有该主讲教师名下待审批的项目，用于同意和驳回中的选项，包含approval主键suid，courseName
//            List<String> leadTApprovalNameList = ApprovalService.getLTApprovalNameList(ltuid);
//            request.getSession().setAttribute("leadTApprovalNameList",leadTApprovalNameList);

            //这种打印提示信息的方法只能在dopost里输出
            response.getWriter().print("<script language='javascript'>" +
                    "alert('审批成功！');" +
                    "window.location.href='teacherFile.jsp';</script>')");  //查询对应项失败重定向到主管教师页面
            System.out.println("lt审批成功！");
            //response.sendRedirect("teacherFile.jsp");
        } else if (role.equals("主管教师")) {

            //动态审批情况
            Course courseByName = CourseService.getCourseByName(ap.getCourseName());
            if(courseByName.getLtuid().equals("DynamicApproval")) {
                //进入动态审批
                //看ap的order是否是st的最后一个
                List<DA> dastListByCourseName = DynamicApprovalService.getDASTListByCourseName(ap.getCourseName());
                int len = dastListByCourseName.size();
                System.out.println("主管列表长度：" + len);
                if(ap.getSt() == dastListByCourseName.get(len-1).getOrder()) {
                    System.out.println("主管最后一个");
                    //是最后一个则state变为成功
                    ap.setState("审批成功");
                } else {
                    //不是最后一个则变ap中st为dalt中下一个order
                    System.out.println("主管非最后一个");
                    int p = 0;
                    for (int i = 0;i<len;i++) {
                        if(ap.getSt()==dastListByCourseName.get(i).getOrder()) {
                            p = i;
                            break;
                        }
                    }
                    ap.setSt(dastListByCourseName.get(p+1).getOrder());
                }
            } else {
                //普通情况
                ap.setState("审批成功");
            }

            //更新
            ApprovalService.updateApproval(ap);

//            //查询所有该主讲教师名下待审批的项目，用于同意和驳回中的选项，包含approval主键suid，courseName
//            List<String> sTApprovalNameList = ApprovalService.getSTApprovalNameList(ltuid);
//            request.getSession().setAttribute("sTApprovalNameList",sTApprovalNameList);

            //这种打印提示信息的方法只能在dopost里输出
            response.getWriter().print("<script language='javascript'>" +
                    "alert('审批成功！');" +
                    "window.location.href='teacherFile.jsp';</script>')");  //查询对应项失败重定向到教师页面
            System.out.println("st审批成功！");


        }

    }
}
