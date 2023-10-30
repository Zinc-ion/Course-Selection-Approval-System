package com.neu.zincIon.mapper;

import com.neu.zincIon.pojo.Approval;

import java.util.List;
import java.util.Map;

public interface ApprovalMapper {
    List<Approval> getApprovalList();

    List<Approval> getApprovalRecordList();
    List<Approval> getAGApprovalRecordList();

    Approval getApprovalById(Map<String,Object> map); //用map存多参数

    int addApproval(Approval Approval);

    int deleteApprovalById(Map<String,Object> map);

    int updateApproval(Approval Approval);

    List<Approval> selectApprovalBySuid(String suid);

    List<Approval> selectAVApprovalBySuid(String suid); //查询未完成的申请

    List<Approval> selectAVApprovalBySuidLike(Map<String,Object> map); //模糊查询未完成的申请

    List<Approval> selectCAGApprovalListBySuid(String suid); //查询完成被同意的申请
    List<Approval> selectCAGApprovalListBySuidLike(Map<String,Object> map); //模糊查询完成被同意的申请

    List<Approval> selectCRJApprovalListBySuid(String suid); //查询完成被驳回的申请
    List<Approval> selectCRJApprovalListBySuidLike(Map<String,Object> map); //查询完成被驳回的申请

    List<String> selectApprovalNameByLTuid(String ltuid); //查看该主讲教师名下待审批的ap

    List<Approval> selectApprovalByLTuid(String ltuid);
    List<Approval> selectApprovalByLTuidLike(Map<String,Object> map);

    Approval getApproval(Map<String,Object> map);

    List<String> selectApprovalNameBySTuid(String stuid); //查看该主管教师名下待审批的ap

    List<Approval> selectApprovalBySTuid(String stuid);
    List<Approval> selectApprovalBySTuidLike(Map<String,Object> map);
    List<Approval> selectPastAgApByLTuid(String ltuid); //查看该主管教师名下待审批的ap
    List<Approval> selectPastAgApByLTuidLike(Map<String,Object> map);
    List<Approval> selectPastAgApBySTuid(String stuid); //查看该主管教师名下待审批的ap
    List<Approval> selectPastAgApBySTuidLike(Map<String,Object> map);
    List<Approval> selectPastRjApByLTuid(String ltuid); //查看该主管教师名下驳回的ap
    List<Approval> selectPastRjApByLTuidLike(Map<String,Object> map);
    List<Approval> selectPastRjApBySTuid(String stuid);//查看该主管教师名下驳回的ap
    List<Approval> selectPastRjApBySTuidLike(Map<String,Object> map);




}
