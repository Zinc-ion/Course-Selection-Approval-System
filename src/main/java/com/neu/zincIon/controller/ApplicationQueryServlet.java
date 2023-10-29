package com.neu.zincIon.controller;

import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.neu.zincIon.pojo.Approval;
import com.neu.zincIon.service.ApprovalService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@WebServlet(name = "ApplicationQueryServlet", value = "/acq")
public class ApplicationQueryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入acq.get");

        String stuid = java.net.URLDecoder.decode((String) request.getSession().getAttribute("user"),"UTF-8");
        String suid = stuid; //将session中存的当前用户id取出后解码
        //取出type类型知道是那种查询
        String type = request.getParameter("type");

            System.out.println("进入acq.get.");
            //从session中取出pageInfo
            PageInfo pageInfo = (PageInfo) request.getSession().getAttribute("pageInfo");
            System.out.println("总页数");
            System.out.println(pageInfo.getPages());
            System.out.println("总条数");
            System.out.println(pageInfo.getTotal());
            System.out.println("当前页");
            System.out.println(pageInfo.getPageNum());
            System.out.println("每页多少条");
            System.out.println(pageInfo.getPageSize());


            //取出我们需要的值，存入map中
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("pageNum",pageInfo.getPageNum());
            map.put("pageSize",pageInfo.getPageSize());
            map.put("pages",pageInfo.getPages());

            //以json返回map
            Gson gson = new Gson();
            String approvalListString=gson.toJson(map);
            PrintWriter respWritter = response.getWriter();
            /*将JSON格式的对象toString()后发送*/
            respWritter.write(approvalListString);


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入acq.post");

        String stuid = java.net.URLDecoder.decode((String) request.getSession().getAttribute("user"),"UTF-8");
        String suid = stuid; //将session中存的当前用户id取出后解码

        //取出type类型知道是那种查询
        String tmp = request.getParameter("type");
        String type;
        //tmp为空则是分页查询切换页面导致的二次查询，从Session中取出type，不为空则是切换了查询内容，将切换的type存入Session中
        if(tmp!=null) {
            request.getSession().setAttribute("stuType",tmp);
            type = tmp;
        } else {
            type = (String)request.getSession().getAttribute("stuType");
        }


        //取出page数，无则置1
        int pageNum;
        if(request.getParameter("page")==null) {
            pageNum = 1;
        } else {
            pageNum = Integer.parseInt(request.getParameter("page"));
            System.out.println("查询第：" + pageNum + " 页");
        }

        //取出stateQuery用于多条件搜索
        String stateQuery = request.getParameter("stateQuery");

        if(type.equals("acq")) {
            System.out.println("进入acq.acq");
            //查询该用户名下的所有申请
            Map<String,Object> map;
            if(stateQuery == null) {
                map = ApprovalService.selectApprovalBySuid(suid,pageNum);
            } else {
                map = ApprovalService.selectApprovalBySuidLike(suid,pageNum,stateQuery);
            }
            List<Approval> approvalList1 = (List<Approval>) map.get("list");
            PageInfo pageInfo = (PageInfo) map.get("pageInfo");
            //将pageInfo存入session中
            request.getSession().setAttribute("pageInfo",pageInfo);
            //更改修改和删除下拉栏的列表,让其适用于cagacq
            //查询该学生下的所有申请的课程名字,用于修改删除中的选项
            List<String> applicationNameList = new ArrayList<String>();
            List<String> completeACNameList = new ArrayList<String>();
            for(Approval ap : approvalList1) {
                applicationNameList.add(ap.getCourseName());
                if(ap.getState().equals("申请驳回") || ap.getState().equals("审批成功")) {
                    completeACNameList.add(ap.getCourseName());
                }
            }
            request.getSession().setAttribute("applicationNameList",applicationNameList);
            //查询该学生下所有申请中状态为驳回或审批成功的申请，用于申请确认选项
            request.getSession().setAttribute("completeACNameList" ,completeACNameList);
            //查询该学生下所有申请中状态为驳回或审批成功的申请，用于申请确认选项
            completeACNameList = ApprovalService.getCompleteACNameList(suid);
            request.getSession().setAttribute("completeACNameList" ,completeACNameList);
            //将总条数存入最后一个ap用于前台展示
            approvalList1.add(new Approval("acqTotalNum",Long.toString(pageInfo.getTotal())));
            /*将list集合装换成json对象的tostring*/
            Gson gson = new Gson();
            String approvalListString=gson.toJson(approvalList1);
            PrintWriter respWritter = response.getWriter();
            /*将JSON格式的对象toString()后发送*/
            respWritter.write(approvalListString);
        } else if (type.equals("cagacq")) {
            System.out.println("进入acq.cagacq");
            //查询该用户名下的所有已完成的被同意的申请
            Map<String,Object> map;
            if(stateQuery == null) {
                map = ApprovalService.selectCAGApplicationBySuid(suid,pageNum);
            } else {
                map = ApprovalService.selectCAGApplicationBySuidLike(suid,pageNum,stateQuery);
            }
            List<Approval> approvalList2 = (List<Approval>) map.get("list");
            PageInfo pageInfo = (PageInfo) map.get("pageInfo");
            //将pageInfo存入session中
            request.getSession().setAttribute("pageInfo",pageInfo);
            //更改修改和删除下拉栏的列表,让其适用于cagacq
            //查询该学生下的所有申请的课程名字,用于修改删除中的选项
            List<String> applicationNameList = new ArrayList<String>();
            for(Approval ap : approvalList2) {
                applicationNameList.add(ap.getCourseName());
            }
            request.getSession().setAttribute("applicationNameList",applicationNameList);
            //查询该学生下所有申请中状态为驳回或审批成功的申请，用于申请确认选项
            List<String> completeACNameList = null;
            request.getSession().setAttribute("completeACNameList" ,completeACNameList);
            //将总条数存入最后一个ap用于前台展示
            approvalList2.add(new Approval("cagacqTotalNum",Long.toString(pageInfo.getTotal())));
            /*将list集合装换成json对象的tostring*/
            Gson gson = new Gson();
            String approvalListString2=gson.toJson(approvalList2);
            PrintWriter respWritter = response.getWriter();
            /*将JSON格式的对象toString()后发送*/
            respWritter.write(approvalListString2);
        } else if (type.equals("crjacq")) {
            System.out.println("进入acq.crjacq");
            //查询该用户名下的所有已完成的被驳回的申请
            Map<String,Object> map;
            if(stateQuery == null) {
                map = ApprovalService.selectCRJApplicationBySuid(suid,pageNum);
            } else {
                map = ApprovalService.selectCRJApplicationBySuidLike(suid,pageNum,stateQuery);
            }
            List<Approval> approvalList3 = (List<Approval>) map.get("list");
            PageInfo pageInfo = (PageInfo) map.get("pageInfo");
            //将pageInfo存入session中
            request.getSession().setAttribute("pageInfo",pageInfo);
            //更改修改和删除下拉栏的列表，让其适用于crjacq
            //查询该学生下的所有申请的课程名字,用于修改删除中的选项
            List<String> applicationNameList = new ArrayList<String>();
            for(Approval ap : approvalList3) {
                applicationNameList.add(ap.getCourseName());
            }
            request.getSession().setAttribute("applicationNameList",applicationNameList);
            //查询该学生下所有申请中状态为驳回或审批成功的申请，用于申请确认选项
            List<String> completeACNameList = null;
            request.getSession().setAttribute("completeACNameList" ,completeACNameList);
            //将总条数存入最后一个ap用于前台展示
            approvalList3.add(new Approval("crjacqTotalNum",Long.toString(pageInfo.getTotal())));
            /*将list集合装换成json对象的tostring*/
            Gson gson = new Gson();
            String approvalListString3=gson.toJson(approvalList3);
            PrintWriter respWritter = response.getWriter();
            /*将JSON格式的对象toString()后发送*/
            respWritter.write(approvalListString3);
        }

    }
}
