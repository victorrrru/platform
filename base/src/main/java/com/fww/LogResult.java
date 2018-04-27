package com.fww;

import lombok.Data;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @author 范文武
 * @date 2018/04/27 15:30
 */
@Data
public class LogResult implements Serializable {
    private static final long serialVersionUID = 1L;
    public static String TEMPLATE_WEB = "\r\n@0@_第1步:来源=@1@,请求地址=@2@,开始时间=@3@,共@4@步,日志类型=@5@,共耗时=@6@";
    private String uuid = UUIDUtil.getUUID();
    private String teamName;
    private String url;
    private String method;
    private String ip;
    private String msg;
    private int status;
    private String logType = "info";
    private int count;
    private String content = "";
    private long startTimes = System.currentTimeMillis();
    private long longtimes = 0L;
    private String params;
    private Date startDate;
    private Date endDate;
    private int code;
    private List<LogResultApi> apiList = new ArrayList<>();

    LogResult() {
        this.startDate = new Date();
    }

    LogResult(String teamName, String url, String ip, String params) {
        this.teamName = teamName;
        this.url = url;
        if(url.contains(".do")) {
            this.method = url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf("."));
        } else if(url.contains("?")) {
            this.method = url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf("?"));
        } else {
            this.method = url.substring(url.lastIndexOf("/") + 1, url.length());
        }

        this.ip = ip;
        this.count = 1;
        this.startDate = new Date();
        this.params = params;
        this.content = TEMPLATE_WEB.replace("@0@", this.getUuid()).replace("@1@", StringUtils.trimToEmpty(teamName)).replace("@2@", "IP:" + ip + "," + StringUtils.trimToEmpty(url)).replace("@3@", DateUtil.date2String(this.startDate));
        this.content = this.content + ", 请求参数：" + params;
    }

    public void beforePut(LogResultApi api) {
        if(!api.isCustom()) {
            ++this.count;
        }

        api.setCount(this.count);
        this.apiList.add(api);
    }

    public void throwingPut(LogResultApi api) {
        try {
            Iterator var3 = this.apiList.iterator();

            while(var3.hasNext()) {
                LogResultApi logResultApi = (LogResultApi)var3.next();
                if(logResultApi.getPath().equals(api.getPath())) {
                    logResultApi.setStatus(api.getStatus());
                    logResultApi.setMsg(api.getMsg());
                    logResultApi.setErrorMsg(api.getErrorMsg());
                    logResultApi.setLogType(api.getLogType());
                    logResultApi.setLongtimes(api.getEndDate().getTime() - logResultApi.getStartDate().getTime());
                    break;
                }
            }
        } catch (Exception var4) {
            ;
        }

    }

    public void afterPutApi(LogResultApi api) {
        try {
            Iterator var3 = this.apiList.iterator();

            while(var3.hasNext()) {
                LogResultApi logResultApi = (LogResultApi)var3.next();
                if(logResultApi.getPath().equals(api.getPath())) {
                    logResultApi.setStatus(api.getStatus());
                    logResultApi.setMsg(api.getMsg());
                    logResultApi.setEndDate(api.getEndDate());
                    long logtimes = api.getEndDate().getTime() - logResultApi.getStartDate().getTime();
                    logResultApi.setLongtimes(logtimes < 1L?1L:logtimes);
                    break;
                }
            }
        } catch (Exception var6) {
            ;
        }

    }

    public void colse(int status, String msg, String errorMsg) {
        try {
            long colseTime = System.currentTimeMillis() - this.startTimes;
            this.status = status;
            this.msg = msg;

            LogResultApi logResultApi;
            Iterator var7;
            for(var7 = this.apiList.iterator(); var7.hasNext(); this.longtimes += logResultApi.getLongtimes()) {
                logResultApi = (LogResultApi)var7.next();
                if("warn".equalsIgnoreCase(logResultApi.getLogType())) {
                    this.logType = logResultApi.getLogType();
                } else if("error".equalsIgnoreCase(logResultApi.getLogType()) || logResultApi.getStatus() != Result.CODE_OK) {
                    this.logType = "error";
                }
            }

            if(this.longtimes < colseTime) {
                this.longtimes = colseTime;
            }

            for(int i = 0; i < this.apiList.size(); ++i) {
                LogResultApi logApi = this.apiList.get(i);

                for(int j = i + 1; j < this.apiList.size(); ++j) {
                    if(StringUtils.trimToEmpty(logApi.getErrorMsg()).length() == StringUtils.trimToEmpty(((LogResultApi)this.apiList.get(j)).getErrorMsg()).length()) {
                        logApi.setErrorMsg(null);
                    }
                }
            }

            for(var7 = this.apiList.iterator(); var7.hasNext(); this.content = this.content + logResultApi.getContent()) {
                logResultApi = (LogResultApi)var7.next();
            }

            if(StringUtils.isNotBlank(this.url)) {
                this.content = this.content.replace("@4@", ObjectUtils.toString(this.count)).replace("@5@", this.logType).replace("@6@", ObjectUtils.toString(Long.valueOf(this.longtimes)));
            }

            this.endDate = new Date();
        } catch (Exception var9) {
            ;
        }

    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
