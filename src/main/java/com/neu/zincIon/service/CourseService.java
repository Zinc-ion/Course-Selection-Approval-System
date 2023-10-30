package com.neu.zincIon.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.neu.zincIon.mapper.CourseMapper;
import com.neu.zincIon.pojo.Course;
import com.neu.zincIon.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseService {
    private static int pageSize = 4;

    public static Course getCourseByName(String courseName) {
        //获取sqlSession对象
        SqlSession sqlSession = null;
        try {
            sqlSession = MybatisUtils.getSqlSession();
            //执行sql 方式一
            CourseMapper mapper = sqlSession.getMapper(CourseMapper.class);
            Course course = mapper.getCourseById(courseName);

            return course;

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            sqlSession.rollback();
        } finally {
            //finally中保证关闭sqlSession
            sqlSession.close();
        }

        return null;
    }

    public static List<Course> getCourseList() {
        //获取sqlSession对象
        SqlSession sqlSession = null;
        try {
            sqlSession = MybatisUtils.getSqlSession();
            //执行sql 方式一
            CourseMapper mapper = sqlSession.getMapper(CourseMapper.class);
            List<Course> courseList = mapper.getCourseList();
            for (Course course : courseList) {
                System.out.println(course.toString());
            }
            return courseList;

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            sqlSession.rollback();
        } finally {
            //finally中保证关闭sqlSession
            sqlSession.close();
        }

        return null;
    }
    //分页查询
    public static Map<String, Object> getCourseListByPage(int pageNum) {
        //获取sqlSession对象
        SqlSession sqlSession = null;
        try {
            sqlSession = MybatisUtils.getSqlSession();
            //执行sql 方式一
            CourseMapper mapper = sqlSession.getMapper(CourseMapper.class);
            PageHelper.startPage(pageNum,pageSize);
            List<Course> courseList = mapper.getCourseList();
            PageInfo pageInfo = new PageInfo(courseList);
            for (Course course : courseList) {
                System.out.println(course.toString());
            }
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("list",courseList);
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


    //获取课名和上课时间
    public static List<String> getCourseListNameAndTime() {
        //获取sqlSession对象
        SqlSession sqlSession = null;
        try {
            sqlSession = MybatisUtils.getSqlSession();
            //执行sql 方式一
            CourseMapper mapper = sqlSession.getMapper(CourseMapper.class);
            List<Course> courseList = mapper.getCourseList();
            List<String> courseName = new ArrayList<String>();
            for (Course course : courseList) {
                courseName.add(course.getNameAndTime());
                System.out.println(course.toString());
            }
            return courseName;

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            sqlSession.rollback();
        } finally {
            //finally中保证关闭sqlSession
            sqlSession.close();
        }

        return null;
    }

    //获取课名
    public static List<String> getCourseListName() {
        //获取sqlSession对象
        SqlSession sqlSession = null;
        try {
            sqlSession = MybatisUtils.getSqlSession();
            //执行sql 方式一
            CourseMapper mapper = sqlSession.getMapper(CourseMapper.class);
            List<Course> courseList = mapper.getCourseList();
            List<String> courseName = new ArrayList<String>();
            for (Course course : courseList) {
                courseName.add(course.getCourseName());
                System.out.println(course.toString());
            }
            return courseName;

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            sqlSession.rollback();
        } finally {
            //finally中保证关闭sqlSession
            sqlSession.close();
        }

        return null;
    }

    public static boolean addCourse(Course course) {
        SqlSession sqlSession = null;
        try {
            sqlSession = MybatisUtils.getSqlSession();
            CourseMapper mapper = sqlSession.getMapper(CourseMapper.class);
            int res = mapper.addCourse(course);
            if(res > 0) {
                sqlSession.commit();
                return true;
            } else {
                return false;
            }
        }catch (Exception ex) {
            System.err.println(ex.getMessage());
            sqlSession.rollback();
        } finally {
            //finally中保证关闭sqlSession
            sqlSession.close();
        }

        return false;
    }

    public static boolean upDateCourse(Course course) {
        SqlSession sqlSession = null;
        try {
            sqlSession = MybatisUtils.getSqlSession();
            CourseMapper mapper = sqlSession.getMapper(CourseMapper.class);
            int res = mapper.updateCourse(course);
            if(res > 0) {
                sqlSession.commit();
                return true;
            } else {
                return false;
            }
        }catch (Exception ex) {
            System.err.println(ex.getMessage());
            sqlSession.rollback();
        } finally {
            //finally中保证关闭sqlSession
            sqlSession.close();
        }

        return false;
    }

    public static boolean deleteCourse(String courseName) {
        SqlSession sqlSession = null;
        try {
            sqlSession = MybatisUtils.getSqlSession();
            CourseMapper mapper = sqlSession.getMapper(CourseMapper.class);
            int res = mapper.deleteCourseById(courseName);
            if(res > 0) {
                sqlSession.commit();
                return true;
            } else {
                return false;
            }
        }catch (Exception ex) {
            System.err.println(ex.getMessage());
            sqlSession.rollback();
        } finally {
            //finally中保证关闭sqlSession
            sqlSession.close();
        }

        return false;
    }
}
