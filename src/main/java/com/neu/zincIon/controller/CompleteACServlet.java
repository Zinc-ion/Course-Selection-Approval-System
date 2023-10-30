package com.neu.zincIon.controller;

import com.neu.zincIon.pojo.Approval;
import com.neu.zincIon.service.ApprovalService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "CompleteACServlet", value = "/cac")
public class CompleteACServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cname = request.getParameter("complete");
        //检查选项是否未空
        if(cname == null) {
            response.getWriter().print("<script language='javascript'>" +
                    "alert('确认失败，未找到对应申请');" +
                    "window.location.href='studentFile.jsp';</script>')");  //查询对应项失败重定向到学生页面jsp
            System.out.println("确认失败，未找到对应申请！");
        }
        String stuid = java.net.URLDecoder.decode((String) request.getSession().getAttribute("user"),"UTF-8");
        String suid = stuid; //将session中存的当前用户id取出后解码

        Approval approval = ApprovalService.getApproval(suid, cname);
        StringBuffer sb = new StringBuffer();
        sb.append("结束-");
        sb.append(approval.getState());
        approval.setState(sb.toString());

        if(ApprovalService.updateApproval(approval)) {
            //重复查询该学生下的所有申请的名字，更新修改和删除下拉菜单中的选项
            List<String> applicationNameList = ApprovalService.getApprovalNameList(suid);
            request.getSession().setAttribute("applicationNameList",applicationNameList);
            //更新确认下拉列表中选项
            List<String> completeACNameList = ApprovalService.getCompleteACNameList(suid);
            request.getSession().setAttribute("completeACNameList" ,completeACNameList);

            response.getWriter().print("<script language='javascript'>" +
                    "alert('确认成功！');" +
                    "window.location.href='studentFile.jsp';</script>')");
            System.out.println("确认成功！");
        }
        //修改失败
        response.getWriter().print("<script language='javascript'>" +
                "alert('确认失败！');" +
                "window.location.href='studentFile.jsp';</script>')");  //注册失败重定向到注册jsp
        System.out.println("确认失败！");

    }
}
