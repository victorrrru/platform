package com.fww.result;

import lombok.Data;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * @author 范文武
 * @date 2018/04/27 15:21
 */
@Data
public class ReturnDTO implements Serializable{
    private static final long serialVersionUID = 1L;
    protected int code;
    protected String msg = null;
    protected Object data;

    public ReturnDTO() {
    }

    public ReturnDTO(int code) {
        this.code = code;
    }

    public ReturnDTO(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static ReturnDTO OK() {
        ReturnDTO dto = new ReturnDTO(ReturnStatusEnum.SUCCESS.getValue());
        return dto;
    }

    public static ReturnDTO OK(Object data) {
        ReturnDTO dto = new ReturnDTO(ReturnStatusEnum.SUCCESS.getValue());
        dto.setData(data);
        return dto;
    }

    public static ReturnDTO OK(Object data, String msg) {
        ReturnDTO dto = new ReturnDTO(ReturnStatusEnum.SUCCESS.getValue());
        dto.setMsg(msg);
        dto.setData(data);
        return dto;
    }

    public static ReturnDTO NO(int code, String msg) {
        ReturnDTO dto = new ReturnDTO(code);
        dto.setMsg(msg);
        return dto;
    }

    public void success(Object data) {
        this.code = ReturnStatusEnum.SUCCESS.getValue();
        this.data = data;
    }

    public void success(String msg, Object data) {
        this.code = ReturnStatusEnum.SUCCESS.getValue();
        this.msg = msg;
        this.data = data;
    }

    public void error(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
