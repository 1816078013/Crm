package com.xuexi.commons.domain;

import com.xuexi.workbench.domain.Activity;

import java.util.List;
import java.util.Objects;

public class a {
    private List<Activity> activityList;
    private Integer result;

    public a() {
    }

    public a(List<Activity> activityList, Integer result) {
        this.activityList = activityList;
        this.result = result;
    }

    public List<Activity> getActivityList() {
        return activityList;
    }

    public void setActivityList(List<Activity> activityList) {
        this.activityList = activityList;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        a a = (a) o;
        return Objects.equals(activityList, a.activityList) && Objects.equals(result, a.result);
    }

    @Override
    public int hashCode() {
        return Objects.hash(activityList, result);
    }

    @Override
    public String toString() {
        return "a{" +
                "activityList=" + activityList +
                ", result=" + result +
                '}';
    }


}
