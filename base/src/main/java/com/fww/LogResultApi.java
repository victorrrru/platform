package com.fww;

import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.Date;

/**
 * @author 范文武
 * @date 2018/04/27 15:32
 */
@Data
public class LogResultApi {
    private static final long serialVersionUID = 1L;
    private String uuid;
    private String method;
    private String api;
    private String path;
    private String params;
    private int count;
    private String msg;
    private int status;
    private String logType;
    private long longtimes = 0L;
    private Date startDate;
    private Date endDate;
    private String errorMsg;
    private boolean isCustom = false;

    public LogResultApi(String uuid, String method, String api, String path, String params, int count, String msg, int status, String logType, Date startDate) {
        this.uuid = uuid;
        this.method = method;
        this.api = api;
        this.path = path;
        this.params = params;
        this.count = count;
        this.msg = msg;
        this.status = status;
        this.logType = logType;
        this.startDate = startDate;
    }

    public LogResultApi(String uuid, String path, String msg, int status, String logType, Date endDate, String errorMsg) {
        this.uuid = uuid;
        this.path = path;
        this.msg = msg;
        this.status = status;
        this.logType = logType;
        this.endDate = endDate;
        this.errorMsg = errorMsg;
    }

    public LogResultApi(String uuid, String path, String msg, int status, Date endDate) {
        this.uuid = uuid;
        this.path = path;
        this.msg = msg;
        this.status = status;
        this.endDate = endDate;
        if(status != Result.CODE_OK) {
            this.logType = "warn";
        }

    }

    public LogResultApi(String uuid, String msg, boolean isCustom) {
        this.uuid = uuid;
        this.msg = msg;
        this.isCustom = isCustom;
        if(this.status != Result.CODE_OK) {
            this.logType = "warn";
        }

    }

    public String getContent() {
        String content = "";
        if(this.isCustom) {
            content = "\r\n" + this.uuid + "_custom:,message=" + this.msg;
        } else {
            content = "\r\n" + this.uuid + "_第" + this.count + "步:接口方法=" + this.path + ",status=" + this.status + ",message=" + this.msg + ",日志类型=" + this.logType + ",耗时=" + this.longtimes;
            if(StringUtils.isNotBlank(this.params)) {
                content = content + ", 参数：" + this.params;
            }
        }

        if(StringUtils.isNotBlank(this.errorMsg)) {
            content = content + "\r\n" + this.uuid + "_错误信息：" + this.errorMsg;
        }

        return content;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public boolean isCustom() {
        return this.isCustom;
    }

    public void setCustom(boolean isCustom) {
        this.isCustom = isCustom;
    }
}
