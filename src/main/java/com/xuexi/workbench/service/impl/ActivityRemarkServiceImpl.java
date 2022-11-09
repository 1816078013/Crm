package com.xuexi.workbench.service.impl;

import com.xuexi.workbench.domain.ActivityRemark;
import com.xuexi.workbench.mapper.ActivityRemarkMapper;
import com.xuexi.workbench.service.ActivityRemarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class ActivityRemarkServiceImpl implements ActivityRemarkService {

    @Resource
    private ActivityRemarkMapper activityRemarkMapper;

    @Override
    public List<ActivityRemark> qureyActivityRemarkForDetailByActivityId(String Activityid) {
        return activityRemarkMapper.selectActivityRemarkForDetailByActivityId(Activityid);
    }

    @Override
    public Integer saveCreateActivityRemark(ActivityRemark activityRemark) {
        return activityRemarkMapper.insertActivityRemark(activityRemark);
    }

    @Override
    public Integer deleteActivityRemarkById(String id) {
        return activityRemarkMapper.deleteActivityRemarkById(id);
    }

    @Override
    public Integer saveEditActivityRemark(ActivityRemark activityRemark) {
        return activityRemarkMapper.updateActivityRemark(activityRemark);
    }
}
