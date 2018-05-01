package com.fww;

/**
 * @author Administrator
 * @date 2018/04/29 18:22
 */
public class BaseResponseCode {
    public BaseResponseCode() {
    }

    public static enum BaseResponseCodeEnum {
        COMM_SUCCESS(0, "操作成功"),
        COMM_FAIL(1, "操作失败"),
        COMM_LOGIN_FAIL(2, "用户名或密码错误"),
        COMM_TOKEN_VALID_FAIL(3, "无效token或token已过期"),
        COMM_NO_AUTH(4, "无访问权限"),
        COMM_SMS_SEND_FAIL(5, "短信发送异常");

        private int code;
        private String msg;

        private BaseResponseCodeEnum(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public int getCode() {
            return this.code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return this.msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }
}
