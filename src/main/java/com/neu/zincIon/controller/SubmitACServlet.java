package com.neu.zincIon.controller;

import com.neu.zincIon.pojo.Course;
import com.neu.zincIon.service.ApprovalService;
import com.neu.zincIon.service.CourseService;
import com.neu.zincIon.service.DynamicApprovalService;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

@WebServlet(name = "SubmitACServlet", value = "/sac")
public class SubmitACServlet extends HttpServlet {

    private static String courseNameAndTime;
    private static String cause;
    private static String proof;//存文件绝对路径,经过utf-8编码后的

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //判断是普通表单还是文件表单
        if (!ServletFileUpload.isMultipartContent(request)) {
            response.getWriter().print("<script language='javascript'>" +
                    "alert('提交失败，证明文件为空，请选择证明文件！');" +
                    "window.location.href='courseSelect.jsp';</script>')");
            System.out.println("提交失败，证明文件为空，请选择证明文件！");
            return;//终止方法运行，说明这是一个普通表单
        }

        //创建文件报存路径，存在web-inf下安全，用户无法直接访问
        String uploadPath = this.getServletContext().getRealPath("/download");
        File uploadFile = new File(uploadPath);
        if (!uploadFile.exists()) {
            uploadFile.mkdir();//没有则创建目录
        }

        //缓存，临时文件 如果文件超过大小就放到缓存里，过几天删除，或提醒用户转为永久
        String tmpPath = this.getServletContext().getRealPath("/download/tmp");
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

            if(msg.equals("文件上传失败！")) {
                response.getWriter().print("<script language='javascript'>" +
                        "alert('提交失败，证明文件为空，请选择证明文件！');" +
                        "window.location.href='courseSelect.jsp';</script>')");
                System.out.println("提交失败，证明文件为空，请选择证明文件！");
                return;
            }

        } catch (FileUploadException e) {
            e.printStackTrace();
        }

        //获取信息
        String stuid = java.net.URLDecoder.decode((String) request.getSession().getAttribute("user"),"UTF-8");
        String suid = stuid; //将session中存的当前用户id取出后解码

        String rejection_reason = null;
        String state = "申请已提交";  //申请已提交、课程主讲教师审批中、课程主管教师审批中、审批成功、申请驳回。
        String[] c = courseNameAndTime.split(" "); //只取主键
        String courseName = c[0];

        //查询该课程审批是否为动态审批
        Course course = CourseService.getCourseByName(courseName);
        if(course.getLtuid().equals("DynamicApproval") && course.getStuid().equals("DynamicApproval")) {
            //是动态审批
            //查看lt和st是否为空
            List<String> ltListByCourseName = DynamicApprovalService.getLTListByCourseName(courseName);
            List<String> stListByCourseName = DynamicApprovalService.getSTListByCourseName(courseName);
            if(ltListByCourseName.size() == 0) {
                state = "课程主管教师审批中";
                if(stListByCourseName.size() == 0) {
                    state = "审批成功";
                }
            }

        }


        //正常流程
        System.out.println("suid: " + suid+" courseName:"+courseName+ " cause：" +cause+ " proof:" +proof+ " " );

        if(ApprovalService.addApproval(suid,courseName,cause,proof,rejection_reason,state)) {
            //提交新ap后更新查询该学生下的所有申请的名字，覆盖原有session
            List<String> applicationNameList = ApprovalService.getApprovalNameList(suid);
            request.getSession().setAttribute("applicationNameList",applicationNameList);

            response.getWriter().print("<script language='javascript'>" +
                    "alert('申请已提交！');" +
                    "window.location.href='studentFile.jsp';</script>')");  //注册成功重定向
            System.out.println("申请已提交");
        }else {
            //出现问题先把文件删了
            File file =new File(java.net.URLDecoder.decode(proof,"UTF-8")); //该文件的绝对路径
            file.delete();//删除文件；

            response.getWriter().print("<script language='javascript'>" +
                    "alert('提交失败，该课程已被选择过，请重新选课！');" +
                    "window.location.href='courseSelect.jsp';</script>')");  //注册失败重定向到注册jsp
            System.out.println("提交失败，该课程已被选择过，请重新选择！");
        }
    }



    public static DiskFileItemFactory getDiskFileItemFactory(File file) {
        //1.创建磁盘工厂对象，处理文件上传路径或限制大小
        DiskFileItemFactory factory = new DiskFileItemFactory();
        //设置缓冲区大小，大于则放到临时文件夹中
        factory.setSizeThreshold(1024*1024*100); //缓冲大小100m
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
        upload.setFileSizeMax(1024 * 1024 * 100); //100m
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
                if(name.equals("course")) {
                    courseNameAndTime = value;   //使用enctype="multipart/form-data"提交表单后不能用request取域对象
                }else if (name.equals("cause")) {
                    cause = value;
                }
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
                FileOutputStream fos = new FileOutputStream(realPath+"/"+fileName);


                //将绝对路径存入proof中，存入数据库,为防止中文乱码，使用utf-8来编码
                proof = java.net.URLEncoder.encode(realPath+"/"+fileName,"UTF-8");


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
        System.out.println("msg:" + msg);
        return msg;
    }
}
