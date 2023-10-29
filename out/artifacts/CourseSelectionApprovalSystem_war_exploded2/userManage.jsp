<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Zinc-ion
  Date: 2023/5/1
  Time: 22:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>用户列表</title>
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
        #deleteBtn {
            background-color: red;
            width: 100px;
            margin-top: 0px;
            height: 35px;
            vertical-align:middle;
            line-height: 18px;
        }
        #modifyBtn{
            width: 100px;
            margin-top: 0px;
            height: 35px;
            vertical-align:middle;
            line-height: 18px;
        }


        #modifyDiv {
            overflow: hidden;
            float: left;
            margin-right: 20px;
        }

        #deleteDiv {
            overflow: hidden;
            float: left;

        }

        #mLabel{
            margin-bottom: 0px;
        }
        #dLabel{
            margin-bottom: 0px;
        }


        #modify{
            width: 100px;
            margin-top: 0px;
            height: 35px;
            vertical-align:middle;
        }

        #delete{
            width: 100px;
            margin-top: 0px;
            height: 35px;
            vertical-align:middle;
        }

        #page {
            width: 800px;
            float: right;
        }

        #mqDiv{
            float: right;
            width: 500px;
            margin-bottom: 12px;
        }
        #jobQuery,#uidQuery{
            width: 100px;
            float: right;
            margin-bottom: 0px;
            height: 30px;
            margin-right: 5px;

        }
        #uidQuery{
            margin-right: 0px;
        }

        #jqLabel,#uidLabel{
            width: 50px;
            margin-right: 0px;
            margin-bottom: 0px;
            margin-left: 0px;
            float: right;
            height: 30px;
            text-align: center;
            line-height: 30px;
        }
        #uidLabel {
            margin-right: 5px;
        }
        #stateQueryBtn{
            float: right;
            margin-top: 0px;
            height: 30px;
            text-align: center;
            line-height: 15px;
            background-color: #2aabd2;
            width: 50px;
        }



    </style>
</head>
<body>
<form  action=""  name="stuForm" id="stuForm">
    <h2>用户管理</h2>
    <button type="button" name="auBtn" id="auBtn" onclick="window.location.href='register.jsp'">新增用户</button>
    <button type="button" name="uqBtn" id="uqBtn" >用户列表查询</button>


    <br>
    <br>

    <div id="mqDiv">
        <button type="button"  name="stateQueryBtn" id="stateQueryBtn">搜索</button>
        <input type="text" name="jobQuery" id="jobQuery">
        <label id="jqLabel" for="jobQuery" >职业:</label>

        <input type="text" name="uidQuery" id="uidQuery">
        <label id="uidLabel" for="uidQuery" >用户名:</label>
    </div>

    <span id="msg" style="font-size: 12px;color: red"></span>
    <br>
    <br>
    <table  class="table table-hover table-bordered table-striped">
        <tr>
            <td>用户名</td>
            <td>用户密码</td>
            <td>用户邮箱</td>
            <td>用户职业</td>
        </tr>
        <tbody id="userTable">

        </tbody>
    </table>

    <br>



    <nav class="text-right" id="page"> <!--text-right 右对齐，bootstrap中样式-->
        <ul id="pageLimit"></ul>
    </nav>



    <div id="modifyDiv">
        <label id="mLabel">修改此用户：</label>
        <select  id="modify" name="modify" >

        </select>

        <button type="button" name="modifyBtn" id="modifyBtn" onclick="stuForm.action='mu';stuForm.method='get';stuForm.submit()" >修改用户</button>
    </div>




    <div id="deleteDiv">
        <label id="dLabel">删除此用户：</label>
        <select  id="delete" name="delete" >

        </select>
        <button type="button" name="deleteBtn" id="deleteBtn" onclick="stuForm.action='du';stuForm.method='post';stuForm.submit()" >删除用户</button>
    </div>


</form>
</body>


<script type="text/javascript">
    //全局变量
    var typeOfQuery; //当前查询的类别

    $("#stateQueryBtn").click(function () {
        var inputUid = $("#uidQuery").val();
        var inputJob = $("#jobQuery").val();
        console.log("inputUid:" + inputUid);
        console.log("inputJob:" + inputJob);

        if (inputUid != "" || inputJob != "") {
            $("#userTable").empty(""); //先清空table
            $("#msg").empty();
            $("#delete").empty();
            $("#modify").empty();

            console.log("准备ajax")
            var appendURL = "uq?mq=true";
            if(inputUid != "") {
                appendURL = appendURL.concat("&uidQuery=" + inputUid);
            }
            if(inputJob != "") {
                appendURL = appendURL.concat("&jobQuery=" + inputJob);
            }
            console.log(appendURL);

            $.post({           //使用ajax获取后台数据库数据到前台进行拼接
                url:appendURL,
                dataType: "json",
                success: function(data){
                    /*这个方法里是ajax发送请求成功之后执行的代码*/
                    showData1(data);//我们仅做数据展示

                    //将分页放到查询的ajax的success中，避免线程问题导致分页栏早于查询
                    $.get({           //使用ajax获取后台数据库数据到前台渲染分页组件
                        url:"uq",
                        dataType: "json",
                        success: function(page){
                            /*这个方法里是ajax发送请求成功之后执行的代码*/
                            //渲染分页栏
                            showPaginator(page);
                        },
                        error: function(msg){
                            alert("分页ajax连接异常："+msg);
                        }
                    });
                },
                error: function(msg){
                    alert("查询ajax连接异常："+msg);
                }
            });
        }
    })

    $("#uqBtn").click(function (){
        $("#userTable").empty(""); //先清空table
        $("#msg").empty();

        $("#delete").empty();
        $("#modify").empty();

        $.post({           //使用ajax获取后台数据库数据到前台进行拼接
            url:"uq",
            dataType: "json",
            success: function(data){
                /*这个方法里是ajax发送请求成功之后执行的代码*/
                showData1(data);//我们仅做数据展示

                //将分页放到查询的ajax的success中，避免线程问题导致分页栏早于查询
                $.get({           //使用ajax获取后台数据库数据到前台渲染分页组件
                    url:"uq",
                    dataType: "json",
                    success: function(page){
                        /*这个方法里是ajax发送请求成功之后执行的代码*/
                        //渲染分页栏
                        showPaginator(page);
                    },
                    error: function(msg){
                        alert("分页ajax连接异常："+msg);
                    }
                });
            },
            error: function(msg){
                alert("查询ajax连接异常："+msg);
            }
        });
    });



    function showPaginator(page) {
        //初始化分页栏
        $('#pageLimit').bootstrapPaginator({
            currentPage:page.pageNum|| 1 ,
            totalPages: page.pages|| 1,
            size:"normal",
            bootstrapMajorVersion: 3,
            alignment:"right",
            numberOfPages:page.pageSize|| 4,
            itemTexts: function (type, page, current) {
                switch (type) {
                    case "first": return "首页";
                    case "prev": return "上一页";
                    case "next": return "下一页";
                    case "last": return "末页";
                    case "page": return page;
                }
            },
            onPageClicked:function (event, originalEvent, type, page) {
                console.log("onPageClicked,Page:" +page );
                findData(page);
            }
        });
    }

    function findData(page) {
        $("#userTable").empty(""); //先清空table
        $("#msg").empty();
        $("#delete").empty();
        $("#modify").empty();


        $.post({           //使用ajax获取后台数据库数据到前台进行拼接
            url:"uq?" + "page=" +page,
            dataType: "json",
            success: function(data){
                /*这个方法里是ajax发送请求成功之后执行的代码*/
                showData1(data);//要么直接return 要么使用break！！！！！！！！！！！！！！！！！！！！

                //将分页放到查询的ajax的success中，避免线程问题导致分页栏早于查询
                $.get({           //使用ajax获取后台数据库数据到前台渲染分页组件
                    url:"uq",
                    dataType: "json",
                    success: function(page){
                        /*这个方法里是ajax发送请求成功之后执行的代码*/
                        showPaginator(page);
                    },
                    error: function(msg){
                        alert("分页ajax连接异常："+msg);
                    }
                });

            },
            error: function(msg){
                alert("查询ajax连接异常："+msg);
            }
        });

    }

    function showData1(data) {
        console.log("showData1");

        var str = "";//定义用于拼接的字符串
        var i = 0;
        for ( i = 0; i < data.length-1; i++) {
            //最后一个data存的是总条数
            //拼接表格的行和列
            str = "<tr><td>" + data[i].userId + "</td><td>" + data[i].userPwd + "</td><td>" + data[i].userMail  + "</td><td>" +
                data[i].userJob + "</td>";
            //追加到table中
            $("#userTable").append(str);
            //前面清空了，现在追加新的选项
            $("#delete").append('<option value='+ data[i].userId +' id=' + data[i].userId + '>' + data[i].userId + '</option>');
            $("#modify").append('<option value='+ data[i].userId +' id=' + data[i].userId + '>' + data[i].userId + '</option>');
        }


        $("#msg").html("用户列表共查询到：" + data[i].courseName + " 条数据");
        console.log(data[i].suid + ":" + data[i].courseName)
    }










</script>

</html>

