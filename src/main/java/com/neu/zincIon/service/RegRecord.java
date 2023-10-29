package com.neu.zincIon.service;

import com.neu.zincIon.mapper.UserMapper;
import com.neu.zincIon.pojo.User;
import com.neu.zincIon.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class RegRecord {
    public static boolean reg(String uid, String upwd, String mail, String uJob) {
        //获取sqlSession对象
        SqlSession sqlSession = null;
        try {
            sqlSession = MybatisUtils.getSqlSession();
            //执行sql 方式一
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);

            //检查uid是否重复
            List<User> userList = mapper.getUserList();
            for (User user : userList) {
                if(uid.equals(user.getUserId())) {
                    System.out.println("uid重复");
                    return false;
                }
            }

            int res = mapper.addUser(new User(uid,  upwd, mail, uJob));
            if (res > 0) {
                System.out.println("插入成功");
                //记得提交事务,放在return之前
                sqlSession.commit();
                return true;
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            sqlSession.rollback();
        } finally {
            //finally中保证关闭sqlSession
            sqlSession.close();
        }

        return false;
    }
}

