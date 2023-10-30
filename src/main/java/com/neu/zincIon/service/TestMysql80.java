package com.neu.zincIon.service;


import java.sql.*;

public class TestMysql80 {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");

        // 使用我自己的数据库 test
        String url = "jdbc:mysql://localhost:3306/university_schema?useUnicode=true&characterEncoding=UTF-8&userSSL=false&serverTimezone=GMT%2B8";

        // 数据库的用户名和密码
        String username = "root";
        String password = "20021110xJmS";


        Connection connection = DriverManager.getConnection(url, username, password);


        Statement statement = connection.createStatement();

        // SQL语句，使用我自己的test数据库下的 boss 表
        String sql = "SELECT * FROM student";

        ResultSet resultSet = statement.executeQuery(sql);

        // 需要与自己的数据库里的表结构相对应
        while (resultSet.next()){
            System.out.println("ID = " + resultSet.getObject("ID"));
            System.out.println("name = " + resultSet.getObject("name"));
            System.out.println("dept_name = " + resultSet.getObject("dept_name"));
            System.out.println("tot_cred = " + resultSet.getObject("tot_cred"));
        }

        resultSet.close();
        statement.close();
        connection.close();
    }
}