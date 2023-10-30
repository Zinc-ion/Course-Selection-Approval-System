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
  <title>审批记录查询</title>
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
      width: 180px;

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


    #page {
      width: 800px;
      float: right;
    }





  </style>
</head>
<body>
<form  action=""  name="apForm" id="apForm">
  <h2>审批记录查询</h2>
  <button type="button" name="apqBtn" id="apqBtn" >审批记录列表查询</button>


  <br>
  <br>


  <span id="msg" style="font-size: 12px;color: red"></span>

  <br>
  <table  class="table table-hover table-bordered table-striped">
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

  <br>



  <nav class="text-right" id="page"><!--text-right 右对齐，bootstrap中样式-->
    <ul id="pageLimit"></ul>
  </nav>



  <button type="button" name="downLoadagap" id="downLoadagap" onclick="window.location.href='/ZincIon/daga'" >导出已审批通过记录</button>


</form>
</body>


<script type="text/javascript">

 

  $("#apqBtn").click(function (){
    $("#apTable").empty(""); //先清空table
    $("#msg").empty();


    $.post({           //使用ajax获取后台数据库数据到前台进行拼接
      url:"aprq",
      dataType: "json",
      success: function(data){
        /*这个方法里是ajax发送请求成功之后执行的代码*/
        showData1(data);//我们仅做数据展示

        //将分页放到查询的ajax的success中，避免线程问题导致分页栏早于查询
        $.get({           //使用ajax获取后台数据库数据到前台渲染分页组件
          url:"aprq",
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
    $("#apTable").empty(""); //先清空table
    $("#msg").empty();



    $.post({           //使用ajax获取后台数据库数据到前台进行拼接
      url:"aprq?" + "page=" +page,
      dataType: "json",
      success: function(data){
        /*这个方法里是ajax发送请求成功之后执行的代码*/
        showData1(data);//要么直接return 要么使用break！！！！！！！！！！！！！！！！！！！！

        //将分页放到查询的ajax的success中，避免线程问题导致分页栏早于查询
        $.get({           //使用ajax获取后台数据库数据到前台渲染分页组件
          url:"aprq",
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
      str = "<tr><td>" + data[i].suid + "</td><td>" + data[i].courseName + "</td><td>" + data[i].cause + "</td><td><a href='/ZincIon/pd?filePath=" + data[i].proof + "' onclick='return linkClick(this)'>" + "点此查看证明" + "</a></td><td>" +
              data[i].rejection_reason + "</td><td>" + data[i].state + "</td></tr>";
      //追加到table中
      $("#apTable").append(str);
      //前面清空了，现在追加新的选项
    }

    $("#msg").html("审批记录列表共查询到：" + data[i].courseName + " 条数据");
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



</script>

</html>

