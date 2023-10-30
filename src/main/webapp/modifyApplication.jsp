<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Zinc-ion
  Date: 2023/4/16
  Time: 17:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%--使用el表达式要加上isELIgnored="false"！！！！！--%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Select Your Courses</title>
    <style type="text/css">
        @media (max-width: 1919px) and (min-width: 768px) {
            html {
                zoom: 90%;
            }
            @-ms-viewport { width: 1920px; }
        }
        body {
            background-color: #f2f2f2;
            font-family: Arial, sans-serif;
            color: #333;
        }
        form {
            background-color: #fff;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.3);
            max-width: 80%;
            margin: 50px auto;
        }
        h1 {
            text-align: center;
        }
        label {
            display: block;
            margin-bottom: 10px;
        }
        input[type="text"],
        input[type="password"] {
            width: 100%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
            margin-bottom: 20px;
            box-sizing: border-box;
        }
        button[type="button"] {
            width: 10%;
            background-color: #4CAF50;
            color: #fff;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            margin-top: 20px;

        }
        button[type="button"]:hover {
            background-color: #45a049;
        }
        table {
            border-collapse: collapse;
            width: 100%;
        }
        th, td {
            padding: 8px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        th {
            background-color: #f2f2f2;
        }
        select {
            text-align: center; text-align-last: center;
            height: 30px;
            font-size: 18px;
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
<form action="mac" method="post" name="maForm" id="maForm" enctype="multipart/form-data">
    <h2>原申请内容:</h2>
    <table >
        <tr>
            <td>选择的课程</td>
            <td>原因</td>
            <td>证明</td>
            <td>驳回原因</td>
            <td>申请状态</td>
        </tr>

        <tbody id="acTable">
             <tr>
                 <td>${modifyApplication.courseName}</td>
                 <td>${modifyApplication.cause}</td>
                 <td><a href="/ZincIon/pd?filePath=${modifyApplication.proof}" onclick="return linkClick(this)">点此查看证明</a></td>
                 <td>${modifyApplication.rejection_reason}</td>
                 <td>${modifyApplication.state}</td>
             </tr>
        </tbody>

    </table>
    <br>
    <h2>修改你的申请:</h2>
    <br>
    <label for="cause">申请原因:</label>
    <input type="text" name="cause" id="cause">
    <br>
    <label for="proof">申请证明:</label>
    <input type="file" name="proof" id="proof">
    <br>

    <span id="msg" style="font-size: 20px;color: red"></span>

    <br>

    <button type="button" id="maBtn">修改申请</button>
</form>

<script type="text/javascript" src="js/jquery-3.5.1.js"></script>
<script type="text/javascript">
    $("#maBtn").click(function () {
        var cause = $("#cause").val();
        if(isEmpty(cause)) {
            $("#msg").html("申请原因不可为空！");
            return;
        }
        $("#maForm").submit();

    });

    //判断字符串是否为空 为空返回true
    function isEmpty(str) {
        if(str == null || str.trim() == "")
            return true;
        return  false;
    }

    //proof文件地址链接中可能存在中文，将超链接跳转转为post 真nb啊！
    function linkClick(linkObject) {

        var formObject = document.createElement('form');
        document.body.appendChild(formObject);
        formObject.setAttribute('method', 'post');
        var url = linkObject.href;
        var uri = '';
        var i = url.indexOf('?');

        if(i == -1) {
            formObject.action = url;
        } else {
            formObject.action = url.substring(0, i);
        }

        if( i >= 0 && url.length >= i + 1) {
            uri = url.substring(i + 1, url.length);
        }

        var sa = uri.split('&');

        for(var i = 0; i < sa.length; i++) {
            var isa = sa[i].split('=');
            var inputObject = document.createElement('input');
            inputObject.setAttribute('type', 'hidden');
            inputObject.setAttribute('name', isa[0]);
            inputObject.setAttribute('value', isa[1]);
            formObject.appendChild(inputObject);
        }

        formObject.submit();

        return false;
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
