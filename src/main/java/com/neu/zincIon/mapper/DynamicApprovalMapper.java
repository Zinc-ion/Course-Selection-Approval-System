package com.neu.zincIon.mapper;

import com.neu.zincIon.pojo.DA;

import java.util.List;
import java.util.Map;

public interface DynamicApprovalMapper {

    //    Dynamic Approval
    List<String> selectLTuidNameListByCourseName(String courseName);
    List<String> selectSTuidNameListByCourseName(String courseName);

    int addLTuid(Map<String,Object> map);
    int addSTuid(Map<String,Object> map);
    int deleteLTuid(Map<String,Object> map);
    int deleteSTuid(Map<String,Object> map);

    List<DA> getDALTListByCourseName(String courseName);
    List<DA> getDASTListByCourseName(String courseName);
}
