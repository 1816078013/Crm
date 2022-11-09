package com.xuexi.workbench.service;

import com.xuexi.workbench.domain.Activity;

import java.util.List;
import java.util.Map;

public interface ActivityService {
    int saveCreateActivity(Activity activity);

    List<Activity> queryActivityByConditionForPage(Map<String,Object> map);

    int queryCountOfActivityByCondition(Map<String,Object> map);

    Integer deleteActivityByIds(String[] ids);

    Activity queryActivityById(String id);

    Integer updateActivityById(Map<String,Object> map);

    List<Activity> queryAllActivitys();

    Integer saveCreateByList(List<Activity> activityList);

    Activity queryActivityForDetailById(String id);

    List<Activity> queryActivityForDetailByClueId(String id);

    List<Activity> queryActivityForDetailByNameClueId(Map<String,Object> map);

    List<Activity> queryActivityForDetailByIds(String[] ids);
}
