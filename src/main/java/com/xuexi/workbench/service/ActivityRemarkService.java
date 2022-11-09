package com.xuexi.workbench.service;

import com.xuexi.workbench.domain.ActivityRemark;

import java.util.List;

public interface ActivityRemarkService {

    List<ActivityRemark> qureyActivityRemarkForDetailByActivityId(String Activityid);

    Integer saveCreateActivityRemark(ActivityRemark activityRemark);

    Integer deleteActivityRemarkById(String id);

    Integer saveEditActivityRemark(ActivityRemark activityRemark);
}
