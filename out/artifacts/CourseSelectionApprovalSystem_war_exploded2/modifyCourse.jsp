<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  Course: Zinc-ion
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

    <title>修改课程信息</title>
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
        #stuidDiv,#ltuidDiv{
            float: left;
            width: 200px;
            height: 50px;
        }
        #stuid,#ltuid{
            width: 80px;
            float: left;
        }
        #ltuidLabel,#stuidLabel{
            float: left;
            vertical-align:middle;
            line-height: 32px;
            margin-right: 5px;
        }

    </style>
</head>
<body>
<form action="mc" method="post" name="mcForm" id="mcForm" >
    <h2>原课程信息:</h2>
    <table class="table table-hover table-bordered table-striped">
        <tr>
            <td>课程名</td>
            <td>上课时间</td>
            <td>所属主讲教师</td>
            <td>所属主管教师</td>
        </tr>

        <tbody id="acTable" >
        <tr>
            <td>${modifyCourse.courseName}</td>
            <td>${modifyCourse.begTime}</td>
            <td>${modifyCourse.ltuid}</td>
            <td>${modifyCourse.stuid}</td>
        </tr>
        </tbody>

    </table>
    <br>
    <h2>修改课程信息:</h2>
    <br>
    <label for="begTime">新上课时间:</label>
    <input type="text" name="begTime" id="begTime" value=${modifyCourse.begTime} onFocus="if(value==defaultValue){value='';this.style.color='#000'}" onBlur="if(value==''){value=defaultValue;this.style.color='#999'}" />
    </div>
    <br>
    <div id="ltuidDiv">
        <label for="ltuid" id="ltuidLabel">所属主讲教师:</label>
        <select  id="ltuid" name="ltuid" >
            <c:forEach var="courseName" items="${ltuidList}">
                <option value="${courseName}" id="${courseName}">${courseName}</option>
            </c:forEach>
        </select>
    </div>

    <div id="stuidDiv">
        <label for="stuid" id="stuidLabel">所属主管教师:</label>
        <select  id="stuid" name="stuid" >
            <c:forEach var="courseName" items="${stuidList}">
                <option value="${courseName}" id="${courseName}">${courseName}</option>
            </c:forEach>
        </select>
    </div>



<%--    <span id="msg" style="font-size: 20px;color: red"></span>--%>

    <br><br><br>
    <button type="button" id="mcBtn">修改课程信息</button>
</form>


<script type="text/javascript">
    $("#ltuid").val("${modifyCourse.ltuid}"); //选项默认值为用户原信息
    $("#stuid").val("${modifyCourse.stuid}"); //选项默认值为用户原信息

    $("#muBtn").click(function () {

        $("#mcForm").submit();

    });



    $("#mcBtn").click(function () {

        $("#mcForm").submit();

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
