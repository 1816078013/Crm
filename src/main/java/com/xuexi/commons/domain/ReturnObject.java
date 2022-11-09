package com.xuexi.commons.domain;

import java.util.Objects;

public class ReturnObject {
       private Enum code ; //处理成功或者失败的标记    1--成功     0--失败
      private String message;         //失败时的报错信息
      private  Object retDate;

    public ReturnObject() {
    }

   /* public ReturnObject(Integer code, String message, Object retDate) {
        this.code = code;
        this.message = message;
        this.retDate = retDate;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getRetDate() {
        return retDate;
    }

    public void setRetDate(Object retDate) {
        this.retDate = retDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReturnObject that = (ReturnObject) o;
        return Objects.equals(code, that.code) && Objects.equals(message, that.message) && Objects.equals(retDate, that.retDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, message, retDate);
    }

    @Override
    public String toString() {
        return "ReturnObject{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", retDate=" + retDate +
                '}';
    }*/

    public ReturnObject(Enum code, String message, Object retDate) {
        this.code = code;
        this.message = message;
        this.retDate = retDate;
    }

    public Enum getCode() {
        return code;
    }

    public void setCode(Enum code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getRetDate() {
        return retDate;
    }

    public void setRetDate(Object retDate) {
        this.retDate = retDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReturnObject that = (ReturnObject) o;
        return Objects.equals(code, that.code) && Objects.equals(message, that.message) && Objects.equals(retDate, that.retDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, message, retDate);
    }

    @Override
    public String toString() {
        return "ReturnObject{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", retDate=" + retDate +
                '}';
    }
}
