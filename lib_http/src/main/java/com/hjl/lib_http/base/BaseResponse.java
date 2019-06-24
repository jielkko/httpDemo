package com.hjl.lib_http.base;

/**
 * 作者：senon on 2017/12/28 11:19
 * 邮箱：a1083911695@163.com
 *
 * 返回的数据结构：
 * {
 "data": [],
 "errorCode": 0,
 "errorMsg": ""
 }
 */

public class BaseResponse<T> {

    public String version;
    public ResultStatus status;
    private T data;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public ResultStatus getStatus() {
        return status;
    }

    public void setStatus(ResultStatus status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
