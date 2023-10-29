package com.neu.zincIon.controller;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

//实现验证码
@WebServlet(name = "ImageServlet", value = "/image")
public class ImageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //浏览器每3s刷新一次
        response.setHeader("refresh","3");

        //创建图片
        BufferedImage image = new BufferedImage(80,20,BufferedImage.TYPE_INT_BGR);
        Graphics2D g = (Graphics2D) image.getGraphics(); //笔
        //设置图片背景颜色
        g.setColor(Color.white);
        g.fillRect(0,0,80,20);
        //给图片写数据
        g.setColor(Color.BLUE);
        g.setFont(new Font(null,Font.BOLD,20));
        g.drawString(makeNum(),0,20);
        //告诉浏览器这个请求用图片的方式打开
        response.setContentType("image/jpeg");
        //网址存在缓存，
        response.setDateHeader("expires",-1);
        response.setHeader("Cache-Control","no-cache");
        response.setHeader("Pragma","no-cache");
        //把图片写给浏览器
        boolean write = ImageIO.write(image, "jpg",response.getOutputStream());


    }

    //生成随机数
    private String makeNum() {
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for(int i =0; i < 7; i++) {
            sb.append(random.nextInt(10)); //7个0~9的数拼接起来
        }
        return sb.toString();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
