package com.neu.zincIon.service;

import com.neu.zincIon.mapper.UserMapper;
import com.neu.zincIon.pojo.User;
import com.neu.zincIon.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class LoginCheck {
    public static int login(String uid, String upwd) {
        //获取sqlSession对象
        int flag = -1;                  //标示旗，0：管理员 1：学生 2：主讲教师 3：主管教师
        SqlSession sqlSession = null;
        try {
            sqlSession = MybatisUtils.getSqlSession();
            //执行sql
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            List<User> userList = mapper.getUserList();
            for (User user : userList) {
                if(uid.equals(user.getUserId())&&upwd.equals(user.getUserPwd())) {
                    if(user.getUserJob().equals("管理员"))
                        flag = 0;
                    if(user.getUserJob().equals("主讲教师"))
                        flag = 2;
                    if(user.getUserJob().equals("学生"))
                        flag = 1;
                    if(user.getUserJob().equals("主管教师"))
                        flag = 3;
                    System.out.println("login成功");
                }
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            sqlSession.rollback();
        } finally {
            //finally中保证关闭sqlSession
            sqlSession.close();
        }

        return flag;
    }
}
