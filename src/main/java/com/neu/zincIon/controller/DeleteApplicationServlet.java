package com.neu.zincIon.controller;

import com.neu.zincIon.pojo.Approval;
import com.neu.zincIon.service.ApprovalService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "DeleteApplicationServlet", value = "/dac")
public class DeleteApplicationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cname = request.getParameter("delete");
        //检查选项是否未空
        if(cname == null) {
            response.getWriter().print("<script language='javascript'>" +
                    "alert('删除失败，未找到对应申请');" +
                    "window.location.href='studentFile.jsp';</script>')");  //查询对应项失败重定向到学生页面jsp
            System.out.println("删除失败，未找到对应申请！");
        }
        String stuid = java.net.URLDecoder.decode((String) request.getSession().getAttribute("user"),"UTF-8");
        String suid = stuid; //将session中存的当前用户id取出后解码

        //同时删除学生上传的证明文件
        Approval approval = ApprovalService.getApproval(suid, cname);
        String path = java.net.URLDecoder.decode(approval.getProof(),"UTF-8");
        String[] ss = path.split("/");
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < ss.length-1; i++) {
            if(i != 0) {
                sb.append("/");
            }
            sb.append(ss[i]); //找到文件的上一级uuid命名的文件夹，删除整个文件夹
        }
        System.out.println("删除地址：" + sb.toString());
        File file =new File(sb.toString()); //该文件的绝对路径
        deleteFile(file);//删除文件；

        //要在删前先删文件
        ApprovalService.deleteApproval(suid,cname);

        //删除后更新查询该学生下的所有申请的名字，覆盖原有session
        List<String> applicationNameList = ApprovalService.getApprovalNameList(suid);
        request.getSession().setAttribute("applicationNameList",applicationNameList);

        //查询该学生下所有申请中状态为驳回或审批成功的申请，用于申请确认选项
        List<String> completeACNameList = ApprovalService.getCompleteACNameList(suid);
        request.getSession().setAttribute("completeACNameList" ,completeACNameList);

        response.getWriter().print("<script language='javascript'>" +
                "alert('删除成功！');" +
                "window.location.href='studentFile.jsp';</script>')");  //查询对应项失败重定向到学生页面jsp
        System.out.println("删除成功！");

//        response.sendRedirect("studentFile.jsp");
    }

    public void deleteFile(File file) {
        boolean flag = false;
        if(file.exists()) {
            // 如果是目录，则遍历删除
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    System.out.println("删除文件：" + files[i].getName());
                    deleteFile(files[i]);
                }
            }
            file.delete();
        } else {
            System.out.println("要删除的文件不存在：" + file.getName());

        }
    }


}
