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
    <title>课程列表</title>
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
        #cidQuery{
            width: 100px;
            float: right;
            margin-bottom: 0px;
            height: 30px;
            margin-right: 5px;

        }


        #cidLabel{
            width: 50px;
            margin-right: 0px;
            margin-bottom: 0px;
            margin-left: 0px;
            float: right;
            height: 30px;
            text-align: center;
            line-height: 30px;
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
<form  action=""  name="courseForm" id="courseForm">
    <h2>课程管理</h2>
    <button type="button" name="acBtn" id="acBtn" onclick="window.location.href='ac'">新增课程</button>
    <button type="button" name="cqBtn" id="cqBtn" >课程列表查询</button>


    <br>
    <br>

<%--    <div id="mqDiv">--%>
<%--        <button type="button"  name="stateQueryBtn" id="stateQueryBtn">搜索</button>--%>
<%--        <input type="text" name="cidQuery" id="cidQuery">--%>
<%--        <label id="cidLabel" for="cidQuery" >课程名:</label>--%>
<%--    </div>--%>

    <span id="msg" style="font-size: 12px;color: red"></span>

    <br>
    <table  class="table table-hover table-bordered table-striped">
        <tr>
            <td>课程名</td>
            <td>上课时间</td>
            <td>所属主讲教师</td>
            <td>所属主管教师</td>
        </tr>
        <tbody id="courseTable">

        </tbody>
    </table>

    <br>



    <nav class="text-right" id="page"> <!--text-right 右对齐，bootstrap中样式-->
        <ul id="pageLimit"></ul>
    </nav>



    <div id="modifyDiv">
        <label id="mLabel">修改此课程：</label>
        <select  id="modify" name="modify" >

        </select>

        <button type="button" name="modifyBtn" id="modifyBtn" onclick="courseForm.action='mc';courseForm.method='get';courseForm.submit()" >修改课程</button>
    </div>




    <div id="deleteDiv">
        <label id="dLabel">删除此课程：</label>
        <select  id="delete" name="delete" >

        </select>
        <button type="button" name="deleteBtn" id="deleteBtn" onclick="courseForm.action='dc';courseForm.method='post';courseForm.submit()" >删除课程</button>
    </div>


</form>
</body>


<script type="text/javascript">

    $("#stateQueryBtn").click(function () {
        var inputCid = $("#cidQuery").val();
        console.log("inputUid:" + inputCid);


        if (inputCid != "") {
            $("#courseTable").empty(""); //先清空table
            $("#msg").empty();
            $("#delete").empty();
            $("#modify").empty();

            console.log("准备ajax")
            var appendURL = "cq?mq=true";
            if(inputCid != "") {
                appendURL = appendURL.concat("&cidQuery=" + inputCid);
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
                        url:"cq",
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

    $("#cqBtn").click(function (){
        console.log("cqBtnClicked")
        $("#courseTable").empty(""); //先清空table
        $("#msg").empty();

        $("#delete").empty();
        $("#modify").empty();

        $.post({           //使用ajax获取后台数据库数据到前台进行拼接
            url:"cq",
            dataType: "json",
            success: function(data){
                /*这个方法里是ajax发送请求成功之后执行的代码*/
                showData1(data);//我们仅做数据展示

                //将分页放到查询的ajax的success中，避免线程问题导致分页栏早于查询
                $.get({           //使用ajax获取后台数据库数据到前台渲染分页组件
                    url:"cq",
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
        $("#courseTable").empty(""); //先清空table
        $("#msg").empty();
        $("#delete").empty();
        $("#modify").empty();


        $.post({           //使用ajax获取后台数据库数据到前台进行拼接
            url:"cq?" + "page=" +page,
            dataType: "json",
            success: function(data){
                /*这个方法里是ajax发送请求成功之后执行的代码*/
                showData1(data);//要么直接return 要么使用break！！！！！！！！！！！！！！！！！！！！

                //将分页放到查询的ajax的success中，避免线程问题导致分页栏早于查询
                $.get({           //使用ajax获取后台数据库数据到前台渲染分页组件
                    url:"cq",
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
            str = "<tr><td>" + data[i].courseName + "</td><td>" + data[i].begTime + "</td><td>" + data[i].ltuid  + "</td><td>" +
                data[i].stuid + "</td>";
            //追加到table中
            $("#courseTable").append(str);
            //前面清空了，现在追加新的选项
            $("#delete").append('<option value='+ data[i].courseName +' id=' + data[i].courseName + '>' + data[i].courseName + '</option>');
            $("#modify").append('<option value='+ data[i].courseName +' id=' + data[i].courseName + '>' + data[i].courseName + '</option>');
        }

        $("#msg").html("课程列表共查询到：" + data[i].begTime + " 条数据");
        console.log(data[i].courseName + ":" + data[i].begTime)
    }


</script>

</html>

