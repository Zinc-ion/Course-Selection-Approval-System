package com.neu.zincIon.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/*
    全过程步骤：
    1.在resources文件夹下写mybatis-config.xml
    2.写MybatisUtils工具类
    3.写实体类user
    4.写接口userMapper
    5.写UserMapper.xml
    6.写test
    7.记得在pom.xml中的build中配置resource，来防止我们资源导出失败 （因为我们把UserMapper.xml没有写在maven规定的配置文件该在的resources文件夹下）
 */
//sqlSessionFactory-->sqlSession
public class MybatisUtils {
    private static SqlSessionFactory sqlSessionFactory;

    static {
        try {
            //使用mybatis获取sqlSessionFactory对象
            //资源路径只用写文件名，该文件一定要在resources文件下
            String resource = "mybatis-config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //既然有了 SqlSessionFactory，顾名思义，我们可以从中获得 SqlSession 的实例。SqlSession 提供了在数据库执行 SQL 命令所需的所有方法。
    public static SqlSession getSqlSession() {
        return sqlSessionFactory.openSession();

    }

}
