package com.neu.zincIon;

import com.neu.zincIon.mapper.UserMapper;
import com.neu.zincIon.pojo.User;
import com.neu.zincIon.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;


public class UserMapperTestLog4j2 {
    static Logger logger = LogManager.getLogger(UserMapperTestLog4j2.class.getName());


    @Test
    public void hello() {
        logger.entry();   //trace级别的信息，单独列出来是希望你在某个方法或者程序逻辑开始的时候调用，和logger.trace("entry")基本一个意思
        logger.error("Did it again!");   //error级别的信息，参数就是你输出的信息
        logger.info("我是info信息");    //info级别的信息
        logger.debug("我是debug信息");
        logger.warn("我是warn信息");
        logger.fatal("我是fatal信息");
        logger.log(Level.DEBUG, "我是debug信息");   //这个就是制定Level类型的调用：谁闲着没事调用这个，也不一定哦！
        logger.exit();    //和entry()对应的结束方法，和logger.trace("exit");一个意思
        return ;
    }

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
    public void getUserByLimit() {
        SqlSession sqlSession = null;

        try {
            sqlSession = MybatisUtils.getSqlSession();
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            HashMap<String, Integer> map = new HashMap<String, Integer>();
            map.put("startIndex",0);
            map.put("pageSize",2);
            for (User user : mapper.getUserByLimit(map)) {
                System.out.println(user.toString());
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
            sqlSession.rollback();
        } finally {
            sqlSession.close();
        }
    }


}
