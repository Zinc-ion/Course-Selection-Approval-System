<%--
  Created by IntelliJ IDEA.
  User: Zinc-ion
  Date: 2023/4/15
  Time: 15:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>上传文件</title>
    <style>
        @media (max-width: 1919px) and (min-width: 768px) {
            html {
                zoom: 90%;
            }
            @-ms-viewport { width: 1920px; }
        }
        body {
            background-image: url("/img/logo.png");
            background-repeat:no-repeat;
            background-size: 135px 125px;
            background-position:50% 675px;
        }

    </style>
</head>
<body>
<%--通过表单上传文件 必须在表单中增加enctype="multipart/form-data" --%>
  <form action="uploadFile" enctype="multipart/form-data" method="post">
    上传用户:<input type="text" name="uname"><br>
    上传用户2:<input type="text" name="uname2"><br>
    <p><input type="file" name="file1"></p>
    <p><input type="file" name="file1"> </p>

    <p><input type="submit"> | <input type="reset"></p>
  </form>

<script>

<%--动态线条背景--%>
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
