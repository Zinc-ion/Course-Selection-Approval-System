package com.neu.zincIon.controller;

import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.neu.zincIon.pojo.Approval;
import com.neu.zincIon.service.ApprovalService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "ApprovalRecordQueryServlet", value = "/aprq")
public class ApprovalRecordQueryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入aprq.get");
        String teauid = java.net.URLDecoder.decode((String) request.getSession().getAttribute("user"),"UTF-8");
        String tuid = teauid; //将session中存的当前主讲id取出后解码

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
        System.out.println("进入aprq.post");
        List<Approval> approvalList = new ArrayList<Approval>();

        //取出page数，无则置1
        int pageNum;
        if(request.getParameter("page")==null) {
            pageNum = 1;
        } else {
            pageNum = Integer.parseInt(request.getParameter("page"));
            System.out.println("查询第：" + pageNum + " 页");
        }
        Map<String,Object> map;
        map = ApprovalService.getApprovalRecordList(pageNum);
        approvalList = (List<Approval>)map.get("list");
        PageInfo pageInfo = (PageInfo) map.get("pageInfo");

        //将pageInfo存入session中
        request.getSession().setAttribute("pageInfo",pageInfo);

        //将总条数存入最后一个ap用于前台展示
        System.out.println("aprqTotalNum:" + Long.toString(pageInfo.getTotal()));
        approvalList.add(new Approval("aprqTotalNum",Long.toString(pageInfo.getTotal())));

        /*将list集合装换成json对象的tostring*/
        Gson gson = new Gson();
        String approvalListString=gson.toJson(approvalList);
        PrintWriter respWritter = response.getWriter();
        /*将JSON格式的对象toString()后发送*/
        respWritter.write(approvalListString);

    }
}
