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

@WebServlet(name = "PastRjApQueryServlet", value = "/prjapq")
public class PastRjApQueryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入prjapq.get");
        String teauid = java.net.URLDecoder.decode((String) request.getSession().getAttribute("user"),"UTF-8");
        String tuid = teauid; //将session中存的当前用户id取出后解码

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
        String pageString=gson.toJson(map);
        PrintWriter respWritter = response.getWriter();
        /*将JSON格式的对象toString()后发送*/
        respWritter.write(pageString);


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入prjapq.post");
        String teauid = java.net.URLDecoder.decode((String) request.getSession().getAttribute("user"),"UTF-8");
        String tuid = teauid; //将session中存的当前用户id取出后解码

        List<Approval> approvalList = new ArrayList<Approval>();
        //查询该用户名下的所有申请，分用户操作
        //不同教师页面传来的分开处理
        String role = (String) request.getSession().getAttribute("role");
        System.out.println(role);

        //取出page数，无则置1
        int pageNum;
        if(request.getParameter("page")==null) {
            pageNum = 1;
        } else {
            pageNum = Integer.parseInt(request.getParameter("page"));
            System.out.println("查询第：" + pageNum + " 页");
        }

        //模糊查询
        String mq = request.getParameter("mq");
        String suidQuery = request.getParameter("suidQuery");
        String courseQuery = request.getParameter("courseQuery");
        String stateQuery = request.getParameter("stateQuery");
        System.out.println("suidQuery:"+suidQuery);
        System.out.println("courseQuery:"+courseQuery);
        System.out.println("stateQuery:"+stateQuery);


        if(role.equals("主讲教师")) {
            Map<String,Object> map;
            if(mq == null) {
                map = ApprovalService.selectPastRjApByLTuid(tuid,pageNum);
            } else {
                map = ApprovalService.selectPastRjApByLTuidLike(tuid,pageNum,suidQuery,courseQuery,stateQuery);
            }

            PageInfo pageInfo = (PageInfo) map.get("pageInfo");
            approvalList = (List<Approval>) map.get("list");
            //将pageInfo存入session中
            request.getSession().setAttribute("pageInfo",pageInfo);


            //将总条数存入最后一个ap用于前台展示
            approvalList.add(new Approval("prjapqTotalNum",Long.toString(pageInfo.getTotal())));

        } else if (role.equals("主管教师")) {
            Map<String,Object> map;
            if(mq == null) {
                map = ApprovalService.selectPastRjApBySTuid(tuid,pageNum);
            } else {
                map = ApprovalService.selectPastRjApBySTuidLike(tuid,pageNum,suidQuery,courseQuery,stateQuery);
            }

            PageInfo pageInfo = (PageInfo) map.get("pageInfo");
            approvalList = (List<Approval>) map.get("list");
            //将pageInfo存入session中
            request.getSession().setAttribute("pageInfo",pageInfo);

            //将总条数存入最后一个ap用于前台展示
            approvalList.add(new Approval("prjapqTotalNum",Long.toString(pageInfo.getTotal())));
        }


        /*将list集合装换成json对象的tostring*/
        Gson gson = new Gson();
        String approvalListString=gson.toJson(approvalList);

        PrintWriter respWritter = response.getWriter();
        /*将JSON格式的对象toString()后发送*/
        respWritter.write(approvalListString);
    }
}
