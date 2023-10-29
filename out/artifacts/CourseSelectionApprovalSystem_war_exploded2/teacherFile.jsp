<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Zinc-ion
  Date: 2023/5/1
  Time: 22:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>主讲教师主页</title>
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
        #rjBtn {
            background-color: red;
            margin-top: 0px;
            height: 35px;
            vertical-align:middle;
            line-height: 18px;
        }
        #agBtn {
            margin-top: 0px;
            height: 35px;
            vertical-align:middle;
            line-height: 18px;
        }
        #pagapqBtn {
            float: right;
            margin-right: 0px;
            margin-right: 5px;
        }

        #prjapqBtn {
            width: 150px;
            float: right;
            margin-right: 0px;
        }
        body {
            background-image: url("${pageContext.request.contextPath}/img/logo.png");
            background-repeat:no-repeat;
            background-size: 135px 125px;
            background-position:50% 675px;
        }
        #reject {
            width: 120px;
            height: 35px;
            vertical-align:middle;

        }
        #agree {
            width: 120px;
            height: 35px;
            vertical-align:middle;
        }
        #agLabel {
            margin-top: 0px;
        }
        #apTable {
            margin-bottom: 0px;
        }
        #page {
            width: 500px;
            float: right;
        }


        #mqDiv{
            float: right;
            width: 800px;
            margin-bottom: 12px;
        }
        #stateQuery,#courseQuery,#suidQuery{
            width: 80px;
            float: right;
            margin-bottom: 0px;
            height: 30px;
            margin-right: 5px;
        }
        #sqLabel,#suidLabel{
            width: 50px;
            margin-right: 0px;
            margin-bottom: 0px;
            margin-left: 0px;
            float: right;
            height: 30px;
            text-align: center;
            line-height: 30px;
        }
        #cqLabel{
            width: 100px;
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
            width: 70px;
            text-align: center;
            line-height: 15px;
            background-color: #2aabd2;
        }



    </style>
</head>
<body>


<form  action="rja" method="post" name="teaForm" id="teaForm">
    <h1>${sessionScope.role}主页</h1>
    <button type="button" name="apqBtn" id="apqBtn" >待审批查询</button>
    <button type="button" name="prjapqBtn" id="prjapqBtn" >已驳回审批查询</button>
    <button type="button" name="pagapqBtn" id="pagapqBtn" >已审批查询</button>
    <br>
    <br>

    <div id="mqDiv">
        <button type="button"  name="stateQueryBtn" id="stateQueryBtn">搜索</button>
        <input type="text" name="stateQuery" id="stateQuery">
        <label id="sqLabel" for="stateQuery" >状态:</label>

        <input type="text" name="courseQuery" id="courseQuery">
        <label id="cqLabel" for="courseQuery" >选择的课程:</label>


        <input type="text" name="suidQuery" id="suidQuery">
        <label id="suidLabel" for="suidQuery" >学生ID:</label>
    </div>

    <span id="msg1" style="font-size: 12px;color: red"></span>

    <br>
    <br>
    <table class="table table-hover table-bordered ">
        <tr>
            <td>学生ID</td>
            <td>选择的课程</td>
            <td>原因</td>
            <td>证明</td>
            <td>驳回原因</td>
            <td>申请状态</td>
        </tr>
        <tbody id="apTable">

        </tbody>
    </table>



    <nav class="text-right" id="page"> <!--text-right 右对齐，bootstrap中样式-->
        <ul id="pageLimit"></ul>
    </nav>



    <label id = "agLabel">同意此申请：</label>
    <select  id="agree" name="agree" >
<%--        <c:forEach var="courseName" items="${leadTApprovalNameList}">--%>
<%--            <option value="${courseName}" id="${courseName}">${courseName}</option>--%>
<%--        </c:forEach>--%>
    </select>
    <button type="button" name="agBtn" id="agBtn" onclick="teaForm.action='aga';teaForm.method='get';teaForm.submit()" >同意申请</button>

    <br><br>

    <label id= "rjLabel">驳回此申请：</label>
    <select  id="reject" name="reject" >
<%--        <c:forEach var="courseName" items="${leadTApprovalNameList}">--%>
<%--            <option value="${courseName}" id="${courseName}">${courseName}</option>--%>
<%--        </c:forEach>--%>
    </select>
    <button type="button" name="rjBtn" id="rjBtn" >驳回申请</button>
    <br><br>
    <label for="rejectReason">驳回理由：</label>
    <input type="text" name="rejectReason" id="rejectReason">
    <br>

    <span id="msg2" style="font-size: 12px;color: red"></span>


</form>
</body>

<script type="text/javascript">
    var typeOfQuery;

    $("#stateQueryBtn").click(function () {
        console.log("typeOfQuery:" + typeOfQuery);
        var inputState = $("#stateQuery").val();
        var inputCourse = $("#courseQuery").val();
        var inputSuid = $("#suidQuery").val();
        console.log("inputSuid:" + inputSuid);
        console.log("inputCourse:" + inputCourse);
        console.log("inputState:" + inputState);

        switch (typeOfQuery) {
            case "apq" :
                if(inputState != "" || inputSuid != "" || inputCourse != "") {
                    $("#apTable").empty(); //先清空table
                    $("#agree").empty(); //情况选项
                    $("#reject").empty(); //情况选项
                    typeOfQuery="apq"

                    console.log("进入apqAjax")
                    var appendURL = "apq?mq=true";
                    if(inputState != "") {
                        appendURL = appendURL.concat("&stateQuery=" + inputState);
                    }
                    if(inputSuid != "") {
                        appendURL = appendURL.concat("&suidQuery=" + inputSuid);
                    }
                    if(inputCourse != ""){
                        appendURL = appendURL.concat("&courseQuery=" + inputCourse);
                    }
                    console.log(appendURL);

                    $.post({           //使用ajax获取后台数据库数据到前台进行拼接
                        url:appendURL,
                        dataType: "json",
                        success: function(data){
                            /*这个方法里是ajax发送请求成功之后执行的代码*/
                            showData(data);//我们仅做数据展示

                            //将分页放到查询的ajax的success中，避免线程问题导致分页栏早于查询
                            $.get({           //使用ajax获取后台数据库数据到前台渲染分页组件
                                url:"apq",
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
                            alert("ajax连接异常："+msg);
                        }
                    });
                }
                break;
            case "pagapq" :
                if(inputState != "" || inputSuid != "" || inputCourse != "") {
                    $("#apTable").empty(); //先清空table
                    $("#agree").empty(); //情况选项
                    $("#reject").empty(); //情况选项
                    typeOfQuery="pagapq"

                    var appendURL = "pagapq?mq=true";
                    if(inputState != "") {
                        appendURL = appendURL.concat("&stateQuery=" + inputState);
                    }
                    if(inputSuid != "") {
                        appendURL = appendURL.concat("&suidQuery=" + inputSuid);
                    }
                    if(inputCourse != ""){
                        appendURL = appendURL.concat("&courseQuery=" + inputCourse);
                    }
                    console.log(appendURL);

                    $.post({           //使用ajax获取后台数据库数据到前台进行拼接
                        url:appendURL,
                        dataType: "json",
                        success: function(data){
                            /*这个方法里是ajax发送请求成功之后执行的代码*/
                            showData(data);//我们仅做数据展示
                            //将分页放到查询的ajax的success中，避免线程问题导致分页栏早于查询
                            $.get({           //使用ajax获取后台数据库数据到前台渲染分页组件
                                url:"pagapq",
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
                            alert("ajax连接异常："+msg);
                        }
                    });
                }
                break;
            case "prjapq" :
                if(inputState != "" || inputSuid != "" || inputCourse != "") {
                    $("#apTable").empty(); //先清空table
                    $("#agree").empty(); //情况选项
                    $("#reject").empty(); //情况选项
                    typeOfQuery="prjapq"

                    var appendURL = "prjapq?mq=true";
                    if(inputState != "") {
                        appendURL = appendURL.concat("&stateQuery=" + inputState);
                    }
                    if(inputSuid != "") {
                        appendURL = appendURL.concat("&suidQuery=" + inputSuid);
                    }
                    if(inputCourse != ""){
                        appendURL = appendURL.concat("&courseQuery=" + inputCourse);
                    }
                    console.log(appendURL);

                    $.post({           //使用ajax获取后台数据库数据到前台进行拼接
                        url:appendURL,
                        dataType: "json",
                        success: function(data){
                            /*这个方法里是ajax发送请求成功之后执行的代码*/
                            showData(data);//我们仅做数据展示
                            //将分页放到查询的ajax的success中，避免线程问题导致分页栏早于查询
                            $.get({           //使用ajax获取后台数据库数据到前台渲染分页组件
                                url:"prjapq",
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
                            alert("ajax连接异常："+msg);
                        }
                    });
                }
                break;
        }

    })

    $("#apqBtn").click(function (){
        $("#apTable").empty(); //先清空table
        $("#agree").empty(); //情况选项
        $("#reject").empty(); //情况选项
        $("#msg1").empty();
        typeOfQuery="apq"

        $.post({           //使用ajax获取后台数据库数据到前台进行拼接
            url:"apq",
            dataType: "json",
            success: function(data){
                /*这个方法里是ajax发送请求成功之后执行的代码*/
                showData(data);//我们仅做数据展示

                //将分页放到查询的ajax的success中，避免线程问题导致分页栏早于查询
                $.get({           //使用ajax获取后台数据库数据到前台渲染分页组件
                    url:"apq",
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
                alert("ajax连接异常："+msg);
            }
        });



    });

    $("#pagapqBtn").click(function (){
        $("#apTable").empty(); //先清空table
        $("#agree").empty(); //情况选项
        $("#reject").empty(); //情况选项
        $("#msg1").empty();
        typeOfQuery="pagapq"

        $.post({           //使用ajax获取后台数据库数据到前台进行拼接
            url:"pagapq",
            dataType: "json",
            success: function(data){
                /*这个方法里是ajax发送请求成功之后执行的代码*/
                showData(data);//我们仅做数据展示
                //将分页放到查询的ajax的success中，避免线程问题导致分页栏早于查询
                $.get({           //使用ajax获取后台数据库数据到前台渲染分页组件
                    url:"pagapq",
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
                alert("ajax连接异常："+msg);
            }
        });



    });

    $("#prjapqBtn").click(function (){
        $("#apTable").empty(); //先清空table
        $("#agree").empty(); //情况选项
        $("#reject").empty(); //情况选项
        $("#msg1").empty();
        typeOfQuery="prjapq"

        $.post({           //使用ajax获取后台数据库数据到前台进行拼接
            url:"prjapq",
            dataType: "json",
            success: function(data){
                /*这个方法里是ajax发送请求成功之后执行的代码*/
                showData(data);//我们仅做数据展示
                //将分页放到查询的ajax的success中，避免线程问题导致分页栏早于查询
                $.get({           //使用ajax获取后台数据库数据到前台渲染分页组件
                    url:"prjapq",
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
                alert("ajax连接异常："+msg);
            }
        });


    });

    function showPaginator(page) {
        console.log("showPaginator:pageNum,pages,pageSize" + page.pageNum);
        console.log(page.pages);
        console.log(page.pageSize);
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
        console.log("findData,page,typeofquery:" + page + " " +typeOfQuery);
        $("#apTable").empty(""); //先清空table

        //$("#complete").empty(); 进度查询不需要清空确认栏
        $("#reject").empty();
        $("#agree").empty();


        $.post({           //使用ajax获取后台数据库数据到前台进行拼接
            url:"" + typeOfQuery + "?page=" +page,
            dataType: "json",
            success: function(data){
                /*这个方法里是ajax发送请求成功之后执行的代码*/
                console.log("findData:" + typeOfQuery);
                showData(data);

                //将分页放到查询的ajax的success中，避免线程问题导致分页栏早于查询
                $.get({           //使用ajax获取后台数据库数据到前台渲染分页组件
                    url:"" + typeOfQuery,
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

    function showData(data) {
        var str = "";//定义用于拼接的字符串
        var i = 0;
        for ( i = 0; i < data.length -1; i++) {
            // 最后一个approval存了总条数
            //拼接表格的行和列
            str = "<tr><td>" + data[i].suid + "</td><td>" + data[i].courseName + "</td><td>" + data[i].cause + "</td><td><a href='/ZincIon/pd?filePath=" + data[i].proof + "' onclick='return linkClick(this)'>" + "点此查看证明" + "</a></td><td>" +
                data[i].rejection_reason + "</td><td>" + data[i].state + "</td></tr>";
            //追加到table中
            $("#apTable").append(str);

            //修改选项
            switch (typeOfQuery) {
                case "apq" :
                    $("#agree").append("<option value='" + data[i].suid + " " +data[i].courseName + "' id=" + data[i].suid + " " + data[i].courseName + ">" + data[i].suid + " " + data[i].courseName + "</option>");
                    $("#reject").append("<option value='" + data[i].suid + " " + data[i].courseName + "' id=" + data[i].suid + " " + data[i].courseName + ">" + data[i].suid + " " + data[i].courseName + "</option>");
                    break;
            }
        }

        switch (typeOfQuery) {
            case "apq" :
                $("#msg1").html("待审批申请共查询到：" + data[i].courseName + " 条数据");
                console.log(data[i].suid + ":" + data[i].courseName)
                break;
            case "pagapq" :
                $("#msg1").html("已审批申请共查询到：" + data[i].courseName + " 条数据");
                console.log(data[i].suid + ":" + data[i].courseName)
                break;
            case "prjapq" :
                $("#msg1").html("已驳回申请共查询到：" + data[i].courseName + " 条数据");
                console.log(data[i].suid + ":" + data[i].courseName)
                break;
        }

    }

    $("#rjBtn").click(function () {
        var rjr = $("#rejectReason").val();
        if(isEmpty(rjr)) {
            $("#msg2").html("驳回理由不可为空！");
            return;
        } else {
           $("#teaForm").submit();
        }

    })

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

</html>
