package com.neu.zincIon.controller;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

@WebServlet(name = "FileServlet", value = "/uploadFile")
public class FileServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //判断是普通表单还是文件表单
        if (!ServletFileUpload.isMultipartContent(request)) {
            return;//终止方法运行，说明这是一个普通表单
        }

        //创建文件报存路径，存在web-inf下安全，用户无法直接访问
        String uploadPath = this.getServletContext().getRealPath("/WEB-INF/upload");
        File uploadFile = new File(uploadPath);
        if (!uploadFile.exists()) {
            uploadFile.mkdir();//没有则创建目录
        }

        //缓存，临时文件 如果文件超过大小就放到缓存里，过几天删除，或提醒用户转为永久
        String tmpPath = this.getServletContext().getRealPath("/WEB-INF/tmp");
        File tmpFile = new File(tmpPath);
        if (!tmpFile.exists()) {
            tmpFile.mkdir();//没有则创建目录
        }

        //用流获取 用request.getInputStream（）十分麻烦
        //用Apache的组件实现
        /*
            具体流程：
            ServletFileUpload处理上传文件数据 将表单中每一个输入项封装为FileItem
            使用ServletFileUpload解析请求需要DiskFileItemFactory对象
            因此提前构造DiskFileItemFactory
            通过ServletFileUpload对象构造方法或setFIleItemFactory（）设置ServletFileUpload对象的fileItemFactory属性
         */

        String msg ="文件上传失败!";
        try {
            //1.创建磁盘工厂对象，处理文件上传路径或限制大小
            DiskFileItemFactory factory = getDiskFileItemFactory(uploadFile);

            //2.获取ServletFileUpload
            ServletFileUpload upload = getServletFileUpload(factory);

            //3.处理上传文件
            msg = uploadParseRequest(upload, request, uploadPath);

        } catch (FileUploadException e) {
            e.printStackTrace();
        }

        //servlet转发消息
        request.setAttribute("msg", msg);
        request.getRequestDispatcher("uploadFile.jsp").forward(request, response);

    }




    public static DiskFileItemFactory getDiskFileItemFactory(File file) {
        //1.创建磁盘工厂对象，处理文件上传路径或限制大小
        DiskFileItemFactory factory = new DiskFileItemFactory();
        //设置缓冲区大小，大于则放到临时文件夹中
        factory.setSizeThreshold(1024*1024); //缓冲大小1m
        factory.setRepository(file);//临时目录 需要一个file
        return factory;
    }

    public static ServletFileUpload getServletFileUpload(DiskFileItemFactory factory) {
        //2.获取ServletFileUpload
        ServletFileUpload upload = new ServletFileUpload(factory);

        //监听文件上传进度
        upload.setProgressListener(new ProgressListener() {
            @Override
            public void update(long pBytesRead, long pContentLength, int pItems) {
                System.out.println("总大小：" + pContentLength + "已上传：" + pBytesRead );
            }
        });

        //处理乱码
        upload.setHeaderEncoding("UTF-8");
        //设置单个文件最大值
        upload.setFileSizeMax(1024 * 1024 * 10); //10m
        //设置总共能传文件的大小
        upload.setSizeMax(1024 * 1024 * 100);//100m
        return upload;
    }

    public String uploadParseRequest(ServletFileUpload upload,HttpServletRequest request,String uploadPath ) throws IOException, FileUploadException {
        //3.处理上传文件
        //把前端请求解析，封装成FileItem对象,从ServletFileUpload对象获取
            List<FileItem> fileItems = upload.parseRequest(request);
            String msg = "文件上传失败！";
            for (FileItem fileItem : fileItems) {
                //判断是文件还是普通表单
                if(fileItem.isFormField()) {
                    //普通表单
                    String name = fileItem.getFieldName();//获取前端表单名
                    String value = fileItem.getString("UTF-8"); //处理乱码
                    System.out.println(name + ":" +value);
                } else {
                    //文件情况
                    //处理文件======================================================================================================
                    String upFileName = fileItem.getName();
                    //文件名不合法
                    if(upFileName.trim().equals("") || upFileName==null) {
                        System.out.println("错误文件名：" + upFileName);
                        continue;
                    }
                    //获取文件名与文件后缀
                    String fileName = upFileName.substring(upFileName.lastIndexOf("/") + 1);
                    String fileExtName = upFileName.substring(upFileName.lastIndexOf(".") + 1);
                    System.out.println("文件信息：【文件名】；" + fileName + " 【文件类型】：" + fileExtName);
                    //使用UUID保证名唯一 UUID.random随机生成
                    //网络传输中的东西都需要序列化 pojo实体类想在多个电脑中运行都需要实现这个标记接口serializable==》jvm--》本地方法栈（native）--》c++
                    String uuidPath = UUID.randomUUID().toString();
                    //存放地址======================================================================================================
                    //存到uploadPath
                    //真实路径 realPath
                    String realPath = uploadPath+"/"+ uuidPath;
                    //给每个文件一个文件夹
                    File realPathFile = new File(realPath);
                    if(!realPathFile.exists()) {
                        realPathFile.mkdir();
                    }
                    //文件传输======================================================================================================
                    //获取流
                    InputStream inputStream = fileItem.getInputStream();
                    //输出流
                    //realPath=真实文件夹
                    FileOutputStream fos = new FileOutputStream(realPath+"/"+fileName);


                    //存入Session中用于下载的时候取出  路径：/WEB-INF/upload/uuidPath/fileName
                    request.getSession().setAttribute("filePath",realPath+"/"+fileName);


                    //创建缓冲区
                    byte[] buffer = new byte[1024*1024];
                    //判断读完没有
                    int len = 0;
                    //大于0则还在读
                    while((len=inputStream.read(buffer))>0){
                        fos.write(buffer,0,len);
                    }
                    //关闭流
                    fos.close();
                    inputStream.close();

                    msg = "文件上传成功！";
                    fileItem.delete();//上传完毕删除临时文件
                }
            }
        return msg;
    }
}