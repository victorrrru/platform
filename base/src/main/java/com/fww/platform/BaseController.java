package com.fww.platform;

import com.fww.result.Result;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

/**
 * @author 范文武
 * @date 2018/04/27 09:46
 */
//@Scope("prototype")
public abstract class BaseController {
    protected Logger logger = Logger.getLogger(this.getClass());
    protected HttpServletRequest request = null;
    protected HttpServletResponse response = null;
    protected OutputStream out = null;

    public BaseController() {
    }

    @ModelAttribute
    public void setReqAndRes(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    public Result error(Result result, Throwable e) {
        if(e instanceof FunctionException) {
            result = ((FunctionException)e).getResult();
        } else if(e instanceof AopException) {
            result = ((AopException)e).getResult();
        } else if(e instanceof RuntimeException) {
            Throwable re = e.getCause();
            if(re != null && re instanceof AopException) {
                result = ((AopException)re).getResult();
            } else {
                result.exception(result.getStackTrace(e));
            }
        } else {
            result.exception(result.getStackTrace(e));
        }

        return result;
    }

    public Result getResult() {return new Result();};

    public void send(Result result) {
        this.logger.info(result.getContent());
    }

    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }

    public void setServletResponse(HttpServletResponse response) {
        this.response = response;
    }

    protected HttpSession getSession() {
        return this.request.getSession();
    }

    protected void redirect(String url) throws IOException {
        this.response.sendRedirect(url);
    }

    protected void forward(String url) throws ServletException, IOException {
        this.request.getRequestDispatcher(url).forward(this.request, this.response);
    }

    protected ServletOutputStream getServletOutputStream() throws IOException {
        return this.response.getOutputStream();
    }

    protected Writer getWriter() throws IOException {
        this.response.setContentType("text/html;charset=UTF-8");
        return this.response.getWriter();
    }

    protected void getWriter(String str) throws IOException {
        this.response.setContentType("text/html;charset=UTF-8");
        this.response.getWriter().write(str);
    }

    public void forwardFromRequest() throws ServletException, IOException {
        String url = this.request.getParameter("url");
        this.request.getRequestDispatcher(url).forward(this.request, this.response);
    }

    protected void setAttribute(String key, Object value) {
        this.request.setAttribute(key, value);
    }
}
