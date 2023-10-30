package com.neu.zincIon.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
1.注解配置拦截路径：所有路径含有写入路径的资源都会被过滤，具体页面就写具体资源路径（不是工程目录路径！！）是服务器资源路径
2.写/*就是拦截所有资源
3.如果是过滤器链，则先配置的先执行（工程目录里首字母在前的先配置），响应时顺序反过来执行
4.chain.doFilter(request,response);前做请求拦截，后做响应拦截
 */

@WebFilter("/*")
public class CharacterEncodingFilter implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("cef已经初始化");
    }

    /*
        过滤器中所有代码在过滤特定请求的时候都会执行
        必须让资源继续前行  chain.doFilter(request,response);
     */
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        //过滤器中设置编码格式，就不用在每个servlet中设置了

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        // 处理（POST）请求,响应乱码
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        //tomcat8及以上不用处理（GET）请求乱码

        System.out.println("cef拦截请求...");
        chain.doFilter(request,response); //让请求继续走，不然程序会停止
        System.out.println("cef拦截响应...");

    }

    public void destroy() {
        System.out.println("cef已经销毁");
    }
}
