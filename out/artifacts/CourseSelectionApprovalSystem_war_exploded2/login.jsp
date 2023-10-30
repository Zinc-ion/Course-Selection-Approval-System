<%--
  Created by IntelliJ IDEA.
  User: Zinc-ion
  Date: 2023/4/8
  Time: 19:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>在线申请审批系统登录页面</title>
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
            background-image: url("/ZincIon/img/logo.png");
            background-repeat:no-repeat;
            background-size: 135px 125px;
            background-position:50% 675px;
        }

    </style>

    <%--    <script language="javascript">--%>
    <%--        function check(){--%>
    <%--            if(loginForm.uname.value() == ""){		//当真实姓名为空时--%>
    <%--                alert("请输入用户名！");loginForm.uname.focus();return;--%>
    <%--            }else if(loginForm.upwd.value() ==  ""){		//当密码为空时--%>
    <%--                alert("请输入密码！");loginForm.upwd.focus();return;--%>
    <%--            }else{--%>
    <%--                loginForm.submit;--%>
    <%--            }--%>
    <%--        }--%>
    <%--    </script>--%>

</head>
<body>
<form action="login" method="post"  name="loginForm" id="loginForm">
    <h1>登录系统</h1>
    <label for="uid">用户ID</label>
    <input type="text" name="uid" id="uid">
    <label for="upwd">密码</label>
    <input type="password" name="upwd" id="upwd"> <br>
    <span id="msg" style="font-size: 12px;color: red"></span>
    <button type="button"  id="loginBtn">登录</button>
    <button type="button"  id="rgBtn" onclick="location='register.jsp'">注册</button>
    <%--跳转到注册页面--%>
</form>
</body>

<script type="text/javascript" src="js/jquery-3.5.1.js"></script>
<script type="text/javascript">
    $("#loginBtn").click(function () {
        var uid = $("#uid").val();      //这里取值用。val（）不是。value()在浏览器上说value()不是方法，但idea里弹出来的是value，很奇怪
        var upwd = $("#upwd").val();
        if(isEmpty(uid)) {
            $("#msg").html("用户ID不可为空");
            return;
        }
        if(isEmpty(upwd)) {
            $("#msg").html("用户密码不可为空");
            return;
        }
        $("#loginForm").submit();
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


</html>
