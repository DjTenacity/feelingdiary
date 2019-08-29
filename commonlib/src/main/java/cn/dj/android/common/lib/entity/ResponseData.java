package cn.dj.android.common.lib.entity;

import java.io.Serializable;

/**
 * 响应
 *
 * @param <T> the type parameter
 * @author : gaodianjie / gaodianjie
 * @version : 1.0
 * @date : 2016-06-23
 */
public class ResponseData<T> implements Serializable {

    /**
     * code = 0,表示调用成功. data 每个接口的返回的结构可能不一样。
     * code > 0,表示错误.每个接口都会定义好可能返回的值.
     * 错误码	含义
     * 101	AUTH/APP-ID/CLIENT-ID/VERSION 任一个header参数不能为空
     * 102	AUTH 参数错误
     * 103	AUTH 过期,请重新登录
     * 104	没有权限访问
     * 110	已有关联数据,不能删除，是否禁用
     * 121	参数错误
     * 122	数据不存在
     */
    private Integer code;

    /**
     * 返回的消息
     */
    private String msg;

    /**
     * 返回的数据
     */
    private T data;


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess(){
        if(code.intValue() == 0){
            return true;
        }
        return false;
    }
}
