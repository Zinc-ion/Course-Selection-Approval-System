package com.neu.zincIon.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.neu.zincIon.mapper.UserMapper;
import com.neu.zincIon.pojo.User;
import com.neu.zincIon.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserService {
    private static int pageSize = 4;
    
    public static Map<String,Object> getUserList(int pageNum) {
        //获取sqlSession对象
        SqlSession sqlSession = null;
        try {
            sqlSession = MybatisUtils.getSqlSession();
            //执行sql 方式一
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            PageHelper.startPage(pageNum,pageSize);
            List<User> userList = mapper.getUserList();
            PageInfo pageInfo = new PageInfo(userList);
            for (User user : userList) {
                System.out.println("user列表： " + user.toString());
            }
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("list",userList);
            map.put("pageInfo",pageInfo);

            return  map;

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            sqlSession.rollback();
        } finally {
            //finally中保证关闭sqlSession
            sqlSession.close();
        }
        return null;
    }
    //查找主讲和主管，用map返回两个list
    public static Map<String,Object> getLtuidAndStuidList() {
        //获取sqlSession对象
        SqlSession sqlSession = null;
        try {
            sqlSession = MybatisUtils.getSqlSession();
            //执行sql 方式一
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            List<User> userList = mapper.getUserList();
            List<String> ltuidList = new ArrayList<String>();
            List<String> stuidList = new ArrayList<String>();
            for (User user : userList) {
                if(user.getUserJob().equals("主讲教师")){
                    ltuidList.add(user.getUserId());
                    System.out.println("ltuid： " + user.toString());
                } else if (user.getUserJob().equals("主管教师")) {
                    stuidList.add(user.getUserId());
                    System.out.println("stuid： " + user.toString());
                }
            }
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("ltuidList",ltuidList);
            map.put("stuidList",stuidList);

            return  map;

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            sqlSession.rollback();
        } finally {
            //finally中保证关闭sqlSession
            sqlSession.close();
        }
        return null;
    }


    public static Map<String,Object> getUserListLike(int pageNum,String uidQuery,String jobQuery) {
        //获取sqlSession对象
        SqlSession sqlSession = null;
        try {
            Map<String,Object> queryMap = new HashMap<String, Object>();
       
            queryMap.put("uidQuery",uidQuery);
            queryMap.put("jobQuery",jobQuery);
         
            sqlSession = MybatisUtils.getSqlSession();
            //执行sql 方式一
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            PageHelper.startPage(pageNum,pageSize);
            List<User> userList = mapper.getUserLike(queryMap);
            PageInfo pageInfo = new PageInfo(userList);
            for (User user : userList) {
                System.out.println("模糊查出的user： " + user.toString());
            }
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("list",userList);
            map.put("pageInfo",pageInfo);

            return  map;

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            sqlSession.rollback();
        } finally {
            //finally中保证关闭sqlSession
            sqlSession.close();
        }
        return null;
    }

    public static void deleteUserById(String userId) {
        //获取sqlSession对象
        SqlSession sqlSession = null;
        try {
            sqlSession = MybatisUtils.getSqlSession();
            //执行sql 方式一
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);

            int res = mapper.deleteUser(userId);

            if (res > 0) {
                System.out.println("删除成功");
            }

            //记得提交事务
            sqlSession.commit();

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            sqlSession.rollback();
        } finally {
            //finally中保证关闭sqlSession
            sqlSession.close();
        }
    }

    public static User seletUserById(String userId) {
        //获取sqlSession对象
        SqlSession sqlSession = null;
        try {
            sqlSession = MybatisUtils.getSqlSession();
            //执行sql 方式一
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);

            User user = mapper.getUserById(userId);

            return user;

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            sqlSession.rollback();
        } finally {
            //finally中保证关闭sqlSession
            sqlSession.close();
        }
        System.out.println("未找到对应用户");
        return null;
    }

    public static boolean modifyUser(User user) {
        //获取sqlSession对象
        SqlSession sqlSession = null;
        try {
            sqlSession = MybatisUtils.getSqlSession();
            //执行sql 方式一
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);

            int res = mapper.updateUser(user);

            if(res > 0) {
                sqlSession.commit();
                return true;
            } else {
                return false;
            }

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            sqlSession.rollback();
        } finally {
            //finally中保证关闭sqlSession
            sqlSession.close();
        }
        System.out.println("未找到对应用户");
        return false;
    }


}
