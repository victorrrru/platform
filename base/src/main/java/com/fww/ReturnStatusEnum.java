package com.fww;

import java.io.Serializable;

/**
 * @author 范文武
 * @date 2018/04/27 15:25
 */
public enum ReturnStatusEnum implements Serializable {
    SUCCESS(200, "成功"),
    OK(200, "成功"),
    Error(500, "异常"),
    StockNotEnough(301, "业务检查异常：库存不足"),
    Continue(100, "继续"),
    SwitchingProtocols(101, "切换协议"),
    Created(201, "已创建"),
    BadRequest(400, "错误请求"),
    Unauthorized(401, "未授权"),
    Forbidden(403, "服务器拒绝请求"),
    FileNotFound(404, "未找到"),
    MethodNotAllowed(405, "禁用的方法");

    private int value;
    private String info;

    private ReturnStatusEnum(int status, String info) {
        this.setValue(status);
        this.setInfo(info);
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getInfo() {
        return this.info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
