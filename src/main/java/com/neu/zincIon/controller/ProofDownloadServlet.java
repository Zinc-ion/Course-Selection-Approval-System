package com.neu.zincIon.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

@WebServlet(name = "ProofDownloadServlet", value = "/pd")
public class ProofDownloadServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入pd.get");
        doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入pd.post");
        System.out.println("进入pd");
        String filePath = java.net.URLDecoder.decode(request.getParameter("filePath"),"UTF-8");
        System.out.println("filePath:"+filePath);


        try{
            String path= filePath; //获取解码后的路径
            File file = new File(path);
            boolean exists = file.exists();
            if (!exists){
                throw new Exception("文件不存在！");
            }

            // 获取文件名
            String filename = file.getName();

            // 获取文件后缀名
            String ext = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();

            // 将文件写入输入流
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStream fis = new BufferedInputStream(fileInputStream);
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();

            // 清空response
            response.reset();
            // 设置response的Header
            response.setCharacterEncoding("UTF-8");
            //Content-Disposition的作用：告知浏览器以何种方式显示响应返回的文件，用浏览器打开还是以附件的形式下载到本地保存
            //attachment表示以附件方式下载 inline表示在线打开 "Content-Disposition: inline; filename=文件名.mp3"
            // filename表示文件的默认名称，因为网络传输只支持URL编码的相关支付，因此需要将文件名URL编码后进行传输,前端收到后需要反编码才能获取到真正的名称
            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
//            response.setHeader("Content-Disposition", "attachment; filename=\""
//                    + new String(filename.getBytes(),"UTF-8") + "\"");

            // 告知浏览器文件的大小
            response.addHeader("Content-Length", "" + file.length());
            OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());

            //设置响应格式，已文件流的方式返回给前端。
            response.setContentType("application/octet-stream");
            outputStream.write(buffer);
            outputStream.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }

        response.getWriter().print("<script language='javascript'>" +
                "alert('文件下载成功！');" +
                "window.location.href='studentFile.jsp';</script>')");
        System.out.println("文件下载成功！");

    }
}
