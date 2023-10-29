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
<html>
<head>
    <meta charset="UTF-8">
    <%--    引入jquery bootstrap--%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/bootstrap-3.4.1-dist/css/bootstrap.min.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.5.1.js"></script>
    <script src="${pageContext.request.contextPath}/static/bootstrap-3.4.1-dist/js/bootstrap.min.js"></script>
    <%--    引入bootstrap-paginator插件--%>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/bootstrap-paginator/js/bootstrap-paginator.js"></script>

    <title>修改用户信息</title>
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
<form action="mu" method="post" name="muForm" id="muForm" >
    <h2>原用户信息:</h2>
    <table class="table table-hover table-bordered table-striped">
        <tr>
            <td>用户名</td>
            <td>密码</td>
            <td>邮箱</td>
            <td>职业</td>
        </tr>

        <tbody id="acTable" >
        <tr>
            <td>${modifyUser.userId}</td>
            <td>${modifyUser.userPwd}</td>
            <td>${modifyUser.userMail}</td>
            <td>${modifyUser.userJob}</td>
        </tr>
        </tbody>

    </table>
    <br>
    <h2>修改用户信息:</h2>
    <br>
    <label for="pwd">新密码:</label>
    <input type="text" name="pwd" id="pwd" value=${modifyUser.userPwd} onFocus="if(value==defaultValue){value='';this.style.color='#000'}" onBlur="if(value==''){value=defaultValue;this.style.color='#999'}" />
    </div>
    <br>
    <label for="mail">新邮箱:</label>
    <input type="text" name="mail" id="mail" value=${modifyUser.userMail} onFocus="if(value==defaultValue){value='';this.style.color='#000'}" onBlur="if(value==''){value=defaultValue;this.style.color='#999'}">
    <br>
    <label for="pwd">新职业:</label>
    <select name="job" id="job" >
        <option value="学生" name="userJobStu">学生</option>
        <option value="主讲教师" name="userJobTea">主讲教师</option>
        <option value="主管教师" name="userJobTea">主管教师</option>
        <option value="管理员" name="userJobMan">管理员</option>
    </select>
    <br>


    <span id="msg" style="font-size: 20px;color: red"></span>

    <button type="button" id="muBtn">修改用户信息</button>
</form>


<script type="text/javascript">
    $("#job").val("${modifyUser.userJob}"); //选项默认值为用户原信息

    $("#muBtn").click(function () {

        $("#muForm").submit();

    });

    //判断字符串是否为空 为空返回true
    function isEmpty(str) {
        if(str == null || str.trim() == "")
            return true;
        return  false;
    }


</script>

</body>
</html>
