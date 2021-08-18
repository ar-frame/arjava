package com.ar.core;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.ar.comp.rpc.Service.response;

public class ServiceException extends Exception {
    private String message;

    //构造函数
    public ServiceException(String message){
        super(message);
        this.message = message;

        response(message);

    }

}
