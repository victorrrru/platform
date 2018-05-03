package com.fww.result;

import com.fww.utils.StringUtil;
import lombok.Data;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.poi.ss.formula.functions.T;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 范文武
 * @date 2018/04/27 15:20
 */
@Data
public class Result implements Serializable{
    private static final long serialVersionUID = 1L;
    public static final String FAIL_MSG = "操作失败";
    public static final String SUCCESS_MSG = "操作成功";
    public static final String MOCK_MSG = "当前服务无法正常响应请求";
    public static int CODE_ERROR = 500;
    public static int CODE_OK = 0;
    public static int CODE_MOCK = 503;
    private Object data;
    private String content;
    private String errorMsg;
    private LogResult logResult;
    private int code;
    private String msg;

    public <X> X getData() {
        return (X) this.data;
    }

    public Result() {
        this.code = CODE_OK;
        this.msg = "操作成功";
        this.logResult = new LogResult();
    }

    public Result(HttpServletRequest request, String teamName) {
        this.code = CODE_OK;
        this.msg = "操作成功";
        this.logResult = new LogResult(teamName, this.getRequstURL(request), getClientIp(request), this.getRequestParams(request));
    }

    public Result Mock() {
        this.code = CODE_MOCK;
        this.msg = "当前服务无法正常响应请求";
        this.data = null;
        return this;
    }

    public Result Mock(T data) {
        this.code = CODE_MOCK;
        this.msg = "当前服务无法正常响应请求";
        this.data = data;
        return this;
    }

    public Result NO(int code, String msg) {
        this.code = code;
        this.msg = msg;
        this.data = null;
        return this;
    }

    public Result NO(String msg) {
        this.msg = msg;
        this.code = CODE_ERROR;
        this.data = null;
        return this;
    }

    public Result Mock(String msg) {
        this.code = CODE_MOCK;
        this.msg = msg;
        this.data = null;
        return this;
    }

    public Result NO(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        return this;
    }

    public Result Mock(String msg, T data) {
        this.code = CODE_MOCK;
        this.msg = msg;
        this.data = data;
        return this;
    }

    public Result OK(T data) {
        this.code = CODE_OK;
        this.data = data;
        return this;
    }

    public Result OK(String msg, T data) {
        this.code = CODE_OK;
        this.msg = msg;
        this.data = data;
        return this;
    }

    public void beforePutApi(LogResultApi api) {
        this.logResult.beforePut(api);
    }

    public void throwingPutApi(LogResultApi api) {
        this.logResult.throwingPut(api);
    }

    public void afterPutApi(LogResultApi api) {
        this.logResult.afterPutApi(api);
    }

    public void log(String msgInfo) {
        if(StringUtils.isNotBlank(msgInfo)) {
            LogResultApi logApi = new LogResultApi(this.logResult.getUuid(), msgInfo, true);
            this.beforePutApi(logApi);
        }

    }

    public void exception(String errorMessage) {
        this.code = this.code == CODE_OK?CODE_ERROR:this.code;
        this.msg = this.msg.equals("操作成功")?"操作失败":this.msg;
        this.errorMsg = errorMessage;
        this.logResult.setLogType("error");
        if(StringUtils.isNotBlank(errorMessage)) {
            this.logResult.setContent(this.logResult.getContent() + "\r\n" + errorMessage);
        }

    }

    public String getStack(Throwable cause) {
        String message = cause.toString();
        StackTraceElement[] stes = cause.getStackTrace();
        if(ArrayUtils.isNotEmpty(stes)) {
            StackTraceElement stack = stes[0];
            message = message + "\r\n" + stack.getClassName() + "." + stack.getMethodName() + "(" + stack.getFileName() + ":" + stack.getLineNumber() + ")";
        }

        return message;
    }

    public String getStackTrace(Throwable cause) {
        String stackTrace = cause.toString();
        StackTraceElement[] stes = cause.getStackTrace();
        StackTraceElement[] var4 = stes;
        int var5 = stes.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            StackTraceElement stack = var4[var6];
            stackTrace = stackTrace + "\r\n" + stack.getClassName() + "." + stack.getMethodName() + "(" + stack.getFileName() + ":" + stack.getLineNumber() + ")";
        }

        if(cause.getCause() != null) {
            stackTrace = this.getStackTrace(cause.getCause());
        }

        return stackTrace;
    }

    public String getRequstURL(HttpServletRequest request) {
        if(request == null) {
            return "";
        } else {
            String path = request.getRequestURI();
            String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
            return basePath;
        }
    }

    public String getRequestParams(HttpServletRequest request) {
        if(request == null) {
            return "";
        } else if(StringUtils.contains(StringUtils.trimToEmpty(this.getRequstURL(request)).toLowerCase(), "password")) {
            return "[密码相关请求不记录参数]";
        } else {
            Map<String, String> map = new HashMap<>();
            Enumeration<String> paramNames = request.getParameterNames();

            while(paramNames.hasMoreElements()) {
                String paramName = paramNames.nextElement();
                String[] paramValues = request.getParameterValues(paramName);
                if(paramValues.length == 1) {
                    String paramValue = StringUtil.cleanSpecialChar(paramValues[0]);
                    if(StringUtils.isNotBlank(paramValue)) {
                        map.put(paramName, paramValue);
                    }
                }
            }

            return map.toString();
        }
    }

    public static String getClientIp(HttpServletRequest request) {
        if(request == null) {
            return "";
        } else {
            String ip = "";

            try {
                ip = request.getHeader("x-forwarded-for");
                System.setProperty("java.net.preferIPv4Stack", "true");
                if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                    ip = request.getHeader("Proxy-Client-IP");
                }

                if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                    ip = request.getHeader("WL-Proxy-Client-IP");
                }

                if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                    ip = request.getRemoteAddr();
                    if(ip.equals("127.0.0.1") || ip.equals("0:0:0:0:0:0:0:1")) {
                        InetAddress inet = null;

                        try {
                            inet = InetAddress.getLocalHost();
                        } catch (UnknownHostException var4) {
                            var4.printStackTrace();
                        }

                        ip = inet.getHostAddress();
                    }
                }

                if(ip != null && ip.length() > 15 && ip.indexOf(",") > 0) {
                    ip = ip.substring(0, ip.indexOf(","));
                }
            } catch (Exception var5) {
                ;
            }

            return ip;
        }
    }


    public String getContent() {
        this.logResult.colse(this.code, this.msg, this.errorMsg);
        this.content = this.logResult.getContent();
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ReturnDTO DTO() {
        return new ReturnDTO(this.getCode(), this.getMsg(), this.getData());
    }

    public void setDTO(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public LogResult getLogResult() {
        return this.logResult;
    }

    public void setLogResult(LogResult logResult) {
        this.logResult = logResult;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
