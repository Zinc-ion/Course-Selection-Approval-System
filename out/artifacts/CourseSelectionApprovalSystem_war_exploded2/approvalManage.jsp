<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Zinc-ion
  Date: 2023/5/21
  Time: 22:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>动态审批流管理</title>
    <meta charset="utf-8">

    <%--    引入jquery bootstrap--%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/bootstrap-3.4.1-dist/css/bootstrap.min.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.5.1.js"></script>
    <script src="${pageContext.request.contextPath}/static/bootstrap-3.4.1-dist/js/bootstrap.min.js"></script>
    <%--    引入bootstrap-paginator插件--%>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/bootstrap-paginator/js/bootstrap-paginator.js"></script>

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
            max-width: 95%;
            margin: 50px auto;
            overflow: auto;
            

            vertical-align:bottom;
        }
        h2 {
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
            padding: 10px 10px;
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
            font-size: 18px;
            height: 30px;
        }
        body {
            background-image: url("${pageContext.request.contextPath}/img/logo.png");
            background-repeat:no-repeat;
            background-size: 135px 125px;
            background-position:58.5% 675px;
        }
        table {
            margin-bottom: 5px;
        }
        #deleteSTBtn ,#deleteLTBtn{
            background-color: red;
            width: 100px;
            margin-top: 0px;
            height: 35px;
            vertical-align:middle;
            line-height: 18px;
        }
        #addSTBtn,#addLTBtn{
            width: 100px;
            margin-top: 0px;
            height: 35px;
            vertical-align:middle;
            line-height: 18px;
        }


        #addLTDiv,#addSTDiv {
            overflow: hidden;
            float: left;
            margin-right: 20px;
        }

        #deleteLTDiv,#deleteSTDiv {
            overflow: hidden;
            float: left;

        }

        #addLTLabel,#addSTLabel{
            margin-bottom: 0px;
        }
        #deleteLTLabel,#deleteSTLabel{
            margin-bottom: 0px;
        }


        #addLT,#addST{
            width: 100px;
            margin-top: 0px;
            height: 35px;
            vertical-align:middle;
        }

        #deleteLT,#deleteST{
            width: 100px;
            margin-top: 0px;
            height: 35px;
            vertical-align:middle;
        }

        #courseDiv{
            margin-left: 80px;
            width: 500px;
            height: 40px;
            margin-right: 0px;
        }
        #courseLabel{
            float: left;
            vertical-align:middle;
            height: 30px;
            line-height: 30px;
            margin-right: 5px;
        }
        #course{
            float: left;
            width: 100px;
        }
        #daCourseQueryBtn{
            float: left;
            width: 180px;
            margin-top: 0px;
            height: 32px;
            line-height: 15px;
            margin-left: 10px;
        }
        #ltuidDiv{
            float: left;
            width: 200px;
            margin-right: 20px;
            margin-left: 80px;
        }
        #stuidDiv{
            float: left;
            width: 200px;
            margin-right: 20px;
        }
        #LTBtnDiv,#STBtnDiv{
            margin-right: 10px;
            float: left;
        }
        #LTBtnDiv{
            margin-right: 200px;
        }





    </style>
</head>
<body>
<form  action=""  name="apmForm" id="apmForm">
    <h2>动态审批流管理</h2>
    <div id="courseDiv">
        <label for="course" id="courseLabel">课程:</label>
        <select  id="course" name="course" >
            <c:forEach var="courseName" items="${courseNameList}">
                <option value="${courseName}" id="${courseName}">${courseName}</option>
            </c:forEach>
        </select>
        <button type="button" name="daCourseQueryBtn" id="daCourseQueryBtn" >管理此课程审批流</button>

    </div>


    <br>
    <br>



    <div id="ltuidDiv">
        <span id="msg" style="font-size: 12px;color: red"></span>

        <br>
        <table  class="table table-hover table-bordered table-striped">
            <tr>
                <th>所属主讲教师</th>
            </tr>
            <tbody id="ltuidTable">

            </tbody>
        </table>
    </div>

    <div id="LTBtnDiv">
        <div id="addLTDiv">
            <label id="addLTLabel">增加主讲教师：</label>
            <select  id="addLT" name="addLT" >

            </select>

            <button type="button" name="addLTBtn" id="addLTBtn"  >增加</button>
        </div>

        <br>
        <br>
        <br>
        <br>

        <div id="deleteLTDiv">
            <label id="deleteLTLabel">删除主讲教师：</label>
            <select  id="deleteLT" name="deleteLT" >

            </select>
            <button type="button" name="deleteLTBtn" id="deleteLTBtn"  >删除</button>
        </div>
    </div>




    <div id="stuidDiv">
        <span id="msg1" style="font-size: 12px;color: red"></span>

        <br>
        <table  class="table table-hover table-bordered table-striped">
            <tr>
                <th>所属主管教师</th>
            </tr>
            <tbody id="stuidTable">

            </tbody>
        </table>
    </div>

    <div id="STBtnDiv">
        <div id="addSTDiv">
            <label id="addSTLabel">增加主管教师：</label>
            <select  id="addST" name="addST" >

            </select>

            <button type="button" name="addSTBtn" id="addSTBtn" >增加</button>
        </div>

        <br>
        <br>
        <br>
        <br>

        <div id="deleteSTDiv">
            <label id="deleteSTLabel">删除主管教师：</label>
            <select  id="deleteST" name="deleteST" >

            </select>
            <button type="button" name="deleteSTBtn" id="deleteSTBtn"  >删除</button>
        </div>
    </div>


    <br>
    <br>

</form>
</body>


<script type="text/javascript">

    $("#daCourseQueryBtn").click(function (){
        console.log("daCourseQueryBtn")
        $("#ltuidTable").empty(""); //先清空table
        $("#stuidTable").empty(""); //先清空table
        $("#msg").empty();
        $("#msg1").empty();

        $("#deleteST").empty();
        $("#deleteLT").empty();
        $("#addLT").empty();
        $("#addST").empty();

        $.post({           //使用ajax获取后台数据库数据到前台进行拼接
            url:"dnap",
            dataType: "json",
            data: {
                course:$("#course").val(),
            },
            success: function(data){
                /*这个方法里是ajax发送请求成功之后执行的代码*/
                showData1(data);//我们仅做数据展示
            },
            error: function(msg){
                alert("查询ajax连接异常："+msg);
            }
        });
    });

    $("#addLTBtn").click(function (){
        console.log("daCourseQueryBtn");
        // $("#ltuidTable").empty(""); //先清空table
        // $("#stuidTable").empty(""); //先清空table
        // $("#msg").empty();
        // $("#msg1").empty();
        //
        // $("#deleteST").empty();
        // $("#deleteLT").empty();
        // $("#addLT").empty();
        // $("#addST").empty();
        //
        // console.log("addLT" + $("#addLT").val())
        // console.log("addLT" + $("#addLT"))
        //
        // $.post({           //使用ajax获取后台数据库数据到前台进行拼接
        //     url:"dnap",
        //     dataType: "json",
        //     data: {
        //         "course":$("#course").val(),
        //         "addLT":$("#addLT").val(),
        //     },
        //     success: function(data){
        //         /*这个方法里是ajax发送请求成功之后执行的代码*/
        //         showData1(data);//我们仅做数据展示
        //     },
        //     error: function(msg){
        //         alert("查询ajax连接异常："+msg);
        //     }
        // });


        // $("#apmForm").action='alt';
        // $("#apmForm").method='post'; //这种方法无效果！！！
        $("#apmForm").attr("action", "alt"); //要用这种方法修改form中的参数
        $("#apmForm").attr("method", "post");
        $("#apmForm").submit();
    });
    $("#addSTBtn").click(function (){
        console.log("daCourseQueryBtn");
        // $("#ltuidTable").empty(""); //先清空table
        // $("#stuidTable").empty(""); //先清空table
        // $("#msg").empty();
        // $("#msg1").empty();
        //
        // $("#deleteST").empty();
        // $("#deleteLT").empty();
        // $("#addLT").empty();
        // $("#addST").empty();
        //
        // $.post({           //使用ajax获取后台数据库数据到前台进行拼接
        //     url:"dnap",
        //     dataType: "json",
        //     data: {
        //         course:$("#course").val(),
        //         addST:$("#addST").val(),
        //     },
        //     success: function(data){
        //         /*这个方法里是ajax发送请求成功之后执行的代码*/
        //         showData1(data);//我们仅做数据展示
        //     },
        //     error: function(msg){
        //         alert("查询ajax连接异常："+msg);
        //     }
        // });

        $("#apmForm").attr("action", "ast"); //要用这种方法修改form中的参数
        $("#apmForm").attr("method", "post");
        $("#apmForm").submit();


    });
    $("#deleteLTBtn").click(function (){
        console.log("daCourseQueryBtn");
        // $("#ltuidTable").empty(""); //先清空table
        // $("#stuidTable").empty(""); //先清空table
        // $("#msg").empty();
        // $("#msg1").empty();
        //
        // $("#deleteST").empty();
        // $("#deleteLT").empty();
        // $("#addLT").empty();
        // $("#addST").empty();
        //
        // $.post({           //使用ajax获取后台数据库数据到前台进行拼接
        //     url:"dnap",
        //     dataType: "json",
        //     data: {
        //         course:$("#course").val(),
        //         deleteLT:$("#deleteLT").val(),
        //     },
        //     success: function(data){
        //         /*这个方法里是ajax发送请求成功之后执行的代码*/
        //         showData1(data);//我们仅做数据展示
        //     },
        //     error: function(msg){
        //         alert("查询ajax连接异常："+msg);
        //     }
        // });

        $("#apmForm").attr("action", "dlt"); //要用这种方法修改form中的参数
        $("#apmForm").attr("method", "post");
        $("#apmForm").submit();
    });
    $("#deleteSTBtn").click(function (){
        console.log("daCourseQueryBtn");
        // $("#ltuidTable").empty(""); //先清空table
        // $("#stuidTable").empty(""); //先清空table
        // $("#msg").empty();
        // $("#msg1").empty();
        //
        // $("#deleteST").empty();
        // $("#deleteLT").empty();
        // $("#addLT").empty();
        // $("#addST").empty();
        //
        // $.post({           //使用ajax获取后台数据库数据到前台进行拼接
        //     url:"dnap",
        //     dataType: "json",
        //     data: {
        //         course:$("#course").val(),
        //         deleteST:$("#deleteST").val(),
        //     },
        //     success: function(data){
        //         /*这个方法里是ajax发送请求成功之后执行的代码*/
        //         showData1(data);//我们仅做数据展示
        //     },
        //     error: function(msg){
        //         alert("查询ajax连接异常："+msg);
        //     }
        // });

        $("#apmForm").attr("action", "dst"); //要用这种方法修改form中的参数
        $("#apmForm").attr("method", "post");
        $("#apmForm").submit();
    });



    var countLT = 0;
    var countST = 0; //用于计数

    function showData1(data) {
        console.log("showData1");
        var str = "";//定义用于拼接的字符串
        var i = 0;
        var flag = 0;

        for ( i = 0; i < data.length; i++) {
            if(data[i] == "STuid") {
                countLT = i;
                flag = 1;
                continue;
            } else if(data[i] == "availableLTuid") {
                countST = i - countLT -1;
                flag = 2;
                continue;
            } else if(data[i] == "availableSTuid") {
                flag = 3;
                continue;
            }
            //最后一个data存的是总条数
            //拼接表格的行和列
            str = "<tr><td>" + data[i] + "</td></tr>";
            //追加到table中
            if(flag == 0) {
                $("#ltuidTable").append(str);
                $("#deleteLT").append('<option value='+ data[i] +' id=' + data[i] + '>' + data[i] + '</option>');
            } else if(flag == 1) {
                $("#stuidTable").append(str);
                $("#deleteST").append('<option value='+ data[i] +' id=' + data[i] + '>' + data[i] + '</option>');
            } else if(flag == 2) {
                $("#addLT").append('<option value='+ data[i] +' id=' + data[i] + '>' + data[i] + '</option>');
            } else if(flag == 3) {
                $("#addST").append('<option value='+ data[i] +' id=' + data[i] + '>' + data[i] + '</option>');
            }
        }

        $("#msg").html("所属主讲查询到：" + countLT + " 条数据");
        $("#msg1").html("所属主管查询到：" + countST + " 条数据");
        console.log("所属主讲查询到：" + countLT);
        console.log("所属主管查询到：" + countST);
    }



</script>

</html>

