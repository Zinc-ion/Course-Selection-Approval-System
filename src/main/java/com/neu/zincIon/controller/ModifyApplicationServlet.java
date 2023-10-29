package com.neu.zincIon.controller;

import com.neu.zincIon.pojo.Approval;
import com.neu.zincIon.service.ApprovalService;
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
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@WebServlet(name = "ModifyApplicationServlet", value = "/mac")
public class ModifyApplicationServlet extends HttpServlet {

    private static String cause;
    private static String proof;//新存文件绝对路径
    private static String courseName;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入mac.get");
        //先在get里检查是否找到对应项，再传给修改页面进行修改
        String cname = request.getParameter("modify");
        //检查选项是否未空
        if(cname == null) {
            response.getWriter().print("<script language='javascript'>" +
                    "alert('修改失败，未找到对应申请');" +
                    "window.location.href='studentFile.jsp';</script>')");  //查询对应项失败重定向到学生页面jsp
            System.out.println("修改失败，未找到对于申请！");
        }
        System.out.println("要修改的课程名：" + cname);
        courseName = cname;
        String stuid = java.net.URLDecoder.decode((String) request.getSession().getAttribute("user"),"UTF-8");
        String suid = stuid; //将session中存的当前用户id取出后解码


        //查询该用户名下的所有申请
        boolean flag = false;
        List<Approval> approvalList = ApprovalService.selectAllApplicationBySuid(suid);
        Map<String,Object> map = new HashMap<String, Object>();
        for(Approval approval : approvalList) {
            if(cname.equals(approval.getCourseName())) {
                flag = true;
                //申请已提交、课程主讲教师审批中、课程主管教师审批中、审批成功、申请驳回。
                map.put("courseName",approval.getCourseName());
                map.put("cause",approval.getCause());
                map.put("proof",approval.getProof());
                map.put("rejection_reason",approval.getRejection_reason());
                map.put("state",approval.getState());
                //将map存入session
                request.getSession().setAttribute("modifyApplication",map);
                response.sendRedirect("modifyApplication.jsp");   //
            }
        }

        if(!flag) {
            //未找到对应申请
            response.getWriter().print("<script language='javascript'>" +
                    "alert('修改失败，未找到对应申请');" +
                    "window.location.href='studentFile.jsp';</script>')");  //查询对应项失败重定向到学生页面jsp
            System.out.println("修改失败，未找到对应申请！");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入mac.post courseName：" + courseName);
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
            }

        } catch (FileUploadException e) {
            e.printStackTrace();
        }



        System.out.println("要修改的课程名：" + courseName);
        String stuid = java.net.URLDecoder.decode((String) request.getSession().getAttribute("user"),"UTF-8");
        String suid = stuid; //将session中存的当前用户id取出后解码
        //课程是主键之一，不能改
        String rejection_reason = null;
        String state = "申请已提交";  //修改状态 申请已提交、课程主讲教师审批中、课程主管教师审批中、审批成功、申请驳回。

        //拿到原文件地址
        Approval ap = ApprovalService.getApproval(suid,courseName);
        String oldProofPath = ap.getProof();

        //拿到文件夹地址
        String oldPackagePath = DecoderAndFindPackagePath(oldProofPath);
        System.out.println("旧文件夹地址1：" + oldPackagePath);



        System.out.println("修改后内容："+suid+" "+courseName+ " " +cause+ " " +proof+ " " );

        if(ApprovalService.updateApproval(new Approval(suid,courseName,cause,proof,rejection_reason,state))) {
            //修改成功
            //删除原文件
            System.out.println("删除原文件夹：" + oldPackagePath);
            File file1 =new File(oldPackagePath); //该文件的绝对路径
            deleteFile(file1);
            //重复查询该学生下的所有申请的名字，更新修改和删除下拉菜单中的选项
            List<String> applicationNameList = ApprovalService.getApprovalNameList(suid);
            request.getSession().setAttribute("applicationNameList",applicationNameList);
            //查询该学生下所有申请中状态为驳回或审批成功的申请，用于申请确认选项
            List<String> completeACNameList = ApprovalService.getCompleteACNameList(suid);
            request.getSession().setAttribute("completeACNameList" ,completeACNameList);

            response.getWriter().print("<script language='javascript'>" +
                    "alert('修改成功！');" +
                    "window.location.href='studentFile.jsp';</script>')");
            System.out.println("申请修改成功！");
        }else {
            //修改失败
            //先把文件删了
            //拿到新文件夹地址
            String newPackPath = DecoderAndFindPackagePath(proof);
            System.out.println("新文件夹地址：" + newPackPath);
            File file3 =new File(newPackPath); //该文件的绝对路径
            deleteFile(file3);//删除文件；

            response.getWriter().print("<script language='javascript'>" +
                    "alert('修改失败！');" +
                    "window.location.href='studentFile.jsp';</script>')");  //注册失败重定向到注册jsp
            System.out.println("修改失败！");
        }

    }


    //文件上传
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
                 //使用enctype="multipart/form-data"提交表单后不能用request取域对象
                if (name.equals("cause")) {
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
                //realPath=真实文件夹
                FileOutputStream fos = new FileOutputStream(realPath+"/"+fileName);


                //将绝对路径存入proof中，存入数据库,为防止中文乱码，使用utf-8来编码
                proof = java.net.URLEncoder.encode(realPath+"/"+fileName,"UTF-8");
                System.out.println("新文件地址：" + realPath+"/"+fileName );


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


    public void deleteFile(File file) {
        boolean flag = false;
        if(file.exists()) {
            // 如果是目录，则遍历删除
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    System.out.println("删除文件：" + files[i].getName());
                    deleteFile(files[i]);
                }
            }
            file.delete();
        } else {
            System.out.println("要删除的文件不存在：" + file.getName());

        }
    }

    public String DecoderAndFindPackagePath(String path)  {
        String filePath = java.net.URLDecoder.decode(path);
        String[] ss;
        ss = (filePath).split("/");
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < ss.length-1; i++) {
            if(i != 0) {
                sb.append("/");
            }
            sb.append(ss[i]); //找到文件的上一级uuid命名的文件夹，删除整个文件夹
        }
        System.out.println("要删除的文件夹：" + sb.toString());
        return sb.toString();

    }

}
