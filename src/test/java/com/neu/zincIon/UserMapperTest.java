package com.neu.zincIon;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.neu.zincIon.mapper.ApprovalMapper;
import com.neu.zincIon.mapper.UserMapper;
import com.neu.zincIon.pojo.Approval;
import com.neu.zincIon.pojo.User;
import com.neu.zincIon.pojo.UserTest;
import com.neu.zincIon.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserMapperTest {
    @Test
    public void test() {
        //获取sqlSession对象
        SqlSession sqlSession = null;
        try {
            sqlSession = MybatisUtils.getSqlSession();
            //执行sql 方式一
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            List<User> userList = mapper.getUserList();
            for (User user : userList) {
                System.out.println(user.toString());
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            sqlSession.rollback();
        } finally {
            //finally中保证关闭sqlSession
            sqlSession.close();
        }
    }

    @Test
    public void getUserById() {
        //获取sqlSession对象
        SqlSession sqlSession = null;
        try {
            sqlSession = MybatisUtils.getSqlSession();
            //执行sql 方式一
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            User user = mapper.getUserById("admin");

            System.out.println(user.toString());

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            sqlSession.rollback();
        } finally {
            //finally中保证关闭sqlSession
            sqlSession.close();
        }
    }

    //增删改需要提交事务
    @Test
    public void addUser() {
        //获取sqlSession对象
        SqlSession sqlSession = null;
        try {
            sqlSession = MybatisUtils.getSqlSession();
            //执行sql 方式一
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            int res = mapper.addUser(new User("3",  "123", "456@qq.com", "学生"));
            if (res > 0) {
                System.out.println("插入成功");
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

    @Test
    public void updateUser() {
        //获取sqlSession对象
        SqlSession sqlSession = null;
        try {
            sqlSession = MybatisUtils.getSqlSession();
            //执行sql 方式一
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            int res = mapper.updateUser(new User("3",  "123", "123@qq.com", "教师"));
            if (res > 0) {
                System.out.println("修改成功");
            }

            //记得提交事务
            sqlSession.commit();


        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            assert sqlSession != null;
            sqlSession.rollback();
        } finally {
            //finally中保证关闭sqlSession
            sqlSession.close();
        }
    }

    @Test
    public void deleteUser() {
        //获取sqlSession对象
        SqlSession sqlSession = null;
        try {
            sqlSession = MybatisUtils.getSqlSession();
            //执行sql 方式一
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            int res = mapper.deleteUser("3");
            if (res > 0) {
                System.out.println("删除成功");
            }

            //记得提交事务
            sqlSession.commit();


        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            assert sqlSession != null;
            sqlSession.rollback();
        } finally {
            //finally中保证关闭sqlSession
            sqlSession.close();
        }
    }

    //使用map定制化传参，在表中属性过多时使用，可以不用每次都传一整个对象进去，节省空间,同时可以定制参数名，不必与user类中相同，但在xml中要与自定义名字一致
    //总结，用map可以做到需要什么用什么
    @Test
    public void getUserById2() {
        //获取sqlSession对象
        SqlSession sqlSession = null;
        try {
            sqlSession = MybatisUtils.getSqlSession();
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("helloId", "2");                              //使用map存多参数
            User user = mapper.getUserById2(map);
            System.out.println(user.toString());

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            sqlSession.rollback();
        } finally {
            //finally中保证关闭sqlSession
            sqlSession.close();
        }

    }

//    @Test
//    public void getUserLike() {
//        //获取sqlSession对象
//        SqlSession sqlSession = null;
//        try {
//            sqlSession = MybatisUtils.getSqlSession();
//            //执行sql 方式一
//            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
//            List<User> userList = mapper.getUserLike("张");
//            for (User user : userList) {
//                System.out.println(user.toString());
//            }
//        } catch (Exception ex) {
//            System.err.println(ex.getMessage());
//            sqlSession.rollback();
//        } finally {
//            //finally中保证关闭sqlSession
//            sqlSession.close();
//        }
//    }

    @Test
    public void getUserTestById() {
        //获取sqlSession对象
        SqlSession sqlSession = null;
        try {
            sqlSession = MybatisUtils.getSqlSession();
            //执行sql 方式一
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            UserTest user = mapper.getUserTestById("admin");

            System.out.println(user.toString());

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            sqlSession.rollback();
        } finally {
            //finally中保证关闭sqlSession
            sqlSession.close();
        }
    }

    @Test
    public void getApprovalListByPageHelper() {
        SqlSession sqlSession = null;
        try {
            sqlSession = MybatisUtils.getSqlSession();
            ApprovalMapper mapper = sqlSession.getMapper(ApprovalMapper.class);
            PageHelper.startPage(1,4);   //页数从1开始
            List<Approval> approvalList = mapper.selectAVApprovalBySuid("zn");
            PageInfo pageInfo = new PageInfo(approvalList);
            System.out.println("总页数");
            System.out.println(pageInfo.getPages());
            System.out.println("总条数");
            System.out.println(pageInfo.getTotal());
            System.out.println("每页多少条");
            System.out.println(pageInfo.getPageSize());
            System.out.println("起始行");
            System.out.println(pageInfo.getStartRow());
            System.out.println("结束行");
            System.out.println(pageInfo.getEndRow());
            System.out.println("第一页");
            System.out.println(pageInfo.getNavigateFirstPage());
            System.out.println("最后一页");
            System.out.println(pageInfo.getNavigateLastPage());
            System.out.println("是否第一页");
            System.out.println(pageInfo.isIsFirstPage());
            System.out.println("是否有后一页");
            System.out.println(pageInfo.isHasNextPage());

            for(Approval ap : approvalList) {
                System.out.println(ap.toString());
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            sqlSession.rollback();
        } finally {
            //finally中保证关闭sqlSession
            sqlSession.close();
        }

    }

    @Test
    public void selectApprovalBySuidLike() {   //pageSize为全局变量
        //获取sqlSession对象
        String suid = "zn";
        int pageNum = 1;
        String stateQuery = "主";
        int pageSize = 3;
        SqlSession sqlSession = null;
        try {
            Map<String,Object> queryMap = new HashMap<String, Object>();
            queryMap.put("suid",suid);
            queryMap.put("stateQuery",stateQuery);
            sqlSession = MybatisUtils.getSqlSession();
            //执行sql 方式一
            ApprovalMapper mapper = sqlSession.getMapper(ApprovalMapper.class);
            PageHelper.startPage(pageNum,pageSize);
            List<Approval> approvalList = mapper.selectAVApprovalBySuidLike(queryMap);
            PageInfo pageInfo = new PageInfo(approvalList);

            for (Approval approval : approvalList) {
                System.out.println("进度模糊查询到申请："+approval.toString());
            }

            Map<String,Object> map = new HashMap<String, Object>();
            map.put("list",approvalList);
            map.put("pageInfo",pageInfo);



        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            sqlSession.rollback();
        } finally {
            //finally中保证关闭sqlSession
            sqlSession.close();
        }

    }
}
