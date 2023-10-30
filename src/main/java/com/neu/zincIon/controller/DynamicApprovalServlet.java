package com.neu.zincIon.controller;

import com.google.gson.Gson;
import com.neu.zincIon.service.DynamicApprovalService;
import com.neu.zincIon.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebServlet(name = "DynamicApprovalServlet", value = "/dnap")
public class DynamicApprovalServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入dnap.post");
        String courseName = request.getParameter("course");
        System.out.println("course:" + courseName);
        String addLT = request.getParameter("addLT");
        System.out.println("addLT:" + addLT);
        String addST = request.getParameter("addST");
        System.out.println("addST:" + addST);
        String deleteLT = request.getParameter("deleteLT");
        System.out.println("deleteLT:" + deleteLT);
        String deleteST = request.getParameter("deleteST");
        System.out.println("deleteST:" + deleteST);


        if(courseName != null) {


            //处理table和select
            List<String> ltuidList,stuidList;
            Map<String,Object> map = DynamicApprovalService.getLtuidAndStuidByCourseName(courseName);
            ltuidList = (List<String>) map.get("ltuidList");
            stuidList = (List<String>) map.get("stuidList");

            List<String> nameList = new ArrayList<String>();
            for(String lt:ltuidList) {
                nameList.add(lt);
            }
            nameList.add("STuid");//标示符，用于前台区分前后两端
            for(String st:stuidList) {
                nameList.add(st);
            }

            //加上可用的ltuid和stuid用于前端添加选项
            Map<String, Object> ltuidAndStuidList = UserService.getLtuidAndStuidList();
            List<String> aVLtuidList = getAVList(ltuidList,(List<String>) ltuidAndStuidList.get("ltuidList"));
            List<String> aVStuidList = getAVList(stuidList,(List<String>) ltuidAndStuidList.get("stuidList"));

            //
            nameList.add("availableLTuid");
            for(String st:aVLtuidList) {
                System.out.println("可用ltuid：" +st);
                nameList.add(st);
            }
            //
            nameList.add("availableSTuid");
            for(String st:aVStuidList) {
                System.out.println("可用stuid：" +st);
                nameList.add(st);
            }

            /*将list集合装换成json对象的tostring*/
            Gson gson = new Gson();
            String approvalListString=gson.toJson(nameList);

            PrintWriter respWritter = response.getWriter();
            /*将JSON格式的对象toString()后发送*/
            respWritter.write(approvalListString);

        } else {
            response.getWriter().print("<script language='javascript'>" +
                    "alert('查找失败，课程名为空');" +
                    "window.location.href='manage.jsp';</script>')");  //查询对应项失败重定向到学生页面jsp
            System.out.println("查找失败，课程名为空！");
        }



    }

    public List<String> getAVList(List<String> used,List<String> all) {
        List<String> list = all;
        for(String m: used) {
            if(list.contains(m)) {
                list.remove(m);
            }
        }
        return list;
    }
}
