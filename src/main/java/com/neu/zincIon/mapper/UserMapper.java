package com.neu.zincIon.mapper;

import com.neu.zincIon.pojo.User;
import com.neu.zincIon.pojo.UserTest;

import java.util.List;
import java.util.Map;

public interface UserMapper {
    //查询全部用户
    List<User> getUserList();

    //根据id查询用户
    User getUserById(String id);

    //insert用户
    int addUser(User user);

    //修改用户
    int updateUser(User user);

    //删除用户
    int deleteUser(String id);

    User getUserById2(Map<String,Object> map);

    List<User> getUserLike(Map<String,Object> map);

    UserTest getUserTestById(String id);

    //分页
    List<User> getUserByLimit(Map<String,Integer> map);
}
