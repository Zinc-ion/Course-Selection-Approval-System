<%@ page import="java.lang.reflect.Array" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="com.neu.zincIon.service.ApprovalService" %>
<%@ page import="com.neu.zincIon.service.CourseService" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Zinc-ion
  Date: 2023/4/16
  Time: 17:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
                                                                <%--使用el表达式要加上isELIgnored="false"！！！！！--%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Select Your Courses</title>
    <style>
        /* CSS styles for the form and elements */
        @media (max-width: 1919px) and (min-width: 768px) {
            html {
                zoom: 90%;
            }
            @-ms-viewport { width: 1920px; }
        }
        form {
            background-color: #fff;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.3);
            max-width: 80%;
            margin: 50px auto;
        }
        h2 {
            margin-top: 0;
        }
        input[type="text"],
        input[type="cid"] {
            width: 100%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
            margin-bottom: 20px;
            box-sizing: border-box;
        }
        label {
            display: inline-block;
            margin-bottom: 10px;
        }
        select {
            display: block;
            width: 100%;
            padding: 10px;
            font-size: 16px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
        }
        button[type="button"] {
            display: block;
            margin-top: 20px;
            padding: 10px 20px;
            font-size: 16px;
            font-weight: bold;
            color: #fff;
            background-color: #007bff;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        button[type="button"]:hover {
            background-color: #0062cc;
        }
        body {
            background-image: url("${pageContext.request.contextPath}/img/logo.png");
            background-repeat:no-repeat;
            background-size: 135px 125px;
            background-position:50% 675px;
        }
    </style>
</head>
<body>
<form action="sac" method="post" name="csForm" id="csForm" enctype="multipart/form-data">
    <h2>选择你的课程:</h2>
    <br>

    <%
        String stuid = java.net.URLDecoder.decode((String) request.getSession().getAttribute("user"),"UTF-8");
        String suid = stuid; //将session中存的当前用户id取出后解码

        //查询当前用户还可以选那些课不包括被确认的课程
        List<String> selectedList = ApprovalService.getApprovalNameListWithCAC(suid);
        List<String> courseNATList = CourseService.getCourseListNameAndTime();
        List<String> courseAvailableNameList  = new ArrayList<String>(); //包含名字和时间
        for(int i = 0; i<courseNATList.size();i++) {
            boolean flag = true;
            String[] c = courseNATList.get(i).split(" "); //只取主键
            String courseName = c[0];

            for(int j = 0; j<selectedList.size();j++) {
                if(selectedList.get(j).equals(courseName)) {
                    flag = false;
                }
            }
            if(flag) {
                courseAvailableNameList.add(courseNATList.get(i));
                System.out.println("可选课程：");
                System.out.println(courseNATList.get(i));
            }
        }


        //覆盖可用课程列表
        request.getSession().setAttribute("courseAvailableNameList",courseAvailableNameList);
    %>

    <label for="course">课程:</label>
    <select  id="course" name="course" >
        <c:forEach var="courseName" items="${courseAvailableNameList}">
            <option value="${courseName}" id="${courseName}">${courseName}</option>
        </c:forEach>
    </select>
    <br>
    <label for="cause">申请原因:</label>
    <input type="text" name="cause" id="cause">
    <br>
    <label for="proof">申请证明:</label>
    <br>
    <input type="file" name="proof" id="proof">
    <br>

    <span id="msg" style="font-size: 20px;color: red"></span>

    <button type="button" id="scBtn">提交申请</button>
</form>

<script type="text/javascript" src="js/jquery-3.5.1.js"></script>
<script type="text/javascript">
    $("#scBtn").click(function () {
        var cause = $("#cause").val();
        if(isEmpty(cause)) {
            $("#msg").html("申请原因不可为空！");
            return;
        }
        $("#csForm").submit();
    });

    //判断字符串是否为空 为空返回true
    function isEmpty(str) {
        if(str == null || str.trim() == "")
            return true;
        return  false;
    }

    //动态线条背景
    !function(){
        function n(n,e,t){
            return n.getAttribute(e)||t
        }
        function e(n){
            return document.getElementsByTagName(n)
        }
        function t(){
            var t=e("script"),o=t.length,i=t[o-1];
            return{
                l:o,z:n(i,"zIndex",-1),o:n(i,"opacity",.5),c:n(i,"color","0,0,0"),n:n(i,"count",99)
            }
        }
        function o(){
            a=m.width=window.innerWidth||document.documentElement.clientWidth||document.body.clientWidth,
                c=m.height=window.innerHeight||document.documentElement.clientHeight||document.body.clientHeight
        }
        function i(){
            r.clearRect(0,0,a,c);
            var n,e,t,o,m,l;
            s.forEach(function(i,x){
                for(i.x+=i.xa,i.y+=i.ya,i.xa*=i.x>a||i.x<0?-1:1,i.ya*=i.y>c||i.y<0?-1:1,r.fillRect(i.x-.5,i.y-.5,1,1),e=x+1;e<u.length;e++)n=u[e],
                null!==n.x&&null!==n.y&&(o=i.x-n.x,m=i.y-n.y,
                    l=o*o+m*m,l<n.max&&(n===y&&l>=n.max/2&&(i.x-=.03*o,i.y-=.03*m),
                    t=(n.max-l)/n.max,r.beginPath(),r.lineWidth=t/2,r.strokeStyle="rgba("+d.c+","+(t+.2)+")",r.moveTo(i.x,i.y),r.lineTo(n.x,n.y),r.stroke()))
            }),
                x(i)
        }
        var a,c,u,m=document.createElement("canvas"),
            d=t(),l="c_n"+d.l,r=m.getContext("2d"),
            x=window.requestAnimationFrame||window.webkitRequestAnimationFrame||window.mozRequestAnimationFrame||window.oRequestAnimationFrame||window.msRequestAnimationFrame||
                function(n){
                    window.setTimeout(n,1e3/45)
                },
            w=Math.random,y={x:null,y:null,max:2e4};m.id=l,m.style.cssText="position:fixed;top:0;left:0;z-index:"+d.z+";opacity:"+d.o,e("body")[0].appendChild(m),o(),window.onresize=o,
            window.onmousemove=function(n){
                n=n||window.event,y.x=n.clientX,y.y=n.clientY
            },
            window.onmouseout=function(){
                y.x=null,y.y=null
            };
        for(var s=[],f=0;d.n>f;f++){
            var h=w()*a,g=w()*c,v=2*w()-1,p=2*w()-1;s.push({x:h,y:g,xa:v,ya:p,max:6e3})
        }
        u=s.concat([y]),
            setTimeout(function(){i()},100)
    }();

</script>

</body>
</html>
