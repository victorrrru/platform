package com.fww.platform;

import com.fww.result.Result;

/**
 * @author 范文武
 * @date 2018/04/27 15:50
 */
public class AopException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    public Result result;

    public Result getResult() {
        return this.result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public AopException(String message) {
        super(message);
    }

    public AopException(Result result, Throwable e) {
        super(result.getStack(e), e);
        this.result = result;
    }
}
