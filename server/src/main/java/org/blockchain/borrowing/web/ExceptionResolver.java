package org.blockchain.borrowing.web;

import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * 异常拦截
 *
 * Created by pengchangguo on 16/1/9.
 */

@Component
public class ExceptionResolver extends SimpleMappingExceptionResolver {

    private static Logger logger = Logger.getLogger(ExceptionResolver.class);

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        logger.error(String.format("执行错误 %s : %s", request.getRequestURL(), new Gson().toJson(request.getParameterMap())), ex);
        HandlerMethod mathod = (HandlerMethod) handler;
        ResponseBody body = mathod.getMethodAnnotation(ResponseBody.class);
        if (body == null) {
            return super.doResolveException(request, response, handler, ex);
        }
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Cache-Control","no-cache, must-revalidate");
        try {
            response.getWriter().write(
                    "{\"error\":\"" + ex.getMessage() + "\"}");
            return new ModelAndView();
        } catch (IOException e) {
            logger.error("response error!", e);
            throw new RuntimeException(e);
        }
    }
}
