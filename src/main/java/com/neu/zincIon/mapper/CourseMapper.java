package com.neu.zincIon.mapper;

import com.neu.zincIon.pojo.Course;

import java.util.List;

public interface CourseMapper {
    List<Course> getCourseList();

    Course getCourseById(String courseName);

    int addCourse(Course course);

    int deleteCourseById(String courseName);

    int updateCourse(Course course);
}
