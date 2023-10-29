<%--
  Created by IntelliJ IDEA.
  User: Zinc-ion
  Date: 2023/4/8
  Time: 19:07
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
  <title>新增课程</title>
  <meta charset="utf-8">
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
      max-width: 400px;
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
      background-color: #4CAF50;
      color: #fff;
      padding: 10px 20px;
      border: none;
      border-radius: 4px;
      cursor: pointer;
      margin-top: 20px;
      width: 100%;
    }
    button[type="button"]:hover {
      background-color: #45a049;
    }
    body {
      background-image: url("${pageContext.request.contextPath}/img/logo.png");
      background-repeat:no-repeat;
      background-size: 135px 125px;
      background-position:50% 675px;
    }
    #ltuid,#stuid {
      height: 30px;
      width: 50px;
      line-height: 15px;
      vertical-align:middle;
    }

  </style>
</head>
<body>
<form action="ac" method="post"  name="acForm" id="acForm">
  <h1>新增课程</h1>
  <label for="courseName">课程名</label>
  <input type="text" name="courseName" id="courseName">
  <label for="begTime">上课时间</label>
  <input type="text" name="begTime" id="begTime">
  <label for="ltuid">所属主讲教师</label>
  <select  id="ltuid" name="ltuid" >
    <c:forEach var="courseName" items="${ltuidList}">
      <option value="${courseName}" id="${courseName}">${courseName}</option>
    </c:forEach>
  </select>
  <br><br>
  <label for="stuid">所属主管教师</label>
  <select  id="stuid" name="stuid" >
    <c:forEach var="courseName" items="${stuidList}">
      <option value="${courseName}" id="${courseName}">${courseName}</option>
    </c:forEach>
  </select>

  <span id="msg" style="font-size: 12px;color: red"></span>
  <button type="button"  id="rgBtn" >注册</button>
</form>

<script type="text/javascript" src="js/jquery-3.5.1.js"></script>
<script type="text/javascript">
  $("#rgBtn").click(function () {
    var courseName = $("#courseName").val();      //这里取值用。val（）不是。value()在浏览器上说value()不是方法，但idea里弹出来的是value，很奇怪
    var begTime = $("#begTime").val();
    var ltuid = $("#ltuid").val();
    var stuid = $("#stuid").val();

    if(isEmpty(courseName)) {
      $("#msg").html("课程名不可为空");
      return;
    }
    if(courseName.length > 30) {
      $("#msg").html("课程名不可超过30个字"); //数据库中设定最长30
      return;
    }
    if(isEmpty(begTime)) {
      $("#msg").html("上课时间不可为空");
      return;
    }
    if(begTime.length > 30) {
      $("#msg").html("上课时间不可超过30个字"); //数据库中设定最长30
      return;
    }
    if(isEmpty(ltuid)) {
      $("#msg").html("所属主讲教师不可为空");
      return;
    }
    if(ltuid.length > 30) {
      $("#msg").html("所属主讲教师不可超过30个字"); //数据库中设定最长30
      return;
    }
    if(isEmpty(stuid)) {
      $("#msg").html("所属主讲教师不可为空");
      return;
    }
    if(stuid.length > 30) {
      $("#msg").html("所属主讲教师不可超过30个字"); //数据库中设定最长30
      return;
    }
    $("#acForm").submit();

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