package com.neu.zincIon.service;

import com.neu.zincIon.mapper.DynamicApprovalMapper;
import com.neu.zincIon.pojo.Course;
import com.neu.zincIon.pojo.DA;
import com.neu.zincIon.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DynamicApprovalService {
    public static Map<String, Object> getLtuidAndStuidByCourseName(String courseName) {
        //获取sqlSession对象
        SqlSession sqlSession = null;
        try {
            sqlSession = MybatisUtils.getSqlSession();
            //执行sql 方式一
           DynamicApprovalMapper mapper = sqlSession.getMapper(DynamicApprovalMapper.class);
            Course course = CourseService.getCourseByName(courseName);
            List<String> ltuidList = new ArrayList<String>();
            List<String> stuidList = new ArrayList<String>();
            if(course.getLtuid().equals("DynamicApproval")) {
                ltuidList = mapper.selectLTuidNameListByCourseName(courseName);
                stuidList = mapper.selectSTuidNameListByCourseName(courseName);
            }


            for(String name: ltuidList) {
                System.out.println("ltuidList:" + name);
            }
            for(String name: stuidList) {
                System.out.println("stuidList:" + name);
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

    public static void addLTuid(String courseName,String ltuid) {
        //获取sqlSession对象
        SqlSession sqlSession = null;
        try {
            sqlSession = MybatisUtils.getSqlSession();
            //执行sql 方式一
            DynamicApprovalMapper mapper = sqlSession.getMapper(DynamicApprovalMapper.class);
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("courseName",courseName);
            map.put("ltuid",ltuid);
            int res = mapper.addLTuid(map);
            if(res > 0) {
                sqlSession.commit();
                System.out.println("添加成功");
            } else {
                System.out.println("添加失败");
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            sqlSession.rollback();
        } finally {
            //finally中保证关闭sqlSession
            sqlSession.close();
        }
    }
    public static void addSTuid(String courseName,String stuid) {
        //获取sqlSession对象
        SqlSession sqlSession = null;
        try {
            sqlSession = MybatisUtils.getSqlSession();
            //执行sql 方式一
            DynamicApprovalMapper mapper = sqlSession.getMapper(DynamicApprovalMapper.class);
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("courseName",courseName);
            map.put("stuid",stuid);
            int res = mapper.addSTuid(map);
            if(res > 0) {
                sqlSession.commit();
                System.out.println("添加成功");
            } else {
                System.out.println("添加失败");
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            sqlSession.rollback();
        } finally {
            //finally中保证关闭sqlSession
            sqlSession.close();
        }
    }
    public static void deleteLTuid(String courseName,String ltuid) {
        //获取sqlSession对象
        SqlSession sqlSession = null;
        try {
            sqlSession = MybatisUtils.getSqlSession();
            //执行sql 方式一
            DynamicApprovalMapper mapper = sqlSession.getMapper(DynamicApprovalMapper.class);
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("courseName",courseName);
            map.put("ltuid",ltuid);
            int res = mapper.deleteLTuid(map);
            if(res > 0) {
                sqlSession.commit();
                System.out.println("删除成功");
            } else {
                System.out.println("删除失败");
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            sqlSession.rollback();
        } finally {
            //finally中保证关闭sqlSession
            sqlSession.close();
        }
    }
    public static void deleteSTuid(String courseName,String stuid) {
        //获取sqlSession对象
        SqlSession sqlSession = null;
        try {
            sqlSession = MybatisUtils.getSqlSession();
            //执行sql 方式一
            DynamicApprovalMapper mapper = sqlSession.getMapper(DynamicApprovalMapper.class);
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("courseName",courseName);
            map.put("stuid",stuid);
            int res = mapper.deleteSTuid(map);
            if(res > 0) {
                sqlSession.commit();
                System.out.println("删除成功");
            } else {
                System.out.println("删除失败");
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            sqlSession.rollback();
        } finally {
            //finally中保证关闭sqlSession
            sqlSession.close();
        }
    }

    public static List<String> getLTListByCourseName(String courseName) {
        //获取sqlSession对象
        SqlSession sqlSession = null;
        try {
            sqlSession = MybatisUtils.getSqlSession();
            //执行sql 方式一
            DynamicApprovalMapper mapper = sqlSession.getMapper(DynamicApprovalMapper.class);

            List<String> list = mapper.selectLTuidNameListByCourseName(courseName);
            return list;
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            sqlSession.rollback();
        } finally {
            //finally中保证关闭sqlSession
            sqlSession.close();
        }
        return null;
    }
    public static List<String> getSTListByCourseName(String courseName) {
        //获取sqlSession对象
        SqlSession sqlSession = null;
        try {
            sqlSession = MybatisUtils.getSqlSession();
            //执行sql 方式一
            DynamicApprovalMapper mapper = sqlSession.getMapper(DynamicApprovalMapper.class);

            List<String> list = mapper.selectSTuidNameListByCourseName(courseName);
            return list;
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            sqlSession.rollback();
        } finally {
            //finally中保证关闭sqlSession
            sqlSession.close();
        }
        return null;
    }

    public static List<DA> getDALTListByCourseName(String courseName) {
        //获取sqlSession对象
        SqlSession sqlSession = null;
        try {
            sqlSession = MybatisUtils.getSqlSession();
            //执行sql 方式一
            DynamicApprovalMapper mapper = sqlSession.getMapper(DynamicApprovalMapper.class);

            List<DA> list = mapper.getDALTListByCourseName(courseName);
            return list;
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            sqlSession.rollback();
        } finally {
            //finally中保证关闭sqlSession
            sqlSession.close();
        }
        return null;
    }
    public static List<DA> getDASTListByCourseName(String courseName) {
        //获取sqlSession对象
        SqlSession sqlSession = null;
        try {
            sqlSession = MybatisUtils.getSqlSession();
            //执行sql 方式一
            DynamicApprovalMapper mapper = sqlSession.getMapper(DynamicApprovalMapper.class);

            List<DA> list = mapper.getDASTListByCourseName(courseName);
            return list;
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            sqlSession.rollback();
        } finally {
            //finally中保证关闭sqlSession
            sqlSession.close();
        }
        return null;
    }



}
