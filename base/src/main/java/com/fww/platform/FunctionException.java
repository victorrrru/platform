package com.fww.platform;

import com.fww.result.Result;
import org.apache.commons.lang.StringUtils;

/**
 * @author 范文武
 * @date 2018/04/27 15:49
 */
public class FunctionException extends Exception{
    private static final long serialVersionUID = 1L;
    public Result result;

    public Result getResult() {
        return this.result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public FunctionException(Result result, String message) {
        super(message);
        this.result = result;
        if(result != null && StringUtils.isNotBlank(message)) {
            this.result.setCode(Result.CODE_ERROR);
            this.result.setMsg(message);
        }

    }

    public FunctionException(Result result, Throwable e) {
        super(result.getStack(e), e);
        this.result = result;
    }
}
