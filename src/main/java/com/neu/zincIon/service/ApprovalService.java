package com.neu.zincIon.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.neu.zincIon.mapper.ApprovalMapper;
import com.neu.zincIon.pojo.Approval;
import com.neu.zincIon.pojo.DA;
import com.neu.zincIon.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApprovalService {
    private static int pageSize = 3; //分页查询每页多少条数据
    public static boolean addApproval(String suid, String courseName, String cause, String proof, String rejection_reason,String state) {
        //获取sqlSession对象
        SqlSession sqlSession = null;
        try {
            sqlSession = MybatisUtils.getSqlSession();
            //执行sql 方式一
            ApprovalMapper mapper = sqlSession.getMapper(ApprovalMapper.class);

            //检查uid是否重复
            List<Approval> approvalList = mapper.getApprovalList();
            for (Approval approval : approvalList) {
                if(suid.equals(approval.getSuid()) && courseName.equals(approval.getCourseName())) {
                    //当添加的suid和courseName与数据库中有重复时且状态非被驳回且已确认的话，视作重复申请
                    System.out.println("申请提交重复");
                    return false;

                }
            }

            int res = mapper.addApproval(new Approval(suid, courseName, cause, proof, rejection_reason,state));
            if (res > 0) {
                System.out.println("申请提交成功");
                //记得提交事务,放在return之前
                sqlSession.commit();
                return true;
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            sqlSession.rollback();
        } finally {
            //finally中保证关闭sqlSession
            sqlSession.close();
        }

        return false;
    }

    //根据uid查询其名下的申请
    public static Map<String,Object> selectApprovalBySuid(String suid, int pageNum) {   //pageSize为全局变量
        //获取sqlSession对象
        SqlSession sqlSession = null;
        try {
            sqlSession = MybatisUtils.getSqlSession();
            //执行sql 方式一
            ApprovalMapper mapper = sqlSession.getMapper(ApprovalMapper.class);
            PageHelper.startPage(pageNum,pageSize);
            List<Approval> approvalList = mapper.selectAVApprovalBySuid(suid);
            PageInfo pageInfo = new PageInfo(approvalList);

            for (Approval approval : approvalList) {
                System.out.println("进度查询到申请："+approval.toString());
            }

            Map<String,Object> map = new HashMap<String, Object>();
            map.put("list",approvalList);
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

    //根据uid和stateQuery模糊查询其名下的申请
    public static Map<String,Object> selectApprovalBySuidLike(String suid, int pageNum,String stateQuery) {   //pageSize为全局变量
        //获取sqlSession对象
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

    //根据uid查询其名下的完成的被同意的申请
    public static Map<String,Object> selectCAGApplicationBySuid(String suid, int pageNum) {
        //获取sqlSession对象
        SqlSession sqlSession = null;
        try {
            sqlSession = MybatisUtils.getSqlSession();
            //执行sql 方式一
            ApprovalMapper mapper = sqlSession.getMapper(ApprovalMapper.class);
            PageHelper.startPage(pageNum,pageSize);
            List<Approval> approvalList = mapper.selectCAGApprovalListBySuid(suid);
            PageInfo pageInfo = new PageInfo(approvalList);
            for (Approval approval : approvalList) {
                System.out.println("完成的被同意的申请："+approval.toString());
            }
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("list",approvalList);
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

    //根据uid模糊查询其名下的完成的被同意的申请
    public static Map<String,Object> selectCAGApplicationBySuidLike(String suid, int pageNum,String stateQuery) {
        //获取sqlSession对象
        SqlSession sqlSession = null;
        try {
            Map<String,Object> queryMap = new HashMap<String, Object>();
            queryMap.put("suid",suid);
            queryMap.put("stateQuery",stateQuery);
            sqlSession = MybatisUtils.getSqlSession();
            //执行sql 方式一
            ApprovalMapper mapper = sqlSession.getMapper(ApprovalMapper.class);
            PageHelper.startPage(pageNum,pageSize);
            List<Approval> approvalList = mapper.selectCAGApprovalListBySuidLike(queryMap);
            PageInfo pageInfo = new PageInfo(approvalList);
            for (Approval approval : approvalList) {
                System.out.println("完成的被同意的申请："+approval.toString());
            }
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("list",approvalList);
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

    //根据uid查询其名下的完成的被驳回的申请
    public static Map<String,Object> selectCRJApplicationBySuid(String suid, int pageNum) {
        //获取sqlSession对象
        SqlSession sqlSession = null;
        try {
            sqlSession = MybatisUtils.getSqlSession();
            //执行sql 方式一
            ApprovalMapper mapper = sqlSession.getMapper(ApprovalMapper.class);
            PageHelper.startPage(pageNum,pageSize);
            List<Approval> approvalList = mapper.selectCRJApprovalListBySuid(suid);
            PageInfo pageInfo = new PageInfo(approvalList);
            for (Approval approval : approvalList) {
                System.out.println("完成的被驳回的申请："+approval.toString());
            }
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("list",approvalList);
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

    //根据uid模糊查询其名下的完成的被驳回的申请
    public static Map<String,Object> selectCRJApplicationBySuidLike(String suid, int pageNum,String stateQuery) {
        //获取sqlSession对象
        SqlSession sqlSession = null;
        try {
            Map<String,Object> queryMap = new HashMap<String, Object>();
            queryMap.put("suid",suid);
            queryMap.put("stateQuery",stateQuery);
            sqlSession = MybatisUtils.getSqlSession();
            //执行sql 方式一
            ApprovalMapper mapper = sqlSession.getMapper(ApprovalMapper.class);
            PageHelper.startPage(pageNum,pageSize);
            List<Approval> approvalList = mapper.selectCRJApprovalListBySuidLike(queryMap);
            PageInfo pageInfo = new PageInfo(approvalList);
            for (Approval approval : approvalList) {
                System.out.println("完成的被驳回的申请："+approval.toString());
            }
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("list",approvalList);
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

    //根据uid查询其名下的完成的申请和未完成的申请
    public static List<Approval> selectAllApplicationBySuid(String suid) {
        //获取sqlSession对象
        SqlSession sqlSession = null;
        try {
            sqlSession = MybatisUtils.getSqlSession();
            //执行sql 方式一
            ApprovalMapper mapper = sqlSession.getMapper(ApprovalMapper.class);
            List<Approval> approvalList = mapper.selectApprovalBySuid(suid);


            return  approvalList;

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            sqlSession.rollback();
        } finally {
            //finally中保证关闭sqlSession
            sqlSession.close();
        }
        return null;
    }

    //学生查询其名下的申请,不包括已完成的申请
    public static List<String> getApprovalNameList(String suid) {
        //获取sqlSession对象
        SqlSession sqlSession = null;
        try {
            sqlSession = MybatisUtils.getSqlSession();
            //执行sql 方式一
            ApprovalMapper mapper = sqlSession.getMapper(ApprovalMapper.class);
            List<Approval> approvalList = mapper.selectApprovalBySuid(suid);
            List<String> nameList = new ArrayList<String>();
            for (Approval approval : approvalList) {
                if(!(approval.getState().equals("结束-审批成功")) && !(approval.getState().equals("结束-申请驳回"))) {
                    nameList.add(approval.getCourseName());
                    System.out.println("acName:" + approval.getCourseName());
                }
            }

            return  nameList;

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            sqlSession.rollback();
        } finally {
            //finally中保证关闭sqlSession
            sqlSession.close();
        }
        return null;
    }

    //学生查询其名下的申请的名字，包括已完成的申请
    public static List<String> getApprovalNameListWithCAC(String suid) {
        //获取sqlSession对象
        SqlSession sqlSession = null;
        try {
            sqlSession = MybatisUtils.getSqlSession();
            //执行sql 方式一
            ApprovalMapper mapper = sqlSession.getMapper(ApprovalMapper.class);
            List<Approval> approvalList = mapper.selectApprovalBySuid(suid);
            List<String> nameList = new ArrayList<String>();
            for (Approval approval : approvalList) {
                nameList.add(approval.getCourseName());
                System.out.println("acWithCacName:" + approval.getCourseName());
            }

            return  nameList;

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            sqlSession.rollback();
        } finally {
            //finally中保证关闭sqlSession
            sqlSession.close();
        }
        return null;
    }


    public static void deleteApproval(String suid, String courseName) {
        //获取sqlSession对象
        SqlSession sqlSession = null;
        try {
            sqlSession = MybatisUtils.getSqlSession();
            //执行sql 方式一
            ApprovalMapper mapper = sqlSession.getMapper(ApprovalMapper.class);
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("suid",suid);
            map.put("courseName",courseName);
            int res = mapper.deleteApprovalById(map);

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

    //修改申请
    public static boolean updateApproval(Approval approval) {
        //获取sqlSession对象
        SqlSession sqlSession = null;
        try {
            sqlSession = MybatisUtils.getSqlSession();
            //执行sql 方式一
            ApprovalMapper mapper = sqlSession.getMapper(ApprovalMapper.class);

            int res = mapper.updateApproval(approval);

            if (res > 0) {
                System.out.println("更新成功");
                //记得提交事务
                sqlSession.commit();
                return true;
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            sqlSession.rollback();
        } finally {
            //finally中保证关闭sqlSession
            sqlSession.close();
        }
        return false;
    }


    //查询所有该主讲教师名下待审批的项目state=0or1，用于同意和驳回中的选项，包含approval主键suid，courseName
    public static List<String> getLTApprovalNameList(String ltuid) {
        //获取sqlSession对象
        SqlSession sqlSession = null;
        try {
            sqlSession = MybatisUtils.getSqlSession();
            //执行sql 方式一
            ApprovalMapper mapper = sqlSession.getMapper(ApprovalMapper.class);
            List<String> leadTApprovalNameList = mapper.selectApprovalNameByLTuid(ltuid);
            for (String s : leadTApprovalNameList) {
                System.out.println(s);
            }

            return  leadTApprovalNameList;

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            sqlSession.rollback();
        } finally {
            //finally中保证关闭sqlSession
            sqlSession.close();
        }
        return null;

    }


    //查询所有该主讲教师名下待审批的项目state=0or1
    public static Map<String, Object> selectApprovalByLTuid(String ltuid, int pageNum) {
        //获取sqlSession对象
        SqlSession sqlSession = null;
        try {
            sqlSession = MybatisUtils.getSqlSession();
            //执行sql 方式一
            ApprovalMapper mapper = sqlSession.getMapper(ApprovalMapper.class);
            PageHelper.startPage(pageNum,pageSize);
            List<Approval> approvalList = mapper.selectApprovalByLTuid(ltuid);
            PageInfo pageInfo = new PageInfo(approvalList);
            for (Approval approval : approvalList) {
                System.out.println("ltuid查出的approval： " + approval.toString());

                //看有无动态审批
                if(CourseService.getCourseByName(approval.getCourseName()).getLtuid().equals("DynamicApproval")) {
                    System.out.println("进入动态审批判断");
                    System.out.println("lt:"+approval.getLt());
                    //看ap中lt的序号
                    //lt为-1，则比较第一个，不为-1则比较lt与order
                    List<DA> daltListByCourseName = DynamicApprovalService.getDALTListByCourseName(approval.getCourseName());
                    if(approval.getLt()== -1) {
                        System.out.println("进入approval.getLt()== -1" );
                        if(!(ltuid.equals(daltListByCourseName.get(0).getTuid()))) {
                            System.out.println("");
                            System.out.println("进入不等：lt:" + ltuid + " dalt:" + daltListByCourseName.get(0).getTuid());
                            //第一个审批人非此主讲教师，移除此申请
                            approvalList.remove(approval);
                            System.out.println("移除ap：" + approval.toString());
                            //修改pageInfo
                            pageInfo.setSize(pageInfo.getSize()-1);
                            pageInfo.setTotal(pageInfo.getTotal()-1);
                        } else {
                            System.out.println("进入首位相等" );
                            System.out.println("修改前的ap" + approval.toString());
                            //第一个审批人为此主讲教师，修改ap中lt为本教师的order值
                            approval.setLt(daltListByCourseName.get(0).getOrder());
                            System.out.println("修改后：" + approval.toString());
                            ApprovalService.updateApproval(approval);
                        }
                    } else {
                        //lt不为-1，为相应主讲的order
                        for(DA da:daltListByCourseName) {
                            if(da.getOrder() == approval.getLt()) {
                                //找到此审批现在应该被谁审批
                                System.out.println("现在应该审批的主讲：" + da.getTuid());
                                System.out.println("此主讲：" + ltuid);
                                if(!(da.getTuid().equals(ltuid))) {
                                    //比较两者uid不等则移除此申请
                                    approvalList.remove(approval);
                                    System.out.println("移除ap：" + approval.toString());
                                    //修改pageInfo
                                    pageInfo.setSize(pageInfo.getSize()-1);
                                    pageInfo.setTotal(pageInfo.getTotal()-1);
                                }
                            }
                        }
                    }
                }
                if (approvalList.size() == 0) {
                    break;
                }
            }
            Map<String,Object> map = new HashMap<String, Object>();
            if(approvalList == null) {
                System.out.println("approvalList is null");
            } else {
                System.out.println("size:");
                System.out.println(approvalList.size());
            }
            map.put("list",approvalList);
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

    //模糊查询所有该主讲教师名下待审批的项目state=0or1
    public static Map<String, Object> selectApprovalByLTuidLike(String ltuid, int pageNum,String suidQuery,String courseQuery,String stateQuery) {
        //获取sqlSession对象
        SqlSession sqlSession = null;
        try {
            Map<String,Object> queryMap = new HashMap<String, Object>();
            queryMap.put("ltuid",ltuid);
            queryMap.put("suidQuery",suidQuery);
            queryMap.put("courseQuery",courseQuery);
            queryMap.put("stateQuery",stateQuery);
            sqlSession = MybatisUtils.getSqlSession();
            //执行sql 方式一
            ApprovalMapper mapper = sqlSession.getMapper(ApprovalMapper.class);
            PageHelper.startPage(pageNum,pageSize);
            List<Approval> approvalList = mapper.selectApprovalByLTuidLike(queryMap);
            PageInfo pageInfo = new PageInfo(approvalList);
            for (Approval approval : approvalList) {
                System.out.println("ltuid查出的approval： " + approval.toString());


                //看有无动态审批
                if(CourseService.getCourseByName(approval.getCourseName()).getLtuid().equals("DynamicApproval")) {
                    System.out.println("进入动态审批判断");
                    System.out.println("lt:"+approval.getLt());
                    //看ap中lt的序号
                    //lt为-1，则比较第一个，不为-1则比较lt与order
                    List<DA> daltListByCourseName = DynamicApprovalService.getDALTListByCourseName(approval.getCourseName());
                    if(approval.getLt()== -1) {
                        System.out.println("进入approval.getLt()== -1" );
                        if(!(ltuid.equals(daltListByCourseName.get(0).getTuid()))) {
                            System.out.println("");
                            System.out.println("进入不等：lt:" + ltuid + " dalt:" + daltListByCourseName.get(0).getTuid());
                            //第一个审批人非此主讲教师，移除此申请
                            approvalList.remove(approval);
                            System.out.println("移除ap：" + approval.toString());
                            //修改pageInfo
                            pageInfo.setSize(pageInfo.getSize()-1);
                            pageInfo.setTotal(pageInfo.getTotal()-1);
                        } else {
                            System.out.println("进入首位相等" );
                            System.out.println("修改前的ap" + approval.toString());
                            //第一个审批人为此主讲教师，修改ap中lt为本教师的order值
                            approval.setLt(daltListByCourseName.get(0).getOrder());
                            System.out.println("修改后：" + approval.toString());
                            ApprovalService.updateApproval(approval);
                        }
                    } else {
                        //lt不为-1，为相应主讲的order
                        for(DA da:daltListByCourseName) {
                            if(da.getOrder() == approval.getLt()) {
                                //找到此审批现在应该被谁审批
                                System.out.println("现在应该审批的主讲：" + da.getTuid());
                                System.out.println("此主讲：" + ltuid);
                                if(!(da.getTuid().equals(ltuid))) {
                                    //比较两者uid不等则移除此申请
                                    approvalList.remove(approval);
                                    System.out.println("移除ap：" + approval.toString());
                                    //修改pageInfo
                                    pageInfo.setSize(pageInfo.getSize()-1);
                                    pageInfo.setTotal(pageInfo.getTotal()-1);
                                }
                            }
                        }
                    }
                }
                if (approvalList.size() == 0) {
                    break;
                }
            }
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("list",approvalList);
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

    public static Approval getApproval(String suid,String courseName) {
        //获取sqlSession对象
        SqlSession sqlSession = null;
        try {
            sqlSession = MybatisUtils.getSqlSession();
            //执行sql 方式一
            ApprovalMapper mapper = sqlSession.getMapper(ApprovalMapper.class);
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("suid",suid);
            map.put("courseName",courseName);
            Approval ap = mapper.getApproval(map);

            System.out.println(ap.toString());


            return  ap;

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            sqlSession.rollback();
        } finally {
            //finally中保证关闭sqlSession
            sqlSession.close();
        }
        return null;
    }



    //查询所有该主管教师名下待审批的项目state=0or1，用于同意和驳回中的选项，包含approval主键suid，courseName
    public static List<String> getSTApprovalNameList(String stuid) {
        //获取sqlSession对象
        SqlSession sqlSession = null;
        try {
            sqlSession = MybatisUtils.getSqlSession();
            //执行sql 方式一
            ApprovalMapper mapper = sqlSession.getMapper(ApprovalMapper.class);
            List<String> leadTApprovalNameList = mapper.selectApprovalNameBySTuid(stuid);
            for (String s : leadTApprovalNameList) {
                System.out.println(s);
            }

            return  leadTApprovalNameList;

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            sqlSession.rollback();
        } finally {
            //finally中保证关闭sqlSession
            sqlSession.close();
        }
        return null;

    }


    //查询所有该主管教师名下待审批的项目state=0or1
    public static Map<String, Object> selectApprovalBySTuid(String stuid, int pageNum) {
        //获取sqlSession对象
        SqlSession sqlSession = null;
        try {
            sqlSession = MybatisUtils.getSqlSession();
            //执行sql 方式一
            ApprovalMapper mapper = sqlSession.getMapper(ApprovalMapper.class);
            PageHelper.startPage(pageNum,pageSize);
            List<Approval> approvalList = mapper.selectApprovalBySTuid(stuid);
            PageInfo pageInfo = new PageInfo(approvalList);
            for (Approval approval : approvalList) {
                System.out.println("stuid查出的approval： " + approval.toString());


                //看有无动态审批
                if(CourseService.getCourseByName(approval.getCourseName()).getStuid().equals("DynamicApproval")) {
                    System.out.println("进入动态审批判断");
                    System.out.println("st:"+approval.getSt());
                    //看ap中lt的序号
                    //lt为-1，则比较第一个，不为-1则比较lt与order
                    List<DA> dastListByCourseName = DynamicApprovalService.getDASTListByCourseName(approval.getCourseName());
                    if(approval.getSt()== -1) {
                        System.out.println("进入approval.getSt()== -1" );
                        if(!(stuid.equals(dastListByCourseName.get(0).getTuid()))) {
                            System.out.println("");
                            System.out.println("进入不等：st:" + stuid + " dast:" + dastListByCourseName.get(0).getTuid());
                            //第一个审批人非此主讲教师，移除此申请
                            approvalList.remove(approval);
                            System.out.println("移除ap：" + approval.toString());
                            //修改pageInfo
                            pageInfo.setSize(pageInfo.getSize()-1);
                            pageInfo.setTotal(pageInfo.getTotal()-1);
                        } else {
                            System.out.println("进入首位相等" );
                            System.out.println("修改前的ap" + approval.toString());
                            //第一个审批人为此主讲教师，修改ap中lt为本教师的order值
                            approval.setSt(dastListByCourseName.get(0).getOrder());
                            System.out.println("修改后：" + approval.toString());
                            ApprovalService.updateApproval(approval);
                        }
                    } else {
                        //lt不为-1，为相应主讲的order
                        for(DA da:dastListByCourseName) {
                            if(da.getOrder() == approval.getSt()) {
                                //找到此审批现在应该被谁审批
                                System.out.println("现在应该审批的主管：" + da.getTuid());
                                System.out.println("此主讲：" + stuid);
                                if(!(da.getTuid().equals(stuid))) {
                                    //比较两者uid不等则移除此申请
                                    approvalList.remove(approval);
                                    System.out.println("移除ap：" + approval.toString());
                                    //修改pageInfo
                                    pageInfo.setSize(pageInfo.getSize()-1);
                                    pageInfo.setTotal(pageInfo.getTotal()-1);
                                }
                            }
                        }
                    }
                }
                if (approvalList.size() == 0) {
                    break;
                }
            }
            Map<String,Object>map = new HashMap<String, Object>();
            map.put("list",approvalList);
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
    //模糊查询所有该主管教师名下待审批的项目state=0or1
    public static Map<String, Object> selectApprovalBySTuidLike(String stuid, int pageNum,String suidQuery,String courseQuery,String stateQuery) {
        //获取sqlSession对象
        SqlSession sqlSession = null;
        try {
            Map<String,Object> queryMap = new HashMap<String, Object>();
            queryMap.put("stuid",stuid);
            queryMap.put("suidQuery",suidQuery);
            queryMap.put("courseQuery",courseQuery);
            queryMap.put("stateQuery",stateQuery);
            sqlSession = MybatisUtils.getSqlSession();
            //执行sql 方式一
            ApprovalMapper mapper = sqlSession.getMapper(ApprovalMapper.class);
            PageHelper.startPage(pageNum,pageSize);
            List<Approval> approvalList = mapper.selectApprovalBySTuidLike(queryMap);
            PageInfo pageInfo = new PageInfo(approvalList);
            for (Approval approval : approvalList) {
                System.out.println("stuid查出的approval： " + approval.toString());

                //看有无动态审批
                if(CourseService.getCourseByName(approval.getCourseName()).getStuid().equals("DynamicApproval")) {
                    System.out.println("进入动态审批判断");
                    System.out.println("st:"+approval.getSt());
                    //看ap中lt的序号
                    //lt为-1，则比较第一个，不为-1则比较lt与order
                    List<DA> dastListByCourseName = DynamicApprovalService.getDASTListByCourseName(approval.getCourseName());
                    if(approval.getSt()== -1) {
                        System.out.println("进入approval.getSt()== -1" );
                        if(!(stuid.equals(dastListByCourseName.get(0).getTuid()))) {
                            System.out.println("");
                            System.out.println("进入不等：st:" + stuid + " dast:" + dastListByCourseName.get(0).getTuid());
                            //第一个审批人非此主讲教师，移除此申请
                            approvalList.remove(approval);
                            System.out.println("移除ap：" + approval.toString());
                            //修改pageInfo
                            pageInfo.setSize(pageInfo.getSize()-1);
                            pageInfo.setTotal(pageInfo.getTotal()-1);
                        } else {
                            System.out.println("进入首位相等" );
                            System.out.println("修改前的ap" + approval.toString());
                            //第一个审批人为此主讲教师，修改ap中lt为本教师的order值
                            approval.setSt(dastListByCourseName.get(0).getOrder());
                            System.out.println("修改后：" + approval.toString());
                            ApprovalService.updateApproval(approval);
                        }
                    } else {
                        //lt不为-1，为相应主讲的order
                        for(DA da:dastListByCourseName) {
                            if(da.getOrder() == approval.getSt()) {
                                //找到此审批现在应该被谁审批
                                System.out.println("现在应该审批的主管：" + da.getTuid());
                                System.out.println("此主讲：" + stuid);
                                if(!(da.getTuid().equals(stuid))) {
                                    //比较两者uid不等则移除此申请
                                    approvalList.remove(approval);
                                    System.out.println("移除ap：" + approval.toString());
                                    //修改pageInfo
                                    pageInfo.setSize(pageInfo.getSize()-1);
                                    pageInfo.setTotal(pageInfo.getTotal()-1);
                                }
                            }
                        }
                    }
                }
                if (approvalList.size() == 0) {
                    break;
                }
            }
            Map<String,Object>map = new HashMap<String, Object>();
            map.put("list",approvalList);
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

    //查询所有该主管教师名下审批通过项目
    public static Map<String, Object> selectPastAgApBySTuid(String stuid, int pageNum) {
        //获取sqlSession对象
        SqlSession sqlSession = null;
        try {
            sqlSession = MybatisUtils.getSqlSession();
            //执行sql 方式一
            ApprovalMapper mapper = sqlSession.getMapper(ApprovalMapper.class);
            PageHelper.startPage(pageNum,pageSize);
            List<Approval> approvalList = mapper.selectPastAgApBySTuid(stuid);
            PageInfo pageInfo = new PageInfo(approvalList);

            for (Approval approval : approvalList) {
                System.out.println("PastAgApBySTuid： " + approval.toString());

                //课程主管审批的
                if (approval.getState().equals("课程主管教师审批中")){
                    //sql中设置为只有动态审批才将主管加入
                    //看现在正在审批的老师的order是否小于等于本st的order，小于等于则去除ap
                    System.out.println("进入动态审批判断，课程主管教师审批中");
                    List<DA> dastListByCourseName = DynamicApprovalService.getDASTListByCourseName(approval.getCourseName());
                    //找本lt的order
                    for(DA da:dastListByCourseName) {
                        if(da.getTuid().equals(stuid)) {
                            //看现在正在审批的老师的order是否小于等于本st的order，小于等于则去除ap
                            if(da.getOrder() >= approval.getSt() ) {
                                //移除ap，修改pageinfo
                                approvalList.remove(approval);
                                System.out.println("移除ap：" + approval.toString());
                                //修改pageInfo
                                pageInfo.setSize(pageInfo.getSize()-1);
                                pageInfo.setTotal(pageInfo.getTotal()-1);
                                break;
                            }
                        }
                    }
                    if (approvalList.size() == 0) {
                        break;
                    }
                } else if (approval.getState().equals("申请驳回") || approval.getState().equals("结束-申请驳回")) {
                    //主管驳回,去除st的order >= rjst的项
                    System.out.println("进入动态审批判断，申请驳回");
                    List<DA> dastListByCourseName = DynamicApprovalService.getDASTListByCourseName(approval.getCourseName());
                    //找本lt的order
                    for(DA da:dastListByCourseName) {
                        if(da.getTuid().equals(stuid)) {
                            //去除st的order >= rjst的项
                            if(da.getOrder() >= approval.getRjst() ) {
                                //移除ap，修改pageinfo
                                approvalList.remove(approval);
                                System.out.println("移除ap：" + approval.toString());
                                //修改pageInfo
                                pageInfo.setSize(pageInfo.getSize()-1);
                                pageInfo.setTotal(pageInfo.getTotal()-1);
                                break;
                            }
                        }
                    }
                    if (approvalList.size() == 0) {
                        break;
                    }

                }
            }
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("list",approvalList);
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
    //模糊查询所有该主管教师名下审批通过项目
    public static Map<String, Object> selectPastAgApBySTuidLike(String stuid, int pageNum,String suidQuery,String courseQuery,String stateQuery) {
        //获取sqlSession对象
        SqlSession sqlSession = null;
        try {
            Map<String,Object> queryMap = new HashMap<String, Object>();
            queryMap.put("stuid",stuid);
            queryMap.put("suidQuery",suidQuery);
            queryMap.put("courseQuery",courseQuery);
            queryMap.put("stateQuery",stateQuery);
            sqlSession = MybatisUtils.getSqlSession();
            //执行sql 方式一
            ApprovalMapper mapper = sqlSession.getMapper(ApprovalMapper.class);
            PageHelper.startPage(pageNum,pageSize);
            List<Approval> approvalList = mapper.selectPastAgApBySTuidLike(queryMap);
            PageInfo pageInfo = new PageInfo(approvalList);
            for (Approval approval : approvalList) {
                System.out.println("PastAgApBySTuid： " + approval.toString());

                //课程主管审批的
                if (approval.getState().equals("课程主管教师审批中")){
                    //sql中设置为只有动态审批才将主管加入
                    //看现在正在审批的老师的order是否小于等于本st的order，小于等于则去除ap
                    System.out.println("进入动态审批判断，课程主管教师审批中");
                    List<DA> dastListByCourseName = DynamicApprovalService.getDASTListByCourseName(approval.getCourseName());
                    //找本lt的order
                    for(DA da:dastListByCourseName) {
                        if(da.getTuid().equals(stuid)) {
                            //看现在正在审批的老师的order是否小于等于本st的order，小于等于则去除ap
                            if(da.getOrder() >= approval.getSt() ) {
                                //移除ap，修改pageinfo
                                approvalList.remove(approval);
                                System.out.println("移除ap：" + approval.toString());
                                //修改pageInfo
                                pageInfo.setSize(pageInfo.getSize()-1);
                                pageInfo.setTotal(pageInfo.getTotal()-1);
                                break;
                            }
                        }
                    }
                    if (approvalList.size() == 0) {
                        break;
                    }
                }else if (approval.getState().equals("申请驳回") || approval.getState().equals("结束-申请驳回")) {
                    //主管驳回,去除st的order >= rjst的项
                    System.out.println("进入动态审批判断，申请驳回");
                    List<DA> dastListByCourseName = DynamicApprovalService.getDASTListByCourseName(approval.getCourseName());
                    //找本lt的order
                    for(DA da:dastListByCourseName) {
                        if(da.getTuid().equals(stuid)) {
                            //去除st的order >= rjst的项
                            if(da.getOrder() >= approval.getRjst() ) {
                                //移除ap，修改pageinfo
                                approvalList.remove(approval);
                                System.out.println("移除ap：" + approval.toString());
                                //修改pageInfo
                                pageInfo.setSize(pageInfo.getSize()-1);
                                pageInfo.setTotal(pageInfo.getTotal()-1);
                                break;
                            }
                        }
                    }
                    if (approvalList.size() == 0) {
                        break;
                    }

                }
            }
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("list",approvalList);
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

    //查询所有该主讲教师名下审批通过的项目
    public static Map<String, Object> selectPastAgApByLTuid(String ltuid, int pageNum) {
        //获取sqlSession对象
        SqlSession sqlSession = null;
        try {
            sqlSession = MybatisUtils.getSqlSession();
            //执行sql 方式一
            ApprovalMapper mapper = sqlSession.getMapper(ApprovalMapper.class);
            PageHelper.startPage(pageNum,pageSize);
            List<Approval> approvalList = mapper.selectPastAgApByLTuid(ltuid);
            PageInfo pageInfo = new PageInfo(approvalList);

            for (Approval approval : approvalList) {  //修改状态 申请已提交、课程主讲教师审批中、课程主管教师审批中、审批成功、申请驳回。
                System.out.println("PastAgApByLTuid： " + approval.toString());

                //审批驳回的
                if(approval.getState().equals("申请驳回") || approval.getState().equals("结束-申请驳回")) {
                    //看有无动态审批
                    if(CourseService.getCourseByName(approval.getCourseName()).getLtuid().equals("DynamicApproval")) {
                        //动态审批情况
                        System.out.println("进入动态审批判断，申请驳回");

                        List<DA> daltListByCourseName = DynamicApprovalService.getDALTListByCourseName(approval.getCourseName());
                        //找到ltuid的order
                        String[] ss =approval.getRejection_reason().split(" ");
                        if(ss[0].equals("主讲教师")) {
                            System.out.println("进入主讲教师判断");
                            for(DA da:daltListByCourseName) {
                                if(da.getTuid().equals(ltuid)) {
                                    //判断ap中rjlt与ltuid的order的大小，大于等于则去除qp
                                    if(da.getOrder() >= approval.getRjlt() ) {
                                        //移除ap，修改pageinfo
                                        approvalList.remove(approval);
                                        System.out.println("移除ap：" + approval.toString());
                                        //修改pageInfo
                                        pageInfo.setSize(pageInfo.getSize()-1);
                                        pageInfo.setTotal(pageInfo.getTotal()-1);
                                        break;
                                    }
                                }
                            }

                        }

                    }
                    if (approvalList.size() == 0) {
                        break;
                    }
                } else if (approval.getState().equals("课程主讲教师审批中")){
                    //sql中设置为只有动态审批才将主讲加入
                    //看现在正在审批的老师的order是否小于等于本lt的order，小于等于则去除ap
                    System.out.println("进入动态审批判断，课程主讲教师审批中");
                    List<DA> daltListByCourseName = DynamicApprovalService.getDALTListByCourseName(approval.getCourseName());
                    //找本lt的order
                    for(DA da:daltListByCourseName) {
                        if(da.getTuid().equals(ltuid)) {
                            //看现在正在审批的老师的order是否小于等于本lt的order，小于等于则去除ap
                            if(da.getOrder() >= approval.getLt() ) {
                                //移除ap，修改pageinfo
                                approvalList.remove(approval);
                                System.out.println("移除ap：" + approval.toString());
                                //修改pageInfo
                                pageInfo.setSize(pageInfo.getSize()-1);
                                pageInfo.setTotal(pageInfo.getTotal()-1);
                                break;
                            }
                        }
                    }
                    if (approvalList.size() == 0) {
                        break;
                    }
                }
            }


            Map<String,Object> map = new HashMap<String, Object>();
            map.put("list",approvalList);
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
    //模糊查询所有该主讲教师名下审批通过的项目
    public static Map<String, Object> selectPastAgApByLTuidLike(String ltuid, int pageNum,String suidQuery,String courseQuery,String stateQuery) {
        //获取sqlSession对象
        SqlSession sqlSession = null;
        try {
            Map<String,Object> queryMap = new HashMap<String, Object>();
            queryMap.put("ltuid",ltuid);
            queryMap.put("suidQuery",suidQuery);
            queryMap.put("courseQuery",courseQuery);
            queryMap.put("stateQuery",stateQuery);
            sqlSession = MybatisUtils.getSqlSession();
            //执行sql 方式一
            ApprovalMapper mapper = sqlSession.getMapper(ApprovalMapper.class);
            PageHelper.startPage(pageNum,pageSize);
            List<Approval> approvalList = mapper.selectPastAgApByLTuidLike(queryMap);
            PageInfo pageInfo = new PageInfo(approvalList);

            for (Approval approval : approvalList) {  //修改状态 申请已提交、课程主讲教师审批中、课程主管教师审批中、审批成功、申请驳回。
                System.out.println("PastAgApByLTuid： " + approval.toString());

                //审批驳回的
                if(approval.getState().equals("申请驳回") || approval.getState().equals("结束-申请驳回")) {
                    //看有无动态审批
                    if(CourseService.getCourseByName(approval.getCourseName()).getLtuid().equals("DynamicApproval")) {
                        //动态审批情况
                        System.out.println("进入动态审批判断，申请驳回");

                        List<DA> daltListByCourseName = DynamicApprovalService.getDALTListByCourseName(approval.getCourseName());
                        //找到ltuid的order
                        String[] ss =approval.getRejection_reason().split(" ");
                        if(ss[0].equals("主讲教师")) {
                            System.out.println("进入主讲教师判断");
                            for(DA da:daltListByCourseName) {
                                if(da.getTuid().equals(ltuid)) {
                                    //判断ap中rjlt与ltuid的order的大小，大于等于则去除qp
                                    if(da.getOrder() >= approval.getRjlt() ) {
                                        //移除ap，修改pageinfo
                                        approvalList.remove(approval);
                                        System.out.println("移除ap：" + approval.toString());
                                        //修改pageInfo
                                        pageInfo.setSize(pageInfo.getSize()-1);
                                        pageInfo.setTotal(pageInfo.getTotal()-1);
                                        break;
                                    }
                                }
                            }

                        }

                    }
                    if (approvalList.size() == 0) {
                        break;
                    }
                } else if (approval.getState().equals("课程主讲教师审批中")){
                    //sql中设置为只有动态审批才将主讲加入
                    //看现在正在审批的老师的order是否小于等于本lt的order，小于等于则去除ap
                    System.out.println("进入动态审批判断，课程主讲教师审批中");
                    List<DA> daltListByCourseName = DynamicApprovalService.getDALTListByCourseName(approval.getCourseName());
                    //找本lt的order
                    for(DA da:daltListByCourseName) {
                        if(da.getTuid().equals(ltuid)) {
                            //看现在正在审批的老师的order是否小于等于本lt的order，小于等于则去除ap
                            if(da.getOrder() >= approval.getLt() ) {
                                //移除ap，修改pageinfo
                                approvalList.remove(approval);
                                System.out.println("移除ap：" + approval.toString());
                                //修改pageInfo
                                pageInfo.setSize(pageInfo.getSize()-1);
                                pageInfo.setTotal(pageInfo.getTotal()-1);
                                break;
                            }
                        }
                    }
                    if (approvalList.size() == 0) {
                        break;
                    }
                }
            }

            Map<String,Object> map = new HashMap<String, Object>();
            map.put("list",approvalList);
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

    //查询所有该主管教师名下驳回的项目
    public static Map<String, Object> selectPastRjApBySTuid(String stuid, int pageNum) {
        //获取sqlSession对象
        SqlSession sqlSession = null;
        try {
            sqlSession = MybatisUtils.getSqlSession();
            //执行sql 方式一
            ApprovalMapper mapper = sqlSession.getMapper(ApprovalMapper.class);
            PageHelper.startPage(pageNum,pageSize);
            List<Approval> approvalList = mapper.selectPastRjApBySTuid(stuid);
            PageInfo pageInfo = new PageInfo(approvalList);
            for (Approval approval : approvalList) {
                System.out.println("PastRjApBySTuid:" + approval.toString());

                //动态审批情况，去除其中ap的rjst ！= 自己order的项
                //看有无动态审批
                if(CourseService.getCourseByName(approval.getCourseName()).getStuid().equals("DynamicApproval")) {
                    //动态审批情况
                    System.out.println("进入动态审批判断，查找st申请驳回");

                    List<DA> dastListByCourseName = DynamicApprovalService.getDASTListByCourseName(approval.getCourseName());
                    //找到ltuid的order
                    for(DA da:dastListByCourseName) {
                        if(da.getTuid().equals(stuid)) {
                            //去除其中ap的rjst ！= 自己order的项
                            if(da.getOrder() != approval.getRjst() ) {
                                //移除ap，修改pageinfo
                                approvalList.remove(approval);
                                System.out.println("移除ap：" + approval.toString());
                                //修改pageInfo
                                pageInfo.setSize(pageInfo.getSize()-1);
                                pageInfo.setTotal(pageInfo.getTotal()-1);
                                break;
                            }
                        }
                    }
                }
                if (approvalList.size() == 0) {
                    break;
                }

            }
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("list",approvalList);
            map.put("pageInfo",pageInfo);

            return map;

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            sqlSession.rollback();
        } finally {
            //finally中保证关闭sqlSession
            sqlSession.close();
        }
        return null;
    }
    //模糊查询所有该主管教师名下驳回的项目
    public static Map<String, Object> selectPastRjApBySTuidLike(String stuid, int pageNum,String suidQuery,String courseQuery,String stateQuery) {
        //获取sqlSession对象
        SqlSession sqlSession = null;
        try {
            Map<String,Object> queryMap = new HashMap<String, Object>();
            queryMap.put("stuid",stuid);
            queryMap.put("suidQuery",suidQuery);
            queryMap.put("courseQuery",courseQuery);
            queryMap.put("stateQuery",stateQuery);
            sqlSession = MybatisUtils.getSqlSession();
            //执行sql 方式一
            ApprovalMapper mapper = sqlSession.getMapper(ApprovalMapper.class);
            PageHelper.startPage(pageNum,pageSize);
            List<Approval> approvalList = mapper.selectPastRjApBySTuidLike(queryMap);
            PageInfo pageInfo = new PageInfo(approvalList);
            for (Approval approval : approvalList) {
                System.out.println("PastRjApBySTuid:" + approval.toString());

                //动态审批情况，去除其中ap的rjst ！= 自己order的项
                //看有无动态审批
                if(CourseService.getCourseByName(approval.getCourseName()).getStuid().equals("DynamicApproval")) {
                    //动态审批情况
                    System.out.println("进入动态审批判断，查找st申请驳回");

                    List<DA> dastListByCourseName = DynamicApprovalService.getDASTListByCourseName(approval.getCourseName());
                    //找到ltuid的order
                    for(DA da:dastListByCourseName) {
                        if(da.getTuid().equals(stuid)) {
                            //去除其中ap的rjst ！= 自己order的项
                            if(da.getOrder() != approval.getRjst() ) {
                                //移除ap，修改pageinfo
                                approvalList.remove(approval);
                                System.out.println("移除ap：" + approval.toString());
                                //修改pageInfo
                                pageInfo.setSize(pageInfo.getSize()-1);
                                pageInfo.setTotal(pageInfo.getTotal()-1);
                                break;
                            }
                        }
                    }
                }
                if (approvalList.size() == 0) {
                    break;
                }
            }
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("list",approvalList);
            map.put("pageInfo",pageInfo);

            return map;

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            sqlSession.rollback();
        } finally {
            //finally中保证关闭sqlSession
            sqlSession.close();
        }
        return null;
    }


    //查询所有该主讲教师名下驳回的项目
    public static Map<String, Object> selectPastRjApByLTuid(String ltuid, int pageNum) {
        //获取sqlSession对象
        SqlSession sqlSession = null;
        try {
            sqlSession = MybatisUtils.getSqlSession();
            //执行sql 方式一
            ApprovalMapper mapper = sqlSession.getMapper(ApprovalMapper.class);
            PageHelper.startPage(pageNum,pageSize);
            List<Approval> approvalList = mapper.selectPastRjApByLTuid(ltuid);
            PageInfo pageInfo = new PageInfo(approvalList);
            for (Approval approval : approvalList) {
                System.out.println("PastRjApByLTuid:" + approval.toString());

                //动态审批情况，去除其中ap的rjlt ！= 自己order的项
                //看有无动态审批
                if(CourseService.getCourseByName(approval.getCourseName()).getLtuid().equals("DynamicApproval")) {
                    //动态审批情况
                    System.out.println("进入动态审批判断，查找lt申请驳回");

                    List<DA> daltListByCourseName = DynamicApprovalService.getDALTListByCourseName(approval.getCourseName());
                    //找到ltuid的order
                    for(DA da:daltListByCourseName) {
                        if(da.getTuid().equals(ltuid)) {
                            //去除其中ap的rjlt ！= 自己order的项
                            if(da.getOrder() != approval.getRjlt() ) {
                                //移除ap，修改pageinfo
                                approvalList.remove(approval);
                                System.out.println("移除ap：" + approval.toString());
                                //修改pageInfo
                                pageInfo.setSize(pageInfo.getSize()-1);
                                pageInfo.setTotal(pageInfo.getTotal()-1);
                                break;
                            }
                        }
                    }


                }
                if (approvalList.size() == 0) {
                    break;
                }


            }
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("list",approvalList);
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
    //模糊查询所有该主讲教师名下驳回的项目
    public static Map<String, Object> selectPastRjApByLTuidLike(String ltuid, int pageNum,String suidQuery,String courseQuery,String stateQuery) {
        //获取sqlSession对象
        SqlSession sqlSession = null;
        try {
            Map<String,Object> queryMap = new HashMap<String, Object>();
            queryMap.put("ltuid",ltuid);
            queryMap.put("suidQuery",suidQuery);
            queryMap.put("courseQuery",courseQuery);
            queryMap.put("stateQuery",stateQuery);
            sqlSession = MybatisUtils.getSqlSession();
            //执行sql 方式一
            ApprovalMapper mapper = sqlSession.getMapper(ApprovalMapper.class);
            PageHelper.startPage(pageNum,pageSize);
            List<Approval> approvalList = mapper.selectPastRjApByLTuidLike(queryMap);
            PageInfo pageInfo = new PageInfo(approvalList);
            for (Approval approval : approvalList) {
                System.out.println("PastRjApByLTuid:" + approval.toString());
                //动态审批情况，去除其中ap的rjlt ！= 自己order的项
                //看有无动态审批
                if(CourseService.getCourseByName(approval.getCourseName()).getLtuid().equals("DynamicApproval")) {
                    //动态审批情况
                    System.out.println("进入动态审批判断，查找lt申请驳回");
                    List<DA> daltListByCourseName = DynamicApprovalService.getDALTListByCourseName(approval.getCourseName());
                    //找到ltuid的order
                    for(DA da:daltListByCourseName) {
                        if(da.getTuid().equals(ltuid)) {
                            //去除其中ap的rjlt ！= 自己order的项
                            if(da.getOrder() != approval.getRjlt() ) {
                                //移除ap，修改pageinfo
                                approvalList.remove(approval);
                                System.out.println("移除ap：" + approval.toString());
                                //修改pageInfo
                                pageInfo.setSize(pageInfo.getSize()-1);
                                pageInfo.setTotal(pageInfo.getTotal()-1);
                                break;
                            }
                        }
                    }
                }
                if (approvalList.size() == 0) {
                    break;
                }
            }
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("list",approvalList);
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

    //查询该学生下所有申请中状态为驳回或审批成功的申请，用于申请确认选项
    public static List<String> getCompleteACNameList(String suid) {
        //获取sqlSession对象
        SqlSession sqlSession = null;
        try {
            sqlSession = MybatisUtils.getSqlSession();
            //执行sql 方式一
            ApprovalMapper mapper = sqlSession.getMapper(ApprovalMapper.class);
            List<Approval> approvalList = mapper.selectApprovalBySuid(suid);
            List<String> nameList = new ArrayList<String>();
            for (Approval approval : approvalList) {
                if(approval.getState().equals("申请驳回") || approval.getState().equals("审批成功")) {
                    nameList.add(approval.getCourseName());
                    System.out.println("cacName:" + approval.getCourseName());
                }
            }

            return  nameList;

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            sqlSession.rollback();
        } finally {
            //finally中保证关闭sqlSession
            sqlSession.close();
        }
        return null;
    }


    public static Map<String,Object> getApprovalRecordList(int pageNum) {
        //获取sqlSession对象
        SqlSession sqlSession = null;
        try {
            sqlSession = MybatisUtils.getSqlSession();
            //执行sql 方式一
            ApprovalMapper mapper = sqlSession.getMapper(ApprovalMapper.class);
            PageHelper.startPage(pageNum,4);
            List<Approval> approvalList = mapper.getApprovalRecordList();
            PageInfo pageInfo = new PageInfo(approvalList);
            for (Approval approval : approvalList) {
                System.out.println("ApprovalRecord:" + approval.toString());
            }
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("list",approvalList);
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

    //全部已审批通过记录，不分页
    public static List<Approval> getAGApprovalRecordList() {
        //获取sqlSession对象
        SqlSession sqlSession = null;
        try {
            sqlSession = MybatisUtils.getSqlSession();
            //执行sql 方式一
            ApprovalMapper mapper = sqlSession.getMapper(ApprovalMapper.class);
            List<Approval> approvalList = mapper.getAGApprovalRecordList();
            for (Approval approval : approvalList) {
                System.out.println("AGApprovalRecord:" + approval.toString());
            }

            return  approvalList;

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
