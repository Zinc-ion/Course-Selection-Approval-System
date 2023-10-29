package com.neu.zincIon.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
非法访问拦截
    需要放行的资源:
    1.放行不用登录就可访问的页面（登录页面）
    2.静态资源（image，js，css文件等）
    3.指定操作，无需登录即可放行的操作（登录，注册的Servlet）
    4.登录状态放行（判断session中用户信息是否为空）
 */

@WebFilter("/*")
public class AccessFilter implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("af已经初始化");
    }

    /*
        过滤器中所有代码在过滤特定请求的时候都会执行
        必须让资源继续前行  chain.doFilter(request,response);
     */
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        //过滤器中设置编码格式，就不用在每个servlet中设置了

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        //获取请求路径
        String url = request.getRequestURI();
        System.out.println(url);

        //1.2.3.是登录页面就放行，静态资源（image，js，css文件等，具体需要与目录对应）也放行 登录注册操作也放行
        if(url.contains("/login.jsp") || url.contains("/js") || url.contains("/image") || url.contains("/png") || url.contains("/logo.png") ||
                url.contains("/css") || url.contains("/login") || url.contains("/reg")) {
            chain.doFilter(request,response);
            return;
        }

        //4.登录成功就放行 session中有登录信息 记得改成存的值的name
        String uname = (String) request.getSession().getAttribute("user");
        if(uname != null) {
            //4.1不同用户权限区分学生不能进教师，教师不能进学生，管理员可以进任何一个页面
            String role = (String) request.getSession().getAttribute("role");
            if(role.equals("学生")) {
                if(url.contains("/aga") || url.contains("/apq") || url.contains("/pagapq") ||
                        url.contains("/prjapq") || url.contains("/rja") || url.contains("/teacherFile.jsp")) {
                    response.getWriter().print("<script language='javascript'>" +
                            "alert('此用户无权访问该页面！');" +
                            "window.location.href='studentFile.jsp';</script>')");
                    System.out.println("此用户无权访问该页面！");
                    return;
                }
            }else if(role.equals("主讲教师")) {
                if(url.contains("/acq") || url.contains("/dac") || url.contains("/mac") ||
                        url.contains("/sa") || url.contains("/studentFile.jsp") || url.contains("/courseSelect.jsp")|| url.contains("/modifyApplication.jsp")) {
                    response.getWriter().print("<script language='javascript'>" +
                            "alert('此用户无权访问该页面！');" +
                            "window.location.href='teacherFile.jsp';</script>')");
                    System.out.println("此用户无权访问该页面！");
                    return;
                }
            } else if (role.equals("主管教师")) {
                if(url.contains("/acq") || url.contains("/dac") || url.contains("/mac") ||
                        url.contains("/sa") || url.contains("/studentFile.jsp") || url.contains("/courseSelect.jsp")|| url.contains("/modifyApplication.jsp")
//                        || url.contains("/teacherFile.jsp")
                ) {
                    response.getWriter().print("<script language='javascript'>" +
                            "alert('此用户无权访问该页面！');" +
                            "window.location.href='teacherFile.jsp';</script>')");
                    System.out.println("此用户无权访问该页面！");
                    return;
                }
            }
            chain.doFilter(request,response);
            return;
        }




        //用户未登录则跳转登录页面
        response.sendRedirect("login.jsp");

        System.out.println("af拦截请求...");
        chain.doFilter(request,response); //让请求继续走，不然程序会停止
        System.out.println("af拦截响应...");

    }

    public void destroy() {
        System.out.println("af已经销毁");
    }
}
