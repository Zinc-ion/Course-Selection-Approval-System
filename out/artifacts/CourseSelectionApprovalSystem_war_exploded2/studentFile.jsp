<%@ page import="java.util.List" %>
<%@ page import="com.neu.zincIon.service.ApprovalService" %>
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
    <title>学生主页</title>
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
            max-width: 80%;
            margin: 50px auto;
            overflow: auto;

            vertical-align:bottom;
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
            background-position:50% 675px;
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
        #completeBtn {
            width: 50px;
            margin-top: 0px;
            height: 35px;
            vertical-align:middle;
            line-height: 18px;
        }

        #modifyDiv {
            overflow: hidden;
            float: left;
        }
        #completeDiv {
            overflow: hidden;
            float: right;
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
        #cLabel {
            margin-bottom: 0px;
        }

        #modify{
            width: 100px;
            margin-top: 0px;
            height: 35px;
            vertical-align:middle;
        }
        #complete{
            width: 130px;
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
        #crjacqBtn{
            float: right;
            margin-right: 5px;
            width: 170px;
        }
        #cagacqBtn{
            float: right;
            width: 170px;
        }
        #page {
            width: 800px;
            float: right;
        }

        #mqDiv{
            float: right;
            width: 500px;
            margin-bottom: 10px;
        }
        #stateQuery{
            width: 80px;
            float: right;
            margin-bottom: 0px;
            height: 30px;
            margin-right: 5px;
        }
        #sqLabel{
            width: 80px;
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
        }



    </style>
</head>
<body>
<form  action=""  name="stuForm" id="stuForm">
    <h1>学生主页</h1>
    <button type="button" name="scBtn" id="scBtn" onclick="window.location.href='courseSelect.jsp'">选择课程</button>
    <button type="button" name="acqBtn" id="acqBtn" >申请进度查询</button>
    <button type="button" name="cagacqBtn" id="cagacqBtn" >申请通过记录查询</button>
    <button type="button" name="crjacqBtn" id="crjacqBtn" >申请驳回记录查询</button>

    <br>
    <br>

    <div id="mqDiv">
        <button type="button"  name="stateQueryBtn" id="stateQueryBtn">搜索</button>
        <input type="text" name="stateQuery" id="stateQuery">
        <label id="sqLabel" for="stateQuery" >搜索状态:</label>
    </div>

    <span id="msg" style="font-size: 12px;color: red"></span>
    <br>
    <br>
    <table  class="table table-hover table-bordered ">
        <tr>
            <td>选择的课程</td>
            <td>原因</td>
            <td>证明</td>
            <td>驳回原因</td>
            <td>申请状态</td>
        </tr>
        <tbody id="acTable">

        </tbody>
    </table>



    <nav class="text-right" id="page"> <!--text-right 右对齐，bootstrap中样式-->
        <ul id="pageLimit"></ul>
    </nav>



    <div id="modifyDiv">
        <label id="mLabel">修改此申请：</label>
        <select  id="modify" name="modify" >
            <c:forEach var="courseName" items="${applicationNameList}">
                <option value="${courseName}" id="${courseName}">${courseName}</option>
            </c:forEach>
        </select>

        <button type="button" name="modifyBtn" id="modifyBtn" onclick="stuForm.action='mac';stuForm.method='get';stuForm.submit()" >修改申请</button>
    </div>



    <br><br><br><br>
    <div id="deleteDiv">
        <label id="dLabel">删除此申请：</label>
        <select  id="delete" name="delete" >
            <c:forEach var="courseName" items="${applicationNameList}">
                <option value="${courseName}" id="${courseName}">${courseName}</option>
            </c:forEach>
        </select>
        <button type="button" name="deleteBtn" id="deleteBtn" onclick="stuForm.action='dac';stuForm.method='post';stuForm.submit()" >删除申请</button>
    </div>


    <%
        String stuid = java.net.URLDecoder.decode((String) request.getSession().getAttribute("user"),"UTF-8");
        String suid = stuid; //将session中存的当前用户id取出后解码
        //查询该学生下所有申请中状态为驳回或审批成功的申请，用于申请确认选项
        List<String> completeACNameList = ApprovalService.getCompleteACNameList(suid);
        request.getSession().setAttribute("completeACNameList" ,completeACNameList);
    %>


    <div id = "completeDiv">
        <label id="cLabel">确认并结束此申请：</label>
        <select  id="complete" name="complete" >
            <c:forEach var="courseName" items="${completeACNameList}">
                <option value="${courseName}" id="${courseName}">${courseName}</option>
            </c:forEach>
        </select>
        <button type="button" name="completeBtn" id="completeBtn" onclick="stuForm.action='cac';stuForm.method='post';stuForm.submit()" >确认</button>
    </div>

</form>
</body>


<script type="text/javascript">
    //全局变量
    var typeOfQuery; //当前查询的类别

    $("#stateQueryBtn").click(function () {
        var inputState = $("#stateQuery").val();
        console.log("inputState:" + inputState);
        console.log("typeOfQuery:" + typeOfQuery);
        switch (typeOfQuery) {
            case "acq":
                if (inputState != "") {
                    $("#acTable").empty(""); //先清空table
                    $("#msg").empty();
                    //$("#complete").empty(); 进度查询不需要清空确认栏
                    $("#delete").empty();
                    $("#modify").empty();
                    console.log("准备ajax")
                    $.post({           //使用ajax获取后台数据库数据到前台进行拼接
                        url:"acq?type=acq&" + "stateQuery=" + inputState,
                        dataType: "json",
                        success: function(data){
                            /*这个方法里是ajax发送请求成功之后执行的代码*/
                            showData1(data);//我们仅做数据展示
                            //将分页放到查询的ajax的success中，避免线程问题导致分页栏早于查询
                            $.get({           //使用ajax获取后台数据库数据到前台渲染分页组件
                                url:"acq?type=acq",
                                dataType: "json",
                                success: function(page){
                                    /*这个方法里是ajax发送请求成功之后执行的代码*/
                                    //渲染分页栏
                                    typeOfQuery = "acq";
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
                break;
            case "cagacq":
                if (inputState != "") {
                    $("#acTable").empty(""); //先清空table
                    $("#msg").empty();
                    $("#complete").empty();
                    $("#delete").empty();
                    $("#modify").empty();
                    $.post({           //使用ajax获取后台数据库数据到前台进行拼接
                        url:"acq?type=cagacq&" + "stateQuery=" + inputState,
                        dataType: "json",
                        success: function(data){
                            /*这个方法里是ajax发送请求成功之后执行的代码*/
                            showData2(data);//我们仅做数据展示
                            //将分页放到查询的ajax的success中，避免线程问题导致分页栏早于查询
                            $.get({           //使用ajax获取后台数据库数据到前台渲染分页组件
                                url:"acq?type=cagacq",
                                dataType: "json",
                                success: function(page){
                                    /*这个方法里是ajax发送请求成功之后执行的代码*/
                                    //渲染分页栏
                                    typeOfQuery = "cagacq";
                                    showPaginator(page);
                                },
                                error: function(msg){
                                    alert("分页ajax连接异常："+msg);
                                }
                            });
                        },
                        error: function(msg){
                            alert("ajax连接异常："+msg);
                        }
                    });
                }
                break;
            case "crjacq":
                if (inputState != "") {
                    $("#acTable").empty(""); //先清空table
                    $("#msg").empty();
                    $("#complete").empty();
                    $("#delete").empty();
                    $("#modify").empty();
                    $.post({           //使用ajax获取后台数据库数据到前台进行拼接
                        url:"acq?type=crjacq&" + "stateQuery=" + inputState,
                        dataType: "json",
                        success: function(data){
                            /*这个方法里是ajax发送请求成功之后执行的代码*/
                            showData3(data);//我们仅做数据展示
                            //将分页放到查询的ajax的success中，避免线程问题导致分页栏早于查询
                            $.get({           //使用ajax获取后台数据库数据到前台渲染分页组件
                                url:"acq?type=crjacq",
                                dataType: "json",
                                success: function(page){
                                    /*这个方法里是ajax发送请求成功之后执行的代码*/
                                    //渲染分页栏
                                    typeOfQuery = "crjacq";
                                    showPaginator(page);
                                },
                                error: function(msg){
                                    alert("分页ajax连接异常："+msg);
                                }
                            });
                        },
                        error: function(msg){
                            alert("ajax连接异常："+msg);
                        }
                    });
                }
                break;
        }

    })

    $("#acqBtn").click(function (){
        $("#acTable").empty(""); //先清空table
        $("#msg").empty();
        //$("#complete").empty(); 进度查询不需要清空确认栏
        $("#delete").empty();
        $("#modify").empty();

        $.post({           //使用ajax获取后台数据库数据到前台进行拼接
            url:"acq?type=acq",
            dataType: "json",
            success: function(data){
                /*这个方法里是ajax发送请求成功之后执行的代码*/
                showData1(data);//我们仅做数据展示

                //将分页放到查询的ajax的success中，避免线程问题导致分页栏早于查询
                $.get({           //使用ajax获取后台数据库数据到前台渲染分页组件
                    url:"acq?type=acq",
                    dataType: "json",
                    success: function(page){
                        /*这个方法里是ajax发送请求成功之后执行的代码*/
                        //渲染分页栏
                        typeOfQuery = "acq";
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
            numberOfPages:page.pageSize|| 3,
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
                console.log("onPageClicked:" + typeOfQuery);
                findData(page);
            }
        });
    }

    function findData(page) {
        $("#acTable").empty(""); //先清空table
        // $("#msg").empty();
        //$("#complete").empty(); 进度查询不需要清空确认栏
        $("#delete").empty();
        $("#modify").empty();
        $("#complete").empty();

        $.post({           //使用ajax获取后台数据库数据到前台进行拼接
            url:"acq?type=" + typeOfQuery + "&page=" +page,
            dataType: "json",
            success: function(data){
                /*这个方法里是ajax发送请求成功之后执行的代码*/
                console.log("findData:" + typeOfQuery);
                switch (typeOfQuery) {
                    case "acq":
                        showData1(data);//要么直接return 要么使用break！！！！！！！！！！！！！！！！！！！！
                        break;
                    case "cagacq":
                        showData2(data);
                        break;
                    case "crjacq":
                        showData3(data);
                        break;
                }

                //将分页放到查询的ajax的success中，避免线程问题导致分页栏早于查询
                $.get({           //使用ajax获取后台数据库数据到前台渲染分页组件
                    url:"acq?type=" + typeOfQuery,
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
            //拼接表格的行和列
            str = "<tr><td>" + data[i].courseName + "</td><td>" + data[i].cause + "</td><td><a href='/ZincIon/pd?filePath=" + data[i].proof + "' onclick='return linkClick(this)'>" + "点此查看证明" + "</a></td><td>" +
                data[i].rejection_reason + "</td><td>" + data[i].state + "</td></tr>";
            //追加到table中
            $("#acTable").append(str);
            //前面清空了，现在追加新的选项
            $("#delete").append('<option value='+ data[i].courseName +' id=' + data[i].courseName + '>' + data[i].courseName + '</option>');
            $("#modify").append('<option value='+ data[i].courseName +' id=' + data[i].courseName + '>' + data[i].courseName + '</option>');
            if (data[i].state == "审批成功") {
                $("#complete").append('<option value='+ data[i].courseName +' id=' + data[i].courseName + '>' + data[i].courseName + '</option>');
            } else if (data[i].state == "申请驳回") {
                $("#complete").append('<option value='+ data[i].courseName +' id=' + data[i].courseName + '>' + data[i].courseName + '</option>');
            }
        }


        $("#msg").html("申请进度共查询到：" + data[i].courseName + " 条数据");
        console.log(data[i].suid + ":" + data[i].courseName)
    }




    //查询已完成的被同意的申请
    $("#cagacqBtn").click(function (){
        $("#acTable").empty(""); //先清空table
        $("#msg").empty();
        $("#complete").empty();
        $("#delete").empty();
        $("#modify").empty();

        $.post({           //使用ajax获取后台数据库数据到前台进行拼接
            url:"acq?type=cagacq",
            dataType: "json",
            success: function(data){
                /*这个方法里是ajax发送请求成功之后执行的代码*/
                showData2(data);//我们仅做数据展示
                //将分页放到查询的ajax的success中，避免线程问题导致分页栏早于查询
                $.get({           //使用ajax获取后台数据库数据到前台渲染分页组件
                    url:"acq?type=cagacq",
                    dataType: "json",
                    success: function(page){
                        /*这个方法里是ajax发送请求成功之后执行的代码*/
                        //渲染分页栏
                        typeOfQuery = "cagacq";
                        showPaginator(page);
                    },
                    error: function(msg){
                        alert("分页ajax连接异常："+msg);
                    }
                });
            },
            error: function(msg){
                alert("ajax连接异常："+msg);
            }
        });
    });
    function showData2(data) {
        console.log("showData2");

        var str = "";//定义用于拼接的字符串
        var i = 0;
        for ( i = 0; i < data.length -1; i++) {
            //拼接表格的行和列
            str = "<tr><td>" + data[i].courseName + "</td><td>" + data[i].cause + "</td><td><a href='/ZincIon/pd?filePath=" + data[i].proof + "' onclick='return linkClick(this)'>" + "点此查看证明" + "</a></td><td>" +
                data[i].rejection_reason + "</td><td>" + data[i].state + "</td></tr>";
            //追加到table中
            $("#acTable").append(str);
            //前面清空了，现在追加新的选项
            $("#delete").append('<option value='+ data[i].courseName +' id=' + data[i].courseName + '>' + data[i].courseName + '</option>');
            $("#modify").append('<option value='+ data[i].courseName +' id=' + data[i].courseName + '>' + data[i].courseName + '</option>');
        }


        $("#msg").html("申请通过记录共查询到：" + data[i].courseName + " 条数据");
        console.log(data[i].suid + ":" + data[i].courseName)
    }




    //查询已完成的被驳回的申请
    $("#crjacqBtn").click(function (){
        $("#acTable").empty(""); //先清空table
        $("#msg").empty();
        $("#complete").empty();
        $("#delete").empty();
        $("#modify").empty();

        $.post({           //使用ajax获取后台数据库数据到前台进行拼接
            url:"acq?type=crjacq",
            dataType: "json",
            success: function(data){
                /*这个方法里是ajax发送请求成功之后执行的代码*/
                showData3(data);//我们仅做数据展示
                //将分页放到查询的ajax的success中，避免线程问题导致分页栏早于查询
                $.get({           //使用ajax获取后台数据库数据到前台渲染分页组件
                    url:"acq?type=crjacq",
                    dataType: "json",
                    success: function(page){
                        /*这个方法里是ajax发送请求成功之后执行的代码*/
                        //渲染分页栏
                        typeOfQuery = "crjacq";
                        showPaginator(page);
                    },
                    error: function(msg){
                        alert("分页ajax连接异常："+msg);
                    }
                });
            },
            error: function(msg){
                alert("ajax连接异常："+msg);
            }
        });
    });
    function showData3(data) {
        console.log("showData3");
        var str = "";//定义用于拼接的字符串
        var i = 0;
        for ( i = 0; i < data.length-1; i++) {
            //拼接表格的行和列
            str = "<tr><td>" + data[i].courseName + "</td><td>" + data[i].cause + "</td><td><a href='/ZincIon/pd?filePath=" + data[i].proof + "' onclick='return linkClick(this)'>" + "点此查看证明" + "</a></td><td>" +
                data[i].rejection_reason + "</td><td>" + data[i].state + "</td></tr>";
            //追加到table中
            $("#acTable").append(str);
            //前面清空了，现在追加新的选项
            $("#delete").append('<option value='+ data[i].courseName +' id=' + data[i].courseName + '>' + data[i].courseName + '</option>');
            $("#modify").append('<option value='+ data[i].courseName +' id=' + data[i].courseName + '>' + data[i].courseName + '</option>');
        }

        $("#msg").html("申请驳回记录共查询到：" + data[i].courseName + " 条数据");
        console.log(data[i].suid + ":" + data[i].courseName)
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

</html>

