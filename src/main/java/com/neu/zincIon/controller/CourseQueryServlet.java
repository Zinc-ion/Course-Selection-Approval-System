package com.neu.zincIon.controller;

import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.neu.zincIon.pojo.Course;
import com.neu.zincIon.service.CourseService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "CourseQueryServlet", value = "/cq")
public class CourseQueryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入cq.get");


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
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pageNum", pageInfo.getPageNum());
        map.put("pageSize", pageInfo.getPageSize());
        map.put("pages", pageInfo.getPages());

        //以json返回map
        Gson gson = new Gson();
        String approvalListString = gson.toJson(map);
        PrintWriter respWritter = response.getWriter();
        /*将JSON格式的对象toString()后发送*/
        respWritter.write(approvalListString);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入cq.post");


        //取出page数，无则置1
        int pageNum;
        if(request.getParameter("page")==null) {
            pageNum = 1;
        } else {
            pageNum = Integer.parseInt(request.getParameter("page"));
            System.out.println("查询第：" + pageNum + " 页");
        }


//        //模糊查询
//        String mq = request.getParameter("mq"); //是否是模糊查询的标识符
//        String cidQuery = request.getParameter("cidQuery");
//        System.out.println("cidQuery:" + cidQuery);


        Map<String, Object> map;
//        if (mq == null) { //非模糊查询
            map = CourseService.getCourseListByPage(pageNum);
//        } else { //模糊查询
//            map = UserService.getUserListLike( pageNum, uidQuery, jobQuery);
//        }
        List<Course> approvalList = (List<Course>) map.get("list");
        PageInfo pageInfo = (PageInfo) map.get("pageInfo");
        //将pageInfo存入session中
        request.getSession().setAttribute("pageInfo", pageInfo);


        //将总条数存入最后一个course用于前台展示
        approvalList.add(new Course("courseTotalNum",Long.toString(pageInfo.getTotal())));


        /*将list集合装换成json对象的tostring*/
        Gson gson = new Gson();
        String approvalListString=gson.toJson(approvalList);

        PrintWriter respWritter = response.getWriter();
        /*将JSON格式的对象toString()后发送*/
        respWritter.write(approvalListString);

    }
}
