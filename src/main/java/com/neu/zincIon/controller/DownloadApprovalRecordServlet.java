package com.neu.zincIon.controller;

import com.neu.zincIon.pojo.Approval;
import com.neu.zincIon.service.ApprovalService;
import com.neu.zincIon.utils.ExcelUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "DownloadApprovalRecordServlet", value = "/daga")
public class DownloadApprovalRecordServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入daga.get");
        doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入daga.post");
        //文件名
        String fileName = "审批通过记录一览表";
        //sheet名
        String sheetName = "审批记录sheet";
        //表头集合，作为表头参数
        List<String> titleList = new ArrayList<String>();
        titleList.add("学生ID");
        titleList.add("申请课程名");
        titleList.add("申请原因");
        titleList.add("申请证明");
        titleList.add("驳回原因");
        titleList.add("申请状态");
        titleList.add("最后审批人(主讲教师)");
        titleList.add("最后审批人(主管教师)");
        titleList.add("驳回审批人(主讲教师)");
        titleList.add("驳回审批人(主管教师)");


        List<Approval> agApprovalRecordList = ApprovalService.getAGApprovalRecordList();


        //调取封装的方法，传入相应的参数
        HSSFWorkbook workbook = null;
        try {
            workbook = ExcelUtil.createExcel(sheetName,titleList, agApprovalRecordList);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        //输出Excel文件
        OutputStream output=response.getOutputStream();
        response.reset();
        //中文名称要进行编码处理
        response.setHeader("Content-disposition", "attachment; filename="+new String(fileName.getBytes("GB2312"),"ISO8859-1")+".xls");
        response.setContentType("application/x-xls");
        workbook.write(output);
        output.close();


        response.getWriter().print("<script language='javascript'>" +
                "alert('文件下载成功！');" +
                "window.location.href='manage.jsp';</script>')");
        System.out.println("文件下载成功！");

    }
}
